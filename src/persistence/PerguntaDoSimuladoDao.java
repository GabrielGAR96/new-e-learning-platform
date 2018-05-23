package persistence;

import model.PerguntaDoSimulado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerguntaDoSimuladoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(PerguntaDoSimulado perguntaDoSimulado) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into pergunta_do_simulado values (?,?)");
            statement.setInt(1, perguntaDoSimulado.getSimulado_id());
            statement.setInt(2, perguntaDoSimulado.getPergunta_id());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);

        }
    }

    public void alterar(PerguntaDoSimulado perguntaDoSimulado) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update pergunta_do_simulado set pergunta_id = ?, simulado_id = ?");
            statement.setInt(1, perguntaDoSimulado.getPergunta_id());
            statement.setInt(2, perguntaDoSimulado.getSimulado_id());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void excluir(int id_pergunta, int id_simulado) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from pergunta_do_simulado where pergunta_id = ? and simulado_id = ?");
            statement.setInt(1, id_pergunta);
            statement.setInt(2, id_simulado);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<PerguntaDoSimulado> buscarPerguntasDoSimulado(int id_simulado) {
        ArrayList<PerguntaDoSimulado> perguntasDoSimulado = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pergunta_do_simulado where simulado_id = ?");
            statement.setInt(1, id_simulado);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int pergunta_id = rs.getInt("pergunta_id");
                perguntasDoSimulado.add(new PerguntaDoSimulado(id_simulado, pergunta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return perguntasDoSimulado;
    }

    public List<PerguntaDoSimulado> listar() {
        ArrayList<PerguntaDoSimulado> perguntasDosSimulados = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pergunta_do_simulado");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int simulado_id = rs.getInt("simulado_id");
                int pergunta_id = rs.getInt("pergunta_id");
                perguntasDosSimulados.add(new PerguntaDoSimulado(simulado_id, pergunta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return perguntasDosSimulados;
    }
}

