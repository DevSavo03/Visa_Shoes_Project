package donadores.pe.upeu.visa_shoes_project.modelo;

public class UserSession {
        private static UserSession instance;
        private Usuario usuarioLogin;

        private UserSession() {
        }

        public static UserSession getInstance() {
            if (instance == null) {
                instance = new UserSession();
            }
            return instance;
        }

        public Usuario getUsuarioLogin() {
            return usuarioLogin;
        }

        public void setUsuarioLogin(Usuario usuario) {
            this.usuarioLogin = usuario;
        }

        public void cleanSession() {
            usuarioLogin = null;
        }

    }
