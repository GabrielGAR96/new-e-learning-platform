package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private double valor;
    private LocalDate data;
    private int idInscricao;

    public Pagamento(int id, double valor, LocalDate data, int idInscricao) {

        this.id = id;
        this.valor = valor;
        this.data = data;
        this.idInscricao = idInscricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getIdInscricao() {
        return idInscricao;
    }

    public void setIdInscricao(int idInscricao) {
        this.idInscricao = idInscricao;
    }


}
