package model;


import javafx.beans.property.*;

import java.io.Serializable;


public class Simulado {
    IntegerProperty id;
    IntegerProperty alunoMatricula;
    DoubleProperty nota;
    IntegerProperty assuntoId;
    IntegerProperty facilitadorMatricula;
    BooleanProperty respondido;
    BooleanProperty corrigido;

    public Simulado() {
    }

    public Simulado(int alunoMatricula, double nota, int assuntoId, int facilitadorMatricula, boolean respondido, boolean corrigido) {
        this.alunoMatricula = new SimpleIntegerProperty(alunoMatricula);
        this.nota = new SimpleDoubleProperty(nota);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
        this.facilitadorMatricula = new SimpleIntegerProperty(facilitadorMatricula);
        this.respondido = new SimpleBooleanProperty(respondido);
        this.corrigido = new SimpleBooleanProperty(corrigido);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getAlunoMatricula() {
        return alunoMatricula.get();
    }

    public IntegerProperty alunoMatriculaProperty() {
        return alunoMatricula;
    }

    public void setAlunoMatricula(int alunoMatricula) {
        this.alunoMatricula.set(alunoMatricula);
    }

    public double getNota() {
        return nota.get();
    }

    public DoubleProperty notaProperty() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota.set(nota);
    }

    public int getAssuntoId() {
        return assuntoId.get();
    }

    public IntegerProperty assuntoIdProperty() {
        return assuntoId;
    }

    public void setAssuntoId(int assuntoId) {
        this.assuntoId.set(assuntoId);
    }

    public int getFacilitadorMatricula() {
        return facilitadorMatricula.get();
    }

    public IntegerProperty facilitadorMatriculaProperty() {
        return facilitadorMatricula;
    }

    public void setFacilitadorMatricula(int facilitadorMatricula) {
        this.facilitadorMatricula.set(facilitadorMatricula);
    }

    public boolean isRespondido() {
        return respondido.get();
    }

    public BooleanProperty respondidoProperty() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido.set(respondido);
    }

    public boolean isCorrigido() {
        return corrigido.get();
    }

    public BooleanProperty corrigidoProperty() {
        return corrigido;
    }

    public void setCorrigido(boolean corrigido) {
        this.corrigido.set(corrigido);
    }
}
