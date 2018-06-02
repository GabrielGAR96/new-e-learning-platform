package model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;


public class Simulado {
    IntegerProperty id;
    IntegerProperty alunoMatricula;
    IntegerProperty nota;
    IntegerProperty assuntoId;
    IntegerProperty facilitadorMatricula;

    public Simulado() {
    }

    public Simulado(int id, int alunoMatricula, int nota, int assuntoId, int facilitadorMatricula) {
        this.id = new SimpleIntegerProperty(id);
        this.alunoMatricula = new SimpleIntegerProperty(alunoMatricula);
        this.nota = new SimpleIntegerProperty(nota);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
        this.facilitadorMatricula = new SimpleIntegerProperty(facilitadorMatricula);
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

    public int getNota() {
        return nota.get();
    }

    public IntegerProperty notaProperty() {
        return nota;
    }

    public void setNota(int nota) {
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
}
