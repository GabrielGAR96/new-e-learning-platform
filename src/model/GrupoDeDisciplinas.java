package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GrupoDeDisciplinas {
    IntegerProperty id;
    IntegerProperty facilitadorMatricula;
    IntegerProperty disciplinaId;

    public GrupoDeDisciplinas() {
    }

    public GrupoDeDisciplinas(int facilitadorMatricula, int disciplinaId) {
        this.facilitadorMatricula = new SimpleIntegerProperty(facilitadorMatricula);
        this.disciplinaId = new SimpleIntegerProperty(disciplinaId);
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

    public int getFacilitadorMatricula() {
        return facilitadorMatricula.get();
    }

    public IntegerProperty facilitadorMatriculaProperty() {
        return facilitadorMatricula;
    }

    public void setFacilitadorMatricula(int facilitadorMatricula) {
        this.facilitadorMatricula.set(facilitadorMatricula);
    }

    public int getDisciplinaId() {
        return disciplinaId.get();
    }

    public IntegerProperty disciplinaIdProperty() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId.set(disciplinaId);
    }
}
