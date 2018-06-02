package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.util.Date;


public class Inscricao {
    IntegerProperty id;
    IntegerProperty alunoMatricula;
    Property<Date> data;

    public Inscricao() {
    }

//    public Inscricao(int id, int alunoMatricula, Date data) {
//        this.id = new SimpleIntegerProperty(id);
//        this.alunoMatricula = new SimpleIntegerProperty(alunoMatricula);
//        this.data = new SimpleObjectProperty<>();
//    }

    public Inscricao(int alunoMatricula, Date data) {
        this.alunoMatricula = new SimpleIntegerProperty(alunoMatricula);
        this.data = new SimpleObjectProperty<>(data);
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

    public int getAlunoMatricula() {
        return alunoMatricula.get();
    }

    public IntegerProperty alunoMatriculaProperty() {
        return alunoMatricula;
    }

    public void setAlunoMatricula(int matricula) {
        this.alunoMatricula.set(matricula);
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
}
