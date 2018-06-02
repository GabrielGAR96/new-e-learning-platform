package model;
import javafx.beans.property.*;
import persistence.Conexao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

public class Dao {
    private Connection con = null;
    private PreparedStatement statement = null;

    public <T> T inserir(T dado) {

        String nomesDosCampos = "(";
        String valoresDosCampos = "(";
        Field[] camposNaClasse = dado.getClass().getDeclaredFields();

        for(int i = 1; i < camposNaClasse.length; i++) {
            nomesDosCampos += (camposNaClasse[i].getName() + ",");
            valoresDosCampos += "?,";
        }
        valoresDosCampos = valoresDosCampos.substring(0, valoresDosCampos.length() - 1);
        valoresDosCampos += ") ";
        nomesDosCampos = nomesDosCampos.substring(0, nomesDosCampos.length() - 1);
        nomesDosCampos += ") ";
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("INSERT INTO " + dado.getClass().getSimpleName() + " " + nomesDosCampos + " VALUES " + valoresDosCampos);
            for (int i = 1; i < dado.getClass().getDeclaredFields().length; i++) {
                Field[] fds = dado.getClass().getDeclaredFields();
                Object valor = fds[i].get(dado);
                if (valor instanceof IntegerProperty)
                    statement.setInt(i, ((IntegerProperty) valor).get());
                else if (valor instanceof DoubleProperty)
                    statement.setDouble(i, ((DoubleProperty) valor).get());
                else if (valor instanceof StringProperty)
                    statement.setString(i, ((StringProperty) valor).get());
                else if (valor instanceof SimpleObjectProperty) {
                    statement.setDate(i, new java.sql.Date(((SimpleObjectProperty<java.util.Date>) valor).get().getTime()));
                }
            }
            statement.executeUpdate();

            String id = camposNaClasse[0].getName();
            statement = con.prepareStatement("SELECT * FROM " + dado.getClass().getSimpleName() + " ORDER BY " + id + " DESC LIMIT 1");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                for (Field field : dado.getClass().getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    System.out.println(valor);
                    String nomeDoCampo = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Class<?> clazz;
                    if (field.getType().equals(IntegerProperty.class))
                        clazz = int.class;
                    else if (field.getType().equals(DoubleProperty.class))
                        clazz = double.class;
                    else if (field.getType().equals(StringProperty.class))
                        clazz = String.class;
                    else
                        clazz = Date.class;
                    Method method = dado.getClass().getDeclaredMethod("set" + nomeDoCampo, clazz);
                    method.invoke(dado, valor);
                    //field.set(dado, valor);
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }

        return dado;
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
                else if (valor instanceof DoubleProperty)
                    statement.setDouble(i + 1, ((DoubleProperty) valor).get());
                else if (valor instanceof StringProperty)
                    statement.setString(i + 1, ((StringProperty) valor).get());
                else if (valor instanceof SimpleObjectProperty)
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

    public <T> T buscar(Class<T> tabela, String keyName, Object key) {
        T dado;

        try {
            dado = tabela.getDeclaredConstructor().newInstance();
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName() + " where " + keyName + " = ?");
            if(key instanceof Integer)
                statement.setInt(1, (int) key);
            else if(key instanceof Double)
                statement.setDouble(1, (double) key);
            else if(key instanceof String)
                statement.setString(1, (String) key);

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
