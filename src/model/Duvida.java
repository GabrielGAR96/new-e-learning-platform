package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Duvida {
    IntegerProperty id;
    StringProperty texto;
    IntegerProperty alunoMatricula;
    IntegerProperty facilitadorMatricula;

    public Duvida() {
    }

    public Duvida(int id, String texto, int alunoMatricula, int facilitadorMatricula) {
        this.id = new SimpleIntegerProperty(id);
        this.texto = new SimpleStringProperty(texto);
        this.alunoMatricula = new SimpleIntegerProperty(alunoMatricula);
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

    public String getTexto() {
        return texto.get();
    }

    public StringProperty textoProperty() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto.set(texto);
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
