package donadores.pe.upeu.visa_shoes_project.implementacion;

import donadores.pe.upeu.visa_shoes_project.interfaces.IVentaDao;
import donadores.pe.upeu.visa_shoes_project.modelo.DetalleVenta;
import donadores.pe.upeu.visa_shoes_project.modelo.Producto;
import donadores.pe.upeu.visa_shoes_project.modelo.Venta;
import donadores.pe.upeu.visa_shoes_project.utils.ConexionDB_V;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaDaoImpl implements IVentaDao {

    @Override
    public boolean insert(Venta venta) {
        if (venta == null || venta.getIdCliente() <= 0 || venta.getDetalleVenta().isEmpty()) {
            return false;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            if (detalle == null || detalle.getProducto() == null
                    || detalle.getProducto().getIdproducto() <= 0
                    || detalle.getCantidad() <= 0
                    || detalle.getPrecioUnit() == null
                    || detalle.getPrecioUnit().compareTo(BigDecimal.ZERO) < 0) {
                return false;
            }

            detalle.calcularImporte();
            total = total.add(detalle.getImporte());
        }
        venta.setTotal(total);

        try (Connection conexion = ConexionDB_V.getConnection()) {
            conexion.setAutoCommit(false);

            try {
                String consulta = "INSERT INTO Venta (Total, IdCliente) VALUES (?, ?)";
                try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                    sentencia.setBigDecimal(1, total);
                    sentencia.setInt(2, venta.getIdCliente());
                    sentencia.executeUpdate();
                }

                int idVenta = obtenerUltimoId(conexion);
                venta.setIdventa(idVenta);

                for (DetalleVenta detalle : venta.getDetalleVenta()) {
                    if (!descontarStock(conexion, detalle)) {
                        conexion.rollback();
                        return false;
                    }

                    detalle.setIdventa(idVenta);
                    guardarDetalle(conexion, detalle);
                }

                conexion.commit();
                return true;
            } catch (SQLException e) {
                conexion.rollback();
                System.out.println("Error al registrar la venta: " + e.getMessage());
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar para registrar la venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Venta searchById(int id) {
        String consulta = consultaVentas() + " WHERE v.IdVenta = ?";
        Venta venta = null;

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    venta = crearVenta(resultado);
                }
            }

            if (venta != null) {
                cargarDetalles(conexion, venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la venta: " + e.getMessage());
            return null;
        }
        return venta;
    }

    @Override
    public List<Venta> listAll() {
        List<Venta> ventas = new ArrayList<>();
        String consulta = consultaVentas() + " ORDER BY v.IdVenta DESC";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ventas.add(crearVenta(resultado));
                }
            }

            for (Venta venta : ventas) {
                cargarDetalles(conexion, venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las ventas: " + e.getMessage());
        }
        return ventas;
    }

    private boolean descontarStock(Connection conexion, DetalleVenta detalle)
            throws SQLException {
        String consultaStock = "SELECT Stock FROM Producto WHERE IDproducto = ?";
        int stockActual;

        try (PreparedStatement sentencia = conexion.prepareStatement(consultaStock)) {
            sentencia.setInt(1, detalle.getProducto().getIdproducto());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (!resultado.next()) {
                    return false;
                }
                stockActual = resultado.getInt("Stock");
            }
        }

        if (stockActual < detalle.getCantidad()) {
            return false;
        }

        String consulta = "UPDATE Producto SET Stock = Stock - ? WHERE IDproducto = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, detalle.getCantidad());
            sentencia.setInt(2, detalle.getProducto().getIdproducto());
            sentencia.executeUpdate();
            return true;
        }
    }

    private void guardarDetalle(Connection conexion, DetalleVenta detalle)
            throws SQLException {
        String consulta = "INSERT INTO DetalleVenta "
                + "(IdVenta, IDproducto, Cantidad, PrecioUnit, Importe) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, detalle.getIdventa());
            sentencia.setInt(2, detalle.getProducto().getIdproducto());
            sentencia.setInt(3, detalle.getCantidad());
            sentencia.setBigDecimal(4, detalle.getPrecioUnit());
            sentencia.setBigDecimal(5, detalle.getImporte());
            sentencia.executeUpdate();
        }
    }

    private int obtenerUltimoId(Connection conexion) throws SQLException {
        try (Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery("SELECT last_insert_rowid()")) {
            resultado.next();
            return resultado.getInt(1);
        }
    }

    private void cargarDetalles(Connection conexion, Venta venta) throws SQLException {
        String consulta = "SELECT d.IDetalle, d.IdVenta, d.IDproducto, d.Cantidad, "
                + "d.PrecioUnit, d.Importe, p.Modelo, p.Marca, p.Talla, p.Precio, p.Stock "
                + "FROM DetalleVenta d JOIN Producto p ON p.IDproducto = d.IDproducto "
                + "WHERE d.IdVenta = ? ORDER BY d.IDetalle";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, venta.getIdventa());
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    Producto producto = new Producto(
                            resultado.getInt("IDproducto"),
                            resultado.getString("Modelo"),
                            resultado.getString("Marca"),
                            resultado.getString("Talla"),
                            resultado.getBigDecimal("Precio"),
                            resultado.getInt("Stock")
                    );
                    DetalleVenta detalle = new DetalleVenta(
                            resultado.getInt("IDetalle"),
                            resultado.getInt("IdVenta"),
                            producto,
                            resultado.getInt("Cantidad"),
                            resultado.getBigDecimal("PrecioUnit"),
                            resultado.getBigDecimal("Importe")
                    );
                    venta.getDetalleVenta().add(detalle);
                }
            }
        }
    }

    private Venta crearVenta(ResultSet resultado) throws SQLException {
        String nombreCliente = resultado.getString("NombreCliente");
        if (nombreCliente == null || nombreCliente.isBlank()) {
            nombreCliente = "Sin cliente";
        }
        return new Venta(
                resultado.getInt("IdVenta"),
                resultado.getString("Fecha"),
                resultado.getBigDecimal("Total"),
                resultado.getInt("IdCliente"),
                nombreCliente
        );
    }

    private String consultaVentas() {
        return "SELECT v.IdVenta, v.Fecha, v.Total, COALESCE(c.IDcliente, 0) AS IdCliente, "
                + "CASE WHEN c.IDcliente IS NULL THEN 'Sin cliente' "
                + "ELSE TRIM(COALESCE(c.Persona, '') || ' ' || COALESCE(c.Apellido, '')) END "
                + "AS NombreCliente FROM Venta v "
                + "LEFT JOIN Cliente c ON c.IDcliente = v.IdCliente";
    }
}
