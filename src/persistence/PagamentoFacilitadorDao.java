package persistence;

import model.PagamentoFacilitador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagamentoFacilitadorDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(PagamentoFacilitador pagamentoFacilitador) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into pgmt_facilitador values(?,?,?,?,?,?)");
            statement.setInt(1, pagamentoFacilitador.getId());
            statement.setDouble(2, pagamentoFacilitador.getValor());
            java.sql.Date data = new java.sql.Date(pagamentoFacilitador.getData().getTime());
            statement.setDate(3, data);
            statement.setInt(4, pagamentoFacilitador.getFacilitadorMatricula());
            statement.setInt(5, pagamentoFacilitador.getSimuladoId());
            statement.setInt(6, pagamentoFacilitador.getDuvidaId());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(PagamentoFacilitador pagamentoFacilitador) {
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void excluir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from pgmt_facilitador where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<PagamentoFacilitador> buscarPorFacilitador(int facilitadorMatricula) {
        ArrayList<PagamentoFacilitador> pagamentosDoFacilitador = new ArrayList<>();
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from pgmt_facilitador where facilitador_matricula = ?");
//            statement.setInt(1, facilitadorMatricula);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                double valor = rs.getDouble("valor");
//                Date data = rs.getTimestamp("data");
//                int simuladoId = rs.getInt("simulado_id");
//                int duvidaId = rs.getInt("duvida_id");
//                pagamentosDoFacilitador.add(new PagamentoFacilitador(id, valor, data, facilitadorMatricula, simuladoId, duvidaId));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return pagamentosDoFacilitador;
    }

    public List<PagamentoFacilitador> listar() {
        ArrayList<PagamentoFacilitador> pagamentosDosFacilitadores = new ArrayList<>();
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from pgmt_facilitador");
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                double valor = rs.getDouble("valor");
//                Date data = rs.getTimestamp("data");
//                int facilitadorMatricula = rs.getInt("facilitador_matricula");
//                int simuladoId = rs.getInt("simulado_id");
//                int duvidaId = rs.getInt("duvida_id");
//                pagamentosDosFacilitadores.add(new PagamentoFacilitador(id, valor, data, facilitadorMatricula, simuladoId, duvidaId));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return pagamentosDosFacilitadores;
    }
}