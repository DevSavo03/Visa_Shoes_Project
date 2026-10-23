package donadores.pe.upeu.visa_shoes_project.Implements;

import donadores.pe.upeu.visa_shoes_project.Interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.Models.Producto;

import donadores.pe.upeu.visa_shoes_project.Utils.ConexionDB_V;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImp implements IProductoDao {

    @Override
    public boolean insert(Producto producto) {
        String consulta = "INSERT INTO Producto (Modelo, Marca, Talla, Precio, Stock) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, producto.getModelo());
            sentencia.setString(2, producto.getMarca());
            sentencia.setString(3, producto.getTalla());
            sentencia.setBigDecimal(4, producto.getPrecio());
            sentencia.setInt(5, producto.getStock());
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("No se pudo registrar el producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Producto producto) {
        String consulta = "UPDATE Producto SET Modelo = ?, Marca = ?, Talla = ?, "
                + "Precio = ?, Stock = ? WHERE IDproducto = ?";

        try (Connection conexion = ConexionDB_V.getConnection()) {
            if (!existeProducto(conexion, producto.getIdproducto())) {
                return false;
            }

            try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                sentencia.setString(1, producto.getModelo());
                sentencia.setString(2, producto.getMarca());
                sentencia.setString(3, producto.getTalla());
                sentencia.setBigDecimal(4, producto.getPrecio());
                sentencia.setInt(5, producto.getStock());
                sentencia.setInt(6, producto.getIdproducto());
                sentencia.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Producto searchById(int id) {
        String consulta = "SELECT IDproducto, Modelo, Marca, Talla, Precio, Stock "
                + "FROM Producto WHERE IDproducto = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? crearProducto(resultado) : null;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo buscar el producto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(Producto producto) {
        String consulta = "DELETE FROM Producto WHERE IDproducto = ?";

        try (Connection conexion = ConexionDB_V.getConnection()) {
            if (!existeProducto(conexion, producto.getIdproducto())) {
                return false;
            }

            try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                sentencia.setInt(1, producto.getIdproducto());
                sentencia.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo eliminar el producto. Puede estar incluido en una venta: "
                    + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> listAll() {
        String consulta = "SELECT IDproducto, Modelo, Marca, Talla, Precio, Stock "
                + "FROM Producto ORDER BY IDproducto";
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                productos.add(crearProducto(resultado));
            }
        } catch (SQLException e) {
            System.err.println("No se pudo listar los productos: " + e.getMessage());
        }
        return productos;
    }

    private boolean existeProducto(Connection conexion, int id) throws SQLException {
        String consulta = "SELECT 1 FROM Producto WHERE IDproducto = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        }
    }

    private Producto crearProducto(ResultSet resultado) throws SQLException {
        BigDecimal precio = resultado.getBigDecimal("Precio");
        if (precio == null) {
            precio = BigDecimal.ZERO;
        }

        return new Producto(
                resultado.getInt("IDproducto"),
                resultado.getString("Modelo"),
                resultado.getString("Marca"),
                resultado.getString("Talla"),
                precio,
                resultado.getInt("Stock")
        );
    }
}