package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RankingTemplate {

    @FXML
    private Label colocacao;

    @FXML
    private Label nome;

    @FXML
    private Label disciplinaSimulado;

    @FXML
    private Label assuntoSimulado;

    @FXML
    private Label notaSimulado;

    @FXML
    private Label pmg;

    @FXML
    private Circle circuloPodium;

    @FXML
    void initialize() {

    }

    public void setInfo(String colocacao, String nome, String disciplinaSimulado, String assuntoSimulado, String notaSimulado, String pmg) {
        this.colocacao.setText(colocacao);
        this.nome.setText(nome);
        this.disciplinaSimulado.setText(disciplinaSimulado);
        this.assuntoSimulado.setText(assuntoSimulado);
        this.notaSimulado.setText(notaSimulado);
        this.pmg.setText(pmg);

        if(colocacao.equals("1")) {
            circuloPodium.setFill(Color.web("#c90000"));
            circuloPodium.setStroke(Color.web("#fffb00"));
        } else if (colocacao.equals("2")) {

            circuloPodium.setFill(Color.web("#1714a8"));
            circuloPodium.setStroke(Color.web("#b5b5b5"));
        } else if (colocacao.equals("3")) {
            circuloPodium.setFill(Color.web("#a66b14"));
            circuloPodium.setStroke(Color.web("#6f5110"));
        } else {
            circuloPodium.setVisible(false);
            this.colocacao.setTextFill(Color.web("#000000"));
        }
    }

}
