# Paquete Controllers

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project.Controllers`

Conecta la interfaz JavaFX con los modelos y los DAO:

- `MainController` navega entre Inicio, Clientes, Productos y Ventas cargando las vistas FXML.
- `ClienteController` administra el formulario y la tabla de clientes.
- `ProductoController` administra productos e incluye búsqueda por modelo, marca, talla o ID.
- `VentaController` permite elegir un cliente, agregar productos, registrar ventas y consultar el detalle de una venta del historial.

La imagen de Inicio se declara directamente en `views/home-view.fxml` con `ImageView` y `Image`; no requiere un controlador Java adicional.