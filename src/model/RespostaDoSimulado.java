package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

public class RespostaDoSimulado {
    IntegerProperty simuladoId;
    IntegerProperty respostaId;

    public RespostaDoSimulado() {
    }

    public RespostaDoSimulado(int simuladoId, int respostaId) {
        this.simuladoId = new SimpleIntegerProperty(simuladoId);
        this.respostaId = new SimpleIntegerProperty(respostaId);
    }

    public int getSimuladoId() {
        return simuladoId.get();
    }

    public IntegerProperty simuladoIdProperty() {
        return simuladoId;
    }

    public void setSimuladoId(int simuladoId) {
        this.simuladoId.set(simuladoId);
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
