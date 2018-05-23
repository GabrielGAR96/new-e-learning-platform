package persistence;

import model.Duvidas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuvidaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Duvidas duvidas) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into duvidas values (?,?,?,?)");
            statement.setInt(1, duvidas.getId());
            statement.setString(2, duvidas.getTexto());
            statement.setInt(3, duvidas.getAlunoMatricula());
            statement.setInt(4, duvidas.getFacilitadorMatricula());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Duvidas duvida) {
//
//    }

    public void excluir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from duvidas where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Duvidas> buscarPorAluno(int alunoMatricula) {
        ArrayList<Duvidas> duvidasDoAluno = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from duvidas where aluno_matricula = ?");
            statement.setInt(1, alunoMatricula);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int facilitadorMatricula = rs.getInt("facilitador_matricula");
                duvidasDoAluno.add(new Duvidas(id, texto, alunoMatricula, facilitadorMatricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return duvidasDoAluno;
    }

    public List<Duvidas> listar() {
        ArrayList<Duvidas> duvidas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from duvidas");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int alunoMatricula = rs.getInt("aluno_matricula");
                int facilitadorMatricula = rs.getInt("facilitador_matricula");
                duvidas.add(new Duvidas(id, texto, alunoMatricula, facilitadorMatricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return duvidas;
    }
}
