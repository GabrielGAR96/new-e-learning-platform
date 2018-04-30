package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class PainelPrincipalController {

    @FXML
    private BorderPane painelPrimario;

    @FXML
    private BorderPane painelSecundario;

    @FXML
    private JFXButton cadastrarBtn;

    @FXML
    private JFXButton registrosBtn;

    @FXML
    private JFXButton avaliacaoBtn;

    @FXML
    private JFXTextField pesquisarField;

    @FXML
    void abrirPainelCadastrar(ActionEvent event) {

    }

    public void abrirPainelCadastrar(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane bp =(BorderPane) fxmlLoader.load(getClass().getResource("painelCadastrar.fxml"));
        painelSecundario.setCenter(bp);
    }
}