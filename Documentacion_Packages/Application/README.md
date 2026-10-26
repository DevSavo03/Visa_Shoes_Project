# Paquete principal de la aplicación

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project`

Contiene las clases de arranque de Visa Shoes:

- `HelloApplication` crea la ventana JavaFX y carga `views/main-view.fxml`.
- `Launcher` inicia JavaFX delegando en `HelloApplication`.
- `HelloController` y `hello-view.fxml` son la pantalla de ejemplo original; la ventana de gestión usa `main-view.fxml`.

La navegación desde la ventana principal y la lógica de pantallas están en el paquete `Controllers`.