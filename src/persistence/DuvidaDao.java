package persistence;

import model.Duvida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuvidaDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Duvida duvida) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into duvidas values (?,?,?,?)");
            statement.setInt(1, duvida.getId());
            statement.setString(2, duvida.getTexto());
            statement.setInt(3, duvida.getAlunoMatricula());
            statement.setInt(4, duvida.getFacilitadorMatricula());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Duvida duvida) {
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

    public List<Duvida> buscarPorAluno(int alunoMatricula) {
        ArrayList<Duvida> duvidaDoAluno = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from duvidas where aluno_matricula = ?");
            statement.setInt(1, alunoMatricula);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int facilitadorMatricula = rs.getInt("facilitador_matricula");
                duvidaDoAluno.add(new Duvida(id, texto, alunoMatricula, facilitadorMatricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return duvidaDoAluno;
    }

    public List<Duvida> listar() {
        ArrayList<Duvida> duvidas = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from duvidas");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texto = rs.getString("texto");
                int alunoMatricula = rs.getInt("aluno_matricula");
                int facilitadorMatricula = rs.getInt("facilitador_matricula");
                duvidas.add(new Duvida(id, texto, alunoMatricula, facilitadorMatricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return duvidas;
    }
}
