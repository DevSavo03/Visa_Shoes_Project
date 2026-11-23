package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.Implements.ClienteDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import donadores.pe.upeu.visa_shoes_project.HelloApplication;

import java.io.IOException;
import java.util.Optional;

public class ClienteController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colDocumento;
    @FXML private TableColumn<Cliente, String> colCelular;
    @FXML private TableColumn<Cliente, String> colCorreo;

    private final IClienteDao clienteDao = new ClienteDaoImp();
    private ObservableList<Cliente> listaClientes;

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
        mostrarFormulario(null);
    }

    @FXML
    private void handleEditar() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Selección requerida",
                    "Selecciona un cliente para editarlo.");
            return;
        }

        Cliente cliente = clienteDao.searchById(seleccionado.getIdCliente());
        mostrarFormulario(cliente);
    }

    @FXML
    private void handleEliminar() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Selección requerida",
                    "Selecciona un cliente para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar a " + cliente.getnombre() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        confirmacion.setTitle("Eliminar cliente");
        confirmacion.setHeaderText(null);
        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            if (clienteDao.delete(cliente)) {
                cargarDatos();
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Clientes",
                        "No se pudo eliminar el cliente. Puede tener ventas asociadas.");
            }
        }
    }

    private void mostrarFormulario(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(
                    "/donadores/pe/upeu/visa_shoes_project/views/Cliente-form.fxml"));
            Parent root = loader.load();
            ClienteFormController controller = loader.getController();
            controller.setCliente(cliente);

            Stage stage = new Stage();
            stage.setTitle(cliente == null ? "Nuevo cliente" : "Editar cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                Cliente clienteProcesado = controller.getCliente();
                boolean guardado = cliente == null
                        ? clienteDao.insert(clienteProcesado)
                        : clienteDao.update(clienteProcesado);
                if (guardado) {
                    cargarDatos();
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Clientes",
                            "Los datos del cliente se guardaron correctamente.");
                } else {
                    cargarDatos();
                    mostrarMensaje(Alert.AlertType.ERROR, "Clientes",
                            "No se pudieron guardar los datos. Revisa si el documento o correo ya existe.");
                }
            }
        } catch (IOException e) {
            mostrarMensaje(Alert.AlertType.ERROR, "Formulario de cliente",
                    "No se pudo abrir el formulario: " + e.getMessage());
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
