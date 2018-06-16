package controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.skin.ToggleButtonSkin;
import javafx.scene.layout.VBox;
import model.*;

import java.util.ArrayList;

public class PainelSimulado {

    private Dao dao;


    public PainelSimulado() {
        dao = new Dao();
    }

    public ArrayList<ToggleGroup> opcoes;

    public ArrayList<JFXTextField> respostasSub;

    @FXML
    void initialize() {
        opcoes = new ArrayList<>();
        respostasSub = new ArrayList<>();
    }


    public void setSimulado(int id) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulados = dao.listarComFiltro(PerguntaDoSimulado.class, "simuladoId", id);
        ArrayList<RespostaDoSimulado> respostasDoSimulados = dao.listarComFiltro(RespostaDoSimulado.class, "simuladoId", id);

        ArrayList<Resposta> respostas = new ArrayList<>();
        for(RespostaDoSimulado rds : respostasDoSimulados) {
            respostas.add(dao.buscar(Resposta.class, "id", rds.getRespostaId()));
        }

        ArrayList<Pergunta> perguntas = new ArrayList<>();
        for(PerguntaDoSimulado pds : perguntasDoSimulados) {
            perguntas.add(dao.buscar(Pergunta.class, "id", pds.getPerguntaId()));
        }

        int i = 1;
        for(Pergunta pergunta : perguntas) {
            TitledPane tp = new TitledPane();
            VBox vb = new VBox();
            vb.getChildren().add(new Label(pergunta.getTexto()));

            ToggleGroup tg = new ToggleGroup();
            for(Resposta resposta : respostas) {
                if(pergunta.getTipo() == "obj") {
                    if(resposta.getPerguntaId() == pergunta.getId()) {
                        JFXRadioButton rBtn = new JFXRadioButton(resposta.getTexto());
                        rBtn.setToggleGroup(tg);
                        opcoes.add(tg);
                        vb.getChildren().add(rBtn);
                    }
                } else {
                    JFXTextField tf = new JFXTextField("Insira sua resposta");
                    respostasSub.add(tf);
                    vb.getChildren().add(tf);
                }

            }
            tp.setText("Questão " + Integer.toString(i++));
            tp.setContent(vb);
        }




    }
}
