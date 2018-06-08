package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import model.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PainelRegistrosController {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private ComboBox<?> colunasRegistroBtn;

    @FXML
    private JFXButton inscricoesRegistroBtn;

    @FXML
    private JFXButton pagamentosRegistroBtn;

    @FXML
    private JFXButton disciplinasRegistroBtn;

    @FXML
    private JFXButton assuntosRegistroBtn;

    @FXML
    private JFXButton perguntasRegistroBtn;

    @FXML
    private JFXButton respostasRegistroBtn;

    @FXML
    private JFXButton alunosRegistroBtn;

    @FXML
    private JFXButton facilitadoresRegistroBtn;

    @FXML
    private JFXButton simuladosRegistroBtn;

    @FXML
    private JFXButton duvidasRegistroBtn;

    private TableView<?> tabelaAtual;

    private Dao dao;

    @FXML
    public void initialize() {
        dao = new Dao();
        abrirRegistroAlunos(null);
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
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();

        TablePosition pos = tabela.getSelectionModel().getSelectedCells().get(0);
        TableColumn coluna = tabela.getColumns().get(0);
        int linha = pos.getRow();
        int valor = (int) coluna.getCellObservableValue(linha).getValue();

        dao.excluir(classeDaTabela, valor);
    }

    public void abrirRegistroAlunos(javafx.event.ActionEvent actionEvent) {
        //buttonSetter(false, true, true, true, true, true, true, true);
        List<Aluno> alunos = dao.listar(Aluno.class);
        tabelaAtual = criarTabela(alunos, Aluno.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirRegistroFacilitadores(javafx.event.ActionEvent actionEvent) {
        //buttonSetter(true, false, false, true, true, true, true, true);
        List<Facilitador> facilitadores = dao.listar(Facilitador.class);
        tabelaAtual = criarTabela(facilitadores, Facilitador.class);
        painelPrincipal.setCenter(tabelaAtual);
    }


    public void abrirInscricoes(ActionEvent actionEvent) { //INSCRICAO REFERENTE A UM ALUNO
        //buttonSetter(true, false, false, true, true, true, true, true);
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        ArrayList<Inscricao> inscricoes = null;
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Aluno.class)) {
                Aluno selecionado = (Aluno) tabelaAtual.getSelectionModel().getSelectedItem();
                inscricoes = dao.listarComFiltro(Inscricao.class, "alunoMatricula", selecionado.getMatricula());
            }
        } else {
            inscricoes = dao.listar(Inscricao.class);
        }

        tabelaAtual = criarTabela(inscricoes, Inscricao.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirPagamentos(ActionEvent actionEvent) { //PAGAMENTO REFERENTE AO UMA INSCRICAO OU A UM FACILITADOR
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Inscricao.class)) {
                //buttonSetter(false, true, true, true, true, true, true, true);
                Inscricao selecionada = (Inscricao) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<PagamentoAluno> pagamentos = dao.listarComFiltro(PagamentoAluno.class, "inscricaoId", selecionada.getId());
                tabelaAtual = criarTabela(pagamentos, PagamentoAluno.class);
            } else if (classeDaTabela.equals(Facilitador.class)) {
                //buttonSetter(true, true, true, true, true, true, false, false);
                Facilitador selecionado = (Facilitador) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<PagamentoFacilitador> pagamentos = dao.listarComFiltro(PagamentoFacilitador.class,
                        "facilitadorMatricula", selecionado.getMatricula());
                tabelaAtual = criarTabela(pagamentos, PagamentoFacilitador.class);
            }
            painelPrincipal.setCenter(tabelaAtual);
        }
    }

    public void abrirDisciplinas(ActionEvent actionEvent) {
        //buttonSetter(true, true, true, false, true, true, true, true);
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Inscricao.class)) {
                Inscricao selecionada = (Inscricao) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<DisciplinaInscrita> disciplinaInscritas = dao.listarComFiltro(DisciplinaInscrita.class,
                        "inscricaoId", selecionada.getId());
                for (DisciplinaInscrita di : disciplinaInscritas) {
                    disciplinas.add(dao.buscar(Disciplina.class, "id", di.getDisciplinaId()));
                }
            } else if (classeDaTabela.equals(Facilitador.class)) {
                Facilitador selecionado = (Facilitador) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<GrupoDeDisciplinas> gruposDeDisciplinas = dao.listarComFiltro(GrupoDeDisciplinas.class,
                        "facilitadorMatricula", selecionado.getMatricula());
                for (GrupoDeDisciplinas gd : gruposDeDisciplinas) {
                    disciplinas.add(dao.buscar(Disciplina.class, "id", gd.getDisciplinaId()));
                }
            }
        } else {
            disciplinas = dao.listar(Disciplina.class);
        }

        tabelaAtual = criarTabela(disciplinas, Disciplina.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirAssuntos(ActionEvent actionEvent) {
        //buttonSetter(true, true, false, true, false, false, false, true);
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        ArrayList<Assunto> assuntos = null;
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Disciplina.class)) {
                Disciplina selecionada = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                assuntos = dao.listarComFiltro(Assunto.class, "disciplinaId", selecionada.getId());
            } else if (classeDaTabela.equals(Simulado.class)) {
                Simulado selecionado = (Simulado) tabelaAtual.getSelectionModel().getSelectedItem();
                assuntos = dao.listarComFiltro(Assunto.class, "id", selecionado.getAssuntoId());
            } else if (classeDaTabela.equals(Pergunta.class)) {
                Pergunta selecionada = (Pergunta) tabelaAtual.getSelectionModel().getSelectedItem();
                assuntos = dao.listarComFiltro(Assunto.class, "id", selecionada.getAssuntoId());
            } else if (classeDaTabela.equals(Resposta.class)) {
                Resposta selecionada = (Resposta) tabelaAtual.getSelectionModel().getSelectedItem();
                assuntos = dao.listarComFiltro(Assunto.class, "id", selecionada.getAssuntoId());
            }
        } else {
            assuntos = dao.listar(Assunto.class);
        }

        tabelaAtual = criarTabela(assuntos, Assunto.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirPerguntas(ActionEvent actionEvent) {
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Assunto.class)) {
                Assunto selecionado = (Assunto) tabelaAtual.getSelectionModel().getSelectedItem();
                perguntas = dao.listarComFiltro(Pergunta.class, "assuntoId", selecionado.getId());
            } else if (classeDaTabela.equals(Resposta.class)) {
                Resposta selecionada = (Resposta) tabelaAtual.getSelectionModel().getSelectedItem();
                perguntas = dao.listarComFiltro(Pergunta.class, "respostaId", selecionada.getId());
            } else if (classeDaTabela.equals(Simulado.class)) {
                Simulado selecionado = (Simulado) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<PerguntaDoSimulado> perguntasDoSimulado = dao.listarComFiltro(PerguntaDoSimulado.class,
                        "simuladoId", selecionado.getId());
                for (PerguntaDoSimulado pds : perguntasDoSimulado) {
                    perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
                }
            }
        } else {
            perguntas = dao.listar(Pergunta.class);
        }

        tabelaAtual = criarTabela(perguntas, Pergunta.class);
        painelPrincipal.setCenter(tabelaAtual);
    }

    public void abrirRespostas(ActionEvent actionEvent) {
        Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
        ArrayList<Resposta> respostas = new ArrayList<>();
        if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
            if (classeDaTabela.equals(Assunto.class)) {
                Assunto selecionado = (Assunto) tabelaAtual.getSelectionModel().getSelectedItem();
                respostas = dao.listarComFiltro(Resposta.class, "assuntoId", selecionado.getId());
            } else if (classeDaTabela.equals(Simulado.class)) {
                Simulado selecionado = (Simulado) tabelaAtual.getSelectionModel().getSelectedItem();
                ArrayList<RespostaDoSimulado> respostasDoSimulado = dao.listarComFiltro(RespostaDoSimulado.class,
                        "simuladoId", selecionado.getId());
                for (RespostaDoSimulado rds : respostasDoSimulado) {
                    respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
                }
            } else if (classeDaTabela.equals(Pergunta.class)) {
                Pergunta selecionada = (Pergunta) tabelaAtual.getSelectionModel().getSelectedItem();
                respostas = dao.listarComFiltro(Resposta.class, "id", selecionada.getRespostaId());
            }
        } else {
            respostas = dao.listar(Resposta.class);
        }

        tabelaAtual = criarTabela(respostas, Resposta.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirSimulados(ActionEvent actionEvent) {
    }

    public void abrirDuvidas(ActionEvent actionEvent) {
    }

//    private void buttonSetter(Boolean in, Boolean pa, Boolean di, Boolean as, Boolean pe, Boolean re, Boolean si, Boolean du) {
//        inscricoesRegistroBtn.setDisable(in);
//        pagamentosRegistroBtn.setDisable(pa);
//        disciplinasRegistroBtn.setDisable(di);
//        assuntosRegistroBtn.setDisable(as);
//        perguntasRegistroBtn.setDisable(pe);
//        respostasRegistroBtn.setDisable(re);
//        simuladosRegistroBtn.setDisable(si);
//        duvidasRegistroBtn.setDisable(du);
//    }


}