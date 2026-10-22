package donadores.pe.upeu.visa_shoes_project.Interfaces;

import donadores.pe.upeu.visa_shoes_project.Models.Cliente;

import java.util.List;

public interface IClienteDao {
    public boolean insert(Cliente cliente);

    public boolean update(Cliente cliente);

    public Cliente searchById(int id);

    public Cliente searchByDoc(String documento);

    public boolean delete(Cliente cliente);

    public List<Cliente> listAll();

}
