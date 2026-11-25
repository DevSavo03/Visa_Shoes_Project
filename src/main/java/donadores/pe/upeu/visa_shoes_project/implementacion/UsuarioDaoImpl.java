package donadores.pe.upeu.visa_shoes_project.implementacion;


import donadores.pe.upeu.visa_shoes_project.interfaces.IUsuarioDao;
import donadores.pe.upeu.visa_shoes_project.modelo.Usuario;
import donadores.pe.upeu.visa_shoes_project.utils.ConexionDB_V;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDaoImpl implements IUsuarioDao {

    @Override
    public Usuario login(String user, String password) {
        String consulta = "SELECT IDusuario, Usuario, Clave, TipoUser, Nombre, Apellidos, Documento "
                + "FROM Usuario WHERE Usuario = ? AND Clave = ?";

        try (Connection conexion = ConexionDB_V.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, user);
            sentencia.setString(2, password);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }

                Usuario usuario = new Usuario();
                usuario.setIdusuario(resultado.getInt("IDusuario"));
                usuario.setUser(resultado.getString("Usuario"));
                usuario.setPassword(resultado.getString("Clave"));
                usuario.setTipoUser(resultado.getString("TipoUser"));
                usuario.setNombre(resultado.getString("Nombre"));
                usuario.setApellidos(resultado.getString("Apellidos"));
                usuario.setDocumento(resultado.getString("Documento"));
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo validar el usuario: " + e.getMessage());
            return null;
        }
    }

}
