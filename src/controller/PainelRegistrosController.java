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

    public PainelRegistrosController() {
        tabelaAtual = new TableView<>();

    }

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

    public void abrirRegistroAlunos(javafx.event.ActionEvent actionEvent) { //Possivel abrir a partir de : Insc, Pgmt, Disc, Sim e Duv
        //buttonSetter(false, true, true, true, true, true, true, true);
        ArrayList<Aluno> alunos = new ArrayList<>();
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Inscricao.class)) {
                    Inscricao selecionada = (Inscricao) tabelaAtual.getSelectionModel().getSelectedItem();
                    alunos = dao.listarComFiltro(Aluno.class, "matricula", selecionada.getAlunoMatricula());
                } else if (classeDaTabela.equals(PagamentoAluno.class)) {
                    PagamentoAluno selecionado = (PagamentoAluno) tabelaAtual.getSelectionModel().getSelectedItem();
                    Inscricao insSelecionada = dao.buscar(Inscricao.class, "id", selecionado.getInscricaoId());
                    alunos = dao.listarComFiltro(Aluno.class, "matricula", insSelecionada.getAlunoMatricula());
                } else if (classeDaTabela.equals(Disciplina.class)) {
                    Disciplina disciplina = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<DisciplinaInscrita> dIns = dao.listarComFiltro(DisciplinaInscrita.class, "disciplinaId", disciplina.getId());
                    ArrayList<Inscricao> inscricoes = new ArrayList<>();
                    for (DisciplinaInscrita di : dIns) {
                        inscricoes.add(dao.buscar(Inscricao.class, "id", di.getDisciplinaId()));
                    }
                    for (Inscricao inscricao : inscricoes) {
                        alunos.add(dao.buscar(Aluno.class, "matricula", inscricao.getAlunoMatricula()));
                    }
                } else if (classeDaTabela.equals(Simulado.class)) {
                    Simulado selecionado = (Simulado) tabelaAtual.getSelectionModel().getSelectedItem();
                    alunos = dao.listarComFiltro(Aluno.class, "matricula", selecionado.getAlunoMatricula());
                } else if (classeDaTabela.equals(Duvida.class)) {
                    Duvida selecionada = (Duvida) tabelaAtual.getSelectionModel().getSelectedItem();
                    alunos = dao.listarComFiltro(Aluno.class, "matricula", selecionada.getAlunoMatricula());
                } else {
                    alunos = dao.listar(Aluno.class);
                }
            } else {
                alunos = dao.listar(Aluno.class);
            }

        } else {
            alunos = dao.listar(Aluno.class);
        }

        tabelaAtual = criarTabela(alunos, Aluno.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirRegistroFacilitadores(javafx.event.ActionEvent actionEvent) { //Possivel abrir a partir de : Pgmt, Disc, Sim e Duv
        //buttonSetter(true, false, false, true, true, true, true, true);
        ArrayList<Facilitador> facilitadores = new ArrayList<>();
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(PagamentoFacilitador.class)) {
                    PagamentoFacilitador selecionado = (PagamentoFacilitador) tabelaAtual.getSelectionModel().getSelectedItem();
                    facilitadores = dao.listarComFiltro(Facilitador.class, "matricula", selecionado.getFacilitadorMatricula());
                } else if (classeDaTabela.equals(Disciplina.class)) {
                    Disciplina disciplina = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<GrupoDeDisciplinas> gDisc = dao.listarComFiltro(GrupoDeDisciplinas.class, "disciplinaId", disciplina.getId());
                    for (GrupoDeDisciplinas gdd : gDisc) {
                        facilitadores.add(dao.buscar(Facilitador.class, "matricula", gdd.getFacilitadorMatricula()));
                    }
                } else if (classeDaTabela.equals(Simulado.class)) {
                    Simulado selecionado = (Simulado) tabelaAtual.getSelectionModel().getSelectedItem();
                    facilitadores = dao.listarComFiltro(Facilitador.class, "matricula", selecionado.getFacilitadorMatricula());
                } else if (classeDaTabela.equals(Duvida.class)) {
                    Duvida selecionada = (Duvida) tabelaAtual.getSelectionModel().getSelectedItem();
                    facilitadores = dao.listarComFiltro(Facilitador.class, "matricula", selecionada.getFacilitadorMatricula());
                } else {
                    facilitadores = dao.listar(Facilitador.class);
                }
            } else {
                facilitadores = dao.listar(Facilitador.class);
            }

        } else {
            facilitadores = dao.listar(Facilitador.class);
        }


        tabelaAtual = criarTabela(facilitadores, Facilitador.class);
        painelPrincipal.setCenter(tabelaAtual);
    }


    public void abrirInscricoes(ActionEvent actionEvent) { //Possivel abrir a partir de : Aluno, Pgmt, Disc
        //buttonSetter(true, false, false, true, true, true, true, true);
        ArrayList<Inscricao> inscricoes = new ArrayList<>();
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Aluno.class)) {
                    Aluno selecionado = (Aluno) tabelaAtual.getSelectionModel().getSelectedItem();
                    inscricoes = dao.listarComFiltro(Inscricao.class, "alunoMatricula", selecionado.getMatricula());
                } else if (classeDaTabela.equals(Disciplina.class)) {
                    Disciplina disciplina = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<DisciplinaInscrita> dIns = dao.listarComFiltro(DisciplinaInscrita.class, "disciplinaId", disciplina.getId());
                    for (DisciplinaInscrita di : dIns) {
                        inscricoes.add(dao.buscar(Inscricao.class, "id", di.getDisciplinaId()));
                    }
                } else if (classeDaTabela.equals(PagamentoAluno.class)) {
                    PagamentoAluno selecionado = (PagamentoAluno) tabelaAtual.getSelectionModel().getSelectedItem();
                    inscricoes = dao.listarComFiltro(Inscricao.class, "id", selecionado.getInscricaoId());
                } else {
                    inscricoes = dao.listar(Inscricao.class);
                }
            } else {
                inscricoes = dao.listar(Inscricao.class);
            }
        } else {
            inscricoes = dao.listar(Inscricao.class);
        }

        tabelaAtual = criarTabela(inscricoes, Inscricao.class);
        painelPrincipal.setCenter(tabelaAtual);
    }

    public void abrirPagamentos(ActionEvent actionEvent) { //PAGAMENTO REFERENTE AO UMA INSCRICAO OU A UM FACILITADOR
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Aluno.class)) {
                    //buttonSetter();
                    Aluno aluno = (Aluno) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<Inscricao> inscricoes = dao.listarComFiltro(Inscricao.class, "alunoMatricula", aluno.getMatricula());
                    ArrayList<PagamentoAluno> pagamentos = new ArrayList<>();
                    for (Inscricao inscricao : inscricoes) {
                        pagamentos.addAll(dao.listarComFiltro(PagamentoAluno.class, "inscricaoId", inscricao.getId()));
                    }
                } else if (classeDaTabela.equals(Inscricao.class)) {
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
                } else {
                    //ABRIR TODOS OS PAGAMENTOS POSSIVEIS (ALUNO E FACILITADOR)
                    //tabelaAtual = ....
                }

            } else {
                //ABRIR TODOS OS PAGAMENTOS POSSIVEIS (ALUNO E FACILITADOR)
                //tabelaAtual = ....
            }
        } else {
            //ABRIR TODOS OS PAGAMENTOS POSSIVEIS (ALUNO E FACILITADOR)
            //tabelaAtual = ....
        }

        painelPrincipal.setCenter(tabelaAtual);
    }

    public void abrirDisciplinas(ActionEvent actionEvent) {
        //buttonSetter(true, true, true, false, true, true, true, true);
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        if (!tabelaAtual.getItems().isEmpty()) {
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
                } else {
                    disciplinas = dao.listar(Disciplina.class);

                }
            } else {
                disciplinas = dao.listar(Disciplina.class);

            }
        } else {
            disciplinas = dao.listar(Disciplina.class);
        }

        tabelaAtual = criarTabela(disciplinas, Disciplina.class);
        painelPrincipal.setCenter(tabelaAtual);


    }

    public void abrirAssuntos(ActionEvent actionEvent) {
        //buttonSetter(true, true, false, true, false, false, false, true);
        ArrayList<Assunto> assuntos;
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
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
                } else {
                    assuntos = dao.listar(Assunto.class);
                }
            } else {
                assuntos = dao.listar(Assunto.class);
            }
        } else {
            assuntos = dao.listar(Assunto.class);
        }

        tabelaAtual = criarTabela(assuntos, Assunto.class);
        painelPrincipal.setCenter(tabelaAtual);

    }

    public void abrirPerguntas(ActionEvent actionEvent) {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Disciplina.class)) {
                    Disciplina disciplina = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<Assunto> assuntos = dao.listarComFiltro(Assunto.class, "disciplinaId", disciplina.getId());
                    for (Assunto assunto : assuntos) {
                        perguntas.addAll(dao.listarComFiltro(Pergunta.class, "assuntoId", assunto.getId()));
                    }
                } else if (classeDaTabela.equals(Assunto.class)) {
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
                } else {
                    perguntas = dao.listar(Pergunta.class);
                }
            } else {
                perguntas = dao.listar(Pergunta.class);
            }
        } else {
            perguntas = dao.listar(Pergunta.class);
        }

        tabelaAtual = criarTabela(perguntas, Pergunta.class);
        painelPrincipal.setCenter(tabelaAtual);
    }

    public void abrirRespostas(ActionEvent actionEvent) {
        ArrayList<Resposta> respostas = new ArrayList<>();

        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Disciplina.class)) {
                    Disciplina disciplina = (Disciplina) tabelaAtual.getSelectionModel().getSelectedItem();
                    ArrayList<Assunto> assuntos = dao.listarComFiltro(Assunto.class, "disciplinaId", disciplina.getId());
                    for (Assunto assunto : assuntos) {
                        respostas.addAll(dao.listarComFiltro(Resposta.class, "assuntoId", assunto.getId()));
                    }
                } else if (classeDaTabela.equals(Assunto.class)) {
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
                } else {
                    respostas = dao.listar(Resposta.class);
                }
            } else {
                respostas = dao.listar(Resposta.class);
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
        ArrayList<Duvida> duvidas;
        if (!tabelaAtual.getItems().isEmpty()) {
            Class classeDaTabela = tabelaAtual.getItems().get(0).getClass();
            if (tabelaAtual.getSelectionModel().getSelectedItem() != null) {
                if (classeDaTabela.equals(Aluno.class)) {
                    Aluno aluno = (Aluno) tabelaAtual.getSelectionModel().getSelectedItem();
                    duvidas = dao.listarComFiltro(Duvida.class, "alunoMatricula", aluno.getMatricula());
                } else if (classeDaTabela.equals(Facilitador.class)) {
                    Facilitador facilitador = (Facilitador) tabelaAtual.getSelectionModel().getSelectedItem();
                    duvidas = dao.listarComFiltro(Duvida.class, "facilitadorMatricula", facilitador.getMatricula());
                } else {
                    duvidas = dao.listar(Duvida.class);
                }
            } else {
                duvidas = dao.listar(Duvida.class);
            }
        } else {
            duvidas = dao.listar(Duvida.class);
        }

        tabelaAtual = criarTabela(duvidas, Duvida.class);
        painelPrincipal.setCenter(tabelaAtual);
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