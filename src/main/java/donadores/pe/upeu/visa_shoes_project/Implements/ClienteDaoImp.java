package donadores.pe.upeu.visa_shoes_project.Implements;

import donadores.pe.upeu.visa_shoes_project.Interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.Models.Cliente;

import java.util.List;

public class ClienteDaoImp implements IClienteDao {
    @Override
    public boolean insert(Cliente cliente) {
        return false;
    }

    @Override
    public boolean update(Cliente cliente) {
        return false;
    }

    @Override
    public Cliente searchById(int id) {
        return null;
    }

    @Override
    public Cliente searchByDoc(String documento) {
        return null;
    }

    @Override
    public boolean delete(Cliente cliente) {
        return false;
    }

    @Override
    public List<Cliente> listAll() {
        return List.of();
    }
}
