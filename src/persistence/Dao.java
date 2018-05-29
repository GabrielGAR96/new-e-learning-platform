package persistence;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import javafx.beans.binding.ObjectBinding;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dao {
    /*
    *
    * TODO:
    * 1) O nome da classe tem que ser exatamente o nome do banco.
    * 2) O primeiro atributo da classe tem que ser a primary key
    *
     */

    Connection con = null;
    PreparedStatement statement = null;

    public <T> void inserir(T dado) {

        String campos = "(";
        for(Field field : dado.getClass().getDeclaredFields()) {
            campos += "?,";
        }
        campos = campos.substring(0, campos.length() - 1);
        campos += ")";

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into " + dado.getClass().getSimpleName() + " values " + campos);
            for(int i = 1; i < dado.getClass().getDeclaredFields().length; i++) {
                dado.getClass().getDeclaredFields()[i].setAccessible(true);
                Object valor = dado.getClass().getDeclaredFields()[i].get(dado);
                dado.getClass().getDeclaredFields()[i].setAccessible(false);
                statement.setObject(i, valor);
            }
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public <T> void excluir(T dado, int key) {
        String keyName = dado.getClass().getDeclaredFields()[0].getName();

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("delete from "+ dado.getClass().getSimpleName() + " where "+ keyName +" = ?");
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public <T> void alterar(T dado, int key) {
        String keyName = dado.getClass().getDeclaredFields()[0].getName();

        String campos = "set ";
        for(Field field : dado.getClass().getDeclaredFields()) {
            campos += field.getName() + " = ?,";
        }
        campos = campos.substring(0, campos.length() - 1);

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update " + dado.getClass().getSimpleName() + " set " + campos + " where " + keyName + "= ?");
            int i;
            for(i = 1; i < dado.getClass().getDeclaredFields().length; i++) {
                dado.getClass().getDeclaredFields()[i].setAccessible(true);
                Object valor = dado.getClass().getDeclaredFields()[i].get(dado);
                dado.getClass().getDeclaredFields()[i].setAccessible(false);
                statement.setObject(i, valor);
            }
            statement.setInt(i, key);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

//    public <T> T buscar(int key, Class<T> tabela) {
//        String keyName = tabela.getDeclaredFields()[0].getName();
//        ArrayList<Object> row = new ArrayList<>();
//        T dado;
//
//        try {
//            con = Conexao.getConnection();
//            statement = con.prepareStatement("select * from " + tabela.getSimpleName() + " where " + keyName + " = ?");
//            statement.setInt(1, key);
//            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                for(Field field : tabela.getDeclaredFields()) {
//                    Object value = rs.getObject(field.getName());
//                    row.add(value);
//                }
//            }
//
//            for(Object value : row) {
//
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        } finally {
//            Conexao.closeConnection(statement, con);
//        }
//        return dado;
//    }
}
