package donadores.pe.upeu.visa_shoes_project.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexionDB_V {
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            String ruta = System.getProperty(
                    "visa.shoes.db", "C:/sqlite/db/Visa_Shoes_DataBase.db");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + ruta);

            try (Statement statement = conexion.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON");
            }

            return conexion;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

}
