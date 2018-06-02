package model;

import javafx.beans.property.*;
import persistence.Conexao;

import java.lang.reflect.Field;
import java.sql.*;

public class Dao {
    private Connection con = null;
    private PreparedStatement statement = null;

    public <T> void inserir(T dado) {

        String campos = "(";
        for (Field field : dado.getClass().getDeclaredFields()) {
            campos += "?,";
        }
        campos = campos.substring(0, campos.length() - 1);
        campos += ")";

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("insert into " + dado.getClass().getSimpleName() + " values " + campos);
            for (int i = 0; i < dado.getClass().getDeclaredFields().length; i++) {
                Object valor = dado.getClass().getDeclaredFields()[i].get(dado);
                if (valor instanceof IntegerProperty)
                    statement.setInt(i + 1, ((IntegerProperty) valor).get());
                else if (valor instanceof DoubleProperty)
                    statement.setDouble(i + 1, ((DoubleProperty) valor).get());
                else if (valor instanceof StringProperty)
                    statement.setString(i + 1, ((StringProperty) valor).get());
                else if (valor instanceof SimpleObjectProperty) {
                    statement.setDate(i + 1, new java.sql.Date(((SimpleObjectProperty<java.util.Date>) valor).get().getTime()));
                }
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
            statement = con.prepareStatement("delete from " + dado.getClass().getSimpleName() + " where " + keyName + " = ?");
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

        String campos = new String();
        for (Field field : dado.getClass().getDeclaredFields()) {
            campos += field.getName() + " = ?,";
        }
        campos = campos.substring(0, campos.length() - 1);

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update " + dado.getClass().getSimpleName() + " set " + campos + " where " + keyName + "= ?");
            int i;
            for (i = 1; i < dado.getClass().getDeclaredFields().length; i++) {
                Object valor = dado.getClass().getDeclaredFields()[i].get(dado);
                if (valor instanceof IntegerProperty)
                    statement.setInt(i + 1, ((IntegerProperty) valor).get());
                if (valor instanceof DoubleProperty)
                    statement.setDouble(i + 1, ((DoubleProperty) valor).get());
                if (valor instanceof StringProperty)
                    statement.setString(i + 1, ((StringProperty) valor).get());
                if (valor instanceof SimpleObjectProperty)
                    statement.setDate(i + 1, new java.sql.Date(((SimpleObjectProperty<java.util.Date>) valor).get().getTime()));
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

    public <T> T buscar(Class<T> tabela, int key) {
        String keyName = tabela.getDeclaredFields()[0].getName();
        T dado;

        try {
            dado = tabela.getDeclaredConstructor().newInstance();
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName() + " where " + keyName + " = ?");
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                for (Field field : tabela.getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    field.set(dado, valor);
                }
            }

            return dado;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException r) {
            r.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return null;
    }

}
