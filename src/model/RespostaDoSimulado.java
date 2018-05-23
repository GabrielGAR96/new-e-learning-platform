package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RespostaDoSimulado {
    IntegerProperty simulado_id;
    IntegerProperty resposta_id;

    public RespostaDoSimulado(int simulado_id, int resposta_id) {
        this.simulado_id = new SimpleIntegerProperty(simulado_id);
        this.resposta_id = new SimpleIntegerProperty(resposta_id);
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
