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
                String url = "jdbc:sqlite:" + ruta;
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try (Statement statement = connection.createStatement()) {
                    statement.execute("PRAGMA foreign_keys = ON");
                }
                asegurarRelacionClienteVenta(connection);
            }

            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    private static void asegurarRelacionClienteVenta(Connection conexion) throws Exception {
        boolean existeColumna = false;
        try (Statement statement = conexion.createStatement();
             ResultSet columnas = statement.executeQuery("PRAGMA table_info(Venta)")) {
            while (columnas.next()) {
                if ("IdCliente".equalsIgnoreCase(columnas.getString("name"))) {
                    existeColumna = true;
                    break;
                }
            }
        }
        if (!existeColumna) {
            try (Statement statement = conexion.createStatement()) {
                statement.execute("ALTER TABLE Venta ADD COLUMN IdCliente INTEGER "
                        + "REFERENCES Cliente(IDcliente)");
            }
        }
    }

}
