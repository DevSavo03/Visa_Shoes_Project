package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.HelloApplication;
import donadores.pe.upeu.visa_shoes_project.Implements.UsuarioDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IUsuarioDao;
import donadores.pe.upeu.visa_shoes_project.Models.UserSession;
import donadores.pe.upeu.visa_shoes_project.Models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;

    private final IUsuarioDao usuarioDao = new UsuarioDaoImp();

    @FXML
    private void handleLogin() {
        String user = txtUser.getText().trim();
        String password = txtPassword.getText();

        if (user.isEmpty() || password.isEmpty()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Campos vacíos",
                    "Ingresa el usuario y la contraseña.");
            return;
        }

        Usuario usuario = usuarioDao.login(user, password);
        if (usuario == null) {
            mostrarMensaje(Alert.AlertType.ERROR, "Acceso denegado",
                    "El usuario o la contraseña no son correctos.");
            return;
        }

        UserSession.getInstance().setUsuarioLogin(usuario);
        mostrarMensaje(Alert.AlertType.INFORMATION, "Acceso concedido",
                "Bienvenido, " + usuario.getnombre() + ".");
        abrirSistema();
    }

    private void abrirSistema() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(
                    "/donadores/pe/upeu/visa_shoes_project/views/main-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUser.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 720));
            stage.setTitle("Visa Shoes");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje(Alert.AlertType.ERROR, "Error al abrir Visa Shoes",
                    "No se pudo cargar la ventana principal: " + e.getMessage());
        }
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
