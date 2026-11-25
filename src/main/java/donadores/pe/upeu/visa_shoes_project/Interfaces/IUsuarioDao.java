package donadores.pe.upeu.visa_shoes_project.interfaces;

import donadores.pe.upeu.visa_shoes_project.modelo.Usuario;

public interface IUsuarioDao {
    Usuario login(String user, String password);
}
