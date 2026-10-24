package donadores.pe.upeu.visa_shoes_project.test;

import donadores.pe.upeu.visa_shoes_project.Utils.ConexionDB_V;

import java.sql.Connection;

public class TestConexion {

    public static void testConexion() {
        try (Connection conexion = ConexionDB_V.getConnection()) {
            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexión correcta con SQLite.");
            } else {
                System.out.println("No se pudo abrir la conexión.");
            }
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testConexion();
    }
}
