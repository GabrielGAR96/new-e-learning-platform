package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Pergunta {
    IntegerProperty id;
    StringProperty texto;
    IntegerProperty assuntoId;
    IntegerProperty respostaId;

    public Pergunta() {
    }

    public Pergunta(int id, String texto, int assuntoId, int respostaId) {
        this.id = new SimpleIntegerProperty(id);
        this.texto = new SimpleStringProperty(texto);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
        this.respostaId = new SimpleIntegerProperty(respostaId);
    }

    public Pergunta(String texto, int assuntoId, int respostaId) {
        this.texto = new SimpleStringProperty(texto);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
        this.respostaId = new SimpleIntegerProperty(respostaId);
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

    public int getAssuntoId() {
        return assuntoId.get();
    }

    public IntegerProperty assuntoIdProperty() {
        return assuntoId;
    }

    public void setAssuntoId(int assuntoId) {
        this.assuntoId.set(assuntoId);
    }

    public int getRespostaId() {
        return respostaId.get();
    }

    public IntegerProperty respostaIdProperty() {
        return respostaId;
    }

    public void setRespostaId(int respostaId) {
        this.respostaId.set(respostaId);
    }
}
