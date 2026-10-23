package donadores.pe.upeu.visa_shoes_project.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexionDB_V {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");

                String url = "jdbc:sqlite:C:/sqlite/db/Visa_Shoes_DataBase.db";
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement statement = connection.createStatement()) {
                    statement.execute("PRAGMA foreign_keys = ON");
                }
            }

            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

}