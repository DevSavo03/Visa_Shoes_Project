# Package `test`

El package `donadores.pe.upeu.visa_shoes_project.test` sigue la idea del `project_final`: cada clase tiene un método ejecutable desde el IDE y muestra en consola si la conexión o la lectura funcionó.

## Clases

- `TestConexion`: abre SQLite e imprime el error completo si no logra conectarse.
- `TestTablas`: consulta `Cliente`, `Producto`, `Venta` y `DetalleVenta` para confirmar que existan y se puedan leer.
- `TestCliente`, `TestProducto` y `TestVenta`: listan los registros usando sus DAO.

Las clases solo leen datos; no insertan, actualizan ni eliminan registros.

## Ejecución

Desde IntelliJ IDEA, abre la clase que quieras revisar y ejecuta su método `main`:

1. Ejecuta `TestConexion` para revisar la conexión.
2. Ejecuta `TestTablas` para comprobar las tablas.
3. Ejecuta `TestCliente`, `TestProducto` o `TestVenta` para ver los registros.

La ruta predeterminada es `C:/sqlite/db/Visa_Shoes_DataBase.db`. Si necesitas usar otra, configura la opción de VM `-Dvisa.shoes.db=ruta/a/la/base.db` en la configuración de ejecución.
