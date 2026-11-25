package donadores.pe.upeu.visa_shoes_project.interfaces;

import donadores.pe.upeu.visa_shoes_project.modelo.Venta;

import java.util.List;

public interface IVentaDao {
    public boolean insert(Venta venta);

    public Venta searchById(int id);

    public List<Venta> listAll();
}