package sample;

import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.awt.Color.GRAY;
import static java.awt.Color.gray;

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


    //EXEMPLO DE COMO SELECIONAR ITEM DA LISTA: System.out.println(assuntoDisciplinaList.getSelectionModel().getSelectedItem().getText());


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

        //CADASTRO DE ALUNO:
        if(alunoTab.isSelected()) {
            int matriculaAluno = Integer.parseInt(matriculaAlunoField.getText());    //PEGA MATRICULA
            String nomeAluno = nomeAlunoField.getText();                             //PEGA NOME
            ArrayList<String> disciplinasSelecionadas = new ArrayList<>();

            for(String item : disciplinasSelecionadasAlunoList.getItems()) {          //PEGA DISCIPLINAS SELECIONADAS
                disciplinasSelecionadas.add(item);
            }

            /*
            *   TODO: colocar variaveis no banco de dados
            *
             */
        }

        //CADASTRO DE FACILITADORES
        if(facilitadorTab.isSelected()){
            int matriculaFacilitador = Integer.parseInt(matriculaFacilitadorField.getText());  //PEGA MATRICULA
            String nomeFacilitador = nomeFacilitadorField.getText();            //PEGA NOME
            ArrayList<String> disciplinasSelecionadas = new ArrayList<>();

            for(String item : disciplinasSelecionadasAlunoList.getItems()) {     //PEGA DISCIPLINAS SELECIONADAS
                disciplinasSelecionadas.add(item);
            }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */
        }

        //CADASTRO DE DISCIPLNAS:
        if(disciplinaTab.isSelected()) {
            String nomeDisciplina = nomeDisciplinaField.getText();
            double valorDisciplina = Double.parseDouble(valorDisciplinaField.getText());
            ArrayList<String> assuntosDisciplina = new ArrayList<>();
            ArrayList<String> facilitadoresDisciplina = new ArrayList<>();

            for(String item : assuntoDisciplinaList.getItems()) {
                assuntosDisciplina.add(item);
            }

            for(String item : facilitadoresSelecionadosDisciplinaList.getItems()) {
                facilitadoresDisciplina.add(item);
            }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */
        }

        //CADASTRO DE ASSUNTOS:
        if(assuntoTab.isSelected()) {
            String nomeAssunto = nomeAssuntoField.getText();
            String disciplinaAssunto = disciplinaAssuntoCombo.getValue();

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */
        }

        if(perguntaTab.isSelected()) {
            String textoPergunta = textoPerguntaField.getText();
            String disciplinaPergunta = disciplinaPerguntaCombo.getValue();
            String assuntoPergunta = assuntoPerguntaCombo.getValue();
            ArrayList<String> respostasPergunta = new ArrayList<>();
            for(String item : respostaPerguntaList.getItems()) {
                    respostasPergunta.add(item);
                }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */
        }


        if(pagamentoTab.isSelected()) {
            String matriculaPagamento = matriculaPagamentoField.getText();
            LocalDate dataPagamento = dataPagamentoDP.getValue();
            double valorPagamento;
            int idSimDuv;

            if(alunoOuFacilitadorPagamentoToggle.isSelected()) {
                valorPagamento = Double.parseDouble(valorAlunoPagamentoField.getText());
            } else {
                valorPagamento = Double.parseDouble(valorFacilitadorPagamentoList.getItems().get(0));
                idSimDuv = Integer.parseInt(idSimDuvPagamentoField.getText());
            }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */

        }

        if(duvidaTab.isSelected()) {
            String alunoDuvida = alunoDuvidaCombo.getValue();
            String facilitadorDuvida = facilitadorDuvidaCombo.getValue();
            String textoDuvida = perguntaDuvidaField.getText();

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */

        }




        JFXSnackbar cadastroSnackBar = new JFXSnackbar(painelPai);
        cadastroSnackBar.show("Cadastro realizado com sucesso!", 2000);


        //Aqui vão ser RECARREGADAS todas as tabelas de seleção e comboboxes


    }

    public void valorDisciplinaDigitado(KeyEvent inputMethodEvent) {
        valorDisciplinaField.validate();
    }

    public void valorPagamentoDigitado(KeyEvent keyEvent) {
        valorAlunoPagamentoField.validate();
    }

    public void adicionarValidators() {
        rqValidator = new RequiredFieldValidator();
        dValidator = new DoubleValidator();

        rqValidator.setMessage("Campo Obrigatório");
        dValidator.setMessage("Por favor, insira um número válido.");


        valorDisciplinaField.getValidators().add(dValidator);
        valorAlunoPagamentoField.getValidators().add(dValidator);


    }


    public void tabAlunoSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados na view de disciplinas disponiveis (disciplinasDisponiveisAlunoList)
         *
         */


        disciplinasDisponiveisAlunoList.getItems().add("Matemática");
        disciplinasDisponiveisAlunoList.getItems().add("Fisíca");
        disciplinasDisponiveisAlunoList.getItems().add("Química");
    }

    public void tabFacilitadorSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados na view de disciplinas disponiveis (disciplinasDisponiveisFacilitadorField)
         *
         */
    }

    public void tabDisciplinaSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados na view de facilitadores disponiveis (facilitadoresDisponiveisDisciplinaList)
         *
         */
    }

    public void tabAssuntoSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados na combobox das disciplinas disponiveis (disciplinaAssuntoCombo)
         *
         */
    }

    public void tabPerguntaSelecionada(Event event) {
        /*
         *   TODO: CARREGAR dados na combobox das disciplinas disponiveis (disciplinaPerguntaCombo)
         *
         */
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

    public void transferirItem(MouseEvent mouseEvent, JFXListView origem, JFXListView destino) {
        if(mouseEvent.getClickCount() == 2) {
            int indexSelecionado = origem.getSelectionModel().getSelectedIndex();
            String selecionado = (String) origem.getSelectionModel().getSelectedItem();
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
        String disciplina = disciplinaPerguntaCombo.getValue();
        /*
         *   TODO: CARREGAR os assuntos da disciplina em assuntoPerguntaCombo
         *
         */
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
