package donadores.pe.upeu.visa_shoes_project.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConexionDB_V {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");

                String ruta = System.getProperty(
                        "visa.shoes.db", "C:/sqlite/db/Visa_Shoes_DataBase.db");
                connection = DriverManager.getConnection("jdbc:sqlite:" + ruta);
                connection.setAutoCommit(true);

                try (Statement statement = connection.createStatement()) {
                    statement.execute("PRAGMA foreign_keys = ON");
                    statement.execute("CREATE TABLE IF NOT EXISTS Usuario ("
                            + "IDusuario INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "Usuario TEXT NOT NULL UNIQUE, "
                            + "Clave TEXT NOT NULL, "
                            + "TipoUser TEXT NOT NULL, "
                            + "Nombre TEXT NOT NULL, "
                            + "Apellidos TEXT, "
                            + "Documento TEXT)");
                }
            }

            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }


}
