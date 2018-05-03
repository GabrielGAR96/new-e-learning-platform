package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import model.Aluno;
import model.Inscricao;
import model.Pagamento;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @FXML
    public void initialize() {
        inicializarTesteDaTabela();
    }

    public void abrirRegistroAlunos(javafx.event.ActionEvent actionEvent) {
        TableView<Aluno> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        painelPrincipal.setCenter(tabela);
    }

    public void abrirRegistroFacilitadores(javafx.event.ActionEvent actionEvent) {
    }

    public void inicializarTesteDaTabela() {

        TableView<Aluno> tabela = new TableView<>();
        painelPrincipal.setCenter(tabela);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Aluno a1 = new Aluno(1810001, "Marcos");
        Aluno a2 = new Aluno(1810002, "Fernanda");
        Aluno a3 = new Aluno(1810003, "Beatriz");
        Aluno a4 = new Aluno(1810004, "Wilson");


//
//        Pagamento p1 = new Pagamento(1, 100, LocalDate.of(2018,02,02), 1);
//        Pagamento p2 = new Pagamento(2, 200, LocalDate.of(2018,01,25), 1);
//        Pagamento p3 = new Pagamento(3, 300, LocalDate.of(2018,03,27), 1);
//        Pagamento p4 = new Pagamento(4, 400, LocalDate.of(2018,04,10), 1);



        TableColumn<Aluno, Integer> matricula = new TableColumn<>("Matrícula");
        TableColumn<Aluno, String> nome = new TableColumn<>("Nome");


        tabela.getColumns().add(matricula);
        tabela.getColumns().add(nome);


        ObservableList<Aluno> alunosOList = FXCollections.observableArrayList();

        List<Aluno> listaDeAlunos = new ArrayList<Aluno>();

        listaDeAlunos.add(a1);
        listaDeAlunos.add(a2);
        listaDeAlunos.add(a3);
        listaDeAlunos.add(a4);


        tabela.setItems(alunosOList);

        matricula.setCellValueFactory(cellData -> cellData.getValue().matriculaProperty().asObject());
        nome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());


        alunosOList.addAll(listaDeAlunos);

        JFXListView<JFXButton> labelList = new JFXListView<>();

        JFXPopup popup = new JFXPopup(labelList);

        JFXButton insc = new JFXButton("Inscrições");
        insc.setOnMouseClicked(event -> {
            carregarTabelaDeInscrição();
            popup.hide();
        });

        JFXButton pag = new JFXButton("Pagamentos");
        pag.setOnMouseClicked(event -> {
            System.out.println("FUNCIONOU");
        });

        labelList.getItems().add(insc);
        labelList.getItems().add(pag);

        labelList.setMinHeight(100);
        labelList.setMinWidth(120);

        tabela.setRowFactory(tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {

                    Aluno clickedRow = row.getItem();
                    popup.show((Node) row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), 0);

                }
            });
            return row ;
        });
    }

    public void carregarTabelaDeInscrição() {
        List<Inscricao> inscricaoList = new ArrayList<>();

        Inscricao i1 = new Inscricao(1,180001, LocalDate.of(2018,01,25));
        Inscricao i2 = new Inscricao(2,180002, LocalDate.of(2018,02,02));
        Inscricao i3 = new Inscricao(3,180003, LocalDate.of(2018,03,27));
        Inscricao i4 = new Inscricao(4,180004, LocalDate.of(2018,04,10));

        inscricaoList.add(i1);
        inscricaoList.add(i2);
        inscricaoList.add(i3);
        inscricaoList.add(i4);

        JFXListView<JFXButton> opcoes = new JFXListView<>();
        JFXPopup popup = new JFXPopup(opcoes);


//        JFXButton insc = new JFXButton("Alunos");
//        insc.setOnMouseClicked(event -> {
//            carregarTabelaDeAlunos();
//            popup.hide();
//        });
//
//        JFXButton pag = new JFXButton("Pagamentos");
//        insc.setOnMouseClicked(event -> {
//            carregarTabelaDePgmtAlunos();
//            popup.hide();
//        });
//
//        JFXButton pag = new JFXButton("Disciplinas inscritas");
//        insc.setOnMouseClicked(event -> {
//            carregarTabelaDeDisciplinasInscritas();
//            popup.hide();
//        });


        painelPrincipal.setCenter(criarTabela(inscricaoList,Inscricao.class));
    }

    public <T> TableView<T> criarTabela(List<T> lista, Class<T> classe) {
        TableView<T> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList listObs = FXCollections.observableArrayList();
        for(Field field : classe.getDeclaredFields()) {

            TableColumn<T, Object> coluna = new TableColumn<>(field.getName());
            tabela.getColumns().add(coluna);
            coluna.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
        }

        listObs.addAll(lista);
        tabela.setItems(listObs);



//
//        JFXButton insc = new JFXButton("Inscrições");
//        insc.setOnMouseClicked(event -> {
//            carregarTabelaDeInscrição();
//            popup.hide();
//        });
//
//        JFXButton pag = new JFXButton("Pagamentos");
//        pag.setOnMouseClicked(event -> {
//            System.out.println("FUNCIONOU");
//        });
//
//        labelList.getItems().add(insc);
//        labelList.getItems().add(pag);
//
//        opcoes.setMinHeight(100);
//        opcoes.setMinWidth(120);
//
//        tabela.setRowFactory(tv -> {
//            TableRow<T> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (! row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
//
//                    T clickedRow = row.getItem();
//                    popup.show((Node) row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), 0);
//
//                }
//            });
//            return row ;
//        });



        return tabela;
    }




}
