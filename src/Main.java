import Entidades.*;
import Enums.*;
import Services.*;
import Repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe principal de testes manuais do sistema.
 * Cobre todos os Services: UsuarioService, CursoService, OpotunidadeService,
 * InscricaoService, CertificadoService, AproveitamentoService,
 * GrupoService, CoordenadorService, DocenteService e MembroGrupoService.
 *
 * Cada bloco de teste imprime PASSOU ou FALHOU no console.
 */
public class Main {

        // Contadores globais para o resumo final
        static int totalTestes = 0;
        static int passou = 0;
        static int falhou = 0;

        public static void main(String[] args) {

                System.out.println("==============================================");
                System.out.println("  INICIO DOS TESTES — Trab1 LP2");
                System.out.println("==============================================\n");

                testarUsuarioService();
                testarCursoService();
                testarOpotunidadeService();
                testarInscricaoService();
                testarCertificadoService();
                testarAproveitamentoService();
                testarGrupoService();
                testarCoordenadorService();
                testarDocenteService();
                testarMembroGrupoService();

                // Resumo final de todos os testes
                System.out.println("\n==============================================");
                System.out.println("  RESUMO FINAL");
                System.out.println("==============================================");
                System.out.println("  Total : " + totalTestes);
                System.out.println("  Passou: " + passou);
                System.out.println("  Falhou: " + falhou);
                System.out.println("==============================================");
        }

        // =========================================================
        // USUARIO SERVICE
        // =========================================================
        static void testarUsuarioService() {
                titulo("UsuarioService");
                UsuarioService service = new UsuarioService();

                // Cria um usuário base para usar nos testes abaixo
                Usuario usuario = new Usuario("Ana Silva", "ana@email.com", "senha123", Papel.DISCENTE, true);

                // Teste 1: cadastrar usuário deve retornar o próprio usuário (não null)
                // ATENÇÃO: o método atual retorna null — este teste vai FALHAR e apontar o bug
                Usuario cadastrado = service.cadastrar(usuario);
                testar("cadastrar() deve retornar o usuario cadastrado", cadastrado != null);

                // Teste 2: login com credenciais corretas deve retornar o usuário
                // Para funcionar, precisamos adicionar o usuario à lista manualmente via outro
                // cadastro
                UsuarioService service2 = new UsuarioService();
                Usuario u2 = new Usuario("Bob", "bob@email.com", "abc", Papel.DOCENTE, true);
                service2.cadastrar(u2);
                Usuario logado = service2.login("bob@email.com", "abc");
                testar("login() com credenciais corretas deve retornar usuario", logado != null);

                // Teste 3: login com senha errada deve retornar null
                Usuario loginErrado = service2.login("bob@email.com", "senhaErrada");
                testar("login() com senha errada deve retornar null", loginErrado == null);

                // Teste 4: login com e-mail inexistente deve retornar null
                Usuario loginInexistente = service2.login("naoexiste@email.com", "abc");
                testar("login() com email inexistente deve retornar null", loginInexistente == null);

                // Teste 5: mudarSenha() deve atualizar a senha do usuario
                u2.mudarSenha("novaSenha");
                testar("mudarSenha() deve atualizar a senha", u2.getSenha().equals("novaSenha"));
        }

        // =========================================================
        // CURSO SERVICE
        // =========================================================
        static void testarCursoService() {
                titulo("CursoService");
                CursoService service = new CursoService();

                // Cria cursos e discente para os testes
                Curso comp = new Curso("Ciência da Computação", 1, 3200, "PPC-2022");
                Curso eng = new Curso("Engenharia", 2, 3600, "PPC-2021");
                Discente discente = new Discente("Carlos", "carlos@email.com", "123",
                                Papel.DISCENTE, true, "MAT001", 3, comp);

                service.adcionarCurso(comp);
                service.adcionarCurso(eng);

                // Teste 1: adcionarDiscente() deve adicionar o discente à lista do curso
                service.adcionarDiscente(discente, comp);
                testar("adcionarDiscente() deve incluir discente no curso", comp.getDicentes().contains(discente));

                // Teste 2: listarAlunosPorStatus() com true deve retornar apenas ativos
                List<Discente> ativos = service.listarAlunosPorStatus(comp, true);
                testar("listarAlunosPorStatus(true) deve retornar discentes ativos", ativos.size() == 1);

                // Teste 3: listarAlunosPorStatus() com false deve retornar lista vazia (nenhum
                // inativo)
                List<Discente> inativos = service.listarAlunosPorStatus(comp, false);
                testar("listarAlunosPorStatus(false) deve retornar lista vazia", inativos.isEmpty());

                // Teste 4: mudarCurso() deve mover o discente de comp para eng
                service.mudarCurso(discente, eng);
                testar("mudarCurso() deve remover discente do curso antigo", !comp.getDicentes().contains(discente));
                testar("mudarCurso() deve adicionar discente no novo curso", eng.getDicentes().contains(discente));

                // Teste 5: após mudarCurso(), getCurso() do discente deve refletir o novo curso
                testar("mudarCurso() deve atualizar o curso do discente", discente.getCurso().equals(eng));
        }

        // =========================================================
        // OPOTUNIDADE SERVICE
        // =========================================================
        static void testarOpotunidadeService() {
                titulo("OpotunidadeService");
                OpotunidadeService service = new OpotunidadeService();

                // Cria um docente (tem permissão) e um discente (sem permissão)
                Docente docente = new Docente("Prof. Lima", "lima@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE001", "DCC");
                Discente discente = new Discente("João", "joao@email.com", "123",
                                Papel.DISCENTE, true, "MAT002", 2,
                                new Curso("Comp", 1, 3200, "v1"));

                Oportunidade op = new Oportunidade(
                                "Monitoria de Algoritmos", "Vaga de monitor",
                                statusOportunidade.Pendente, TipoOportunidade.Curso,
                                Modalidade.Pesencial, 60, 3,
                                LocalDate.now(), LocalDate.now().plusMonths(6),
                                docente, docente);

                // Teste 1: docente pode criar oportunidade
                Oportunidade criada = service.criarOportunidade(op, docente);
                testar("criarOportunidade() com DOCENTE deve retornar a oportunidade", criada != null);

                // Teste 2: discente NÃO pode criar oportunidade (permissão negada)
                Oportunidade negada = service.criarOportunidade(op, discente);
                testar("criarOportunidade() com DISCENTE deve retornar null", negada == null);

                // Teste 3: usuario com papel CORD_UCE também pode criar oportunidade
                Usuario coordUCE = new Usuario("Coord UCE", "uce@uni.com", "pass",
                                Papel.CORD_UCE, true);
                Oportunidade criadaUCE = service.criarOportunidade(op, coordUCE);
                testar("criarOportunidade() com CORD_UCE deve retornar a oportunidade", criadaUCE != null);
        }

        // =========================================================
        // INSCRICAO SERVICE
        // =========================================================
        static void testarInscricaoService() {
                titulo("InscricaoService");
                InscricaoService service = new InscricaoService();

                Curso curso = new Curso("Sistemas", 3, 3000, "v2");
                Discente discente = new Discente("Maria", "maria@email.com", "123",
                                Papel.DISCENTE, true, "MAT003", 4, curso);
                Docente docente = new Docente("Prof. Ana", "ana@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE002", "DCC");
                Oportunidade op = new Oportunidade(
                                "Pesquisa em IA", "Iniciação Científica",
                                statusOportunidade.Publicada, TipoOportunidade.Projeto,
                                Modalidade.Hibrido, 120, 2,
                                LocalDate.now(), LocalDate.now().plusMonths(12),
                                docente, docente);

                // Teste 1: inscrever() deve criar uma inscrição com status Pendente
                Inscricao inscricao = service.inscrever(discente, op);
                testar("inscrever() deve retornar uma inscricao nao-nula", inscricao != null);

                // Teste 2: a inscrição criada deve apontar para o discente correto
                testar("inscrever() deve associar o discente correto", inscricao.getDiscente().equals(discente));

                // Teste 3: a inscrição criada deve apontar para a oportunidade correta
                testar("inscrever() deve associar a oportunidade correta", inscricao.getOportunidade().equals(op));

                // Teste 4: status inicial da inscrição deve ser Pendente
                testar("inscrever() deve iniciar com status Pendente",
                                inscricao.getStatus().equals(statusInscricao.Pendente));
        }

        // =========================================================
        // CERTIFICADO SERVICE
        // =========================================================
        static void testarCertificadoService() {
                titulo("CertificadoService");
                CertificadoService service = new CertificadoService();

                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Pedro", "pedro@email.com", "123",
                                Papel.DISCENTE, true, "MAT004", 5, curso);
                Docente docente = new Docente("Prof. Beto", "beto@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE003", "DCC");
                Oportunidade op = new Oportunidade(
                                "Workshop de Java", "Workshop prático",
                                statusOportunidade.Publicada, TipoOportunidade.Oficina,
                                Modalidade.Pesencial, 8, 20,
                                LocalDate.now().minusDays(10), LocalDate.now(),
                                docente, docente);
                Usuario emissor = new Usuario("Admin", "admin@uni.com", "pass",
                                Papel.ADMIN, true);

                // Teste 1: emitir() deve retornar um certificado não-nulo
                Certificado cert = service.emitir(discente, op, emissor);
                testar("emitir() deve retornar um certificado", cert != null);

                // Teste 2: o certificado deve estar associado ao discente correto
                testar("emitir() deve associar o discente correto", cert.getDiscente().equals(discente));

                // Teste 3: o certificado deve estar associado à oportunidade correta
                testar("emitir() deve associar a oportunidade correta", cert.getOportunidade().equals(op));

                // Teste 4: a data do certificado não deve ser nula (definida no construtor)
                testar("Certificado deve ter data de emissao preenchida", cert.getData() != null);

                // Teste 5: CertificadosDiscente() deve listar o certificado recém-emitido
                List<Certificado> lista = service.CertificadosDiscente(discente);
                testar("CertificadosDiscente() deve retornar 1 certificado", lista.size() == 1);

                // Teste 6: validarAutentcidade() com hash correto deve retornar true
                // O hash atual é "//TODO", mas o fluxo de validação deve funcionar
                String hashEmitido = cert.getUuid_hash();
                testar("validarAutentcidade() com hash correto deve retornar true",
                                service.validarAutentcidade(hashEmitido));

                // Teste 7: validarAutentcidade() com hash inválido deve retornar false
                testar("validarAutentcidade() com hash falso deve retornar false",
                                !service.validarAutentcidade("hash-invalido-xyz"));

                // Teste 8: discente diferente não deve ter certificados
                Discente outroDiscente = new Discente("Lucia", "lucia@email.com", "123",
                                Papel.DISCENTE, true, "MAT999", 1, curso);
                List<Certificado> listaVazia = service.CertificadosDiscente(outroDiscente);
                testar("CertificadosDiscente() para outro discente deve retornar lista vazia",
                                listaVazia.isEmpty());
        }

        // =========================================================
        // APROVEITAMENTO SERVICE
        // =========================================================
        static void testarAproveitamentoService() {
                titulo("AproveitamentoService");
                AproveitamentoService service = new AproveitamentoService();

                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Fernanda", "fernanda@email.com", "123",
                                Papel.DISCENTE, true, "MAT005", 6, curso);
                Discente outroDiscente = new Discente("Tiago", "tiago@email.com", "123",
                                Papel.DISCENTE, true, "MAT006", 2, curso);
                Usuario avaliador = new Usuario("Coord", "coord@uni.com", "pass",
                                Papel.CORD_CURSO, true);

                // Cria um aproveitamento sem avaliador (ainda não avaliado)
                Aproveitamento ap = new Aproveitamento(discente, "Curso de Python Online",
                                "Coursera", 40, "/docs/cert_python.pdf", null, null);

                // Teste 1: solicitar() deve adicionar o aproveitamento à lista do serviço
                service.solicitar(discente, ap);
                testar("solicitar() deve adicionar aproveitamento na lista",
                                service.getAproveitamentos().size() == 1);

                // Teste 2: solicitar() deve associar o discente correto ao aproveitamento
                testar("solicitar() deve associar o discente ao aproveitamento",
                                ap.getDiscente().equals(discente));

                // Teste 3: aproveitamentosDiscente() deve retornar somente os do discente
                // correto
                List<Aproveitamento> lista = service.aproveitamentosDiscente(discente);
                testar("aproveitamentosDiscente() deve retornar 1 item para o discente correto",
                                lista.size() == 1);

                // Teste 4: aproveitamentosDiscente() para outro discente deve retornar lista
                // vazia
                List<Aproveitamento> listaOutro = service.aproveitamentosDiscente(outroDiscente);
                testar("aproveitamentosDiscente() deve retornar vazio para discente sem aproveitamentos",
                                listaOutro.isEmpty());

                // Teste 5: adiciona um segundo aproveitamento para o mesmo discente
                Aproveitamento ap2 = new Aproveitamento(discente, "Curso de Redes",
                                "Udemy", 20, "/docs/cert_redes.pdf", null, null);
                service.solicitar(discente, ap2);
                List<Aproveitamento> listaDois = service.aproveitamentosDiscente(discente);
                testar("aproveitamentosDiscente() deve retornar 2 itens apos segundo cadastro",
                                listaDois.size() == 2);
        }

        // =========================================================
        // GRUPO SERVICE
        // =========================================================
        static void testarGrupoService() {
                titulo("GrupoService");

                // GrupoService depende de GrupoRepository
                GrupoRepository repo = new GrupoRepository();
                GrupoService service = new GrupoService(repo);

                Docente docente = new Docente("Prof. Carlos", "carlos@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE010", "DCC");
                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Lucas", "lucas@email.com", "123",
                                Papel.DISCENTE, true, "MAT010", 3, curso);

                // Teste 1: createGrupo() deve retornar um grupo não-nulo
                // TipoGrupo está vazio no projeto, por isso passamos null
                Grupo grupo = service.createGrupo("PET Computação", null,
                                "pet@uni.com", docente, "Grupo de Educação Tutorial");
                testar("createGrupo() deve retornar um grupo", grupo != null);

                // Teste 2: o grupo criado deve ter status Ativo por padrão
                testar("createGrupo() deve iniciar com status Ativo",
                                grupo.getStatus().equals(statusGrupo.Ativo));

                // Teste 3: adicionarMembro() deve incluir o discente na lista de membros
                service.adicionarMembro(grupo, discente);
                testar("adicionarMembro() deve incluir discente no grupo",
                                grupo.getMembros().size() == 1);

                // Teste 4: encerrarGrupo() deve alterar o status para Inativo
                service.encerrarGrupo(grupo);
                testar("encerrarGrupo() deve definir status como Inativo",
                                grupo.getStatus().equals(statusGrupo.Inativo));
        }

        // =========================================================
        // COORDENADOR SERVICE
        // =========================================================
        static void testarCoordenadorService() {
                titulo("CoordenadorService");

                GrupoRepository repo = new GrupoRepository();
                GrupoService grupoService = new GrupoService(repo);
                CoordenadorService service = new CoordenadorService(grupoService);

                // Coordenador válido: ativo e com papel CORD_CURSO
                Coordenador coord = new Coordenador("Coord. Joana", "joana@uni.com",
                                "pass", Papel.CORD_CURSO, true);
                Docente docente = new Docente("Prof. Rui", "rui@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE020", "DCC");
                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Sofia", "sofia@email.com", "123",
                                Papel.DISCENTE, true, "MAT020", 2, curso);

                // Teste 1: coordenador válido pode criar grupo sem exceção
                Grupo grupo = null;
                try {
                        grupo = service.criarGrupo(coord, docente, "LIGA DE COMPUTAÇÃO",
                                        null, "liga@uni.com", "Liga acadêmica de computação");
                        testar("criarGrupo() com coordenador valido nao deve lancar excecao", true);
                } catch (Exception e) {
                        testar("criarGrupo() com coordenador valido nao deve lancar excecao", false);
                }

                // Teste 2: coordenador válido pode adicionar membro
                try {
                        service.adicionarMembro(coord, grupo, discente);
                        testar("adicionarMembro() com coordenador valido nao deve lancar excecao", true);
                } catch (Exception e) {
                        testar("adicionarMembro() com coordenador valido nao deve lancar excecao", false);
                }

                // Teste 3: usuario sem papel CORD_CURSO deve lançar IllegalStateException
                Usuario semPermissao = new Usuario("Fulano", "fulano@email.com", "123",
                                Papel.DISCENTE, true);
                try {
                        service.criarGrupo((Coordenador) new Coordenador("X", "x@x.com", "x",
                                        Papel.DISCENTE, true), docente, "Grupo X", null, "x@x.com", "desc");
                        testar("criarGrupo() com papel errado deve lancar excecao", false);
                } catch (IllegalStateException e) {
                        testar("criarGrupo() com papel errado deve lancar IllegalStateException", true);
                } catch (Exception e) {
                        testar("criarGrupo() com papel errado deve lancar excecao", false);
                }

                // Teste 4: coordenador inativo deve lançar IllegalStateException
                Coordenador coordInativo = new Coordenador("Inativos", "inativo@uni.com",
                                "pass", Papel.CORD_CURSO, false);
                try {
                        service.criarGrupo(coordInativo, docente, "GrupoX", null, "gx@x.com", "desc");
                        testar("criarGrupo() com coordenador inativo deve lancar excecao", false);
                } catch (IllegalStateException e) {
                        testar("criarGrupo() com coordenador inativo deve lancar IllegalStateException", true);
                }

                // Teste 5: dados inválidos (nome vazio) devem lançar IllegalArgumentException
                try {
                        service.criarGrupo(coord, docente, "", null, "gx@x.com", "desc");
                        testar("criarGrupo() com nome vazio deve lancar excecao", false);
                } catch (IllegalArgumentException e) {
                        testar("criarGrupo() com nome vazio deve lancar IllegalArgumentException", true);
                }
        }

        // =========================================================
        // DOCENTE SERVICE
        // =========================================================
        static void testarDocenteService() {
                titulo("DocenteService");

                GrupoRepository repo = new GrupoRepository();
                GrupoService grupoService = new GrupoService(repo);
                DocenteService service = new DocenteService(grupoService);

                Docente docente = new Docente("Prof. Vera", "vera@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE030", "DCC");
                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Diego", "diego@email.com", "123",
                                Papel.DISCENTE, true, "MAT030", 4, curso);

                // Cria o grupo via GrupoService para garantir que está no repositório
                Grupo grupo = grupoService.createGrupo("Grupo Pesquisa IA", null,
                                "ia@uni.com", docente, "Pesquisa em inteligência artificial");

                // Teste 1: docente responsável pode adicionar membro sem exceção
                try {
                        service.adicionarMembro(docente, grupo, discente);
                        testar("adicionarMembro() pelo docente responsavel nao deve lancar excecao", true);
                } catch (Exception e) {
                        testar("adicionarMembro() pelo docente responsavel nao deve lancar excecao", false);
                }

                // Teste 2: docente diferente (não responsável) deve lançar exceção ao adicionar
                // membro
                Docente outroDocente = new Docente("Prof. Hugo", "hugo@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE031", "DCC");
                try {
                        service.adicionarMembro(outroDocente, grupo, discente);
                        testar("adicionarMembro() por docente nao-responsavel deve lancar excecao", false);
                } catch (IllegalStateException e) {
                        testar("adicionarMembro() por docente nao-responsavel deve lancar excecao", true);
                }

                // Teste 3: usuario com papel diferente de DOCENTE deve ser rejeitado
                Usuario semPapel = new Usuario("Qualquer", "q@q.com", "123",
                                Papel.DISCENTE, true);
                try {
                        service.adicionarMembro(semPapel, grupo, discente);
                        testar("adicionarMembro() com papel errado deve lancar excecao", false);
                } catch (IllegalStateException e) {
                        testar("adicionarMembro() com papel errado deve lancar excecao", true);
                }

                // Teste 4: docente responsável pode encerrar o grupo
                try {
                        service.encerrarGrupo(docente, grupo);
                        testar("encerrarGrupo() pelo docente responsavel nao deve lancar excecao", true);
                } catch (Exception e) {
                        testar("encerrarGrupo() pelo docente responsavel nao deve lancar excecao", false);
                }
        }

        // =========================================================
        // MEMBRO GRUPO SERVICE
        // =========================================================
        static void testarMembroGrupoService() {
                titulo("MembroGrupoService");
                MembroGrupoService service = new MembroGrupoService();

                Docente docente = new Docente("Prof. Bia", "bia@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE040", "DCC");
                Curso curso = new Curso("Comp", 1, 3200, "v1");
                Discente discente = new Discente("Rafael", "rafael@email.com", "123",
                                Papel.DISCENTE, true, "MAT040", 3, curso);

                // Cria grupo e adiciona o discente como MEMBRO (função padrão)
                Grupo grupo = new Grupo("Atlética", null, "atletica@uni.com",
                                "Atlética universitária", statusGrupo.Ativo, docente);
                grupo.adicionarMembro(discente, GrupoFunc.MEMBRO, LocalDate.now());

                // Teste 1: função inicial do membro deve ser MEMBRO
                GrupoFunc funcInicial = grupo.getMembros().get(0).getFunc();
                testar("adicionarMembro() deve atribuir funcao MEMBRO por padrao",
                                funcInicial.equals(GrupoFunc.MEMBRO));

                // Teste 2: mudarFuncaoMembro() pelo docente responsável deve alterar para
                // DIRETOR
                service.mudarFuncaoMembro(docente, grupo, discente, GrupoFunc.DIRETOR);
                GrupoFunc funcAtualizada = grupo.getMembros().get(0).getFunc();
                testar("mudarFuncaoMembro() deve alterar funcao para DIRETOR",
                                funcAtualizada.equals(GrupoFunc.DIRETOR));

                // Teste 3: docente não-responsável não deve conseguir mudar a função
                Docente outroDocente = new Docente("Prof. Outro", "outro@uni.com", "pass",
                                Papel.DOCENTE, true, "SIAPE041", "DCC");
                service.mudarFuncaoMembro(outroDocente, grupo, discente, GrupoFunc.VICE);
                // A função deve continuar DIRETOR (a mudança silenciosa é comportamento atual
                // do código)
                GrupoFunc funcDepois = grupo.getMembros().get(0).getFunc();
                testar("mudarFuncaoMembro() por docente nao-responsavel nao deve alterar funcao",
                                funcDepois.equals(GrupoFunc.DIRETOR));
        }

        // =========================================================
        // UTILITÁRIOS DE TESTE
        // =========================================================

        /** Imprime o cabeçalho de uma seção de testes */
        static void titulo(String nome) {
                System.out.println("\n----------------------------------------------");
                System.out.println("  Testando: " + nome);
                System.out.println("----------------------------------------------");
        }

        /**
         * Avalia uma condição e imprime PASSOU ou FALHOU.
         * Incrementa os contadores globais.
         *
         * @param descricao texto descritivo do que está sendo testado
         * @param condicao  resultado esperado (true = passou)
         */
        static void testar(String descricao, boolean condicao) {
                totalTestes++;
                if (condicao) {
                        passou++;
                        System.out.println("  [PASSOU] " + descricao);
                } else {
                        falhou++;
                        System.out.println("  [FALHOU] " + descricao);
                }
        }
}