package model;

import javafx.beans.property.*;

import java.io.Serializable;

public class Resposta {
    IntegerProperty id;
    StringProperty texto;
    IntegerProperty assuntoId;
    IntegerProperty perguntaId;
    BooleanProperty correta;

    public Resposta() {
    }

    public Resposta(String texto, int assuntoId, int perguntaId, boolean correta) {
        this.texto = new SimpleStringProperty(texto);
        this.assuntoId = new SimpleIntegerProperty(assuntoId);
        this.perguntaId = new SimpleIntegerProperty(perguntaId);
        this.correta = new SimpleBooleanProperty(correta);
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

    public int getPerguntaId() {
        return perguntaId.get();
    }

    public IntegerProperty perguntaIdProperty() {
        return perguntaId;
    }

    public void setPerguntaId(int perguntaId) {
        this.perguntaId.set(perguntaId);
    }

    public boolean isCorreta() {
        return correta.get();
    }

    public BooleanProperty corretaProperty() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta.set(correta);
    }
}
