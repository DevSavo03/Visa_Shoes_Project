# Paquete test

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project.test`

Incluye utilidades ejecutables desde el IDE para revisar manualmente:

- `TestConexion`: apertura de SQLite.
- `TestTablas`: disponibilidad de las tablas esperadas.
- `TestCliente`, `TestProducto` y `TestVenta`: lectura de datos por medio de los DAO.

Estas clases están en `src/main/java` y tienen métodos `main`; no son pruebas automáticas JUnit de `src/test`.