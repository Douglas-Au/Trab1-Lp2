package Services;

import Entidades.*;
import Enums.Papel;
import java.util.List;
import java.util.stream.Collectors;

public class UCEService {

    // Cadastro de UCE

    /**
     * Cadastra uma UCE em um PPC específico.
     * Somente Administradores ou Coordenadores podem realizar esta operação.
     */
    public UCE cadastrarUCE(Usuario responsavel, PPC_Historico ppc,
            String nome, String descricao, int cargaHoraria) {
        validarResponsavel(responsavel);
        if (ppc == null)
            throw new IllegalArgumentException("PPC é obrigatório.");

        UCE uce = new UCE(nome, descricao, cargaHoraria, ppc);
        ppc.adicionarUCE(uce);

        System.out.println("[UCEService] UCE '" + nome + "' (" + cargaHoraria
                + "h) cadastrada no PPC " + ppc.getVersao() + ".");
        return uce;
    }

    // Consultas por PPC

    /**
     * Lista todas as UCEs de um PPC.
     */
    public List<UCE> listarUCEsPorPPC(PPC_Historico ppc) {
        if (ppc == null)
            throw new IllegalArgumentException("PPC não pode ser nulo.");
        return ppc.getUces();
    }

    /**
     * Retorna as UCEs que se aplicam a um discente, com base no PPC
     * ao qual ele está vinculado (Requisito #2: regras corretas por aluno).
     */
    public List<UCE> listarUCEsDoDiscente(Discente discente) {
        if (discente == null)
            throw new IllegalArgumentException("Discente não pode ser nulo.");
        PPC_Historico ppc = discente.getPpcVinculado();
        if (ppc == null) {
            System.out.println("[UCEService] Discente " + discente.getNome()
                    + " não possui PPC vinculado.");
            return new java.util.ArrayList<>();
        }
        return ppc.getUces();
    }

    /**
     * Verifica se a soma de UCEs do PPC do discente satisfaz a carga mínima.
     */
    public boolean verificarCargaMinima(Discente discente) {
        PPC_Historico ppc = discente.getPpcVinculado();
        if (ppc == null)
            return false;
        boolean ok = ppc.cargaHorariaMinimaSatisfeita();
        System.out.println("[UCEService] Discente " + discente.getNome()
                + " | PPC " + ppc.getVersao()
                + " | CH UCEs=" + ppc.getCargaHorariaTotalUCEs() + "h"
                + " | Mínimo=" + ppc.getCargaHorariaExtensao() + "h"
                + " | Satisfeito=" + ok);
        return ok;
    }

    /**
     * Vincula explicitamente um discente a um PPC. Útil quando o PPC é
     * cadastrado após o discente, ou quando um aluno muda de grade curricular.
     */
    public void vincularDiscenteAoPPC(Usuario responsavel, Discente discente, PPC_Historico ppc) {
        validarResponsavel(responsavel);
        if (discente == null)
            throw new IllegalArgumentException("Discente não pode ser nulo.");
        if (ppc == null)
            throw new IllegalArgumentException("PPC não pode ser nulo.");

        discente.setPpcVinculado(ppc);
        System.out.println("[UCEService] Discente " + discente.getNome()
                + " vinculado ao PPC " + ppc.getVersao() + ".");
    }

    // Validação privada

    private void validarResponsavel(Usuario responsavel) {
        if (responsavel == null)
            throw new IllegalArgumentException("Responsável não pode ser nulo.");
        if (!responsavel.getAtivo())
            throw new IllegalStateException("Usuário inativo não pode cadastrar UCEs.");
        boolean podeGerenciar = responsavel.getPapel().equals(Papel.ADMIN)
                || responsavel.getPapel().equals(Papel.CORD_CURSO)
                || responsavel.getPapel().equals(Papel.CORD_UCE);
        if (!podeGerenciar)
            throw new IllegalStateException("Apenas Admin ou Coordenador podem gerenciar UCEs.");
    }
}
