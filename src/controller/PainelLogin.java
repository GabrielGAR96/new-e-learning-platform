package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Aluno;
import model.Assunto;
import model.Dao;
import model.Simulado;

import java.io.IOException;
import java.util.ArrayList;

public class PainelLogin {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private JFXTextField matriculaField;

    @FXML
    private JFXButton loginBtn;

    private Dao dao;

    private BorderPane painelPai;

    public PainelLogin() {
        dao = new Dao();
    }

    public void login(javafx.event.ActionEvent actionEvent) {
        Aluno aluno = dao.buscar(Aluno.class, "matricula", Integer.parseInt(matriculaField.getText()));
        if(aluno != null) {
            painelPrincipal.setCenter(null);
            painelPrincipal.setTop(null);
            carregarOpcoes(aluno);
        } else {
            //Validator
        }
    }

    private void carregarOpcoes(Aluno aluno) {
        ArrayList<Simulado> simulados = dao.listarComFiltro(Simulado.class, "alunoMatricula", aluno.getMatricula());
        VBox vbox = new VBox();

        for(Simulado simulado : simulados) {
            Assunto assunto = dao.buscar(Assunto.class, "id", simulado.getAssuntoId());
            String nome = Integer.toString(simulado.getId()) + " : " + assunto.getNome();
            JFXButton btn = new JFXButton(nome);
            btn.setOnMouseClicked(e -> {
                carregarSimulado(simulado.getId());
            });
            vbox.getChildren().add(btn);
        }

        painelPrincipal.setTop(new Label("Escolha o simulado: "));
        painelPrincipal.setCenter(vbox);

    }

    private void carregarSimulado(int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelSimulado.fxml"));
            painelPai.setCenter(fxmlLoader.load());

            PainelSimulado psc = fxmlLoader.getController();
            psc.setSimulado(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPainelPai(BorderPane painelPai) {
        this.painelPai = painelPai;
    }
}
