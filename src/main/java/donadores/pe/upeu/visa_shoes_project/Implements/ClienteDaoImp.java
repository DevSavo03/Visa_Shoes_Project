package donadores.pe.upeu.visa_shoes_project.Implements;

import donadores.pe.upeu.visa_shoes_project.Interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.Models.Cliente;
import donadores.pe.upeu.visa_shoes_project.Utils.ConexionDB_V;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImp implements IClienteDao {

    @Override
    public boolean insert(Cliente cliente) {
        String consulta = "INSERT INTO Cliente "
                + "(Persona, Apellido, Doc, Celular, Direccion, Correo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, cliente.getnombre());
            sentencia.setString(2, cliente.getApellidos());
            sentencia.setString(3, cliente.getDocumento());
            sentencia.setString(4, cliente.getCelular());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getCorreo());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("No se pudo registrar el cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Cliente cliente) {
        String consulta = "UPDATE Cliente SET Persona = ?, Apellido = ?, Doc = ?, "
                + "Celular = ?, Direccion = ?, Correo = ? WHERE IDcliente = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, cliente.getnombre());
            sentencia.setString(2, cliente.getApellidos());
            sentencia.setString(3, cliente.getDocumento());
            sentencia.setString(4, cliente.getCelular());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getCorreo());
            sentencia.setInt(7, cliente.getIdCliente());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("No se pudo actualizar el cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente searchById(int id) {
        String consulta = "SELECT IDcliente, Persona, Apellido, Doc, Celular, Direccion, Correo "
                + "FROM Cliente WHERE IDcliente = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? crearCliente(resultado) : null;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo buscar el cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Cliente searchByDoc(String documento) {
        String consulta = "SELECT IDcliente, Persona, Apellido, Doc, Celular, Direccion, Correo "
                + "FROM Cliente WHERE Doc = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, documento);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? crearCliente(resultado) : null;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo buscar el cliente por documento: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(Cliente cliente) {
        String consulta = "DELETE FROM Cliente WHERE IDcliente = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, cliente.getIdCliente());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("No se pudo eliminar el cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cliente> listAll() {
        String consulta = "SELECT IDcliente, Persona, Apellido, Doc, Celular, Direccion, Correo "
                + "FROM Cliente ORDER BY IDcliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                clientes.add(crearCliente(resultado));
            }
        } catch (SQLException e) {
            System.err.println("No se pudo listar los clientes: " + e.getMessage());
        }
        return clientes;
    }

    private Cliente crearCliente(ResultSet resultado) throws SQLException {
        return new Cliente(
                resultado.getInt("IDcliente"),
                resultado.getString("Persona"),
                resultado.getString("Apellido"),
                resultado.getString("Doc"),
                resultado.getString("Celular"),
                resultado.getString("Direccion"),
                resultado.getString("Correo")
        );
    }
}
