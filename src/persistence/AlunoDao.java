package persistence;


import model.Aluno;
import model.DisciplinaInscrita;
import model.Inscricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

//    Connection con = null;
//    PreparedStatement statement = null;
//
    public void inserir(Aluno aluno) {
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("insert into Aluno values (?,?)");
//            statement.setInt(1, aluno.getMatricula());
//            statement.setString(2, aluno.getNome());
//            statement.executeUpdate();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
//
    }

    public void alterar(Aluno aluno) {
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("update aluno set matricula = ?, nome = ?");
//            statement.setInt(1, aluno.getMatricula());
//            statement.setString(2, aluno.getNome());
//            statement.executeUpdate();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            try {
//                //fechar statement e connection
//                if (statement != null)
//                    statement.close();
//                if (con != null)
//                    con.close();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//        }
    }

    public void excluir(int matricula) {
//
//        InscricaoDao inscricaoDao = new InscricaoDao();
//        Inscricao inscricao = inscricaoDao.buscarPorMatricula(matricula);
//
//        DisciplinaInscritaDao disciplinaInscritaDao = new DisciplinaInscritaDao();
//
//        List<DisciplinaInscrita> disciplinasInscritas = disciplinaInscritaDao.buscarPorInscricao(inscricao.getId());
//        for(DisciplinaInscrita di : disciplinasInscritas) {
//            disciplinaInscritaDao.excluirPelaInscricao(di.getInscricaoId());
//        }
//
//        inscricaoDao.excluir(inscricao.getId());
//
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("delete from Aluno where matricula = ?");
//            statement.setInt(1, matricula);
//            statement.executeUpdate();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
    }

    public List<Aluno> buscarPorNome(String nome) {
        ArrayList<Aluno> alunos = new ArrayList<>();
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from Aluno where nome like ? order by nome");
//            statement.setString(1, "%" + nome + "%");
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                int matricula = rs.getInt("matricula");
//                String name = rs.getString("nome");
//                alunos.add(new Aluno(matricula, name));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return alunos;
    }

    public Aluno buscarPorMatricula(int matricula) {
        Aluno aluno = null;
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from Aluno where matricula = ?");
//            statement.setInt(1, matricula);
//            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                aluno = new Aluno(matricula, rs.getString("nome"));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return aluno;
    }

    public List<Aluno> listar() {
        ArrayList<Aluno> alunos = new ArrayList<>();
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from Aluno order by nome");
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                int matricula = rs.getInt("matricula");
//                String nome = rs.getString("nome");
//                alunos.add(new Aluno(matricula, nome));
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
        return alunos;
    }

}