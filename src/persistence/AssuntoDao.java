package persistence;

import model.Assunto;
import model.Pergunta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssuntoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Assunto assunto){
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into assunto (nome, disciplina_id) values (?,?)");
            statement.setString(1, assunto.getNome());
            statement.setInt(2, assunto.getDisciplina_id());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Assunto assunto) {
//
//    }

    public void excluir(int id) {
        PerguntaDao perguntaDao = new PerguntaDao();
        List<Pergunta> perguntas = perguntaDao.buscarPorAssunto(id);
        for(Pergunta pergunta : perguntas) {
            perguntaDao.excluir(pergunta.getId());
        }

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from assunto where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Assunto> buscarPorId(int id) {
        ArrayList<Assunto> assuntos = new ArrayList<>();
        try {
            con  = Conexao.getConnection();
            statement = con.prepareStatement("select * from assunto where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                int disciplina_id = rs.getInt("disciplina_id");
                assuntos.add(new Assunto(id, nome, disciplina_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return assuntos;
    }

    public Assunto buscarPorNome(String nome) {
        Assunto assunto = null;
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from assunto where nome = ?");
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                assunto = new Assunto(rs.getInt("id"), nome, rs.getInt("disciplina_id"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return assunto;
    }

    public List<Assunto> listar() {
        ArrayList<Assunto> assuntos = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from assunto");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int disciplina_id = rs.getInt("disciplina_id");
                assuntos.add(new Assunto(id, nome, disciplina_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return assuntos;
    }

    public List<Assunto> listarPorDisciplina(int idDisciplina) {
        ArrayList<Assunto> assuntos = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from assunto where disciplina_id = ? order by nome");
            statement.setInt(1, idDisciplina);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                assuntos.add(new Assunto(id, nome, idDisciplina));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return assuntos;
    }
}
