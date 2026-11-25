package donadores.pe.upeu.visa_shoes_project.controller;

import donadores.pe.upeu.visa_shoes_project.implementacion.ProductoDaoImpl;
import donadores.pe.upeu.visa_shoes_project.implementacion.ClienteDaoImpl;
import donadores.pe.upeu.visa_shoes_project.implementacion.VentaDaoImpl;
import donadores.pe.upeu.visa_shoes_project.interfaces.IProductoDao;
import donadores.pe.upeu.visa_shoes_project.interfaces.IVentaDao;
import donadores.pe.upeu.visa_shoes_project.interfaces.IClienteDao;
import donadores.pe.upeu.visa_shoes_project.modelo.Cliente;
import donadores.pe.upeu.visa_shoes_project.modelo.DetalleVenta;
import donadores.pe.upeu.visa_shoes_project.modelo.Producto;
import donadores.pe.upeu.visa_shoes_project.modelo.Venta;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Locale;

public class VentaController {

    @FXML
    private ComboBox<Producto> cbProducto;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Label lblTotal;

    @FXML
    private TableView<DetalleVenta> tablaDetalle;
    @FXML
    private TableColumn<DetalleVenta, String> colDetalleModelo;
    @FXML
    private TableColumn<DetalleVenta, Integer> colDetalleCantidad;
    @FXML
    private TableColumn<DetalleVenta, BigDecimal> colDetallePrecio;
    @FXML
    private TableColumn<DetalleVenta, BigDecimal> colDetalleImporte;

    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableColumn<Venta, Integer> colVentaId;
    @FXML
    private TableColumn<Venta, String> colVentaFecha;
    @FXML
    private TableColumn<Venta, BigDecimal> colVentaTotal;
    @FXML
    private TableColumn<Venta, String> colVentaCliente;

    private final IProductoDao productoDao = new ProductoDaoImpl();
    private final IVentaDao ventaDao = new VentaDaoImpl();
    private final IClienteDao clienteDao = new ClienteDaoImpl();
    private final ObservableList<DetalleVenta> detalleActual = FXCollections.observableArrayList();
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();
    private final FilteredList<Producto> productosFiltrados = new FilteredList<>(productos);

    @FXML
    public void initialize() {
        prepararTablaDetalle();
        prepararTablaVentas();
        prepararComboProductos();
        prepararComboClientes();
        tablaDetalle.setItems(detalleActual);
        cargarProductos();
        cargarClientes();
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
        colVentaCliente.setCellValueFactory(fila -> fila.getValue().nombreClienteProperty());
    }

    private void prepararComboClientes() {
        cbCliente.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" : cliente.getnombre() + " " + cliente.getApellidos()
                        + " — " + cliente.getDocumento();
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });
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
        cbProducto.setItems(productosFiltrados);
        txtBuscarProducto.textProperty().addListener((observable, textoAnterior, textoNuevo) -> {
            cbProducto.getSelectionModel().clearSelection();
            String busqueda = textoNuevo == null ? "" : textoNuevo.trim().toLowerCase(Locale.ROOT);
            productosFiltrados.setPredicate(producto -> {
                if (busqueda.isEmpty()) {
                    return true;
                }
                String descripcion = producto.getMarca() + " " + producto.getModelo()
                        + " " + producto.getTalla();
                return descripcion.toLowerCase(Locale.ROOT).contains(busqueda);
            });
        });
    }

    private void cargarProductos() {
        productos.setAll(productoDao.listAll());
    }

    private void cargarVentas() {
        tablaVentas.setItems(FXCollections.observableArrayList(ventaDao.listAll()));
    }

    private void cargarClientes() {
        cbCliente.setItems(FXCollections.observableArrayList(clienteDao.listAll()));
    }

    @FXML
    private void handleAgregarDetalle() {
        Producto producto = cbProducto.getSelectionModel().getSelectedItem();
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
        Cliente cliente = cbCliente.getValue();
        if (cliente == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Cliente requerido",
                    "Selecciona un cliente registrado en la sección Clientes.");
            return;
        }
        if (detalleActual.isEmpty()) {
            mostrarMensaje(Alert.AlertType.WARNING, "Venta vacía",
                    "Agrega al menos un producto antes de registrar la venta.");
            return;
        }

        Venta venta = new Venta();
        venta.setIdCliente(cliente.getIdCliente());
        venta.setNombreCliente(cliente.getnombre() + " " + cliente.getApellidos());
        venta.getDetalleVenta().addAll(detalleActual);
        venta.calcularTotal();

        if (ventaDao.insert(venta)) {
            mostrarMensaje(Alert.AlertType.INFORMATION, "Ventas",
                    "La venta se registró correctamente.");
            detalleActual.clear();
            cbProducto.getSelectionModel().clearSelection();
            txtBuscarProducto.clear();
            cbCliente.getSelectionModel().clearSelection();
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
        cargarClientes();
        cargarVentas();
    }

    @FXML
    private void handleVerDetalleVenta() {
        Venta venta = tablaVentas.getSelectionModel().getSelectedItem();
        if (venta == null) {
            mostrarMensaje(Alert.AlertType.WARNING, "Seleccion requerida",
                    "Selecciona una venta del historial para ver sus productos.");
            return;
        }

        TableView<DetalleVenta> tabla = new TableView<>();
        TableColumn<DetalleVenta, String> producto = new TableColumn<>("Producto");
        producto.setCellValueFactory(fila -> new ReadOnlyStringWrapper(
                fila.getValue().getProducto().getMarca() + " "
                        + fila.getValue().getProducto().getModelo() + " — talla "
                        + fila.getValue().getProducto().getTalla()));
        TableColumn<DetalleVenta, Integer> cantidad = new TableColumn<>("Cantidad");
        cantidad.setCellValueFactory(fila -> fila.getValue().cantidadProperty().asObject());
        TableColumn<DetalleVenta, BigDecimal> precio = new TableColumn<>("Precio unitario");
        precio.setCellValueFactory(fila -> fila.getValue().precioUnitProperty());
        TableColumn<DetalleVenta, BigDecimal> importe = new TableColumn<>("Importe");
        importe.setCellValueFactory(fila -> fila.getValue().importeProperty());
        tabla.getColumns().add(producto);
        tabla.getColumns().add(cantidad);
        tabla.getColumns().add(precio);
        tabla.getColumns().add(importe);
        tabla.setItems(FXCollections.observableArrayList(venta.getDetalleVenta()));
        tabla.setPrefSize(650, 320);

        Dialog<Void> dialogo = new Dialog<>();
        dialogo.setTitle("Detalle de venta #" + venta.getIdventa());
        dialogo.setHeaderText("Cliente: " + venta.getNombreCliente()
                + "    |    Fecha: " + venta.getFecha()
                + "    |    Total: " + venta.getTotal().toPlainString());
        dialogo.getDialogPane().setContent(tabla);
        dialogo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialogo.showAndWait();
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
