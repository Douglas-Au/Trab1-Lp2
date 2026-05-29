package Services;

import java.time.LocalDate;
import java.util.List;

import Entidades.*;
import Enums.*;

public class CoordenadorService {

    private final GrupoService grupoService;

    public CoordenadorService(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    // Gerenciamento de PPC

    public void cadastrarPPC(Coordenador coordenador, Curso curso,
            String versao, int cargaHorariaExt, LocalDate dataVigencia) {
        validarCoordenador(coordenador);
        validarDadosPPC(versao, cargaHorariaExt, dataVigencia);

        PPC_Historico novoPPC = new PPC_Historico(curso, versao, cargaHorariaExt,
                dataVigencia, coordenador);
        curso.cadastrarOuAtualizarPPC(novoPPC);
    }

    public List<PPC_Historico> listarHistoricoPPC(Coordenador coordenador, Curso curso) {
        validarCoordenador(coordenador);
        return curso.getHistoricoPPC();
    }

    // Gerenciamento de Grupos
    public Grupo criarGrupo(Coordenador coordenador, Docente responsavel, String nome, TipoGrupo tipo, String email,
            String descricao) {
        validarCoordenador(coordenador);
        validarDadosGrupo(nome, email, descricao);

        return grupoService.createGrupo(nome, tipo, email, responsavel, descricao);
    }

    public void adicionarMembro(Usuario coordenador, Grupo grupo, Discente discente) {
        validarCoordenador(coordenador);
        grupoService.adicionarMembro(grupo, discente);
    }

    public void removerMembro(Coordenador coordenador, Grupo grupo, Discente discente) {
        validarCoordenador(coordenador);
        grupoService.removerMembro(grupo, discente);
    }

    public void encerrarGrupo(Usuario coordenador, Grupo grupo) {
        validarCoordenador(coordenador);
        grupoService.encerrarGrupo(grupo);
    }

    // validações privadas
    private void validarCoordenador(Usuario coordenador) {
        if (coordenador == null) {
            throw new IllegalArgumentException("Coordenador não pode ser nulo");
        }
        if (!coordenador.getAtivo()) {
            throw new IllegalStateException("Coordenador inativo não pode realizar esta ação");
        }
        if (!coordenador.getPapel().equals(Papel.CORD_CURSO)) {
            throw new IllegalStateException("Usuário não tem permissão de coordenador");
        }
    }

    private void validarDadosGrupo(String nome, String email, String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do grupo é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email do grupo é obrigatório");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição do grupo é obrigatória");
        }
    }

    private void validarDadosPPC(String versao, int cargaHorariaExt, LocalDate dataVigencia) {
        if (versao == null || versao.isBlank()) {
            throw new IllegalArgumentException("Versão do PPC é obrigatória.");
        }
        if (cargaHorariaExt <= 0) {
            throw new IllegalArgumentException("Carga horária de extensão deve ser maior que zero.");
        }
        if (dataVigencia == null) {
            throw new IllegalArgumentException("Data de vigência é obrigatória.");
        }
    }

}