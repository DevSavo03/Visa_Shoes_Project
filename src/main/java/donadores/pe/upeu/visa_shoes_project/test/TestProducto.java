package donadores.pe.upeu.visa_shoes_project.test;

import donadores.pe.upeu.visa_shoes_project.Implements.ProductoDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.Models.Producto;

import java.util.List;

public class TestProducto {

    private static final IProductoDao productoDao = new ProductoDaoImp();

    public static void listar() {
        List<Producto> productos = productoDao.listAll();
        if (productos == null || productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getIdproducto());
            System.out.println("Modelo: " + producto.getModelo());
            System.out.println("Marca: " + producto.getMarca());
            System.out.println("Talla: " + producto.getTalla());
            System.out.println("Precio: " + producto.getPrecio());
            System.out.println("Stock: " + producto.getStock());
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        listar();
    }
}
