package model;

import javafx.beans.property.*;
import persistence.Conexao;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class Dao {
    private Connection con = null;
    private PreparedStatement statement = null;

    public <T> T inserir(T dado) {

        StringBuilder nomesDosCampos = new StringBuilder("(");
        StringBuilder valoresDosCampos = new StringBuilder("(");
        Field[] camposNaClasse = dado.getClass().getDeclaredFields();

        for (int i = 1; i < camposNaClasse.length; i++) {
            nomesDosCampos.append(camposNaClasse[i].getName()).append(",");
            valoresDosCampos.append("?,");
        }
        valoresDosCampos = new StringBuilder(valoresDosCampos.substring(0, valoresDosCampos.length() - 1));
        valoresDosCampos.append(") ");
        nomesDosCampos = new StringBuilder(nomesDosCampos.substring(0, nomesDosCampos.length() - 1));
        nomesDosCampos.append(") ");
        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("INSERT INTO " + dado.getClass().getSimpleName() + " " + nomesDosCampos + " VALUES " + valoresDosCampos);
            statementSetter(dado, camposNaClasse);
            statement.executeUpdate();

            String id = camposNaClasse[0].getName();
            statement = con.prepareStatement("SELECT * FROM " + dado.getClass().getSimpleName() + " ORDER BY " + id + " DESC LIMIT 1");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                for (Field field : dado.getClass().getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    fieldSetter(dado, field, valor);
                }
            }
        } catch (SQLException | IllegalAccessException e1) {
            e1.printStackTrace();
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
        Field[] camposNaClasse = dado.getClass().getDeclaredFields();
        String keyName = camposNaClasse[0].getName();

        StringBuilder campos = new StringBuilder();
        for (Field field : camposNaClasse) {
            campos.append(field.getName()).append(" = ?,");
        }
        campos = new StringBuilder(campos.substring(0, campos.length() - 1));

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("update " + dado.getClass().getSimpleName() + " set " + campos + " where " + keyName + "= ?");
            int parametrosSetados = statementSetter(dado, camposNaClasse);
            statement.setInt(parametrosSetados, key);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e1) {
            e1.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
    }

    public <T> T buscar(Class<T> tabela, String keyName, Object key) {
        T dado = null;

        try {
            dado = tabela.getDeclaredConstructor().newInstance();
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName() + " where " + keyName + " = ?");
            parametroSetter(key);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                for (Field field : tabela.getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    fieldSetter(dado, field, valor);
                }
            }
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return dado;
    }

    public <T> ArrayList<T> listar(Class<T> tabela) {
        ArrayList<T> dados = new ArrayList<>();
        T dado = null;

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                dado = tabela.getDeclaredConstructor().newInstance();
                for (Field field : tabela.getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    fieldSetter(dado, field, valor);
                }
                dados.add(dado);
            }
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return dados;
    }

    public <T> ArrayList<T> listarComFiltro(Class<T> tabela, String nomeFiltro, Object valorFiltro) {
        ArrayList<T> dados = new ArrayList<>();
        T dado = null;

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName() + " where " + nomeFiltro + " = ?");
            parametroSetter(valorFiltro);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                dado = tabela.getDeclaredConstructor().newInstance();
                for (Field field : tabela.getDeclaredFields()) {
                    Object valor = rs.getObject(field.getName());
                    fieldSetter(dado, field, valor);
                }
                dados.add(dado);
            }
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return dados;
    }



    private void parametroSetter(Object valorFiltro) throws SQLException {
        if (valorFiltro instanceof Integer)
            statement.setInt(1, (int) valorFiltro);
        else if (valorFiltro instanceof Double)
            statement.setDouble(1, (double) valorFiltro);
        else if (valorFiltro instanceof String)
            statement.setString(1, (String) valorFiltro);
    }

    public <T> ArrayList<Integer> buscarChaves(Class<T> tabela) {
        ArrayList<Integer> chaves = new ArrayList<>();

        try {
            con = Conexao.getConnection();
            statement = con.prepareStatement("select * from " + tabela.getSimpleName(), statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            int i = 0;
            while (rs.next()) {
                chaves.add(rs.getInt(i));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(statement, con);
        }
        return chaves;
    }


    private <T> void fieldSetter(T dado, Field field, Object valor) throws IllegalAccessException {
        if (field.getType().equals(IntegerProperty.class))
            field.set(dado, new SimpleIntegerProperty((int) valor));
        else if (field.getType().equals(DoubleProperty.class))
            field.set(dado, new SimpleDoubleProperty((double) valor));
        else if (field.getType().equals(StringProperty.class))
            field.set(dado, new SimpleStringProperty((String) valor));
        else
            field.set(dado, new SimpleObjectProperty<>((Date) valor));
    }

    private <T> int statementSetter(T dado, Field[] camposNaClasse) throws SQLException, IllegalAccessException {
        int i;
        for (i = 1; i < camposNaClasse.length; i++) {
            Object valor = camposNaClasse[i].get(dado);
            if (valor instanceof IntegerProperty)
                statement.setInt(i, ((IntegerProperty) valor).get());
            else if (valor instanceof DoubleProperty)
                statement.setDouble(i, ((DoubleProperty) valor).get());
            else if (valor instanceof StringProperty)
                statement.setString(i, ((StringProperty) valor).get());
            else if (valor instanceof SimpleObjectProperty)
                statement.setDate(i, new java.sql.Date(((SimpleObjectProperty<java.util.Date>) valor).get().getTime()));
        }
        return i; //Retorna quantos parametros foram setados + 1.
    }

}
