package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.Implements.ClienteDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Optional;

public class ClienteController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colDocumento;
    @FXML private TableColumn<Cliente, String> colCelular;
    @FXML private TableColumn<Cliente, String> colCorreo;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtCelular;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCorreo;

    private final IClienteDao clienteDao = new ClienteDaoImp();
    private ObservableList<Cliente> listaClientes;
    private Cliente clienteSeleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(fila -> fila.getValue().idClienteProperty().asObject());
        colNombre.setCellValueFactory(fila -> fila.getValue().nombreProperty());
        colApellidos.setCellValueFactory(fila -> fila.getValue().apellidosProperty());
        colDocumento.setCellValueFactory(fila -> fila.getValue().documentoProperty());
        colCelular.setCellValueFactory(fila -> fila.getValue().celularProperty());
        colCorreo.setCellValueFactory(fila -> fila.getValue().correoProperty());
        cargarDatos();
    }

    private void cargarDatos() {
        listaClientes = FXCollections.observableArrayList(clienteDao.listAll());
        tablaClientes.setItems(listaClientes);
    }

    @FXML
    private void handleNuevo() {
        limpiarFormulario();
    }

    @FXML
    private void handleEditar() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona un cliente para editarlo.");
            return;
        }

        clienteSeleccionado = cliente;
        txtNombre.setText(cliente.nombreProperty().get());
        txtApellidos.setText(cliente.getApellidos());
        txtDocumento.setText(cliente.getDocumento());
        txtCelular.setText(cliente.getCelular());
        txtDireccion.setText(cliente.getDireccion());
        txtCorreo.setText(cliente.getCorreo());
    }

    @FXML
    private void handleGuardar() {
        if (!formularioValido()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Datos incompletos",
                    "Escribe el nombre, apellido, documento y celular del cliente.");
            return;
        }

        Cliente cliente = new Cliente();
        if (clienteSeleccionado != null) {
            cliente.setIdCliente(clienteSeleccionado.getIdCliente());
        }
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellidos(txtApellidos.getText().trim());
        cliente.setDocumento(txtDocumento.getText().trim());
        cliente.setCelular(txtCelular.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        cliente.setCorreo(txtCorreo.getText().trim());

        boolean guardado;
        if (clienteSeleccionado == null) {
            guardado = clienteDao.insert(cliente);
        } else {
            guardado = clienteDao.update(cliente);
        }

        if (guardado) {
            mostrarMensaje(Alert.AlertType.INFORMATION, "Clientes",
                    "Los datos del cliente se guardaron correctamente.");
            cargarDatos();
            limpiarFormulario();
        } else {
            mostrarMensaje(Alert.AlertType.ERROR, "Clientes",
                    "No se pudieron guardar los datos del cliente.");
        }
    }

    @FXML
    private void handleEliminar() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona un cliente para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar a " + cliente.nombreProperty().get() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        confirmacion.setHeaderText("Eliminar cliente");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            if (clienteDao.delete(cliente)) {
                cargarDatos();
                limpiarFormulario();
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Clientes",
                        "No se pudo eliminar el cliente.");
            }
        }
    }

    private boolean formularioValido() {
        return !txtNombre.getText().isBlank()
                && !txtApellidos.getText().isBlank()
                && !txtDocumento.getText().isBlank()
                && !txtCelular.getText().isBlank();
    }

    private void limpiarFormulario() {
        clienteSeleccionado = null;
        txtNombre.clear();
        txtApellidos.clear();
        txtDocumento.clear();
        txtCelular.clear();
        txtDireccion.clear();
        txtCorreo.clear();
        tablaClientes.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
