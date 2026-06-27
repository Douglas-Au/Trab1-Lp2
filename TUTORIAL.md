# SGE — Tutorial de Testes

Guia prático para subir o **Sistema de Gestão Educacional** (API REST Spring Boot) e
testar os principais fluxos de ponta a ponta.

---

## 1. Pré-requisitos

- **JDK 21**
- `curl` (ou Postman / Insomnia) para chamar a API
- `python3` apenas se você quiser usar o script de smoke test no fim deste guia

Não é preciso instalar Maven — o projeto traz o wrapper (`./mvnw`).

---

## 2. Subindo a aplicação

```bash
# rodar a aplicação (porta 8080)
./mvnw spring-boot:run

# rodar os testes
./mvnw test

# empacotar (gera target/sge-0.0.1-SNAPSHOT.jar)
./mvnw clean package -DskipTests
```

Quando aparecer `Started SgeApplication ... on port 8080`, a API está no ar em
`http://localhost:8080`.

### Banco de dados (H2)

O banco é o **H2 em arquivo** (`jdbc:h2:file:./db/testedb`). O esquema é criado/atualizado
automaticamente pelo Hibernate (`ddl-auto=update`) — não há migrations.

Console web do H2: <http://localhost:8080/h2-console>
- **JDBC URL:** `jdbc:h2:file:./db/testedb`
- **User:** `sa`  •  **Senha:** *(em branco)*

---

## 3. Conceitos importantes antes de testar

### 3.1 Autenticação JWT

Quase todas as ações de escrita exigem um **token JWT** no cabeçalho:

```
Authorization: Bearer <token>
```

O token é obtido no login e carrega o **papel** (`papel`) do usuário, usado para autorizar
cada ação.

### 3.2 Modelo de composição (Usuário + Perfil)

`Usuario` é a entidade base com um campo `papel`
(`DISCENTE`, `DOCENTE`, `DISCENTE_DIRETOR`, `COORD_UCE`, `COORD_CURSO`, `ADMIN`).
Para um usuário **agir** como discente ou docente, é preciso criar também o **perfil**
correspondente, vinculado ao usuário:

| Papel              | Perfil a criar depois | Endpoint        |
|--------------------|-----------------------|-----------------|
| `DISCENTE`         | `Discente`            | `POST /discentes` |
| `DISCENTE_DIRETOR` | `Discente`            | `POST /discentes` |
| `DOCENTE`          | `Docente`             | `POST /docentes`  |
| `COORD_UCE`        | `CoordenadorUCE`      | `POST /coordenadores` |

> ⚠️ Só criar o `Usuario` **não basta** para inscrever-se ou aprovar oportunidades — o
> perfil é obrigatório.

---

## 4. Passo a passo manual (curl)

### 4.1 Criar usuários

```bash
curl -X POST http://localhost:8080/usuarios -H 'Content-Type: application/json' \
  -d '{"nome":"Diretor","email":"dir@x.com","senha":"123","ativo":true,"papel":"DISCENTE_DIRETOR"}'

curl -X POST http://localhost:8080/usuarios -H 'Content-Type: application/json' \
  -d '{"nome":"Professor","email":"prof@x.com","senha":"123","ativo":true,"papel":"DOCENTE"}'

curl -X POST http://localhost:8080/usuarios -H 'Content-Type: application/json' \
  -d '{"nome":"Aluno","email":"alu@x.com","senha":"123","ativo":true,"papel":"DISCENTE"}'
```

### 4.2 Login (obter token)

```bash
curl -X POST http://localhost:8080/auth/login -H 'Content-Type: application/json' \
  -d '{"email":"dir@x.com","senha":"123"}'
# -> {"token":"eyJhbGciOi..."}
```

Guarde o `token` de cada usuário.

### 4.3 Criar os perfis (e um curso)

```bash
# curso (para vincular o discente)
curl -X POST http://localhost:8080/cursos -H 'Content-Type: application/json' \
  -d '{"nome":"Ciência da Computação","codigo":"CC01"}'

# perfil docente (usuario.id = id do professor)
curl -X POST http://localhost:8080/docentes -H 'Content-Type: application/json' \
  -d '{"usuario":{"id":2},"siape":"1234","departamento":"Computação"}'

# perfil discente (usuario.id = id do aluno, curso.id = id do curso)
curl -X POST http://localhost:8080/discentes -H 'Content-Type: application/json' \
  -d '{"usuario":{"id":3},"curso":{"id":1},"matricula":"2024001","semestreAtual":1}'
```

### 4.4 Fluxo Oportunidade (máquina de estados)

```bash
DIR="Bearer <token-do-diretor>"
PROF="Bearer <token-do-professor>"

# diretor cria -> AGUARDANDO_APROVACAO
curl -X POST http://localhost:8080/oportunidades -H 'Content-Type: application/json' -H "Authorization: $DIR" \
  -d '{"titulo":"Curso de Extensão","tipo":"CURSO","modalidade":"REMOTO","cargaHoraria":40,"vagas":1}'

# docente aprova -> APROVADA
curl -X PATCH http://localhost:8080/oportunidades/1/aprovar -H "Authorization: $PROF"

# abrir inscrições -> ABERTA
curl -X PATCH http://localhost:8080/oportunidades/1/abrir-inscricoes -H "Authorization: $PROF"
```

Transições disponíveis: `aprovar`, `reprovar?motivo=...`, `reenviar` (corrigir reprovada),
`abrir-inscricoes`, `encerrar`, `cancelar`.

### 4.5 Fluxo Inscrição

```bash
ALU="Bearer <token-do-aluno>"

# aluno se inscreve -> PENDENTE (valida janela de inscrições e vagas)
curl -X POST "http://localhost:8080/inscricoes?oportunidadeId=1" -H "Authorization: $ALU"

# docente/diretor aprova -> APROVADA
curl -X PATCH http://localhost:8080/inscricoes/1/aprovar -H "Authorization: $PROF"
```

Outras ações: `rejeitar`, `cancelar` (só o próprio dono cancela).

### 4.6 Emitir e validar certificado

```bash
# encerra a oportunidade
curl -X PATCH http://localhost:8080/oportunidades/1/encerrar -H "Authorization: $PROF"

# emite certificado (origem: oportunidade ENCERRADA ou aproveitamento APROVADO)
curl -X POST http://localhost:8080/certificados -H 'Content-Type: application/json' -H "Authorization: $PROF" \
  -d '{"discente":{"id":1},"oportunidade":{"id":1},"horas":40,"motivacao":"Conclusão do curso"}'
# -> retorna o certificado com "uuidHash"

# VALIDAÇÃO PÚBLICA — sem token!
curl http://localhost:8080/certificados/validar/<uuidHash>
```

### 4.7 Fluxo Aproveitamento de Horas Externas

```bash
# discente solicita -> PENDENTE  (certificadoPath é obrigatório)
curl -X POST http://localhost:8080/aproveitamentos -H 'Content-Type: application/json' -H "Authorization: $ALU" \
  -d '{"descricao":"Curso online","instituicao":"Coursera","horas":20,"certificadoPath":"/docs/cert.pdf"}'

# coordenador (COORD_UCE) analisa e defere/indefere
curl -X PATCH http://localhost:8080/aproveitamentos/1/iniciar-analise -H "Authorization: $COORD"
curl -X PATCH http://localhost:8080/aproveitamentos/1/deferir       -H "Authorization: $COORD"
# indeferir abre prazo de 5 dias para reenvio:
curl -X PATCH "http://localhost:8080/aproveitamentos/1/indeferir?motivoRejeicao=Documento ilegível" -H "Authorization: $COORD"
```

---

## 5. Smoke test automático (copiar e colar)

Com a aplicação rodando, este script cria tudo do zero e valida o fluxo completo:

```bash
B=http://localhost:8080
TS=$(date +%s)
hdr='-H Content-Type:application/json'
jget(){ python3 -c "import sys,json; print(json.load(sys.stdin)$1)"; }
tok(){ curl -s -X POST $B/auth/login $hdr -d "{\"email\":\"$1\",\"senha\":\"123\"}" | jget "['token']"; }

# usuários
IDPROF=$(curl -s -X POST $B/usuarios $hdr -d "{\"nome\":\"Prof\",\"email\":\"prof$TS@x.com\",\"senha\":\"123\",\"ativo\":true,\"papel\":\"DOCENTE\"}" | jget "['id']")
IDALU=$(curl -s -X POST $B/usuarios $hdr -d "{\"nome\":\"Alu\",\"email\":\"alu$TS@x.com\",\"senha\":\"123\",\"ativo\":true,\"papel\":\"DISCENTE\"}" | jget "['id']")
IDDIR=$(curl -s -X POST $B/usuarios $hdr -d "{\"nome\":\"Dir\",\"email\":\"dir$TS@x.com\",\"senha\":\"123\",\"ativo\":true,\"papel\":\"DISCENTE_DIRETOR\"}" | jget "['id']")

# curso + perfis
IDCUR=$(curl -s -X POST $B/cursos $hdr -d "{\"nome\":\"CC\",\"codigo\":\"CC$TS\"}" | jget "['id']")
curl -s -X POST $B/docentes  $hdr -d "{\"usuario\":{\"id\":$IDPROF},\"siape\":\"S$TS\",\"departamento\":\"DC\"}" >/dev/null
IDDISC=$(curl -s -X POST $B/discentes $hdr -d "{\"usuario\":{\"id\":$IDALU},\"curso\":{\"id\":$IDCUR},\"matricula\":\"M$TS\",\"semestreAtual\":1}" | jget "['id']")

# tokens
TDIR=$(tok "dir$TS@x.com"); TPROF=$(tok "prof$TS@x.com"); TALU=$(tok "alu$TS@x.com")

# oportunidade: criar -> aprovar -> abrir
OPID=$(curl -s -X POST $B/oportunidades $hdr -H "Authorization: Bearer $TDIR" \
  -d '{"titulo":"Curso X","tipo":"CURSO","modalidade":"REMOTO","cargaHoraria":40,"vagas":1}' | jget "['id']")
curl -s -X PATCH "$B/oportunidades/$OPID/aprovar"          -H "Authorization: Bearer $TPROF" | jget "['status']"
curl -s -X PATCH "$B/oportunidades/$OPID/abrir-inscricoes" -H "Authorization: Bearer $TPROF" | jget "['status']"

# inscrição
INSID=$(curl -s -X POST "$B/inscricoes?oportunidadeId=$OPID" -H "Authorization: Bearer $TALU" | jget "['id']")
curl -s -X PATCH "$B/inscricoes/$INSID/aprovar" -H "Authorization: Bearer $TPROF" | jget "['status']"

# certificado + validação pública
curl -s -X PATCH "$B/oportunidades/$OPID/encerrar" -H "Authorization: Bearer $TPROF" >/dev/null
HASH=$(curl -s -X POST $B/certificados $hdr -H "Authorization: Bearer $TPROF" \
  -d "{\"discente\":{\"id\":$IDDISC},\"oportunidade\":{\"id\":$OPID},\"horas\":40,\"motivacao\":\"conclusao\"}" | jget "['uuidHash']")
echo "Validação pública -> HTTP $(curl -s -o /dev/null -w '%{http_code}' $B/certificados/validar/$HASH)"
```

Resultado esperado: `APROVADA`, `ABERTA`, `APROVADA` e, no fim, `Validação pública -> HTTP 200`.

---

## 6. Mapa de endpoints

| Recurso | Base path | Observações |
|---------|-----------|-------------|
| Autenticação | `POST /auth/login` | Retorna o JWT |
| Usuários | `/usuarios` | CRUD + `PATCH /{id}/desativar` |
| Discentes | `/discentes` | CRUD + busca por matrícula |
| Docentes | `/docentes` | CRUD + busca por siape/departamento |
| Coordenadores | `/coordenadores` | CRUD |
| Cursos | `/cursos` | CRUD + busca por código |
| PPCs | `/ppcs` | CRUD; filtro por curso |
| UCEs | `/uces` | CRUD; filtro por PPC |
| Grupos | `/grupos` | CRUD + `GET/POST /{id}/membros` |
| Discentes-Diretores | `/discentes-diretores` | CRUD |
| Oportunidades | `/oportunidades` | Máquina de estados (aprovar/abrir/encerrar...) |
| Inscrições | `/inscricoes` | Inscrever/aprovar/rejeitar/cancelar |
| Planos de Atividade | `/planos-atividade` | CRUD vinculado à oportunidade |
| Aproveitamentos | `/aproveitamentos` | Máquina de estados + histórico |
| Certificados | `/certificados` | Emissão + **`GET /validar/{uuidHash}` público** |
| Notificações | `/notificacoes` | CRUD + `PATCH /{id}/lida` |
| Comunicados | `/comunicados` | CRUD |
| Auditorias | `/auditorias` | Somente leitura |

---

## 7. Dicas de resolução de problemas

- **400 com mensagem de permissão** → o token não tem o papel exigido pela ação.
- **"Usuário autenticado não possui cadastro de Discente/Docente"** → faltou criar o perfil
  (seção 3.2).
- **"Não há vagas disponíveis"** → o número de inscrições `APROVADA` atingiu `vagas`.
- **Quero zerar o banco** → pare a aplicação e apague os arquivos `db/testedb.*`.
