package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Facilitador {
    IntegerProperty matricula;
    StringProperty nome;

    public Facilitador() {
    }

    public Facilitador(int matricula, String nome) {
        this.matricula = new SimpleIntegerProperty(matricula);
        this.nome = new SimpleStringProperty(nome);
    }

    public int getMatricula() {
        return matricula.get();
    }

    public IntegerProperty matriculaProperty() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula.set(matricula);
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
}
