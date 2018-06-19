package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class PainelAlterar {

    @FXML
    private TableView<?> tabela;

    @FXML
    public void initialize() {


    }

    public <T> void setTabela(T elemento) {
        //tabela.getSelectionModel().cellSelectionEnabledProperty().set(true);
        //tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        List<T> listaDoElemento = new ArrayList<>();
//        listaDoElemento.add(elemento);

//        ObservableList listObs = FXCollections.observableArrayList(elemento);
//        for (Field field : elemento.getClass().getDeclaredFields()) {
//            TableColumn<T, Object> coluna = new TableColumn<>(field.getName());
//            tabela.getColumns().add(coluna);
//            coluna.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//        }

    }
}