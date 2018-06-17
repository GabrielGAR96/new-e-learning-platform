package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private ListView<Double> notaView;

    private Dao dao;

    public PainelSimulado() {
        dao = new Dao();
    }

    public ArrayList<ToggleGroup> opcoesDeRespostas;

    public ArrayList<ToggleGroup> corretasEIncorretas;


    public ArrayList<JFXTextArea> respostasSub;

    public Simulado simuladoCarregado;

    public String alunoOuFacilitador;

    @FXML
    void initialize() {
        opcoesDeRespostas = new ArrayList<>();
        respostasSub = new ArrayList<>();
    }


    public void carregarSimuladoAluno(Simulado simulado) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulados = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", simulado.getId());
        ArrayList<RespostaDoSimulado> respostasDoSimulados = dao.listarComFiltro(RespostaDoSimulado.class, "simuladoId", simulado.getId());

        ArrayList<Resposta> respostas = new ArrayList<>();
        for (RespostaDoSimulado rds : respostasDoSimulados) {
            respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
        }

        ArrayList<Pergunta> perguntas = new ArrayList<>();
        for (PerguntaDoSimulado pds : perguntasDoSimulados) {
            perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
        }

        for (int i = 1; i < perguntas.size(); i++ ) {
            TitledPane tp = new TitledPane();
            VBox vb = new VBox();
            vb.setSpacing(20);
            vb.setAlignment(Pos.TOP_LEFT);

            vb.getChildren().add(new Label(perguntas.get(i).getTexto()));

            ToggleGroup tg = new ToggleGroup();
            if (perguntas.get(i).getTipo().equals("obj")) {
                for (Resposta resposta : respostas) {
                    if (resposta.getPerguntaId() == perguntas.get(i).getId()) {
                        JFXRadioButton rBtn = new JFXRadioButton(resposta.getTexto());
                        rBtn.setSelectedColor(Color.web("#6a1b9a"));
                        rBtn.setToggleGroup(tg);
                        vb.getChildren().add(rBtn);
                    }
                }
            } else {
                JFXTextArea tf = new JFXTextArea();
                tf.setPromptText("Insira sua resposta");
                tf.setLabelFloat(true);
                respostasSub.add(tf);
                vb.getChildren().add(tf);
            }

            opcoesDeRespostas.add(tg);
            tp.setText("Questão " + Integer.toString(i++));
            tp.setContent(vb);

            accordion.getPanes().add(tp);
        }

        if (simulado.getNota() > -1) {
            notaView.getItems().add(simulado.getNota());
        }

        simuladoCarregado = simulado;
        alunoOuFacilitador = "aluno";
    }

    public void carregarSimuladoFacilitador(Simulado simulado) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulados = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", simulado.getId());
        ArrayList<RespostaDoSimulado> respostasDoSimulados = dao.listarComFiltro(RespostaDoSimulado.class, "simuladoId", simulado.getId());

        ArrayList<Resposta> respostas = new ArrayList<>();
        for (RespostaDoSimulado rds : respostasDoSimulados) {
            respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
        }

        ArrayList<Pergunta> perguntas = new ArrayList<>();
        for (PerguntaDoSimulado pds : perguntasDoSimulados) {
            perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
        }

        for (int i = 1; i < perguntas.size(); i++ ) {
            TitledPane tp = new TitledPane();
            VBox vb = new VBox();
            vb.setSpacing(20);
            vb.setAlignment(Pos.TOP_LEFT);

            vb.getChildren().add(new Label(perguntas.get(i).getTexto()));

            ToggleGroup tg = new ToggleGroup();
            if (perguntas.get(i).getTipo().equals("sub")) {
                for (Resposta resposta : respostas) {
                    if (resposta.getPerguntaId() == perguntas.get(i).getId()) {
                        JFXListView<String> respostaDaPergunta = new JFXListView<>();
                        respostaDaPergunta.getItems().add(resposta.getTexto());

                        JFXRadioButton correto = new JFXRadioButton("correta");
                        JFXRadioButton incorreto = new JFXRadioButton("incorreta");

                        correto.setSelectedColor(Color.web("#6a1b9a"));
                        incorreto.setSelectedColor(Color.web("#6a1b9a"));

                        correto.setToggleGroup(tg);
                        incorreto.setToggleGroup(tg);

                        vb.getChildren().add(respostaDaPergunta);
                        vb.getChildren().add(correto);
                        vb.getChildren().add(incorreto);
                    }
                }
            }

            corretasEIncorretas.add(tg);
            tp.setText("Questão " + Integer.toString(i++));
            tp.setContent(vb);

            accordion.getPanes().add(tp);
        }


        simuladoCarregado = simulado;
        alunoOuFacilitador = "facilitador";

    }

    public void enviarSimulado(ActionEvent actionEvent) {
        if(alunoOuFacilitador.equals("aluno")) {
            int questoesCertas = 0;

            for (int i = 0; i < accordion.getPanes().size(); i++) {
                VBox vBox = (VBox) accordion.getPanes().get(i).getContent();

                String textoPergunta = ((Label) vBox.getChildren().get(0)).getText();
                Pergunta pergunta = dao.buscar(Pergunta.class, "texto", textoPergunta);

                if (pergunta.getTipo().equals("obj")) {
                    ArrayList<Resposta> respostasDaPergunta = dao.listarComFiltro(Resposta.class, "perguntaId", pergunta.getId());
                    Resposta respostaCorreta = new Resposta();
                    for (int j = 0; j < respostasDaPergunta.size(); j++) {
                        if(respostasDaPergunta.get(j).isCorreta()) {
                            respostaCorreta = respostasDaPergunta.get(j);
                            break;
                        }
                    }

                    String respostaSelecionada = ((JFXRadioButton) opcoesDeRespostas.get(i).getSelectedToggle()).getText();

                    if (respostaSelecionada.equals(respostaCorreta.getTexto())) {
                        questoesCertas++;
                    }
                }
            }

            Simulado simulado = dao.buscar(Simulado.class, "id", simuladoCarregado.getId());

            simulado.setNota(questoesCertas);

            dao.alterar(simulado, simuladoCarregado.getId());

        } else {
            Simulado simulado = dao.buscar(Simulado.class, "id", simuladoCarregado.getId());

            for(ToggleGroup tg : corretasEIncorretas) {
                String correcao = ((JFXRadioButton) tg.getSelectedToggle()).getText();
                if(correcao.equals("correta")) {
                    simulado.setNota(simulado.getNota() + 1);
                }
            }

            double nota;

            ArrayList<PerguntaDoSimulado> perguntasDoSimulado = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", simulado.getId());

            nota = simulado.getNota() / perguntasDoSimulado.size();

            simulado.setNota(nota);

            dao.alterar(simulado, simulado.getId());

        }

//TODO: quando enviado, aparece popup e volta para tela de seleção de simulados (fazer botao p deslogar?)


    }


}
