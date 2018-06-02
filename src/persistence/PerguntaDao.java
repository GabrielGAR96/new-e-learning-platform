package persistence;

import model.Pergunta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerguntaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Pergunta pergunta) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into pergunta (texto, assunto_id, resposta_id) values (?,?,?)");
            statement.setString(1, pergunta.getTexto());
            statement.setInt(2, pergunta.getAssuntoId());
            statement.setInt(3, pergunta.getRespostaId());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void alterar(Pergunta pergunta, String nome) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update pergunta set texto = ? where id = ?");
            statement.setString(1, nome);
            statement.setInt(2, pergunta.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void excluir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from pergunta where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Pergunta> buscarPorId(int id) {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pergunta where id = ? order by texto");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String texto = rs.getString("texto");
                int assuntoId = rs.getInt("assunto_id");
                int respostaId = rs.getInt("resposta_id");
                perguntas.add(new Pergunta(id, texto, assuntoId, respostaId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return perguntas;
    }

    public List<Pergunta> buscarPorAssunto(int assunto_id) {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pergunta where assunto_id = ?");
            statement.setInt(1, assunto_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int resposta_id = rs.getInt("resposta_id");
                perguntas.add(new Pergunta(id, texto, assunto_id, resposta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return perguntas;
    }

    public List<Pergunta> listar() {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pergunta");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int assunto_id = rs.getInt("assunto_id");
                int resposta_id = rs.getInt("resposta_id");
                perguntas.add(new Pergunta(id, texto, assunto_id, resposta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return perguntas;
    }

    public void excluirPelaResposta(int respostaId) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from pergunta where resposta_id = ?");
            statement.setInt(1, respostaId);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }
}
