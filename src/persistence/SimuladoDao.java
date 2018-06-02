package persistence;

import model.Simulado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimuladoDao {
    Connection con = null;
    PreparedStatement statement = null;

    public void inserir(Simulado simulado) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into Simulado values (?,?,?,?)");
            statement.setInt(1, simulado.getId());
            statement.setInt(2, simulado.getAlunoMatricula());
            statement.setInt(3, simulado.getNota());
            statement.setInt(4, simulado.getAssuntoId());
            statement.setInt(5, simulado.getFacilitadorMatricula());
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public void alterar(Simulado simulado) {
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("update simulado set id = ?, aluno_matricula = ?, nota = ?, assunto_id = ?, facilitador_matricula = ? ");
//            statement.setInt(1, simulado.getId());
//            statement.setInt(2, simulado.getAlunoMatricula());
//            statement.setInt(3, simulado.getNota());
//            statement.setInt(4, simulado.getAssuntoId());
//            statement.setInt(5, simulado.getFacilitadorMatricula());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void exlcuir(int id) {
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from simulado where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public List<Simulado> buscarPorAssunto(int assunto_id) {
        ArrayList<Simulado> simulados = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from simulado where assunto_id = ?");
            statement.setInt(1, assunto_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int aluno_matricula = rs.getInt("aluno_matricula");
                int nota = rs.getInt("nota");
                int facilitador_matricula = rs.getInt("facilitador_matricula");
                simulados.add(new Simulado(id, aluno_matricula, nota, assunto_id, facilitador_matricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return simulados;
    }

    public List<Simulado> listar() {
        ArrayList<Simulado> simulados = new ArrayList<>();
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from simulado");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int aluno_matricula = rs.getInt("alunoo_matricula");
                int nota = rs.getInt("nota");
                int assunto_id = rs.getInt("assunto_id");
                int facilitador_matricula = rs.getInt("facilitador_matricula");
                simulados.add(new Simulado(id,aluno_matricula,nota,assunto_id,facilitador_matricula));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return simulados;
    }
}
