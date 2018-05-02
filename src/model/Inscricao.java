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
    private IntegerProperty matriculaAluno;
    private Property<LocalDate> data;

    public Inscricao(int id, int matriculaAluno, LocalDate data) {
        this.id = new SimpleIntegerProperty(id);
        this.matriculaAluno = new SimpleIntegerProperty(matriculaAluno);
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

    public int getMatriculaAluno() {
        return matriculaAluno.get();
    }

    public IntegerProperty matriculaAlunoProperty() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno.set(matriculaAluno);
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
