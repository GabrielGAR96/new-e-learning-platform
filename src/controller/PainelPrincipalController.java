package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.Dao;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

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

    private PainelRegistrosController painelRegistrosController;

    private Dao dao;

    public PainelPrincipalController() {
        dao = new Dao();
    }

    @FXML
    void abrirPainelCadastrar(ActionEvent event) {

    }

    public void abrirPainelCadastrar(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane bp = FXMLLoader.load(getClass().getResource("../view/painelCadastrar.fxml"));
        painelSecundario.setCenter(bp);
    }

    public void abrirPainelRegistros(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelRegistros.fxml"));
        BorderPane bp = fxmlLoader.load();
        painelRegistrosController = fxmlLoader.getController();

        painelSecundario.setCenter(bp);
    }

    public void pesquisar(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && painelRegistrosController != null) {

            TableView<?> tabelaRegistroAtual = painelRegistrosController.getTabelaAtual();
            Class classeDaTabela = tabelaRegistroAtual.getItems().get(0).getClass();
            String textoPesquisa = pesquisarField.getText();
            
            if (tabelaRegistroAtual != null) {
                if (textoPesquisa.isEmpty()) {
                    painelRegistrosController.setTabelaAtual(dao.listar(classeDaTabela), classeDaTabela);
                } else {
                    int indexSeparador = textoPesquisa.indexOf(":");
                    String nomeFiltro = textoPesquisa.substring(0, indexSeparador).trim();
                    String filtro = textoPesquisa.substring(indexSeparador + 1).trim();

                    painelRegistrosController.setTabelaAtual(dao.listarComFiltro(classeDaTabela, nomeFiltro, filtro), classeDaTabela);
                }

            }
        }
    }
}
