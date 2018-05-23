package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Assunto {
    IntegerProperty id;
    StringProperty nome;
    IntegerProperty disciplina_id;

    public Assunto(int id, String nome, int disciplina_id) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.disciplina_id = new SimpleIntegerProperty(disciplina_id);
    }

    public Assunto(String nome, int disciplina_id) {
        this.nome = new SimpleStringProperty(nome);
        this.disciplina_id = new SimpleIntegerProperty(disciplina_id);
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

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
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
