package donadores.pe.upeu.visa_shoes_project.test;

import donadores.pe.upeu.visa_shoes_project.Implements.VentaDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IVentaDao;
import donadores.pe.upeu.visa_shoes_project.Models.Venta;

import java.util.List;

public class TestVenta {

    private static final IVentaDao ventaDao = new VentaDaoImp();

    public static void listar() {
        List<Venta> ventas = ventaDao.listAll();
        if (ventas == null || ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (Venta venta : ventas) {
            System.out.println("Venta: " + venta.getIdventa());
            System.out.println("Fecha: " + venta.getFecha());
            System.out.println("Total: " + venta.getTotal());
            System.out.println("Productos registrados: " + venta.getDetalleVenta().size());
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        listar();
    }
}
