package Entidades;

public class UCE {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String descricao;
    private int cargaHoraria;
    private PPC_Historico ppc; // PPC ao qual esta UCE pertence

    public UCE(String nome, String descricao, int cargaHoraria, PPC_Historico ppc) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome da UCE é obrigatório.");
        if (cargaHoraria <= 0)
            throw new IllegalArgumentException("Carga horária da UCE deve ser maior que zero.");
        if (ppc == null)
            throw new IllegalArgumentException("PPC é obrigatório para cadastrar uma UCE.");

        this.id = contadorId++;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.ppc = ppc;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public PPC_Historico getPpc() {
        return ppc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCargaHoraria(int ch) {
        this.cargaHoraria = ch;
    }

    @Override
    public String toString() {
        return "UCE{id=" + id +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria + "h" +
                ", ppc='" + ppc.getVersao() + '\'' +
                '}';
    }
}
