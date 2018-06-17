package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PainelSimulado {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private Accordion paineisDePerguntas;

    private Dao dao;

    public PainelSimulado() {
        dao = new Dao();
    }

    private ArrayList<ToggleGroup> gruposDeOpcoesDeRespostas;

    private ArrayList<JFXTextArea> fieldsDeRespostasSubjetivas;

    private ArrayList<ToggleGroup> corretasEIncorretas;

    private Simulado simuladoAtual;

    private String modoDoSimulado;

    @FXML
    void initialize() {
        gruposDeOpcoesDeRespostas = new ArrayList<>();
        fieldsDeRespostasSubjetivas = new ArrayList<>();
        corretasEIncorretas = new ArrayList<>();
    }


    public void carregarSimuladoAluno(Simulado simulado) {
        ArrayList<Resposta> respostas = getRespostas(simulado);
        ArrayList<Pergunta> perguntas = getPerguntas(simulado);

        for (int i = 0; i < perguntas.size(); i++ ) {
            TitledPane painelDaPergunta = new TitledPane();
            VBox conteudoDoPainelDaPergunta = criarVBoxConfigurada();

            Label textoDaPergunta = new Label(perguntas.get(i).getTexto());

            conteudoDoPainelDaPergunta.getChildren().add(textoDaPergunta);

            ToggleGroup opcoesDeRespostaParaPergunta = new ToggleGroup();

            Pergunta perguntaAtual = perguntas.get(i);
            if (perguntaAtual.getTipo().equals("obj")) {
                for (Resposta resposta : respostas) {
                    if (resposta.getPerguntaId() == perguntaAtual.getId()) {
                        JFXRadioButton opcaoDeResposta = criarOpcaoDeResposta(resposta, opcoesDeRespostaParaPergunta);
                        conteudoDoPainelDaPergunta.getChildren().add(opcaoDeResposta);
                    }
                }
            } else {
                JFXTextArea campoParaResposta = criarCampoParaResposta();
                conteudoDoPainelDaPergunta.getChildren().add(campoParaResposta);
            }

            gruposDeOpcoesDeRespostas.add(opcoesDeRespostaParaPergunta);
            painelDaPergunta.setText("Questão " + Integer.toString(i + 1));
            painelDaPergunta.setContent(conteudoDoPainelDaPergunta);

            paineisDePerguntas.getPanes().add(painelDaPergunta);
        }

        simuladoAtual = simulado;
        modoDoSimulado = "aluno";
    }

    public void carregarSimuladoFacilitador(Simulado simulado) {
        ArrayList<Resposta> respostas = getRespostas(simulado);
        ArrayList<Pergunta> perguntas = getPerguntas(simulado);

        for (int i = 1; i < perguntas.size(); i++ ) {
            TitledPane painelDaPergunta = new TitledPane();
            VBox conteudoDoPainelDaPergunta = criarVBoxConfigurada();

            Label textoDaPergunta = new Label(perguntas.get(i).getTexto());

            conteudoDoPainelDaPergunta.getChildren().add(textoDaPergunta);

            ToggleGroup corretaOuIncorreta = new ToggleGroup();

            Pergunta perguntaAtual = perguntas.get(i);
            if (perguntaAtual.getTipo().equals("sub")) {
                for (Resposta resposta : respostas) {
                    if (resposta.getPerguntaId() == perguntaAtual.getId()) {
                        adicionarRespostaSubjetivaParaCorrecao(resposta, corretaOuIncorreta, conteudoDoPainelDaPergunta);
                    }
                }
            }

            corretasEIncorretas.add(corretaOuIncorreta);
            painelDaPergunta.setText("Questão " + Integer.toString(i++));
            painelDaPergunta.setContent(conteudoDoPainelDaPergunta);

            paineisDePerguntas.getPanes().add(painelDaPergunta);
        }


        simuladoAtual = simulado;
        modoDoSimulado = "facilitador";

    }

    public void enviarSimulado(ActionEvent actionEvent) {
        Simulado simulado = dao.buscar(Simulado.class, "id", simuladoAtual.getId());

        if(modoDoSimulado.equals("aluno")) {

            int j = 0;
            for (int i = 0; i < paineisDePerguntas.getPanes().size(); i++) {
                VBox painelDaPergunta = (VBox) paineisDePerguntas.getPanes().get(i).getContent();

                String textoPergunta = ((Label) painelDaPergunta.getChildren().get(0)).getText();
                Pergunta pergunta = dao.buscar(Pergunta.class, "texto", textoPergunta);

                if (pergunta.getTipo().equals("obj")) {
                    //ArrayList<Resposta> respostasDaPergunta = dao.listarComFiltro(Resposta.class, "perguntaId", pergunta.getId());
                    //Resposta respostaCorreta = getRespostaCorreta(respostasDaPergunta);

                    String textoRespostaSelecionada = ((JFXRadioButton) gruposDeOpcoesDeRespostas.get(i).getSelectedToggle()).getText();

                    setRespostaSelecionada(textoRespostaSelecionada);
                } else {
                    String textoRespostaSubjetiva = fieldsDeRespostasSubjetivas.get(j++).getText();
                    Resposta resposta = dao.inserir(new Resposta(textoRespostaSubjetiva, pergunta.getAssuntoId(), pergunta.getId(), false));
                }
            }


            simulado.setRespondido(true);

            dao.alterar(simulado, simuladoAtual.getId());

        } else {

            int questoesCorretas = questoesSubjetivasCorretas() + questoesObjetivasCorretas(simulado);

            setNota(simulado, questoesCorretas);

        }

        JFXSnackbar snackBar = new JFXSnackbar((Pane) painelPrincipal.getParent());
        snackBar.show("Simulado enviado", 2500);

    }

    private void setNota(Simulado simulado, int questoesCorretas) {
        double nota;

        ArrayList<PerguntaDoSimulado> perguntasDoSimulado = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", simulado.getId());

        int totalDeQuestoes = perguntasDoSimulado.size();

        DecimalFormat df = new DecimalFormat("#.##");
        nota = Double.parseDouble(df.format((questoesCorretas / totalDeQuestoes) * 10));

        simulado.setNota(nota);

        simulado.setCorrigido(true);

        dao.alterar(simulado, simulado.getId());
    }

    private int questoesObjetivasCorretas(Simulado simulado) {
        int questoesCorretas = 0;
        ArrayList<RespostaDoSimulado> respostasDoSimulado = dao.listarComFiltro(RespostaDoSimulado.class,"simuladoId", simulado.getId());

        for(RespostaDoSimulado respostaDoSimulado : respostasDoSimulado) {
            Resposta resposta = dao.buscar(Resposta.class, "id", respostaDoSimulado.getRespostaId());
            if(respostaDoSimulado.isSelecionada() && resposta.isCorreta()) {
                questoesCorretas++;
            }
        }
        return questoesCorretas;
    }

    private int questoesSubjetivasCorretas() {
        int questoesCorretas = 0;
        for(ToggleGroup tg : corretasEIncorretas) {
            String selecionada = ((JFXRadioButton) tg.getSelectedToggle()).getText();
            if(selecionada.equals("correta")) {
                questoesCorretas++;
            }
        }
        return questoesCorretas;
    }

    private void setRespostaSelecionada(String textoRespostaSelecionada) {
        Resposta respostaSelecionada = dao.buscar(Resposta.class, "texto", textoRespostaSelecionada);

        RespostaDoSimulado respostaDoSimuladoSelecionada = dao.buscar(RespostaDoSimulado.class, "respostaId", respostaSelecionada.getId());

        respostaDoSimuladoSelecionada.setSelecionada(true);

        dao.alterar(respostaDoSimuladoSelecionada, respostaDoSimuladoSelecionada.getRespostaId());
    }

//    private Resposta getRespostaSelecionada(ArrayList<Resposta> respostasDaPergunta) {
//
//    }

    private Resposta getRespostaCorreta(ArrayList<Resposta> respostasDaPergunta) {
        Resposta respostaCorreta = new Resposta();
        for (int j = 0; j < respostasDaPergunta.size(); j++) {
            if(respostasDaPergunta.get(j).isCorreta()) {
                respostaCorreta = respostasDaPergunta.get(j);
                break;
            }
        }
        return respostaCorreta;
    }

    private ArrayList<Resposta> getRespostas(Simulado simulado) {
        ArrayList<RespostaDoSimulado> respostasDoSimulados = dao.listarComFiltro(RespostaDoSimulado.class, "simuladoId", simulado.getId());
        ArrayList<Resposta> respostas = new ArrayList<>();
        for (RespostaDoSimulado rds : respostasDoSimulados) {
            respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
        }
        return respostas;
    }

    private ArrayList<Pergunta> getPerguntas(Simulado simulado) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulados = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", simulado.getId());
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        for (PerguntaDoSimulado pds : perguntasDoSimulados) {
            perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
        }
        return perguntas;
    }

    private JFXTextArea criarCampoParaResposta() {
        JFXTextArea tf = new JFXTextArea();
        tf.setPromptText("Insira sua resposta");
        tf.setLabelFloat(true);
        fieldsDeRespostasSubjetivas.add(tf);
        return tf;
    }

    private JFXRadioButton criarOpcaoDeResposta(Resposta resposta, ToggleGroup opcoesDeRespostaDaPergunta) {
        JFXRadioButton opcaoDeResposta = new JFXRadioButton(resposta.getTexto());
        opcaoDeResposta.setSelectedColor(Color.web("#6a1b9a"));
        opcaoDeResposta.setToggleGroup(opcoesDeRespostaDaPergunta);
        return opcaoDeResposta;
    }

    private void adicionarRespostaSubjetivaParaCorrecao(Resposta resposta, ToggleGroup corretaOuIncorreta, VBox conteudoDoPainelDaPergunta) {
        JFXListView<String> respostaDaPergunta = new JFXListView<>();
        respostaDaPergunta.getItems().add(resposta.getTexto());

        JFXRadioButton correto = new JFXRadioButton("correta");
        JFXRadioButton incorreto = new JFXRadioButton("incorreta");

        correto.setSelectedColor(Color.web("#6a1b9a"));
        incorreto.setSelectedColor(Color.web("#6a1b9a"));

        correto.setToggleGroup(corretaOuIncorreta);
        incorreto.setToggleGroup(corretaOuIncorreta);

        conteudoDoPainelDaPergunta.getChildren().add(respostaDaPergunta);
        conteudoDoPainelDaPergunta.getChildren().add(correto);
        conteudoDoPainelDaPergunta.getChildren().add(incorreto);
    }

    private VBox criarVBoxConfigurada() {
        VBox conteudoDoPainelDaPergunta = new VBox();
        conteudoDoPainelDaPergunta.setSpacing(20);
        conteudoDoPainelDaPergunta.setAlignment(Pos.TOP_LEFT);
        return conteudoDoPainelDaPergunta;
    }


}
