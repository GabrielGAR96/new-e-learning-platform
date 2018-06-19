package controller;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class PainelRanking {

    @FXML
    private JFXListView<HBox> rankingView;

    private Dao dao;


    public PainelRanking() {
        dao = new Dao();
    }

    @FXML
    void initialize() throws IOException {
        List<Simulado> simulados = dao.listar(Simulado.class);

        Comparator<Simulado> simuladoNotaComparator = Comparator.comparing(Simulado::getNota);
        Collections.sort(simulados, simuladoNotaComparator.reversed());

        for (Simulado simulado : simulados) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rankingTemplate.fxml"));
            HBox hb = fxmlLoader.load();
            RankingTemplate rankingTemplateController = fxmlLoader.getController();

            templateConfig(simulados, simulado, rankingTemplateController);

            rankingView.getItems().add(hb);
        }

    }

    private void templateConfig(List<Simulado> simulados, Simulado simulado, RankingTemplate rankingTemplateController) {
        String colocacaoDoAluno = String.valueOf(simulados.indexOf(simulado) + 1);
        Aluno aluno = dao.buscar(Aluno.class, "matricula", simulado.getAlunoMatricula());
        Assunto assunto = dao.buscar(Assunto.class, "id", simulado.getAssuntoId());
        Disciplina disciplina = dao.buscar(Disciplina.class, "id", assunto.getDisciplinaId());
        DecimalFormat df = new DecimalFormat("#.##");
        String nota = df.format(simulado.getNota());
        String pmg = getPmg(aluno);
        rankingTemplateController.setInfo(colocacaoDoAluno, aluno.getNome(), disciplina.getNome(), assunto.getNome(), nota, pmg);
    }

    private String getPmg(Aluno aluno) {
        ArrayList<Simulado> simuladosDoAluno = dao.listarComFiltro(Simulado.class, "alunoMatricula", aluno.getMatricula());

        double soma = 0;
        for(Simulado simulado : simuladosDoAluno) {
            soma += simulado.getNota();
        }

        double media = soma / simuladosDoAluno.size();
        DecimalFormat df = new DecimalFormat("#.##");
        String nota = df.format(media);

        return nota;
    }

}
