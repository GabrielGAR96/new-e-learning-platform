package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import model.*;
import persistence.*;

import java.lang.reflect.Field;
import java.util.List;

public class PainelRegistrosController {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private ComboBox<?> colunasRegistroBtn;

    @FXML
    private JFXButton alunosRegistroBtn;

    @FXML
    private JFXButton facilitadoresRegistroBtn;

    private AlunoDao alunoDao;

    private FacilitadorDao facilitadorDao;

    private InscricaoDao inscricaoDao;

    private PagamentoDao pagamentoDao;

    private DisciplinaDao disciplinaDao;

    private PerguntaDao perguntaDao;

    private RespostaDao respostaDao;

    private AssuntoDao assuntoDao;

    @FXML
    public void initialize() {
        alunoDao = new AlunoDao();
        facilitadorDao = new FacilitadorDao();
        inscricaoDao = new InscricaoDao();
        pagamentoDao = new PagamentoDao();
        disciplinaDao = new DisciplinaDao();
        assuntoDao = new AssuntoDao();
        perguntaDao = new PerguntaDao();
        respostaDao = new RespostaDao();
    }


    private <T> TableView<T> criarTabela(List<T> lista, Class<T> classe) {
        TableView<T> tabela = new TableView<>();
        tabela.setEditable(true);
        tabela.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList listObs = FXCollections.observableArrayList();
        for (Field field : classe.getDeclaredFields()) {
            TableColumn<T, Object> coluna = new TableColumn<>(field.getName());
            tabela.getColumns().add(coluna);
            coluna.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
        }

        JFXButton deleteBtn = new JFXButton("Deletar");
        JFXPopup popup = new JFXPopup(deleteBtn);

        tabela.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setEditable(true);
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {

                    deleteBtn.setOnMouseClicked(e -> {
                        apagarLinha(tabela, classe);
                        row.getTableView().getItems().remove(row.getIndex());
                        popup.hide();
                    });
                    popup.show(row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), 0);
                }
            });

            return row;
        });

        listObs.addAll(lista);
        tabela.setItems(listObs);

        return tabela;
    }

    private <T> void apagarLinha(TableView<T> tabela, Class<T> classe) {
        TablePosition pos = tabela.getSelectionModel().getSelectedCells().get(0);
        TableColumn coluna = tabela.getColumns().get(0);
        int linha = pos.getRow();
        int valor = (int) coluna.getCellObservableValue(linha).getValue();

        if(classe == Aluno.class) {
            alunoDao.excluir(valor);
        }
        if(classe == Facilitador.class) {
            facilitadorDao.excluir(valor);
        }
        if(classe == Disciplina.class) {
           disciplinaDao.excluir(valor);
        }
        if(classe == Assunto.class) {
            assuntoDao.excluir(valor);
        }
        if(classe == Pergunta.class) {
            perguntaDao.excluir(valor);
        }
        if(classe == Resposta.class) {
            respostaDao.excluir(valor);
        }
    }

    public void abrirRegistroAlunos(javafx.event.ActionEvent actionEvent) {
//        TableView<Aluno> tabela = (TableView<Aluno>) painelPrincipal.getCenter();
//        Aluno selecionado = tabela.getSelectionModel().getSelectedItem();
//        int id = selecionado.getMatricula();
        List<Aluno> alunoList = alunoDao.listar();
        painelPrincipal.setCenter(criarTabela(alunoList, Aluno.class));

    }

    public void abrirRegistroFacilitadores(javafx.event.ActionEvent actionEvent) {
        List<Facilitador> facilitadorList = facilitadorDao.listar();
        painelPrincipal.setCenter(criarTabela(facilitadorList, Facilitador.class));
    }


    public void abrirInscricoes(ActionEvent actionEvent) {
//        List<Inscricao> inscricaoList = inscricaoDao.listar();
//        painelPrincipal.setCenter(criarTabela(inscricaoList, Inscricao.class));
    }

    public void abrirPagamentos(ActionEvent actionEvent) {
//        List<Pagamento> pagamentosList = pagamentoDao.listar();
//        painelPrincipal.setCenter(criarTabela(pagamentosList, Pagamento.class));
    }

    public void abrirDisciplinas(ActionEvent actionEvent) {
        List<Disciplina> disciplinaList = disciplinaDao.listar();
        painelPrincipal.setCenter(criarTabela(disciplinaList, Disciplina.class));
    }

    public void abrirAssuntos(ActionEvent actionEvent) {
        List<Assunto> assuntoList = assuntoDao.listar();
        painelPrincipal.setCenter(criarTabela(assuntoList, Assunto.class));
    }

    public void abrirPerguntas(ActionEvent actionEvent) {
        List<Pergunta> perguntaList = perguntaDao.listar();
        painelPrincipal.setCenter(criarTabela(perguntaList, Pergunta.class));
    }

    public void abrirRespostas(ActionEvent actionEvent) {
        List<Resposta> respostaList = respostaDao.listar();
        painelPrincipal.setCenter(criarTabela(respostaList, Resposta.class));
    }
}