package persistence;

import model.GrupoDeDisciplinas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoDeDisciplinasDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(GrupoDeDisciplinas grupoDeDisciplinas) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into grupo_de_disciplinas values (?,?)");
            statement.setInt(1, grupoDeDisciplinas.getFacilitador_matricula());
            statement.setInt(2, grupoDeDisciplinas.getDisciplina_id());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(GrupoDeDisciplinas grupoDeDisciplinas) {
//
//    }

    public void excluir(GrupoDeDisciplinas grupoDeDisciplinas) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from grupo_de_disciplinas where facilitador_matricula = ? and disciplina = ?");
            statement.setInt(1, grupoDeDisciplinas.getFacilitador_matricula());
            statement.setInt(2, grupoDeDisciplinas.getDisciplina_id());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<GrupoDeDisciplinas> buscarPorFacilitador(int facilitador_matricula) {
        ArrayList<GrupoDeDisciplinas> disciplinasDoFacilitador = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from grupo_de_disciplinas where facilitador_matricula = ?");
            statement.setInt(1, facilitador_matricula);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int disciplina_id = rs.getInt("disciplina_id");
                disciplinasDoFacilitador.add(new GrupoDeDisciplinas(facilitador_matricula, disciplina_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
         return disciplinasDoFacilitador;
    }

    public List<GrupoDeDisciplinas> listar(){
        ArrayList<GrupoDeDisciplinas> grupoDeDisciplinas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from grupo_de_disciplinas");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int facilitador_matricula = rs.getInt("facilitador_matricula");
                int disciplina_id = rs.getInt("disciplina_id");
                grupoDeDisciplinas.add(new GrupoDeDisciplinas(facilitador_matricula, disciplina_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return grupoDeDisciplinas;
    }
}
