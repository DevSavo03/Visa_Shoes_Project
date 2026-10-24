package donadores.pe.upeu.visa_shoes_project.Controllers;

import donadores.pe.upeu.visa_shoes_project.Implements.ProductoDaoImp;
import donadores.pe.upeu.visa_shoes_project.Implements.VentaDaoImp;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.Interfaces.IVentaDao;
import donadores.pe.upeu.visa_shoes_project.Models.DetalleVenta;
import donadores.pe.upeu.visa_shoes_project.Models.Producto;
import donadores.pe.upeu.visa_shoes_project.Models.Venta;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;

public class VentaController {

    @FXML private ComboBox<Producto> cbProducto;
    @FXML private TextField txtCantidad;
    @FXML private Label lblTotal;

    @FXML private TableView<DetalleVenta> tablaDetalle;
    @FXML private TableColumn<DetalleVenta, String> colDetalleModelo;
    @FXML private TableColumn<DetalleVenta, Integer> colDetalleCantidad;
    @FXML private TableColumn<DetalleVenta, BigDecimal> colDetallePrecio;
    @FXML private TableColumn<DetalleVenta, BigDecimal> colDetalleImporte;

    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn<Venta, Integer> colVentaId;
    @FXML private TableColumn<Venta, String> colVentaFecha;
    @FXML private TableColumn<Venta, BigDecimal> colVentaTotal;

    private final IProductoDao productoDao = new ProductoDaoImp();
    private final IVentaDao ventaDao = new VentaDaoImp();
    private final ObservableList<DetalleVenta> detalleActual = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        prepararTablaDetalle();
        prepararTablaVentas();
        prepararComboProductos();
        tablaDetalle.setItems(detalleActual);
        cargarProductos();
        cargarVentas();
        actualizarTotal();
    }

    private void prepararTablaDetalle() {
        colDetalleModelo.setCellValueFactory(fila -> {
            Producto producto = fila.getValue().getProducto();
            String modelo = producto == null ? "" : producto.getModelo();
            return new ReadOnlyStringWrapper(modelo);
        });
        colDetalleCantidad.setCellValueFactory(fila -> fila.getValue().cantidadProperty().asObject());
        colDetallePrecio.setCellValueFactory(fila -> fila.getValue().precioUnitProperty());
        colDetalleImporte.setCellValueFactory(fila -> fila.getValue().importeProperty());
    }

    private void prepararTablaVentas() {
        colVentaId.setCellValueFactory(fila -> fila.getValue().idventaProperty().asObject());
        colVentaFecha.setCellValueFactory(fila -> fila.getValue().fechaProperty());
        colVentaTotal.setCellValueFactory(fila -> fila.getValue().totalProperty());
    }

    private void prepararComboProductos() {
        cbProducto.setConverter(new StringConverter<>() {
            @Override
            public String toString(Producto producto) {
                if (producto == null) {
                    return "";
                }
                return producto.getMarca() + " " + producto.getModelo()
                        + " - Talla " + producto.getTalla();
            }

            @Override
            public Producto fromString(String texto) {
                return null;
            }
        });
    }

    private void cargarProductos() {
        cbProducto.setItems(FXCollections.observableArrayList(productoDao.listAll()));
    }

    private void cargarVentas() {
        tablaVentas.setItems(FXCollections.observableArrayList(ventaDao.listAll()));
    }

    @FXML
    private void handleAgregarDetalle() {
        Producto producto = cbProducto.getValue();
        if (producto == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Producto requerido",
                    "Selecciona el producto que se agregará a la venta.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarMensaje(Alert.AlertType.WARNING, "Cantidad incorrecta",
                    "Escribe una cantidad válida.");
            return;
        }

        if (cantidad <= 0) {
            mostrarMensaje(Alert.AlertType.WARNING, "Cantidad incorrecta",
                    "La cantidad debe ser mayor que cero.");
            return;
        }

        int cantidadEnVenta = 0;
        for (DetalleVenta detalle : detalleActual) {
            if (detalle.getProducto().getIdproducto() == producto.getIdproducto()) {
                cantidadEnVenta += detalle.getCantidad();
            }
        }

        if (cantidad + cantidadEnVenta > producto.getStock()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Stock insuficiente",
                    "La cantidad supera el stock disponible.");
            return;
        }

        DetalleVenta detalle = new DetalleVenta(
                0, 0, producto, cantidad, producto.getPrecio(), BigDecimal.ZERO);
        detalle.calcularImporte();
        detalleActual.add(detalle);
        txtCantidad.clear();
        actualizarTotal();
    }

    @FXML
    private void handleQuitarDetalle() {
        DetalleVenta detalle = tablaDetalle.getSelectionModel().getSelectedItem();
        if (detalle == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona un detalle para quitarlo de la venta.");
            return;
        }
        detalleActual.remove(detalle);
        actualizarTotal();
    }

    @FXML
    private void handleRegistrarVenta() {
        if (detalleActual.isEmpty()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Venta vacía",
                    "Agrega al menos un producto antes de registrar la venta.");
            return;
        }

        Venta venta = new Venta();
        venta.getDetalleVenta().addAll(detalleActual);
        venta.calcularTotal();

        if (ventaDao.insert(venta)) {
            mostrarMensaje(Alert.AlertType.INFORMATION, "Ventas",
                    "La venta se registró correctamente.");
            detalleActual.clear();
            cbProducto.getSelectionModel().clearSelection();
            actualizarTotal();
            cargarProductos();
            cargarVentas();
        } else {
            mostrarMensaje(Alert.AlertType.ERROR, "Ventas",
                    "No se pudo registrar la venta. Revisa el stock disponible.");
        }
    }

    @FXML
    private void handleActualizar() {
        cargarProductos();
        cargarVentas();
    }

    private void actualizarTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalleActual) {
            total = total.add(detalle.getImporte());
        }
        lblTotal.setText(total.toPlainString());
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
