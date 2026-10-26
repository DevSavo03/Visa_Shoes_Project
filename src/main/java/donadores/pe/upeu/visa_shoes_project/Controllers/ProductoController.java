package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.Implements.ProductoDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.Models.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductoController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colModelo;
    @FXML private TableColumn<Producto, String> colMarca;
    @FXML private TableColumn<Producto, String> colTalla;
    @FXML private TableColumn<Producto, BigDecimal> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    @FXML private TextField txtModelo;
    @FXML private TextField txtMarca;
    @FXML private TextField txtTalla;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtBuscar;

    private final IProductoDao productoDao = new ProductoDaoImp();
    private ObservableList<Producto> listaProductos;
    private FilteredList<Producto> productosFiltrados;
    private Producto productoSeleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(fila -> fila.getValue().idproductoProperty().asObject());
        colModelo.setCellValueFactory(fila -> fila.getValue().modeloProperty());
        colMarca.setCellValueFactory(fila -> fila.getValue().marcaProperty());
        colTalla.setCellValueFactory(fila -> fila.getValue().tallaProperty());
        colPrecio.setCellValueFactory(fila -> fila.getValue().precioProperty());
        colStock.setCellValueFactory(fila -> fila.getValue().stockProperty().asObject());
        txtBuscar.textProperty().addListener((observable, anterior, actual) -> filtrarProductos(actual));
        cargarDatos();
    }

    private void cargarDatos() {
        listaProductos = FXCollections.observableArrayList(productoDao.listAll());
        productosFiltrados = new FilteredList<>(listaProductos, producto -> true);
        tablaProductos.setItems(productosFiltrados);
        filtrarProductos(txtBuscar.getText());
    }

    private void filtrarProductos(String texto) {
        if (productosFiltrados == null) return;
        String criterio = texto == null ? "" : texto.trim().toLowerCase();
        productosFiltrados.setPredicate(producto -> criterio.isEmpty()
                || contiene(producto.getModelo(), criterio)
                || contiene(producto.getMarca(), criterio)
                || contiene(producto.getTalla(), criterio)
                || String.valueOf(producto.getIdproducto()).contains(criterio));
    }

    private boolean contiene(String valor, String criterio) {
        return valor != null && valor.toLowerCase().contains(criterio);
    }

    @FXML
    private void handleNuevo() {
        limpiarFormulario();
    }

    @FXML
    private void handleEditar() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona un producto para editarlo.");
            return;
        }

        productoSeleccionado = producto;
        txtModelo.setText(producto.getModelo());
        txtMarca.setText(producto.getMarca());
        txtTalla.setText(producto.getTalla());
        txtPrecio.setText(producto.getPrecio().toPlainString());
        txtStock.setText(String.valueOf(producto.getStock()));
    }

    @FXML
    private void handleGuardar() {
        BigDecimal precio;
        int stock;
        try {
            precio = new BigDecimal(txtPrecio.getText().trim());
            stock = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            mostrarMensaje(Alert.AlertType.WARNING, "Datos incorrectos",
                    "Revisa que el precio y el stock sean números válidos.");
            return;
        }

        if (txtModelo.getText().isBlank() || txtMarca.getText().isBlank()
                || txtTalla.getText().isBlank() || precio.signum() < 0 || stock < 0) {
            mostrarMensaje(Alert.AlertType.WARNING, "Datos incompletos",
                    "Completa modelo, marca y talla. Precio y stock no pueden ser negativos.");
            return;
        }

        Producto producto = new Producto();
        if (productoSeleccionado != null) {
            producto.setIdproducto(productoSeleccionado.getIdproducto());
        }
        producto.setModelo(txtModelo.getText().trim());
        producto.setMarca(txtMarca.getText().trim());
        producto.setTalla(txtTalla.getText().trim());
        producto.setPrecio(precio);
        producto.setStock(stock);

        boolean guardado;
        if (productoSeleccionado == null) {
            guardado = productoDao.insert(producto);
        } else {
            guardado = productoDao.update(producto);
        }

        if (guardado) {
            mostrarMensaje(Alert.AlertType.INFORMATION, "Productos",
                    "Los datos del producto se guardaron correctamente.");
            cargarDatos();
            limpiarFormulario();
        } else {
            mostrarMensaje(Alert.AlertType.ERROR, "Productos",
                    "No se pudieron guardar los datos del producto.");
        }
    }

    @FXML
    private void handleEliminar() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona un producto para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar el producto " + producto.getModelo() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        confirmacion.setHeaderText("Eliminar producto");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            if (productoDao.delete(producto)) {
                cargarDatos();
                limpiarFormulario();
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Productos",
                        "No se pudo eliminar el producto. Puede tener ventas registradas.");
            }
        }
    }

    private void limpiarFormulario() {
        productoSeleccionado = null;
        txtModelo.clear();
        txtMarca.clear();
        txtTalla.clear();
        txtPrecio.clear();
        txtStock.clear();
        tablaProductos.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
