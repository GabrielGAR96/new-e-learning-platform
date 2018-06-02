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
import model.*;
import persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PainelCadastrarController {

    @FXML
    private BorderPane painelPai;

    @FXML
    private JFXTabPane paneCadastros;

    @FXML
    private Tab alunoTab;

    @FXML
    private JFXTextField matriculaAlunoField;

    @FXML
    private JFXTextField nomeAlunoField;

    @FXML
    private JFXListView<String> disciplinasDisponiveisAlunoList;

    @FXML
    private JFXListView<String> disciplinasSelecionadasAlunoList;

    @FXML
    private Tab facilitadorTab;

    @FXML
    private JFXTextField matriculaFacilitadorField;

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
    private JFXListView<String> facilitadoresDisponiveisDisciplinaList;

    @FXML
    private JFXListView<String> facilitadoresSelecionadosDisciplinaList;

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

    private RequiredFieldValidator rqValidator;

    private DoubleValidator dValidator;

    private NumberValidator numberValidator;

    private AlunoDao alunoDao;

    private InscricaoDao inscricaoDao;

    private DisciplinaDao disciplinaDao;

    private DisciplinaInscritaDao disciplinaInscritaDao;

    private FacilitadorDao facilitadorDao;

    private GrupoDeDisciplinasDao grupoDeDisciplinasDao;

    private AssuntoDao assuntoDao;

    private RespostaDao respostaDao;

    private PerguntaDao perguntaDao;

    private Dao dao;


    //EXEMPLO DE COMO SELECIONAR ITEM DA LISTA: System.out.println(assuntoDisciplinaList.getSelectionModel().getSelectedItem().getText());

    public PainelCadastrarController() {
        alunoDao = new AlunoDao();
        facilitadorDao = new FacilitadorDao();
        inscricaoDao = new InscricaoDao();
        disciplinaDao = new DisciplinaDao();
        disciplinaInscritaDao = new DisciplinaInscritaDao();
        assuntoDao = new AssuntoDao();
        perguntaDao = new PerguntaDao();
        respostaDao = new RespostaDao();

        dao = new Dao();
    }

    @FXML
    public void initialize() {
        adicionarValidators();

        alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: gray");
        simuladoPagamentoLabel.setStyle("-fx-text-fill: black");



        //Aqui vão ser CARREGADAS todas as tabelas de seleção e comboboxes
    }

    public void adicionarAssuntoDisciplina(javafx.event.ActionEvent actionEvent) {
        String assunto = assuntoDisciplinaField.getText();
        assuntoDisciplinaList.getItems().add(assunto);
    }

    public void adicionarAssuntoDisciplinaEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String assunto = assuntoDisciplinaField.getText();
            assuntoDisciplinaList.getItems().add(assunto);
        }
    }


    public void finalizarCadastro(javafx.event.ActionEvent actionEvent) {

        if(alunoTab.isSelected()) CadastrarAluno();

        if(facilitadorTab.isSelected()) CadastrarFacilitador();

        if(disciplinaTab.isSelected()) CadastrarDisciplina();

        if(assuntoTab.isSelected()) CadastrarAssunto();

        if(perguntaTab.isSelected()) CadastrarPergunta();


//        if(pagamentoTab.isSelected()) {
//            String matriculaPagamento = matriculaPagamentoField.getText();
//            LocalDate dataPagamento = dataPagamentoDP.getValue();
//            double valorPagamento;
//            int idSimDuv;
//
//            if(alunoOuFacilitadorPagamentoToggle.isSelected()) {
//                valorPagamento = Double.parseDouble(valorAlunoPagamentoField.getText());
//            } else {
//                valorPagamento = Double.parseDouble(valorFacilitadorPagamentoList.getItems().get(0));
//                idSimDuv = Integer.parseInt(idSimDuvPagamentoField.getText());
//            }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */

//        }
//
//        if(duvidaTab.isSelected()) {
//            String alunoDuvida = alunoDuvidaCombo.getValue();
//            String facilitadorDuvida = facilitadorDuvidaCombo.getValue();
//            String textoDuvida = perguntaDuvidaField.getText();

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */

//        }


        JFXSnackbar cadastroSnackBar = new JFXSnackbar(painelPai);
        cadastroSnackBar.show("Cadastro realizado com sucesso!", 2000);


        //Aqui vão ser RECARREGADAS todas as tabelas de seleção e comboboxes


    }

    public void CadastrarAluno() {
        int matriculaAluno = Integer.parseInt(matriculaAlunoField.getText());
        String nomeAluno = nomeAlunoField.getText();
        ArrayList<String> disciplinasSelecionadas = new ArrayList<>(disciplinasSelecionadasAlunoList.getItems());

        //TODO: inserir verificação no método DAO.inserir()

        dao.inserir(new Aluno(matriculaAluno, nomeAluno));

        java.util.Date data = new Date();
        Inscricao inscricao = new Inscricao(matriculaAluno, data);
        inscricaoDao.inserir(inscricao);
        //dao.inserir(inscricao);
        inscricao = inscricaoDao.buscarPorMatricula(matriculaAluno);
        //dao.buscar(Inscricao.class, matriculaAluno);

        Disciplina disciplina;
        for(String item : disciplinasSelecionadas) {
            disciplina = disciplinaDao.buscarPorNome(item);
            DisciplinaInscrita disciplinaInscrita = new DisciplinaInscrita(disciplina.getId(), inscricao.getId());
            disciplinaInscritaDao.inserir(disciplinaInscrita);
            //dao.inserir(disciplinaInscrita);
        }
    }

    public void CadastrarFacilitador() {
        int matriculaFacilitador = Integer.parseInt(matriculaFacilitadorField.getText());  //PEGA MATRICULA
        String nomeFacilitador = nomeFacilitadorField.getText();            //PEGA NOME

        //PEGA DISCIPLINAS SELECIONADAS
        ArrayList<String> disciplinasSelecionadas = new ArrayList<>(disciplinasSelecionadasAlunoList.getItems());

        Facilitador facilitador = facilitadorDao.buscarPorMatricula(matriculaFacilitador);
        if (facilitador == null) {
            facilitadorDao.inserir(new Facilitador(matriculaFacilitador, nomeFacilitador));
        }
        GrupoDeDisciplinas grupoDeDisciplinas;
        for(String item : disciplinasSelecionadas) {
            Disciplina disciplina = disciplinaDao.buscarPorNome(item);
            grupoDeDisciplinas = new GrupoDeDisciplinas(disciplina.getId(), facilitador.getMatricula());
            grupoDeDisciplinasDao.inserir(grupoDeDisciplinas);
        }
    }

    public void CadastrarDisciplina() {
        String nomeDisciplina = nomeDisciplinaField.getText();
        double valorDisciplina = Double.parseDouble(valorDisciplinaField.getText());

        ArrayList<String> assuntosDisciplina = new ArrayList<>(assuntoDisciplinaList.getItems());

        ArrayList<Facilitador> facilitadores = (ArrayList<Facilitador>) facilitadorDao.listar();
        for(Facilitador item : facilitadores) {
            facilitadoresDisponiveisDisciplinaList.getItems().add(item.getNome());
        }

        ArrayList<String> facilitadoresDisciplina = new ArrayList<>(facilitadoresSelecionadosDisciplinaList.getItems());

        Disciplina disciplina = new Disciplina(nomeDisciplina, valorDisciplina);
        if (disciplinaDao.buscarPorNome(nomeDisciplina) == null) {
            disciplinaDao.inserir(disciplina);
            disciplina = disciplinaDao.buscarPorNome(nomeDisciplina);
            for (String item : assuntosDisciplina) {
                assuntoDao.inserir(new Assunto(item, disciplina.getId()));
            }
        } else {
            JFXSnackbar cadastroSnackBar = new JFXSnackbar(painelPai);
            cadastroSnackBar.show("Disciplina já cadastrada!", 2000);
        }
    }

    public void CadastrarAssunto() {
        String nomeAssunto = nomeAssuntoField.getText();
        String nomeDisciplina = disciplinaAssuntoCombo.getValue();

        Disciplina disciplina = disciplinaDao.buscarPorNome(nomeDisciplina);

        Assunto assunto = new Assunto(nomeAssunto, disciplina.getId());
        AssuntoDao assuntoDao = new AssuntoDao();
        if (assuntoDao.buscarPorNome(nomeAssunto) == null) {
            assuntoDao.inserir(assunto);
        } else {
            JFXSnackbar cadastroSnackBar = new JFXSnackbar(painelPai);
            cadastroSnackBar.show("Assunto já cadastrado!", 2000);
        }
    }

    public void CadastrarPergunta() {
        String textoPergunta = textoPerguntaField.getText();
        String disciplinaPergunta = disciplinaPerguntaCombo.getValue();
        String assuntoPergunta = assuntoPerguntaCombo.getValue();

        ArrayList<String> respostasPergunta = new ArrayList<>(respostaPerguntaList.getItems());

        String tipo = respObjPerguntaToggle.isSelected() ? "obj" : "sub";

        Resposta resposta;
        for(String item : respostasPergunta) {
            respostaDao.inserir(new Resposta(item, tipo));
        }
        resposta = respostaDao.buscarPorTexto(respostasPergunta.get(0));

        AssuntoDao assuntoDao = new AssuntoDao();
        Assunto assunto = assuntoDao.buscarPorNome(assuntoPergunta);

        PerguntaDao perguntaDao = new PerguntaDao();
        Pergunta pergunta = new Pergunta(textoPergunta, assunto.getId(), resposta.getId());

        perguntaDao.inserir(pergunta);
    }

    public void valorDisciplinaDigitado(KeyEvent inputMethodEvent) {
        valorDisciplinaField.validate();
    }

    public void valorPagamentoDigitado(KeyEvent keyEvent) {
        valorAlunoPagamentoField.validate();
    }

    public void matriculaAlunoDigitado(KeyEvent keyEvent) {
        matriculaAlunoField.validate();
    }

    public void nomeAlunoDigitado(KeyEvent keyEvent) {
        nomeAlunoField.validate();
    }

    public void matriculaFacilitadorDigitado(KeyEvent keyEvent) {
        matriculaFacilitadorField.validate();
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
        matriculaAlunoField.getValidators().add(numberValidator);
        matriculaFacilitadorField.getValidators().add(numberValidator);
        nomeFacilitadorField.getValidators().add(numberValidator);
        matriculaPagamentoField.getValidators().add(numberValidator);
        idSimDuvPagamentoField.getValidators().add(numberValidator);

    }


    public void tabAlunoSelecionada(Event event) {
        disciplinasDisponiveisAlunoList.getItems().clear();

        List<Disciplina> disciplinas = disciplinaDao.listar();
        if(disciplinas != null) {
            for(Disciplina disciplina : disciplinas) {
                disciplinasDisponiveisAlunoList.getItems().add(disciplina.getNome());
            }
        }

    }

    public void tabFacilitadorSelecionada(Event event) {
        disciplinasDisponiveisFacilitadorList.getItems().clear();

        List<Disciplina> disciplinas = disciplinaDao.listar();
        for (Disciplina disciplina : disciplinas) {
            disciplinasDisponiveisFacilitadorList.getItems().add(disciplina.getNome());
        }
    }

    public void tabDisciplinaSelecionada(Event event) {
        facilitadoresDisponiveisDisciplinaList.getItems().clear();

        List<Facilitador> facilitadores = facilitadorDao.listar();
        for (Facilitador facilitador : facilitadores) {
            facilitadoresDisponiveisDisciplinaList.getItems().add(facilitador.getNome());
        }
    }

    public void tabAssuntoSelecionada(Event event) {
        getDisciplinasObservable(disciplinaAssuntoCombo);
    }

    public void tabPerguntaSelecionada(Event event) {
        getDisciplinasObservable(disciplinaPerguntaCombo);
    }

    private void getDisciplinasObservable(JFXComboBox<String> combo) {
        List<Disciplina> disciplinas = disciplinaDao.listar();
        List<String> nomeDisciplinas = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            nomeDisciplinas.add(disciplina.getNome());
        }
        ObservableList<String> disciplinaObservableList = FXCollections.observableArrayList(nomeDisciplinas);
        combo.setItems(disciplinaObservableList);
    }

    public void tabPagamentoSelecionada(Event event) {
    }

    public void tabDuvidaSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados dos alunos e facilitadores disponiveis (alunoDuvidaCombo e facilitadorDuvidaCombo)
         *
         */
    }

    public void tabGerarSimuladoSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados dos alunos e disciplinas disponiveis (alunoSimuladoCombo e disciplinaSimuladoCombo)
         *
         */
    }

    private void transferirItem(MouseEvent mouseEvent, JFXListView<String> origem, JFXListView<String> destino) {
        if(mouseEvent.getClickCount() == 2) {
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

    public void clicarFacilitadoresDisciplinas(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, facilitadoresDisponiveisDisciplinaList, facilitadoresSelecionadosDisciplinaList);

    }

    public void removerFacilitadoresSelecionadosDisciplinas(MouseEvent mouseEvent) {
        transferirItem(mouseEvent, facilitadoresSelecionadosDisciplinaList, facilitadoresDisponiveisDisciplinaList);
    }
    public void removerAssuntosAdicionados(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            int indexSelecionado = assuntoDisciplinaList.getSelectionModel().getSelectedIndex();
            assuntoDisciplinaList.getItems().remove(indexSelecionado);
        }
    }

    public void disciplinaSelecionadaPergunta(ActionEvent actionEvent) {
        String nomeDisciplina = disciplinaPerguntaCombo.getValue();
        Disciplina disciplina = disciplinaDao.buscarPorNome(nomeDisciplina);
        List<Assunto> assuntos = assuntoDao.listarPorDisciplina(disciplina.getId());
        List<String> nomeAssuntos = new ArrayList<>();
        for (Assunto item : assuntos) {
            nomeAssuntos.add(item.getNome());
        }
        ObservableList<String> assuntosObservableList = FXCollections.observableArrayList(nomeAssuntos);
        assuntoPerguntaCombo.setItems(assuntosObservableList);
    }


    public void toggleRespObj(ActionEvent actionEvent) {
        if(respObjPerguntaToggle.isSelected()) {
            respObjPerguntaToggle.setStyle("-fx-font-weight: bold");
            respostaPerguntaField.setDisable(false);
            adicionarRespostaPerguntaBtn.setDisable(false);
            respostaPerguntaList.setDisable(false);
        } else {
            respObjPerguntaToggle.setStyle("-fx-font-weight: normal");
            respostaPerguntaField.setDisable(true);
            adicionarRespostaPerguntaBtn.setDisable(true);
            respostaPerguntaList.setDisable(true);
        }

    }

    public void adicionarRespostaPergunta(ActionEvent actionEvent) {
        String resposta = respostaPerguntaField.getText();
        respostaPerguntaList.getItems().add(resposta);
    }

    public void adicionarRespostaPerguntaEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            String resposta = respostaPerguntaField.getText();
            respostaPerguntaList.getItems().add(resposta);
        }
    }

    public void toggleAlunoFacilitador(ActionEvent actionEvent) {
        if(alunoOuFacilitadorPagamentoToggle.isSelected()) {
            valorAlunoPagamentoField.setDisable(true);
            simuladoOuDuvidaPagamentoToggle.setDisable(false);
            simuladoPagamentoLabel.setDisable(false);
            idSimDuvPagamentoField.setDisable(false);
            valorPagamentoLabel.setDisable(false);
            valorFacilitadorPagamentoList.setDisable(false);
            alunoPagamentoLabel.setStyle("-fx-text-fill: gray");
            alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: black");
        } else {
            valorAlunoPagamentoField.setDisable(false);
            simuladoOuDuvidaPagamentoToggle.setDisable(true);
            idSimDuvPagamentoField.setDisable(true);
            valorPagamentoLabel.setDisable(true);
            valorFacilitadorPagamentoList.setDisable(true);
            alunoPagamentoLabel.setStyle("-fx-text-fill: black");
            alunoOuFacilitadorPagamentoToggle.setStyle("-fx-text-fill: gray");
        }
    }

    public void toggleSimuladoDuvida(ActionEvent actionEvent) {
        if(simuladoOuDuvidaPagamentoToggle.isSelected()) {
            simuladoPagamentoLabel.setStyle("-fx-text-fill: gray");
            simuladoOuDuvidaPagamentoToggle.setStyle("-fx-text-fill: black");
            idSimDuvPagamentoField.setPromptText("ID da dúvida");
            //CARREGA VALOR A SER PAGO
        } else {
            simuladoPagamentoLabel.setStyle("-fx-text-fill: black");
            simuladoOuDuvidaPagamentoToggle.setStyle("-fx-text-fill: gray");
            idSimDuvPagamentoField.setPromptText("ID do simulado");
            //CARREGA VALOR A SER PAGO

        }
    }

    public void disciplinaSelecionadaSimulado(ActionEvent actionEvent) {
        String disciplina = disciplinaSimuladoCombo.getValue();
        /*
         *   TODO: CARREGAR os assuntos da disciplina em assuntoSimuladoCombo
         *
         */
    }

    public void gerarSimulado(ActionEvent actionEvent) {
        String alunoSimulado = alunoSimuladoCombo.getValue();
        String assuntoSimulado = assuntoSimuladoCombo.getValue();


        /*
         *   TODO: Fazer select com os dados acimas e pegar 10 perguntas aleatorias.
         *
         */

        //AQUI AS PERGUNTAS SERÃO COLOCADAS NO VISUALIZADOR DO SIMULADO


    }


}
