package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;

public class PainelSimulado {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private Accordion accordion;

    @FXML
    private JFXListView<Double> notaView;

    private Dao dao;

    public PainelSimulado() {
        dao = new Dao();
    }

    public ArrayList<ToggleGroup> opcoes;

    public ArrayList<JFXTextField> respostasSub;

    public int idDoSimulado;

    @FXML
    void initialize() {
        opcoes = new ArrayList<>();
        respostasSub = new ArrayList<>();
    }


    public void setSimulado(int id) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulados = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", id);
        ArrayList<RespostaDoSimulado> respostasDoSimulados = dao.listarComFiltro(RespostaDoSimulado.class, "simuladoId", id);

        ArrayList<Resposta> respostas = new ArrayList<>();
        for (RespostaDoSimulado rds : respostasDoSimulados) {
            respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
        }

        ArrayList<Pergunta> perguntas = new ArrayList<>();
        for (PerguntaDoSimulado pds : perguntasDoSimulados) {
            perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
        }

        int i = 1;
        for (Pergunta pergunta : perguntas) {
            TitledPane tp = new TitledPane();
            VBox vb = new VBox();
            vb.setSpacing(20);
            vb.setAlignment(Pos.TOP_LEFT);

            vb.getChildren().add(new Label(pergunta.getTexto()));

            ToggleGroup tg = new ToggleGroup();
            if (pergunta.getTipo().equals("obj")) {
                for (Resposta resposta : respostas) {
                    if (resposta.getPerguntaId() == pergunta.getId()) {
                        JFXRadioButton rBtn = new JFXRadioButton(resposta.getTexto());
                        rBtn.setSelectedColor(Color.web("#6a1b9a"));
                        rBtn.setToggleGroup(tg);
                        vb.getChildren().add(rBtn);
                    }
                }
            } else {
                JFXTextField tf = new JFXTextField();
                tf.setPromptText("Insira sua resposta");
                tf.setLabelFloat(true);
                respostasSub.add(tf);
                vb.getChildren().add(tf);
            }

            opcoes.add(tg);
            tp.setText("Questão " + Integer.toString(i++));
            tp.setContent(vb);

            accordion.getPanes().add(tp);

            idDoSimulado = id;
        }


    }

    public void enviarSimulado(ActionEvent actionEvent) {
        int totalDeQuestoes = accordion.getPanes().size();
        int questoesCertas = 0;

        for(int i = 0; i < accordion.getPanes().size(); i++) {
            VBox vBox = (VBox) accordion.getPanes().get(i).getContent();

            String textoPergunta = ((Label) vBox.getChildren().get(0)).getText();
            Pergunta pergunta = dao.buscar(Pergunta.class, "texto", textoPergunta);

            if(pergunta.getTipo().equals("obj")) {
                ArrayList<Resposta> respostasDaPergunta = dao.listarComFiltro(Resposta.class, "perguntaId", pergunta.getId());
                Resposta respostaCorreta = new Resposta();
                for(int j = 0; j < respostasDaPergunta.size(); j ++) {
                    respostaCorreta = respostasDaPergunta.get(j).isCorreta()? respostasDaPergunta.get(j) : null;
                }

                String respostaSelecionada = ((JFXRadioButton) opcoes.get(i).getSelectedToggle()).getText();

                if(respostaSelecionada.equals(respostaCorreta.getTexto())) {
                    questoesCertas++;
                }
            } else {
                //TODO: TRATAR RESPOSTAS SUBJETIVAS
            }
        }

        double nota = questoesCertas / totalDeQuestoes;

        Simulado simulado = dao.buscar(Simulado.class, "id", idDoSimulado);

        simulado.setNota(nota);

        dao.alterar(simulado, idDoSimulado);

        notaView.getItems().clear();
        notaView.getItems().add(nota);


    }
}
