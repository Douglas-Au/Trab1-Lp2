package Entidades;

import Enums.GrupoFunc;
import java.time.LocalDate;

public class MembroGrupo {

    private Discente discente;
    private GrupoFunc func;
    private LocalDate dataEntrada; // quando assumiu a função atual

    public MembroGrupo(Discente discente, GrupoFunc func) {
        this.discente = discente;
        this.func = func;
        this.dataEntrada = LocalDate.now();
    }

    public MembroGrupo(Discente discente, GrupoFunc func, LocalDate dataEntrada) {
        this.discente = discente;
        this.func = func;
        this.dataEntrada = dataEntrada;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public void setFunc(GrupoFunc func) {
        this.func = func;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Discente getDiscente() {
        return discente;
    }

    public GrupoFunc getFunc() {
        return func;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }
}
