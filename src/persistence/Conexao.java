package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao
{
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/e-learning_BD?autoReconnect=true&useSSL=false","root","12345678");
    }

    public static void closeConnection(PreparedStatement statement, Connection con) {
        try {
            //fechar statement e connection
            if (statement != null)
                statement.close();
            if (con != null)
                con.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}