# Paquete `Controllers`

Este paquete conecta las pantallas JavaFX con los modelos y los DAO. Cada clase recibe acciones de la vista, valida los datos básicos y llama al DAO que corresponde. La base de datos sigue concentrada en `Implements`; los controladores no ejecutan SQL.

## Estructura agregada

```text
src/main/java/donadores/pe/upeu/visa_shoes_project/Controllers/
├── MainController.java
├── ClienteController.java
├── ProductoController.java
└── VentaController.java
```

## Clases

- **`MainController`**: cambia el contenido del panel principal entre Inicio, Clientes, Productos y Ventas. Carga las vistas `home-view.fxml`, `cliente-view.fxml`, `producto-view.fxml` y `venta-view.fxml` desde `src/main/resources/donadores/pe/upeu/visa_shoes_project/views/`.
- **`ClienteController`**: carga los clientes en una tabla y permite iniciar un registro, seleccionar uno para editar, guardar cambios y eliminarlo. Usa `IClienteDao` y `Cliente`.
- **`ProductoController`**: carga el catálogo y permite crear, editar y eliminar productos. Revisa que precio y stock sean números válidos y no negativos. Usa `IProductoDao` y `Producto`.
- **`VentaController`**: carga productos y ventas, arma una lista de detalles, calcula el total y registra la venta con `IVentaDao`. El DAO vuelve a revisar el stock al guardar. El esquema actual no relaciona una venta con un cliente, por eso este controlador no solicita cliente.

## Vistas FXML agregadas

- **`main-view.fxml`**: contiene la barra superior, el menú y `contentArea`, donde se muestra cada módulo.
- **`home-view.fxml`**: presenta una bienvenida e indica cómo elegir una sección.
- **`cliente-view.fxml`**: formulario y tabla para trabajar con clientes.
- **`producto-view.fxml`**: formulario y tabla del catálogo.
- **`venta-view.fxml`**: selección de productos, detalle de la venta, total e historial.
- **`../css/style.css`**: reúne los colores y estilos sencillos que comparten las pantallas.

`HelloApplication` inicia ahora desde `main-view.fxml`. La estructura toma como referencia la separación entre pantalla principal y vistas de la Fase 2, ajustando nombres y acciones a los controladores del proyecto Visa Shoes.

## Conexión con FXML

Las vistas usan los controladores con `fx:controller` y respetan los `fx:id` de los campos y columnas. Los botones llaman a estos métodos:

- `cliente-view.fxml`: `tablaClientes`, `colId`, `colNombre`, `colApellidos`, `colDocumento`, `colCelular`, `colCorreo`, `txtNombre`, `txtApellidos`, `txtDocumento`, `txtCelular`, `txtDireccion`, `txtCorreo`.
- `producto-view.fxml`: `tablaProductos`, `colId`, `colModelo`, `colMarca`, `colTalla`, `colPrecio`, `colStock`, `txtModelo`, `txtMarca`, `txtTalla`, `txtPrecio`, `txtStock`.
- `venta-view.fxml`: `cbProducto`, `txtCantidad`, `lblTotal`, `tablaDetalle`, `colDetalleModelo`, `colDetalleCantidad`, `colDetallePrecio`, `colDetalleImporte`, `tablaVentas`, `colVentaId`, `colVentaFecha`, `colVentaTotal`.
- `main-view.fxml`: `contentArea` debe ser un `StackPane`; sus botones llaman a los métodos de navegación que aparecen abajo.

- Clientes: `#handleNuevo`, `#handleEditar`, `#handleGuardar`, `#handleEliminar`.
- Productos: `#handleNuevo`, `#handleEditar`, `#handleGuardar`, `#handleEliminar`.
- Ventas: `#handleAgregarDetalle`, `#handleQuitarDetalle`, `#handleRegistrarVenta`, `#handleActualizar`.
- Navegación: `#mostrarInicio`, `#mostrarClientes`, `#mostrarProductos`, `#mostrarVentas`.

El paquete se llama `Controllers` para conservar el uso de mayúsculas que ya tienen `Models`, `Interfaces` e `Implements` en este proyecto. `module-info.java` abre el paquete a JavaFX FXML para que pueda conectar los campos y métodos anotados con `@FXML`.
