Esquema do Projeto:

O projeto é dividido em três pacotes mais uma classe Main parar testes.

# 1.0 Estrutura:
A estrutura segue como referência os diagramas presentes na pasta compartilhada do projeto, com algumas poucas modificações por enquanto. Importante checar os diagramas enquanto tenta entender a estrutura do projeto
    
## 1.1 Diagrama de classes
Referência:
<img width="1491" height="1346" alt="01 - Diagrama de classes drawio" src="https://github.com/user-attachments/assets/b4ea08c5-cd35-4e50-bfc7-496a25fa7cb7" />

 

## 1.1 Pacotes e suas funcionalidades no Projeto
 
 - Entidades: grupo de classes que fazem parte do contexto da aplicação de forma mais sólida, pense como conjunto de objetos que aglomerados formam um sistema, por isso temos uma classe pra Usuário, Discente ou Docente, mas não uma classe que guarda todos os Usuários, Discentes ou Docentes -isso já seria uma forma de Serviço, embora seja argumentável.
 
 - Services: serviços que gerem o sistema é agrupam os objetos de entidae, por exemplo apesar de podermos fazer um Usuario através do construtor de Usuario, ele só poderá ser Registrado no sistema através de um serviço de cadastro, presente na classe UsuarioService, além do mais serviços podem manipular objetos de classes diferentes, como por exemplo o discente quer se inscrever em uma oportunidade, Discente é uma entidade e Oportunidade e Inscricao também, atráves de uma Serviço unico como InscricaoService que o Discente pode de ser inscrito em uma oportunidade e se de fato for aprovado ele pode ser então registrado no alglomerado de Discentes que dizem respeito à essa oportunidade
 
 - Enums: Pacote de classes Enums, objetos simples que quardam Status como aprovado rejeitado, em espera, guardam também papeis e funçoes, por exemplo um Usuario pode ter o papel especico no sistema como Discente, Docente, Representande, Administrador, etc...
 
## 1.2 Estrutura do projeto

├── src
│   ├── Entidades
│   │   ├── Aproveitamento.java
│   │   ├── Certificado.java
│   │   ├── Curso.java
│   │   ├── DiscenteDiretor.java
│   │   ├── Discente.java
│   │   ├── Docente.java
│   │   ├── Grupo.java
│   │   ├── Inscricao.java
│   │   ├── Oportunidade.java
│   │   ├── PPC_Historico.java
│   │   └── Usuario.java
│   ├── Enums
│   │   ├── Modalidade.java
│   │   ├── Papel.java
│   │   ├── statusAproveitamento.java
│   │   ├── statusAssinatura.java
│   │   ├── statusGrupo.java
│   │   ├── statusInscricao.java
│   │   ├── statusOportunidade.java
│   │   └── TipoOportunidade.java
│   ├── Main.java
│   └── Services
│       ├── AproveitamentoService.java
│       ├── CertificadoService.java
│       ├── CursoService.java
│       ├── GrupoService.java
│       ├── InscricaoService.java
│       ├── OpotunidadeService.java
│       └── UsuarioService.java
└── Trab1-Lp2.iml
