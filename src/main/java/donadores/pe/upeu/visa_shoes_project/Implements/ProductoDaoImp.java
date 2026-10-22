package donadores.pe.upeu.visa_shoes_project.Implements;

import donadores.pe.upeu.visa_shoes_project.Interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.Models.Producto;

import java.util.List;

public class ProductoDaoImp implements IProductoDao {

    @Override
    public boolean insert(Producto producto) {
        return false;
    }

    @Override
    public boolean update(Producto producto) {
        return false;
    }

    @Override
    public Producto searchById(int id) {
        return null;
    }

    @Override
    public boolean delete(Producto producto) {
        return false;
    }

    @Override
    public List<Producto> listAll() {
        return List.of();
    }
}
