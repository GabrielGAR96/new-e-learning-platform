package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

public class DisciplinaInscrita {
    IntegerProperty id;
    IntegerProperty disciplina_id;
    IntegerProperty inscricao_id;

    public DisciplinaInscrita() {
    }

    public DisciplinaInscrita(int disciplina_id, int inscricao_id) {
        this.disciplina_id = new SimpleIntegerProperty(disciplina_id);
        this.inscricao_id = new SimpleIntegerProperty(inscricao_id);
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

    public int getDisciplina_id() {
        return disciplina_id.get();
    }

    public IntegerProperty disciplina_idProperty() {
        return disciplina_id;
    }

    public void setDisciplina_id(int disciplina_id) {
        this.disciplina_id.set(disciplina_id);
    }

    public int getInscricao_id() {
        return inscricao_id.get();
    }

    public IntegerProperty inscricao_idProperty() {
        return inscricao_id;
    }

    public void setInscricao_id(int inscricao_id) {
        this.inscricao_id.set(inscricao_id);
    }
}
