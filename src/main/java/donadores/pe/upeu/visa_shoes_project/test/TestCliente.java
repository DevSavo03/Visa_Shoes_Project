package donadores.pe.upeu.visa_shoes_project.test;

import donadores.pe.upeu.visa_shoes_project.implementacion.ClienteDaoImpl;
import donadores.pe.upeu.visa_shoes_project.interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.modelo.Cliente;

import java.util.List;

public class TestCliente {

    private static final IClienteDao clienteDao = new ClienteDaoImpl();

    public static void listar() {
        List<Cliente> clientes = clienteDao.listAll();
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getIdCliente());
            System.out.println("Nombre: " + cliente.getnombre() + " " + cliente.getApellidos());
            System.out.println("Documento: " + cliente.getDocumento());
            System.out.println("Celular: " + cliente.getCelular());
            System.out.println("Correo: " + cliente.getCorreo());
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        listar();
    }
}
