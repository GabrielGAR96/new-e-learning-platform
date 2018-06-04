package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DisciplinaInscrita {
    IntegerProperty id;
    IntegerProperty disciplinaId;
    IntegerProperty inscricaoId;

    public DisciplinaInscrita() {
    }

    public DisciplinaInscrita(int disciplinaId, int inscricaoId) {
        this.disciplinaId = new SimpleIntegerProperty(disciplinaId);
        this.inscricaoId = new SimpleIntegerProperty(inscricaoId);
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

    public int getDisciplinaId() {
        return disciplinaId.get();
    }

    public IntegerProperty disciplinaIdProperty() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId.set(disciplinaId);
    }

    public int getInscricaoId() {
        return inscricaoId.get();
    }

    public IntegerProperty inscricaoIdProperty() {
        return inscricaoId;
    }

    public void setInscricaoId(int inscricaoId) {
        this.inscricaoId.set(inscricaoId);
    }
}
