package persistence;

import model.RespostaDoSimulado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RespostaDoSimuladoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(RespostaDoSimulado respostaDoSimulado) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into resposta_do_simulado");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

//    public void alterar(RespostaDoSimulado respostaDoSimulado) {
//
//    }

    public void excluir (int simulado_id, int resposta_id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from resposta_do_simulado where simulado_id = ? and reposta_id = ?");
            statement.setInt(1, simulado_id);
            statement.setInt(2, resposta_id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<RespostaDoSimulado> buscarRespostasDoSimulado(int simulado_id) {
        ArrayList<RespostaDoSimulado> respostaDoSimulados = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from simulado where simulado_id = ?");
            statement.setInt(1, simulado_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int reposta_id = rs.getInt("resposta_id");
                respostaDoSimulados.add(new RespostaDoSimulado(simulado_id, reposta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return respostaDoSimulados;
    }

    public List<RespostaDoSimulado> listar() {
        ArrayList<RespostaDoSimulado> respostasDoSimulados = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from resposta_do_simulado");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int simulado_id = rs.getInt("simulado_id");
                int resposta_id = rs.getInt("resposta_id");
                respostasDoSimulados.add(new RespostaDoSimulado(simulado_id, resposta_id));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return respostasDoSimulados;
    }
}
