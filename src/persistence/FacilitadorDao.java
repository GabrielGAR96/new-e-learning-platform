package persistence;

import model.Facilitador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilitadorDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Facilitador facilitador) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into facilitador values (?,?)");
            statement.setInt(1, facilitador.getMatricula());
            statement.setString(2, facilitador.getNome());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Facilitador facilitador) {
//
//    }

    public void excluir(int matricula) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from facilitador where matricula = ?");
            statement.setInt(1, matricula);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public Facilitador buscarPorMatricula(int matricula) {
        Facilitador facilitador = null;
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from facilitador where matricula = ?");
//            statement.setInt(1, matricula);
//            ResultSet rs = statement.executeQuery();
//            if(rs.next()) {
//                facilitador = new Facilitador(matricula, rs.getString("nome"));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return facilitador;
    }

    public List<Facilitador> listar() {
        ArrayList<Facilitador> facilitadores = new ArrayList<>();
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from facilitador");
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                int matricula = rs.getInt("matricula");
//                String nome = rs.getString("nome");
//                facilitadores.add(new Facilitador(matricula, nome));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return facilitadores;
    }
}
