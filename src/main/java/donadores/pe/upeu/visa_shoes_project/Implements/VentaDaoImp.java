package donadores.pe.upeu.visa_shoes_project.Implements;

import donadores.pe.upeu.visa_shoes_project.Interfaces.IVentaDao;
import donadores.pe.upeu.visa_shoes_project.Models.Venta;

import java.util.List;

public class VentaDaoImp implements IVentaDao {
    @Override
    public boolean insert(Venta venta) {
        return false;
    }

    @Override
    public Venta searchById(int id) {
        return null;
    }

    @Override
    public List<Venta> listAll() {
        return List.of();
    }
}
