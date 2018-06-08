package controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.*;

import java.time.ZoneId;
import java.util.*;

public class PainelCadastrarController {

    @FXML
    private BorderPane painelPai;

    @FXML
    private JFXTabPane paneCadastros;

    @FXML
    private Tab alunoTab;

    @FXML
    private JFXTextField nomeAlunoField;

    @FXML
    private JFXListView<String> disciplinasDisponiveisAlunoList;

    @FXML
    private JFXListView<String> disciplinasSelecionadasAlunoList;

    @FXML
    private Tab facilitadorTab;

    @FXML
    private JFXTextField nomeFacilitadorField;

    @FXML
    private JFXListView<String> disciplinasDisponiveisFacilitadorList;

    @FXML
    private JFXListView<String> disciplinasSelecionadasFacilitadorList;

    @FXML
    private Tab disciplinaTab;

    @FXML
    private JFXTextField nomeDisciplinaField;

    @FXML
    private JFXTextField valorDisciplinaField;

    @FXML
    private JFXListView<String> assuntoDisciplinaList;

    @FXML
    private JFXTextField assuntoDisciplinaField;

    @FXML
    private JFXButton adicionarAssuntoDisciplinaBtn;

    @FXML
    private Tab assuntoTab;

    @FXML
    private JFXTextField nomeAssuntoField;

    @FXML
    private JFXComboBox<String> disciplinaAssuntoCombo;

    @FXML
    private Tab perguntaTab;

    @FXML
    private JFXTextArea textoPerguntaField;

    @FXML
    private JFXComboBox<String> assuntoPerguntaCombo;

    @FXML
    private JFXComboBox<String> disciplinaPerguntaCombo;

    @FXML
    private JFXTextArea respostaPerguntaField;

    @FXML
    private JFXListView<String> respostaPerguntaList;

    @FXML
    private JFXButton adicionarRespostaPerguntaBtn;

    @FXML
    private JFXToggleButton respObjPerguntaToggle;

    @FXML
    private Tab pagamentoTab;

    @FXML
    private JFXToggleButton alunoOuFacilitadorPagamentoToggle;

    @FXML
    private Label alunoPagamentoLabel;

    @FXML
    private JFXTextField matriculaPagamentoField;

    @FXML
    private JFXTextField valorAlunoPagamentoField;

    @FXML
    private JFXDatePicker dataPagamentoDP;

    @FXML
    private JFXToggleButton simuladoOuDuvidaPagamentoToggle;

    @FXML
    private JFXTextField idSimDuvPagamentoField;

    @FXML
    private Label simuladoPagamentoLabel;

    @FXML
    private JFXListView<String> valorFacilitadorPagamentoList;

    @FXML
    private Tab duvidaTab;

    @FXML
    private JFXTextArea perguntaDuvidaField;

    @FXML
    private JFXComboBox<String> alunoDuvidaCombo;

    @FXML
    private JFXComboBox<String> facilitadorDuvidaCombo;

    @FXML
    private Tab gerarSimuladoTab;

    @FXML
    private JFXComboBox<String> alunoSimuladoCombo;

    @FXML
    private JFXComboBox<String> disciplinaSimuladoCombo;

    @FXML
    private JFXListView<String> questoesSimuladoView;

    @FXML
    private JFXComboBox<String> assuntoSimuladoCombo;

    @FXML
    private JFXButton gerarSimuladoBtn;

    @FXML
    private JFXButton finalizarCadastroBtn;

    @FXML
    private Label valorPagamentoLabel;

    @FXML
    private Label respostasLabel;

    @FXML
    private JFXTextField inscricaoAlunoPagamentoField;

    private RequiredFieldValidator rqValidator;

    private DoubleValidator dValidator;

    private NumberValidator numberValidator;

    private Dao dao;

    public PainelCadastrarController() {
        dao = new Dao();
    }

    @FXML
    public void initialize() {
        adicionarValidators();
        alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: gray");
        simuladoPagamentoLabel.setStyle("-fx-text-fill: black");
        valorFacilitadorPagamentoList.getItems().add("100"); //TODO: DECIDIR PREÇOS E FIXAR EM ALGUM LOCAL
    }

    public void adicionarAssuntoDisciplina(javafx.event.ActionEvent actionEvent) {
        String assunto = assuntoDisciplinaField.getText();
        assuntoDisciplinaField.clear();
        assuntoDisciplinaList.getItems().add(assunto);
    }

    public void adicionarAssuntoDisciplinaEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String assunto = assuntoDisciplinaField.getText();
            assuntoDisciplinaField.clear();
            assuntoDisciplinaList.getItems().add(assunto);
        }
    }

    public void finalizarCadastro(javafx.event.ActionEvent actionEvent) {

        if (alunoTab.isSelected()) CadastrarAluno();

        else if (facilitadorTab.isSelected()) CadastrarFacilitador();

        else if (disciplinaTab.isSelected()) CadastrarDisciplina();

        else if (assuntoTab.isSelected()) CadastrarAssunto();

        else if (perguntaTab.isSelected()) CadastrarPergunta();

        else if (pagamentoTab.isSelected()) CadastrarPagamento();

        else if (duvidaTab.isSelected()) CadastrarDuvida();

        else CadastrarSimulado();

        JFXSnackbar cadastroSnackBar = new JFXSnackbar(painelPai);
        cadastroSnackBar.show("Cadastro realizado com sucesso!", 1500);

        //TODO: Aqui vão ser RECARREGADAS todas as tabelas de seleção e comboboxes
    }

    public void CadastrarAluno() {
        String nomeAluno = nomeAlunoField.getText();
        ArrayList<String> disciplinasSelecionadas = new ArrayList<>(disciplinasSelecionadasAlunoList.getItems());

        Aluno aluno = dao.inserir(new Aluno(nomeAluno));

        java.util.Date data = new Date();
        Inscricao inscricao = new Inscricao(aluno.getMatricula(), data);
        inscricao = dao.inserir(inscricao);

        Disciplina disciplina;
        for (String item : disciplinasSelecionadas) {
            disciplina = dao.buscar(Disciplina.class, "nome", item);
            dao.inserir(new DisciplinaInscrita(disciplina.getId(), inscricao.getId()));
        }
    }

    public void CadastrarFacilitador() {
        String nomeFacilitador = nomeFacilitadorField.getText();
        ArrayList<String> disciplinasSelecionadas = new ArrayList<>(disciplinasSelecionadasFacilitadorList.getItems());

        Facilitador facilitador = dao.inserir(new Facilitador(nomeFacilitador));

        Disciplina disciplina;
        for (String item : disciplinasSelecionadas) {
            disciplina = dao.buscar(Disciplina.class, "nome", item);
            dao.inserir(new GrupoDeDisciplinas(facilitador.getMatricula(), disciplina.getId()));
        }
    }

    public void CadastrarDisciplina() {
        String nomeDisciplina = nomeDisciplinaField.getText();
        double valorDisciplina = Double.parseDouble(valorDisciplinaField.getText());
        ArrayList<String> assuntosDisciplina = new ArrayList<>(assuntoDisciplinaList.getItems());

        Disciplina disciplina = dao.inserir(new Disciplina(nomeDisciplina, valorDisciplina));

        for (String item : assuntosDisciplina) {
            dao.inserir(new Assunto(item, disciplina.getId()));
        }
    }

    public void CadastrarAssunto() {
        String nomeAssunto = nomeAssuntoField.getText();
        String nomeDisciplina = disciplinaAssuntoCombo.getValue();

        Disciplina disciplina = dao.buscar(Disciplina.class, "nome", nomeDisciplina);

        dao.inserir(new Assunto(nomeAssunto, disciplina.getId()));
    }

    public void CadastrarPergunta() {
        String textoPergunta = textoPerguntaField.getText();
        String assuntoPergunta = assuntoPerguntaCombo.getValue();
        ArrayList<String> respostasPergunta = new ArrayList<>(respostaPerguntaList.getItems());
        String tipo = respObjPerguntaToggle.isSelected() ? "obj" : "sub";

        Assunto assunto = dao.buscar(Assunto.class, "nome", assuntoPergunta);

        if(respObjPerguntaToggle.isSelected()) {
            for (String item : respostasPergunta) {
                dao.inserir(new Resposta(item, tipo, assunto.getId()));
            }

            Resposta respostaCorreta = dao.buscar(Resposta.class, "texto", respostasPergunta.get(0));

            dao.inserir(new Pergunta(textoPergunta, assunto.getId(), respostaCorreta.getId()));

            respostaPerguntaList.getItems().clear();
            respostasLabel.setText("Insira a resposta correta: ");
            respostasLabel.setTextFill(Color.web("#00ff00"));
        } else {
            dao.inserir(new Pergunta(textoPergunta, assunto.getId(), 0));
        }


    }

    public void CadastrarPagamento() {
        int matriculaPagamento = Integer.parseInt(matriculaPagamentoField.getText());
        int inscricaoId = Integer.parseInt(inscricaoAlunoPagamentoField.getText());
        Date dataPagamento = Date.from(dataPagamentoDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        double valorPagamento;
        int idSim;
        int idDuv;

        if (alunoOuFacilitadorPagamentoToggle.isSelected()) {
            valorPagamento = Double.parseDouble(valorFacilitadorPagamentoList.getItems().get(0));
            idSim = simuladoOuDuvidaPagamentoToggle.isSelected() ? 0 : Integer.parseInt(idSimDuvPagamentoField.getText());
            idDuv = simuladoOuDuvidaPagamentoToggle.isSelected() ? Integer.parseInt(idSimDuvPagamentoField.getText()) : 0;
            dao.inserir(new PagamentoFacilitador(valorPagamento, dataPagamento, matriculaPagamento, idSim, idDuv));
        } else {
            valorPagamento = Double.parseDouble(valorAlunoPagamentoField.getText());
            dao.inserir(new PagamentoAluno(valorPagamento, dataPagamento, inscricaoId));
        }
    }

    public void CadastrarSimulado() {
        String alunoSimulado = alunoSimuladoCombo.getValue();
        String assuntoSimulado = assuntoSimuladoCombo.getValue();

    }

    public void CadastrarDuvida() {
        Aluno aluno = dao.buscar(Aluno.class, "nome", alunoDuvidaCombo.getValue());
        Facilitador facilitador = dao.buscar(Facilitador.class, "nome", facilitadorDuvidaCombo.getValue());
        String textoDuvida = perguntaDuvidaField.getText();

        dao.inserir(new Duvida(textoDuvida, aluno.getMatricula(), facilitador.getMatricula()));
    }

    public void valorDisciplinaDigitado(KeyEvent inputMethodEvent) {
        valorDisciplinaField.validate();
    }

    public void valorPagamentoDigitado(KeyEvent keyEvent) {
        valorAlunoPagamentoField.validate();
    }

    public void nomeAlunoDigitado(KeyEvent keyEvent) {
        nomeAlunoField.validate();
    }

    public void nomeFacilitadorDigitado(KeyEvent keyEvent) {
        nomeFacilitadorField.validate();
    }

    public void matriculaPagamentoDigitado(KeyEvent keyEvent) {
        matriculaPagamentoField.validate();
    }

    public void idPagamentoDigitado(KeyEvent keyEvent) {
        idSimDuvPagamentoField.validate();
    }

    private void adicionarValidators() {
        rqValidator = new RequiredFieldValidator();
        dValidator = new DoubleValidator();
        numberValidator = new NumberValidator();

        rqValidator.setMessage("Campo Obrigatório");
        dValidator.setMessage("Por favor, insira um valor válido.");
        numberValidator.setMessage("Por favor, insira um número válido.");


        valorDisciplinaField.getValidators().add(dValidator);
        valorAlunoPagamentoField.getValidators().add(dValidator);
        nomeFacilitadorField.getValidators().add(rqValidator);
        matriculaPagamentoField.getValidators().add(numberValidator);
        idSimDuvPagamentoField.getValidators().add(numberValidator);

    }

    public void tabAlunoSelecionada(Event event) {
        disciplinasDisponiveisAlunoList.getItems().clear();

        List<Disciplina> disciplinas = dao.listar(Disciplina.class);
        for (Disciplina disciplina : disciplinas) {
            disciplinasDisponiveisAlunoList.getItems().add(disciplina.getNome());
        }

    }

    public void tabFacilitadorSelecionada(Event event) {
        disciplinasDisponiveisFacilitadorList.getItems().clear();

        List<Disciplina> disciplinas = dao.listar(Disciplina.class);
        for (Disciplina disciplina : disciplinas) {
            disciplinasDisponiveisFacilitadorList.getItems().add(disciplina.getNome());
        }
    }

    public void tabAssuntoSelecionada(Event event) {
        disciplinaListToComboBox(disciplinaAssuntoCombo);
    }

    public void tabPerguntaSelecionada(Event event) {
        disciplinaListToComboBox(disciplinaPerguntaCombo);
    }

    public void tabDuvidaSelecionada(Event event) {
        alunoListToComboBox(alunoDuvidaCombo);


        List<Facilitador> facilitadores = dao.listar(Facilitador.class);
        List<String> nomeFacilitadores = new ArrayList<>();
        for (Facilitador facilitador : facilitadores) {
            nomeFacilitadores.add(facilitador.getNome());
        }
        ObservableList<String> facilitadoresObservable = FXCollections.observableArrayList(nomeFacilitadores);
        facilitadorDuvidaCombo.setItems(facilitadoresObservable);
    }

    public void tabGerarSimuladoSelecionada(Event event) {
       alunoListToComboBox(alunoSimuladoCombo);
       disciplinaListToComboBox(disciplinaSimuladoCombo);
    }

    private void disciplinaListToComboBox(JFXComboBox<String> combo) {
        List<Disciplina> disciplinas = dao.listar(Disciplina.class);
        List<String> nomeDisciplinas = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            nomeDisciplinas.add(disciplina.getNome());
        }
        ObservableList<String> disciplinaObservableList = FXCollections.observableArrayList(nomeDisciplinas);
        combo.setItems(disciplinaObservableList);
    }

    private void assuntoListToComboBox(JFXComboBox<String> combo) {
        List<Assunto> assuntos = dao.listar(Assunto.class);
        List<String> nomeAssuntos = new ArrayList<>();
        for (Assunto assunto : assuntos) {
            nomeAssuntos.add(assunto.getNome());
        }
        ObservableList<String> assuntoObservableList = FXCollections.observableArrayList(nomeAssuntos);
        combo.setItems(assuntoObservableList);
    }

    private void alunoListToComboBox(JFXComboBox<String> combo) {
        List<Aluno> alunos = dao.listar(Aluno.class);
        List<String> nomeAlunos = new ArrayList<>();
        for (Aluno aluno : alunos) {
            nomeAlunos.add(aluno.getNome());
        }
        ObservableList<String> alunosObservable = FXCollections.observableArrayList(nomeAlunos);
        combo.setItems(alunosObservable);
    }

    private void transferirItem(MouseEvent mouseEvent, JFXListView<String> origem, JFXListView<String> destino) {
        if (mouseEvent.getClickCount() == 2) {
            int indexSelecionado = origem.getSelectionModel().getSelectedIndex();
            String selecionado = origem.getSelectionModel().getSelectedItem();
            origem.getItems().remove(indexSelecionado);
            destino.getItems().add(selecionado);
        }
    }

    public void clicarDisciplinasAluno(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, disciplinasDisponiveisAlunoList, disciplinasSelecionadasAlunoList);
    }

    public void removerDisciplinasSelecionadasAluno(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, disciplinasSelecionadasAlunoList, disciplinasDisponiveisAlunoList);
    }

    public void clicarDisciplinasFacilitador(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, disciplinasDisponiveisFacilitadorList, disciplinasSelecionadasFacilitadorList);

    }

    public void removerDisciplinasSelecionadasFacilitador(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, disciplinasSelecionadasFacilitadorList, disciplinasDisponiveisFacilitadorList);

    }

    public void removerAssuntosAdicionados(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            int indexSelecionado = assuntoDisciplinaList.getSelectionModel().getSelectedIndex();
            assuntoDisciplinaList.getItems().remove(indexSelecionado);
        }
    }

    public void disciplinaSelecionadaPergunta(ActionEvent actionEvent) {
        assuntoToComboBox(disciplinaPerguntaCombo, assuntoPerguntaCombo);
    }

    public void toggleRespObj(ActionEvent actionEvent) {
        if (respObjPerguntaToggle.isSelected()) {
            respObjPerguntaToggle.setStyle("-fx-font-weight: bold");
            respostaPerguntaField.setDisable(false);
            adicionarRespostaPerguntaBtn.setDisable(false);
            respostaPerguntaList.setDisable(false);
            respostasLabel.setDisable(false);
        } else {
            respObjPerguntaToggle.setStyle("-fx-font-weight: normal");
            respostaPerguntaField.setDisable(true);
            adicionarRespostaPerguntaBtn.setDisable(true);
            respostaPerguntaList.setDisable(true);
            respostasLabel.setDisable(true);
        }

    }

    public void adicionarRespostaPergunta(ActionEvent actionEvent) {
        if (respostaPerguntaList.getItems().isEmpty()) {
            respostasLabel.setText("Insira alternativas falsas.");
            respostasLabel.setTextFill(Color.web("#cc0000"));
        }
        String resposta = respostaPerguntaField.getText();
        respostaPerguntaList.getItems().add(resposta);
        respostaPerguntaField.clear();
    }

    public void adicionarRespostaPerguntaEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (respostaPerguntaList.getItems().isEmpty()) {
                respostasLabel.setText("Insira alternativas falsas.");
                respostasLabel.setTextFill(Color.web("#cc0000"));
            }
            String resposta = respostaPerguntaField.getText();
            respostaPerguntaList.getItems().add(resposta);
            respostaPerguntaField.clear();
        }
    }

    public void toggleAlunoFacilitador(ActionEvent actionEvent) {
        if (alunoOuFacilitadorPagamentoToggle.isSelected()) {
            valorAlunoPagamentoField.setDisable(true);
            simuladoOuDuvidaPagamentoToggle.setDisable(false);
            simuladoPagamentoLabel.setDisable(false);
            idSimDuvPagamentoField.setDisable(false);
            valorPagamentoLabel.setDisable(false);
            valorFacilitadorPagamentoList.setDisable(false);
            alunoPagamentoLabel.setStyle("-fx-text-fill: gray");
            alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: black");
            inscricaoAlunoPagamentoField.setDisable(true);
        } else {
            valorAlunoPagamentoField.setDisable(false);
            simuladoOuDuvidaPagamentoToggle.setDisable(true);
            idSimDuvPagamentoField.setDisable(true);
            valorPagamentoLabel.setDisable(true);
            valorFacilitadorPagamentoList.setDisable(true);
            alunoPagamentoLabel.setStyle("-fx-text-fill: black");
            alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: gray");
            inscricaoAlunoPagamentoField.setDisable(false);
        }
    }

    public void toggleSimuladoDuvida(ActionEvent actionEvent) {
        if (simuladoOuDuvidaPagamentoToggle.isSelected()) {
            simuladoPagamentoLabel.setStyle("-fx-text-fill: gray");
            simuladoOuDuvidaPagamentoToggle.setStyle("-fx-text-fill: black");
            idSimDuvPagamentoField.setPromptText("ID da dúvida");
            valorFacilitadorPagamentoList.getItems().clear();
            valorFacilitadorPagamentoList.getItems().add("50"); //TODO: DECIDIR PREÇOS E FIXAR EM ALGUM LOCAL
        } else {
            simuladoPagamentoLabel.setStyle("-fx-text-fill: black");
            simuladoOuDuvidaPagamentoToggle.setStyle("-fx-text-fill: gray");
            idSimDuvPagamentoField.setPromptText("ID do simulado");
            valorFacilitadorPagamentoList.getItems().clear();
            valorFacilitadorPagamentoList.getItems().add("100"); //TODO: DECIDIR PREÇOS E FIXAR EM ALGUM LOCAL

        }
    }

    public void disciplinaSelecionadaSimulado(ActionEvent actionEvent) {
        assuntoToComboBox(disciplinaSimuladoCombo, assuntoSimuladoCombo);
    }

    private void assuntoToComboBox(JFXComboBox<String> disciplinaSimuladoCombo, JFXComboBox<String> assuntoSimuladoCombo) {
        String nomeDisciplina = disciplinaSimuladoCombo.getValue();
        Disciplina disciplina = dao.buscar(Disciplina.class, "nome", nomeDisciplina);

        List<Assunto> assuntos = dao.listarComFiltro(Assunto.class, "disciplinaId", disciplina.getId());
        List<String> nomeAssuntos = new ArrayList<>();
        for (Assunto item : assuntos) {
            nomeAssuntos.add(item.getNome());
        }
        ObservableList<String> assuntosObservableList = FXCollections.observableArrayList(nomeAssuntos);
        assuntoSimuladoCombo.setItems(assuntosObservableList);
    }

    public void gerarSimulado(ActionEvent actionEvent) {
        ArrayList<Integer> idsDasPerguntasDisponiveis = dao.buscarChaves(Pergunta.class);
        ArrayList<Integer> idsDasPerguntasUsadas = new ArrayList<>();

        Random random = new Random();
        Assunto assunto = dao.buscar(Assunto.class, "nome", assuntoSimuladoCombo.getValue());

        while(idsDasPerguntasUsadas.size() <= 10) {
            int id = random.nextInt(Collections.max(idsDasPerguntasDisponiveis));
            Pergunta pergunta = dao.buscar(Pergunta.class, "id", id);

            if(!idsDasPerguntasUsadas.contains(id) && pergunta.getAssuntoId() == assunto.getId()) {
                questoesSimuladoView.getItems().add(pergunta.getTexto());
                idsDasPerguntasUsadas.add(id);
            }
        }
    }


}
