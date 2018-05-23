package persistence;

import model.Resposta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RespostaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Resposta resposta) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into resposta (texto, tipo) values(?,?)");
            statement.setString(1, resposta.getTexto());
            statement.setString(2, resposta.getTipo());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Resposta resposta) {
//
//    }

    public void excluir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from resposta where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Resposta> buscarPorId(int id) {
        ArrayList<Resposta> respostas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from resposta where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String texto = rs.getString("texto");
                String tipo = rs.getString("tipo");

            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.clearBatch();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return respostas;
    }

    public List<Resposta> listar() {
        ArrayList<Resposta> respostas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from resposta");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                String tipo = rs.getString("tipo");
                respostas.add(new Resposta(id, texto, tipo));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return respostas;
    }

    public Resposta buscarPorTexto(String textoPergunta) {
        Resposta resposta = null;
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from resposta where texto = ?");
            statement.setString(1, textoPergunta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                String tipo = rs.getString("tipo");
                resposta = new Resposta(id, texto, tipo);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return resposta;
    }
}
