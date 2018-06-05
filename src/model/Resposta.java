package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Resposta {
    IntegerProperty id;
    StringProperty texto;
    StringProperty tipo;
    IntegerProperty assuntoId;

    public Resposta() {
    }

    public Resposta(int id, String texto, String tipo, int assuntoId) {
        this.id = new SimpleIntegerProperty(id);
        this.texto = new SimpleStringProperty(texto);
        this.tipo = new SimpleStringProperty(tipo);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
    }

    public Resposta(String texto, String tipo, int assuntoId) {
        this.texto = new SimpleStringProperty(texto);
        this.tipo = new SimpleStringProperty(tipo);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
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

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
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
}
