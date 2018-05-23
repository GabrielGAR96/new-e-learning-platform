package model;

import javafx.beans.property.*;

import java.util.Date;

public class Pagamento {
    private IntegerProperty id;
    private DoubleProperty valor;
    private Property<Date> data;
    private IntegerProperty idInscricao;

    public Pagamento(int id, double valor, Date data, int idInscricao) {
        this.id = new SimpleIntegerProperty(id);
        this.valor = new SimpleDoubleProperty(valor);
        this.data = new SimpleObjectProperty<>(data);
        this.idInscricao = new SimpleIntegerProperty(idInscricao);
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

    public int getIdInscricao() {
        return idInscricao.get();
    }

    public IntegerProperty idInscricaoProperty() {
        return idInscricao;
    }

    public void setIdInscricao(int idInscricao) {
        this.idInscricao.set(idInscricao);
    }
}
