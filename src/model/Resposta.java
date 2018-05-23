package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Resposta {
    private IntegerProperty id;
    private StringProperty texto;
    private StringProperty tipo;

    public Resposta(int id, String texto, String tipo) {
        this.id = new SimpleIntegerProperty(id);
        this.texto = new SimpleStringProperty(texto);
        this.tipo = new SimpleStringProperty(tipo);
    }

    public Resposta(String texto, String tipo) {
        this.texto = new SimpleStringProperty(texto);
        this.tipo = new SimpleStringProperty(tipo);
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
}
