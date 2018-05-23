package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pergunta {
    private IntegerProperty id;
    private StringProperty texto;
    private IntegerProperty assunto_id;
    private IntegerProperty resposta_id;

    public Pergunta(int id, String texto, int assunto_id, int resposta_id) {
        this.id = new SimpleIntegerProperty(id);
        this.texto = new SimpleStringProperty(texto);
        this.assunto_id = new SimpleIntegerProperty(assunto_id);
        this.resposta_id = new SimpleIntegerProperty(resposta_id);
    }

    public Pergunta(String texto, int assunto_id, int resposta_id) {
        this.texto = new SimpleStringProperty(texto);
        this.assunto_id = new SimpleIntegerProperty(assunto_id);
        this.resposta_id = new SimpleIntegerProperty(resposta_id);
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

    public int getAssunto_id() {
        return assunto_id.get();
    }

    public IntegerProperty assunto_idProperty() {
        return assunto_id;
    }

    public void setAssunto_id(int assunto_id) {
        this.assunto_id.set(assunto_id);
    }

    public int getResposta_id() {
        return resposta_id.get();
    }

    public IntegerProperty resposta_idProperty() {
        return resposta_id;
    }

    public void setResposta_id(int resposta_id) {
        this.resposta_id.set(resposta_id);
    }
}
