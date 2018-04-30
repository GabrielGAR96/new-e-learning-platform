package sample;

import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class PainelCadastrarController {

    @FXML
    private JFXTabPane paneCadastros;

    @FXML
    private Tab alunoTab;

    @FXML
    private JFXTextField matriculaAlunoField;

    @FXML
    private JFXTextField nomeAlunoField;

    @FXML
    private JFXListView<Label> disciplinasDisponiveisAlunoList;

    @FXML
    private JFXListView<Label> disciplinasSelecionadasAlunoList;

    @FXML
    private Tab facilitadorTab;

    @FXML
    private JFXTextField matriculaFacilitadorField;

    @FXML
    private JFXTextField nomeFacilitadorField;

    @FXML
    private JFXListView<Label> disciplinasDisponiveisFacilitadorField;

    @FXML
    private JFXListView<Label> disciplinasSelecionadasFacilitadorList;

    @FXML
    private Tab disciplinaTab;

    @FXML
    private JFXTextField nomeDisciplinaField;

    @FXML
    private JFXTextField valorDisciplinaField;

    @FXML
    private JFXListView<Label> facilitadoresDisponiveisDisciplinaField;

    @FXML
    private JFXListView<Label> facilitadoresSelecionadosDisciplinaField;

    @FXML
    private JFXListView<Label> assuntoDisciplinaList;

    @FXML
    private JFXTextField assuntoDisciplinaField;

    @FXML
    private JFXButton adicionarAssuntoDisciplinaBtn;

    @FXML
    private Tab assuntoTab;

    @FXML
    private JFXTextField nomeAssuntoField;

    @FXML
    private JFXComboBox<Label> disciplinaAssuntoCombo;

    @FXML
    private Tab perguntaTab;

    @FXML
    private JFXTextArea textoPerguntaField;

    @FXML
    private JFXComboBox<Label> assuntoPerguntaCombo;

    @FXML
    private JFXComboBox<Label> disciplinaPerguntaCombo;

    @FXML
    private JFXTextArea respostaPerguntaField;

    @FXML
    private JFXListView<Label> respostaPerguntaList;

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
    private JFXListView<Label> valorFacilitadorPagamentoList;

    @FXML
    private Tab duvidaTab;

    @FXML
    private JFXTextArea perguntaDuvidaCombo;

    @FXML
    private JFXComboBox<?> alunoDuvidaCombo;

    @FXML
    private JFXComboBox<?> facilitadorDuvidaCombo;

    @FXML
    private Tab gerarSimuladoTab;

    @FXML
    private JFXComboBox<?> alunoSimuladoCombo;

    @FXML
    private JFXComboBox<?> disciplinaSimuladoCombo;

    @FXML
    private JFXListView<?> questoesSimuladoView;

    @FXML
    private JFXComboBox<?> assuntoSimuladoCombo;

    @FXML
    private JFXButton gerarSimuladoBtn;

    @FXML
    private JFXButton finalizarCadastroBtn;

    @FXML
    public void initialize() {
        //Aqui vão ser carregadas todas as tabelas de seleção.
    }

    public void adicionarAssuntoDisciplina(javafx.event.ActionEvent actionEvent) {
        String assunto = assuntoDisciplinaField.getText();

        assuntoDisciplinaList.getItems().add(new Label(assunto));

        //COMO SELECIONAR ITEM DA LISTA: System.out.println(assuntoDisciplinaList.getSelectionModel().getSelectedItem().getText());
    }


    public void finalizarCadastro(javafx.event.ActionEvent actionEvent) {

        //CADASTRO DE ALUNO:
        if(alunoTab.isSelected()) {
            String matriculaAluno = matriculaAlunoField.getText();              //PEGA MATRICULA
            String nomeAluno = nomeAlunoField.getText();                        //PEGA NOME
            ArrayList<String> disciplinasSelecionadas = new ArrayList<>();

            for(Label item : disciplinasSelecionadasAlunoList.getItems()) {     //PEGA DISCIPLINAS SELECIONADAS
                disciplinasSelecionadas.add(item.getText());
            }

            /*
            *   TODO: colocar variaveis no banco de dados
            *
             */
        }

        if(facilitadorTab.isSelected()){
            String matriculaFacilitador = matriculaFacilitadorField.getText();  //PEGA MATRICULA
            String nomeFacilitador = nomeFacilitadorField.getText();            //PEGA NOME
            ArrayList<String> disciplinasSelecionadas = new ArrayList<>();

            for(Label item : disciplinasSelecionadasAlunoList.getItems()) {     //PEGA DISCIPLINAS SELECIONADAS
                disciplinasSelecionadas.add(item.getText());
            }

            /*
             *   TODO: colocar variaveis no banco de dados
             *
             */
        }

        if(disciplinaTab.isSelected()) {
            String nomeDisciplina = nomeDisciplinaField.getText();
            double valorDisciplina = Double.parseDouble(valorDisciplinaField.getText());
            ArrayList<String> assuntosDisciplina = new ArrayList<>();
            ArrayList<String> facilitadoresDisciplina = new ArrayList<>();

            for(Label item : assuntoDisciplinaList.getItems()) {
                assuntosDisciplina.add(item.getText());
            }

            for(Label item : assuntoDisciplinaList.getItems()) {
                facilitadoresDisciplina.add(item.getText());
            }


        }

        if(assuntoTab.isSelected()) {

        }

        if(perguntaTab.isSelected()) {

        }

        if(pagamentoTab.isSelected()) {

        }

        if(duvidaTab.isSelected()) {

        }

        if(gerarSimuladoTab.isSelected()) {

        }



        System.out.println("CADASTRADO COM SUCESSO!"); //BATATA FRITA SOBE!



    }

    public void valorDisciplinaDigitado(KeyEvent inputMethodEvent) {
        DoubleValidator validator = new DoubleValidator();

        validator.setMessage("Por favor, insira um número válido.");

        valorDisciplinaField.getValidators().add(validator);

        valorDisciplinaField.validate();
    }
}
