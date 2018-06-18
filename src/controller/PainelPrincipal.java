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

public class PainelPrincipal {

    @FXML
    private BorderPane painelPrimario;

    @FXML
    private BorderPane painelSecundario;

    @FXML
    private JFXButton cadastrarBtn;

    @FXML
    private JFXButton registrosBtn;

    @FXML
    private JFXButton rankingBtn;

    @FXML
    private JFXTextField pesquisarField;

    private PainelRegistros painelRegistros;

    private Dao dao;

    public PainelPrincipal() {
        dao = new Dao();
    }

    public void abrirPainelCadastrar(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane bp = FXMLLoader.load(getClass().getResource("../view/painelCadastrar.fxml"));
        painelSecundario.setCenter(bp);
    }

    public void abrirPainelRegistros(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelRegistros.fxml"));
        BorderPane bp = fxmlLoader.load();
        painelRegistros = fxmlLoader.getController();

        painelSecundario.setCenter(bp);
    }

    public void abrirPainelRanking(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelRanking.fxml"));
        BorderPane bp = fxmlLoader.load();
        PainelRanking painelRanking = fxmlLoader.getController();

        painelSecundario.setCenter(bp);
    }

    public void abrirPainelSimulado(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelLogin.fxml"));
        BorderPane bp = fxmlLoader.load();
        PainelLogin pl = fxmlLoader.getController();

        pl.setPainelPai(painelSecundario);

        painelSecundario.setCenter(bp);
    }

    public void pesquisar(javafx.event.ActionEvent actionEvent) {
        TableView<?> tabelaRegistroAtual = painelRegistros.getTabelaAtual();
        Class classeDaTabela = tabelaRegistroAtual.getItems().get(0).getClass();
        String textoPesquisa = pesquisarField.getText();

        if (tabelaRegistroAtual != null) {
            if (textoPesquisa.isEmpty()) {
                painelRegistros.setTabelaAtual(dao.listar(classeDaTabela), classeDaTabela);
            } else {
                int indexSeparador = textoPesquisa.indexOf(":");
                String nomeFiltro = textoPesquisa.substring(0, indexSeparador).trim();
                String filtro = textoPesquisa.substring(indexSeparador + 1).trim();

                painelRegistros.setTabelaAtual(dao.listarComFiltro(classeDaTabela, nomeFiltro, filtro), classeDaTabela);
            }

        }
    }

}
