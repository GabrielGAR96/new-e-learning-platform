package persistence;

import model.DisciplinaInscrita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaInscritaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir (DisciplinaInscrita disciplinaInscrita) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into DisciplinaInscrita values (?,?)");
            statement.setInt(1, disciplinaInscrita.getDisciplinaId());
            statement.setInt(2, disciplinaInscrita.getInscricaoId());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar (DisciplinaInscrita disciplinaInscrita) {
//
//    }

    public void excluirPorDisciplina(int disciplina_id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from DisciplinaInscrita where disciplinaId = ?");
            statement.setInt(1, disciplina_id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<DisciplinaInscrita> buscarPorInscricao(int inscricao_id) {
        ArrayList<DisciplinaInscrita> disciplinasInscritas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from DisciplinaInscrita where inscricaoId = ?");
            statement.setInt(1, inscricao_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int disciplina_id = rs.getInt("disciplina_id");
                disciplinasInscritas.add(new DisciplinaInscrita(disciplina_id,inscricao_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return disciplinasInscritas;
    }

    public List<DisciplinaInscrita> listar() {
        ArrayList<DisciplinaInscrita> disciplinaInscritas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from DisciplinaInscrita");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int disciplina_id = rs.getInt("disciplina_id");
                int inscricao_id = rs.getInt("inscricao_id");
                disciplinaInscritas.add(new DisciplinaInscrita(disciplina_id, inscricao_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return disciplinaInscritas;
    }

    public void excluirPelaInscricao(int inscricao_id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from DisciplinaInscrita where inscricaoId = ?");
            statement.setInt(1, inscricao_id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

}
