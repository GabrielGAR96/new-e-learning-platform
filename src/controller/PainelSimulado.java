package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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

    private ArrayList<ToggleGroup> gruposDeOpcoesDeRespostasObjetivas;

    private ArrayList<JFXTextArea> respostasSubjetivas;

    private ArrayList<Integer> perguntasSubjetivas;

    private ArrayList<ToggleGroup> correcoesDasQuestoesSubjetivas;

    private Simulado simuladoAtual;

    private ArrayList<Resposta> respostasDoSimulado;
    private ArrayList<Pergunta> perguntasDoSimulado;

    private String modoDoSimulado;

    @FXML
    void initialize() {
        gruposDeOpcoesDeRespostasObjetivas = new ArrayList<>();
        respostasSubjetivas = new ArrayList<>();
        perguntasSubjetivas = new ArrayList<>();
        correcoesDasQuestoesSubjetivas = new ArrayList<>();

    }

    public void setSimuladoAtual(Simulado simulado) {
        this.simuladoAtual = simulado;
        respostasDoSimulado = getRespostas(simulado);
        perguntasDoSimulado = getPerguntas(simulado);
    }

    public void carregarSimuladoAluno() {
        modoDoSimulado = "avaliação";

        int numeroDaPergunta = 1;
        for(Pergunta pergunta : perguntasDoSimulado) {
            TitledPane painelDaPergunta = new TitledPane();
            VBox conteudoDoPainel = criarVBoxConfigurada();

            Label textoDaPergunta = new Label(pergunta.getTexto());

            conteudoDoPainel.getChildren().add(textoDaPergunta);

            if(pergunta.getTipo().equals("sub")) {
                JFXTextArea campoParaResposta = criarTextAreaConfigurada();
                respostasSubjetivas.add(campoParaResposta);
                perguntasSubjetivas.add(pergunta.getId());
                conteudoDoPainel.getChildren().add(campoParaResposta);
            }
            if(pergunta.getTipo().equals("obj")) {
                ToggleGroup opcoesDeResposta = new ToggleGroup();
                for(Resposta resposta : respostasDoSimulado) {
                    if(resposta.getPerguntaId() == pergunta.getId()) {
                        JFXRadioButton opcaoDeResposta = criarOpcaoDeResposta(resposta, opcoesDeResposta);
                        conteudoDoPainel.getChildren().add(opcaoDeResposta);
                    }
                }
                gruposDeOpcoesDeRespostasObjetivas.add(opcoesDeResposta);
            }

            painelDaPergunta.setText("Questão: " + numeroDaPergunta++);
            painelDaPergunta.setContent(conteudoDoPainel);
            paineisDePerguntas.getPanes().add(painelDaPergunta);
        }
    }

    public void carregarSimuladoFacilitador() {
        modoDoSimulado = "correção";

        int numeroDaPergunta = 1;
        for(Pergunta pergunta : perguntasDoSimulado) {
            if(pergunta.getTipo().equals("sub")) {
                TitledPane painelDaPergunta = new TitledPane();
                VBox conteudoDoPainel = criarVBoxConfigurada();

                Label textoDaPergunta = new Label(pergunta.getTexto());

                conteudoDoPainel.getChildren().add(textoDaPergunta);

                ToggleGroup correcaoDaQuestao = new ToggleGroup();

                Resposta respostaEnviadaPeloAluno = dao.buscar(Resposta.class, "perguntaId", pergunta.getId());

                adicionarRespostaSubjetivaParaCorrecao(respostaEnviadaPeloAluno, correcaoDaQuestao, conteudoDoPainel);

                correcoesDasQuestoesSubjetivas.add(correcaoDaQuestao);
                painelDaPergunta.setText("Questão: " + numeroDaPergunta++);
                painelDaPergunta.setContent(conteudoDoPainel);
                paineisDePerguntas.getPanes().add(painelDaPergunta);
            }
        }
    }

    public void enviarSimulado(javafx.event.ActionEvent actionEvent) {
        if(modoDoSimulado.equals("avaliação")) {
            for(ToggleGroup opcoesDeRespostasObjetivas : gruposDeOpcoesDeRespostasObjetivas) {
                JFXRadioButton opcaoSelecionada = (JFXRadioButton) opcoesDeRespostasObjetivas.getSelectedToggle();
                Resposta resposta = dao.buscar(Resposta.class, "texto", opcaoSelecionada.getText());
                RespostaDoSimulado respostaDoSimulado = dao.buscar(RespostaDoSimulado.class, "respostaId", resposta.getId());
                respostaDoSimulado.setSelecionada(true);
                dao.alterar(respostaDoSimulado, respostaDoSimulado.getId());
            }

            for(JFXTextArea respostaSubjetiva : respostasSubjetivas) {
                String textoDaResposta = respostaSubjetiva.getText();
                int perguntaId = perguntasSubjetivas.get(respostasSubjetivas.indexOf(respostaSubjetiva));

                perguntasSubjetivas.indexOf(perguntaId);

                Resposta resposta = dao.inserir(new Resposta(textoDaResposta, simuladoAtual.getAssuntoId(), perguntaId, false));

                dao.inserir(new RespostaDoSimulado(simuladoAtual.getId(), resposta.getId(), true));

            }

            simuladoAtual.setRespondido(true);
            dao.alterar(simuladoAtual, simuladoAtual.getId());
            setSimuladoAtual(simuladoAtual); //Alterações no BD requerem update do simulado.

        }

        if(modoDoSimulado.equals("correção")) {
            int questoesCertas = 0;

            int indiceDeQuestaoSubjetiva = 0;
            for(Pergunta pergunta : perguntasDoSimulado) {
                if(pergunta.getTipo().equals("obj")) {
                    Resposta respostaCorreta = getRespostaCorreta(pergunta);
                    Resposta respostaSelecionada = getRespostaSelecionada(pergunta);
                    if(respostaCorreta.getId() == respostaSelecionada.getId()) {
                        questoesCertas++;
                    }
                }

                if(pergunta.getTipo().equals("sub")) {
                    ToggleGroup correcaoDaQuestaoSubjetiva = correcoesDasQuestoesSubjetivas.get(indiceDeQuestaoSubjetiva);
                    JFXRadioButton opcaoSelecionada = (JFXRadioButton) correcaoDaQuestaoSubjetiva.getSelectedToggle();
                    if(opcaoSelecionada.getText().equals("correta")) {
                        questoesCertas++;
                    }
                }
            }

            setNota(questoesCertas);

            simuladoAtual.setCorrigido(true);
            dao.alterar(simuladoAtual, simuladoAtual.getId());
            setSimuladoAtual(simuladoAtual); //Alterações no BD requerem update do simulado.
        }
    }

    private void setNota(int questoesCertas) {
        double nota;

        int totalDeQuestoes = perguntasDoSimulado.size();

        DecimalFormat df = new DecimalFormat("#.##");
        nota = Double.parseDouble(df.format((questoesCertas / totalDeQuestoes) * 10));

        simuladoAtual.setNota(nota);

        simuladoAtual.setCorrigido(true);

    }

    private Resposta getRespostaSelecionada(Pergunta pergunta) {
        Resposta respostaSelecionada = new Resposta();
        for(Resposta respostaDaPergunta : respostasDoSimulado) {
            RespostaDoSimulado respostaCorrespondenteNoSimulado = dao.buscar(RespostaDoSimulado.class,
                    "respostaId", respostaDaPergunta.getId());
            if(respostaCorrespondenteNoSimulado.isSelecionada() && respostaDaPergunta.getPerguntaId() == pergunta.getId()) {
                respostaSelecionada = respostaDaPergunta;
            }
        }
        return respostaSelecionada;
    }

    private Resposta getRespostaCorreta(Pergunta pergunta) {
        Resposta respostaCorreta = new Resposta();
        for(Resposta respostaDaPergunta : respostasDoSimulado) {
            if(respostaDaPergunta.isCorreta() && respostaDaPergunta.getPerguntaId() == pergunta.getId()) {
                respostaCorreta = respostaDaPergunta;
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

    private void adicionarRespostaSubjetivaParaCorrecao(Resposta resposta, ToggleGroup correcaoDaQuestao, VBox conteudoDoPainel) {
        Label labelRespostaEnviada = new Label("Resposta enviada pelo aluno: ");
        conteudoDoPainel.getChildren().add(labelRespostaEnviada);

        JFXListView<String> respostaDaPergunta = new JFXListView<>();
        respostaDaPergunta.getItems().add(resposta.getTexto());
        conteudoDoPainel.getChildren().add(respostaDaPergunta);

        JFXRadioButton correto = new JFXRadioButton("correta");
        JFXRadioButton incorreto = new JFXRadioButton("incorreta");
        correto.setSelectedColor(Color.web("#6a1b9a"));
        incorreto.setSelectedColor(Color.web("#6a1b9a"));
        correto.setToggleGroup(correcaoDaQuestao);
        incorreto.setToggleGroup(correcaoDaQuestao);
        conteudoDoPainel.getChildren().add(correto);
        conteudoDoPainel.getChildren().add(incorreto);
    }

    private VBox criarVBoxConfigurada() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_LEFT);
        return vbox;
    }

    private JFXTextArea criarTextAreaConfigurada() {
        JFXTextArea textArea = new JFXTextArea();
        textArea.setPromptText("Insira sua resposta");
        textArea.setLabelFloat(true);
        return textArea;
    }

    private JFXRadioButton criarOpcaoDeResposta(Resposta resposta, ToggleGroup opcoesDeResposta) {
        JFXRadioButton opcaoDeResposta = new JFXRadioButton(resposta.getTexto());
        opcaoDeResposta.setSelectedColor(Color.web("#6a1b9a"));
        opcaoDeResposta.setToggleGroup(opcoesDeResposta);
        return opcaoDeResposta;
    }

}