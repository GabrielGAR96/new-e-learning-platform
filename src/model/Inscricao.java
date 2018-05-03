package model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;


public class Inscricao {
    private IntegerProperty id;
    private IntegerProperty matricula;
    private Property<LocalDate> data;

    public Inscricao(int id, int matricula, LocalDate data) {
        this.id = new SimpleIntegerProperty(id);
        this.matricula = new SimpleIntegerProperty(matricula);
        this.data = new SimpleObjectProperty<LocalDate>();
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

    public int getMatricula() {
        return matricula.get();
    }

    public IntegerProperty matriculaProperty() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula.set(matricula);
    }

    public LocalDate getData() {
        return data.getValue();
    }

    public Property<LocalDate> dataProperty() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data.setValue(data);
    }
}
