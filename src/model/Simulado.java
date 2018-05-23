package model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Simulado {
    private IntegerProperty id;
    private IntegerProperty aluno_matricula;
    private IntegerProperty nota;
    private IntegerProperty assunto_id;
    private IntegerProperty facilitador_matricula;

    public Simulado(int id, int aluno_matricula, int nota, int assunto_id, int facilitador_matricula) {
        this.id = new SimpleIntegerProperty(id);
        this.aluno_matricula = new SimpleIntegerProperty(aluno_matricula);
        this.nota = new SimpleIntegerProperty(nota);
        this.assunto_id = new SimpleIntegerProperty(assunto_id);
        this.facilitador_matricula = new SimpleIntegerProperty(facilitador_matricula);
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

    public int getAluno_matricula() {
        return aluno_matricula.get();
    }

    public IntegerProperty aluno_matriculaProperty() {
        return aluno_matricula;
    }

    public void setAluno_matricula(int aluno_matricula) {
        this.aluno_matricula.set(aluno_matricula);
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

    public int getAssunto_id() {
        return assunto_id.get();
    }

    public IntegerProperty assunto_idProperty() {
        return assunto_id;
    }

    public void setAssunto_id(int assunto_id) {
        this.assunto_id.set(assunto_id);
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
}
