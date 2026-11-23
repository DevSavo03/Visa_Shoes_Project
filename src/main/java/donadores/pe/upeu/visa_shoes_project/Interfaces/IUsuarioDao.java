package donadores.pe.upeu.visa_shoes_project.Interfaces;

import donadores.pe.upeu.visa_shoes_project.Models.Usuario;

public interface IUsuarioDao {
    Usuario login(String user, String password);
}
