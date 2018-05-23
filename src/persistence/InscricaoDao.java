package persistence;

import model.Inscricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InscricaoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Inscricao inscricao) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into inscricao (aluno_matricula, data) values (?,?)");
            statement.setInt(1, inscricao.getAlunoMatricula());
            java.sql.Date data = new java.sql.Date(inscricao.getData().getTime());
            statement.setDate(2, data);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }

    }

    public void alterar(Inscricao inscricao) {

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update inscricao set id = ?, matricula = ?, data = ?");
            statement.setInt(1, inscricao.getId());
            statement.setInt(2, inscricao.getAlunoMatricula());
            statement.setObject(3, inscricao.getData());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void excluir(int id) {
        DisciplinaInscritaDao disciplinaInscritaDao = new DisciplinaInscritaDao();
        disciplinaInscritaDao.excluirPelaInscricao(id);
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from inscricao where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public Inscricao buscarPorMatricula(int alunoMatricula) {
        Inscricao inscricao = null;
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from inscricao where aluno_matricula = ?");
            statement.setInt(1, alunoMatricula);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Date data = rs.getTimestamp("data");
                inscricao = new Inscricao(id, alunoMatricula, data);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return inscricao;
    }

    public List<Inscricao> listar() {
        ArrayList<Inscricao> inscricoes = new ArrayList<>();
        try{
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from inscricao order by matricula");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int alunoMatricula = rs.getInt("matricula");
                Date data = rs.getDate("data");
                inscricoes.add(new Inscricao(id, alunoMatricula, data));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return inscricoes;
    }
}