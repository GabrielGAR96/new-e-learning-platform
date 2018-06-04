package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Assunto {
    IntegerProperty id;
    StringProperty nome;
    IntegerProperty disciplinaId;

    public Assunto() {
    }

    public Assunto(String nome, int disciplinaId) {
        this.nome = new SimpleStringProperty(nome);
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

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
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
