package donadores.pe.upeu.visa_shoes_project.interfaces;

import donadores.pe.upeu.visa_shoes_project.modelo.Producto;

import java.util.List;

public interface IProductoDao {
    public boolean insert(Producto producto);

    public boolean update(Producto producto);

    public Producto searchById(int id);

    public boolean delete(Producto producto);

    public List<Producto> listAll();
}