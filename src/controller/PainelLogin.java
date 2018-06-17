package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class PainelLogin {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private JFXTextField matriculaField;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXRadioButton alunoRadioBtn;

    @FXML
    private JFXRadioButton professorRadioBtn;

    @FXML
    private ToggleGroup matriculaToggle;

    private Dao dao;

    private BorderPane painelPai;

    public PainelLogin() {
        dao = new Dao();
    }

    public void loginSelection(javafx.event.ActionEvent actionEvent) {
        if(matriculaToggle.getSelectedToggle().equals(alunoRadioBtn)) {
            Aluno aluno = dao.buscar(Aluno.class, "matricula", Integer.parseInt(matriculaField.getText()));
            login(aluno);
        } else {
            Facilitador facilitador = dao.buscar(Facilitador.class, "matricula", Integer.parseInt(matriculaField.getText()));
            login(facilitador);
        }

    }

    private void login(Object user) {
        if(user != null) {
            painelPrincipal.setCenter(null);
            painelPrincipal.setTop(null);
            if(user instanceof Aluno)
                carregarOpcoes((Aluno) user);
            else
                carregarOpcoes((Facilitador) user);
        } else {
            JFXSnackbar snackBar = new JFXSnackbar(painelPrincipal);
            snackBar.show("Por favor, insira uma matrícula válida", 2500);
        }
    }


    private void carregarOpcoes(Aluno aluno) {
        ArrayList<Simulado> simulados = dao.listarComFiltro(Simulado.class, "alunoMatricula", aluno.getMatricula());
        VBox vbox = new VBox();

        for(Simulado simulado : simulados) {
            Assunto assunto = dao.buscar(Assunto.class, "id", simulado.getAssuntoId());
            String nome = Integer.toString(simulado.getId()) + " : " + assunto.getNome();
            if(simulado.isRespondido()) {
                Label simuladoRespondido = new Label(nome + " (Aguarde a correção)");
                vbox.getChildren().add(simuladoRespondido);
            } else if(simulado.isCorrigido()) {
                Label simuladoCorrigido = new Label(nome + " | Nota: " + simulado.getNota());
                vbox.getChildren().add(simuladoCorrigido);
            } else {
                JFXButton btn = new JFXButton(nome);
                btn.setOnMouseClicked(e -> {
                    setSimulado(simulado, "aluno");
                });
                vbox.getChildren().add(btn);
            }
        }

        painelPrincipal.setTop(new Label("Escolha o simulado: "));
        painelPrincipal.setCenter(vbox);
    }

    private void carregarOpcoes(Facilitador facilitador) {
        ArrayList<Simulado> simulados = dao.listarComFiltro(Simulado.class, "facilitadorMatricula", facilitador.getMatricula());
        VBox vbox = new VBox();

        for(Simulado simulado : simulados) {
            Assunto assunto = dao.buscar(Assunto.class, "id", simulado.getAssuntoId());
            String nome = Integer.toString(simulado.getId()) + " : " + assunto.getNome();
            if(simulado.isRespondido()) {
                JFXButton btn = new JFXButton(nome);
                btn.setOnMouseClicked(e -> {
                    setSimulado(simulado, "facilitador");
                });
                vbox.getChildren().add(btn);
            } else if(simulado.isCorrigido()) {
                Label simuladoCorrigido = new Label(nome + "(Corrigido)");
                vbox.getChildren().add(simuladoCorrigido);
            } else {
                Label simuladoIndisponivel = new Label(nome + "(Não respondido)");
                vbox.getChildren().add(simuladoIndisponivel);
            }
        }

        painelPrincipal.setTop(new Label("Escolha o simulado: "));
        painelPrincipal.setCenter(vbox);
    }

    private void setSimulado(Simulado simulado, String alunoOuFacilitador) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/painelSimulado.fxml"));
            painelPai.setCenter(fxmlLoader.load());

            PainelSimulado psc = fxmlLoader.getController();
            if(alunoOuFacilitador.equals("aluno"))
                psc.carregarSimuladoAluno(simulado);
            else
                psc.carregarSimuladoFacilitador(simulado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPainelPai(BorderPane painelPai) {
        this.painelPai = painelPai;
    }
}
