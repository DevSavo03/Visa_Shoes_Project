package donadores.pe.upeu.visa_shoes_project.test;

import donadores.pe.upeu.visa_shoes_project.utils.ConexionDB_V;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTablas {

    public static void revisarTablas() {
        String[] tablas = {"Cliente", "Producto", "Venta", "DetalleVenta"};
        String[] consultas = {
                "SELECT IDcliente, Persona, Apellido, Doc, Celular, Direccion, Correo "
                        + "FROM Cliente LIMIT 0",
                "SELECT IDproducto, Modelo, Marca, Talla, Precio, Stock "
                        + "FROM Producto LIMIT 0",
                "SELECT IdVenta, Fecha, Total FROM Venta LIMIT 0",
                "SELECT IDetalle, IdVenta, IDproducto, Cantidad, PrecioUnit, Importe "
                        + "FROM DetalleVenta LIMIT 0"
        };

        try (Connection conexion = ConexionDB_V.getConnection()) {
            for (int i = 0; i < tablas.length; i++) {
                try (Statement sentencia = conexion.createStatement();
                     ResultSet resultado = sentencia.executeQuery(consultas[i])) {
                    System.out.println("Tabla disponible: " + tablas[i]);
                } catch (SQLException e) {
                    System.err.println("No se pudo consultar la tabla " + tablas[i] + ": "
                            + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudieron revisar las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        revisarTablas();
    }
}
