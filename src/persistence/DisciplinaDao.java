package persistence;

import model.Assunto;
import model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Disciplina disciplina) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into disciplina (nome, valor) values (?,?)");
            statement.setString(1, disciplina.getNome());
            statement.setDouble(2, disciplina.getValor());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }


//    public void alterar(Disciplina disciplina) {
//
//    }

    public void excluir(int id) {

        AssuntoDao assuntoDao = new AssuntoDao();
        List<Assunto> assuntos = assuntoDao.listarPorDisciplina(id);
        for(Assunto assunto : assuntos) {
            assuntoDao.excluir(assunto.getId());
        }

        DisciplinaInscritaDao disciplinaInscritaDao = new DisciplinaInscritaDao();
        disciplinaInscritaDao.excluirPorDisciplina(id);

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from disciplina where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Disciplina> buscarPorId(int id) {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from disciplina where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                double valor = rs.getDouble("valor");
                disciplinas.add(new Disciplina(id, nome, valor));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return disciplinas;
    }

    public Disciplina buscarPorNome(String nome) {
        Disciplina disciplina = null;
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from Disciplina where nome = ?");
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                disciplina = new Disciplina(rs.getInt("id"), nome, rs.getDouble("valor"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return disciplina;
    }

    public List<Disciplina> listar() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from Disciplina");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double valor = rs.getDouble("valor");
                disciplinas.add(new Disciplina(id, nome, valor));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return disciplinas;
    }
}
