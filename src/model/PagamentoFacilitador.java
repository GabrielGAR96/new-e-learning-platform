package model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;

public class PagamentoFacilitador {
    IntegerProperty id;
    DoubleProperty valor;
    Property<Date> data;
    IntegerProperty facilitadorMatricula;
    IntegerProperty simuladoId;
    IntegerProperty duvidaId;

    public PagamentoFacilitador() {
    }

    public PagamentoFacilitador(int id, double valor, Date data, int facilitadorMatricula, int simuladoId, int duvidaId) {
        this.id = new SimpleIntegerProperty(id);
        this.valor = new SimpleDoubleProperty(valor);
        this.data = new SimpleObjectProperty<Date>(data);
        this.facilitadorMatricula = new SimpleIntegerProperty(facilitadorMatricula);
        this.simuladoId = new SimpleIntegerProperty(simuladoId);
        this.duvidaId = new SimpleIntegerProperty(duvidaId);
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

    public double getValor() {
        return valor.get();
    }

    public DoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }

    public Date getData() {
        return data.getValue();
    }

    public Property<Date> dataProperty() {
        return data;
    }

    public void setData(Date data) {
        this.data.setValue(data);
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

    public int getSimuladoId() {
        return simuladoId.get();
    }

    public IntegerProperty simuladoIdProperty() {
        return simuladoId;
    }

    public void setSimuladoId(int simuladoId) {
        this.simuladoId.set(simuladoId);
    }

    public int getDuvidaId() {
        return duvidaId.get();
    }

    public IntegerProperty duvidaIdProperty() {
        return duvidaId;
    }

    public void setDuvidaId(int duvidaId) {
        this.duvidaId.set(duvidaId);
    }
}
