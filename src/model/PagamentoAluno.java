package model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;

public class PagamentoAluno {
    IntegerProperty id;
    DoubleProperty valor;
    Property<Date> data;
    IntegerProperty inscricaoId;

    public PagamentoAluno() {
    }

    public PagamentoAluno(double valor, Date data, int inscricaoId) {
        this.valor = new SimpleDoubleProperty(valor);
        this.data = new SimpleObjectProperty<>(data);
        this.inscricaoId = new SimpleIntegerProperty(inscricaoId);
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

    public int getInscricaoId() {
        return inscricaoId.get();
    }

    public IntegerProperty inscricaoIdProperty() {
        return inscricaoId;
    }

    public void setInscricaoId(int inscricaoId) {
        this.inscricaoId.set(inscricaoId);
    }
}
