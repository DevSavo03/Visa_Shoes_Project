package donadores.pe.upeu.visa_shoes_project.Interfaces;

import donadores.pe.upeu.visa_shoes_project.Models.Venta;

import java.util.List;

public interface IVentaDao {
    public boolean insert(Venta venta);

    public Venta searchById(int id);

    public List<Venta> listAll();
}