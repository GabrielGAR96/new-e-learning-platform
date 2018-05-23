package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PerguntaDoSimulado {
    private IntegerProperty simulado_id;
    private IntegerProperty pergunta_id;

    public PerguntaDoSimulado(int simulado_id, int pergunta_id) {
        this.simulado_id = new SimpleIntegerProperty(simulado_id);
        this.pergunta_id = new SimpleIntegerProperty(pergunta_id);
    }

    public int getSimulado_id() {
        return simulado_id.get();
    }

    public IntegerProperty simulado_idProperty() {
        return simulado_id;
    }

    public void setSimulado_id(int simulado_id) {
        this.simulado_id.set(simulado_id);
    }

    public int getPergunta_id() {
        return pergunta_id.get();
    }

    public IntegerProperty pergunta_idProperty() {
        return pergunta_id;
    }

    public void setPergunta_id(int pergunta_id) {
        this.pergunta_id.set(pergunta_id);
    }
}
