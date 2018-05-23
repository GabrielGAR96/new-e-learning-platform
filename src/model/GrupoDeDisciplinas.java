package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GrupoDeDisciplinas {
    IntegerProperty facilitador_matricula;
    IntegerProperty disciplina_id;

    public GrupoDeDisciplinas(int facilitador_matricula, int disciplina_id) {
        this.facilitador_matricula = new SimpleIntegerProperty(facilitador_matricula);
        this.disciplina_id = new SimpleIntegerProperty(disciplina_id);
    }

    public int getFacilitador_matricula() {
        return facilitador_matricula.get();
    }

    public IntegerProperty facilitador_matriculaProperty() {
        return facilitador_matricula;
    }

    public void setFacilitador_matricula(int facilitador_matricula) {
        this.facilitador_matricula.set(facilitador_matricula);
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
}
