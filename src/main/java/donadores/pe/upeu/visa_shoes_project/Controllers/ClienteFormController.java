package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.Models.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClienteFormController {
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtCelular;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCorreo;

    private Cliente cliente;
    private boolean saved;

    public boolean isSaved() {
        return saved;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            lblTitulo.setText("Editar cliente");
            txtNombre.setText(cliente.getnombre());
            txtApellidos.setText(cliente.getApellidos());
            txtDocumento.setText(cliente.getDocumento());
            txtCelular.setText(cliente.getCelular());
            txtDireccion.setText(cliente.getDireccion());
            txtCorreo.setText(cliente.getCorreo());
        }
    }

    @FXML
    private void handleCancelar() {
        cerrar();
    }

    @FXML
    private void handleGuardar() {
        if (txtNombre.getText().isBlank() || txtApellidos.getText().isBlank()
                || txtDocumento.getText().isBlank() || txtCelular.getText().isBlank()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Datos incompletos",
                    "Nombre, apellidos, documento y celular son obligatorios.");
            return;
        }

        if (cliente == null) {
            cliente = new Cliente();
        }
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellidos(txtApellidos.getText().trim());
        cliente.setDocumento(txtDocumento.getText().trim());
        cliente.setCelular(txtCelular.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        cliente.setCorreo(txtCorreo.getText().trim());
        saved = true;
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
