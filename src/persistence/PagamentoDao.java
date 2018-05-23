package persistence;

import model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagamentoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Pagamento pagamento) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into pgmt_aluno values (?,?,?,?)");
            statement.setInt(1, pagamento.getId());
            statement.setDouble(2, pagamento.getValor());
            statement.setObject(3, pagamento.getData());
            statement.setInt(4, pagamento.getIdInscricao());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void alterar(Pagamento pagamento) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update pgmt_aluno set id = ?, valor = ?, data = ?, inscricao_id=?");
            statement.setInt(1, pagamento.getId());
            statement.setDouble(2, pagamento.getValor());
            statement.setObject(3, pagamento.getData());
            statement.setInt(4, pagamento.getIdInscricao());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public void excluir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from pgmt_aluno where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Pagamento> buscarPorId(int id) {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pgmt_aluno where id = ? order by inscricao_id");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            addPagamentoToList(pagamentos, rs);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return pagamentos;
    }

    public List<Pagamento> buscarPorInscricao(int inscricaoId) {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pgmt_aluno where inscricao_id = ? order by inscricao_id");
            statement.setInt(1, inscricaoId);
            ResultSet rs = statement.executeQuery();
            addPagamentoToList(pagamentos, rs);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return pagamentos;
    }

    public List<Pagamento> listar() {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from pgmt_aluno order by inscricao_id");
            ResultSet rs = statement.executeQuery();
            addPagamentoToList(pagamentos, rs);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return pagamentos;
    }

    private void addPagamentoToList(ArrayList<Pagamento> pagamentos, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("id");
            double valor = rs.getDouble("valor");
            java.sql.Date data = rs.getDate("data");
            Date date = Date.from(data.toInstant());
            int idInscricao = rs.getInt("inscricao_id");

            pagamentos.add(new Pagamento(id, valor, date, idInscricao));
        }
    }
}