# Paquete Utils

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project.Utils`

`ConexionDB_V` abre la conexión SQLite, activa las claves foráneas y agrega `Venta.IdCliente` si una base existente aún no tiene la columna. No gestiona recursos visuales: el fondo de Inicio se referencia directamente desde FXML.

La ruta predeterminada es `C:/sqlite/db/Visa_Shoes_DataBase.db`. Para usar otra, define la propiedad del sistema `visa.shoes.db`. La columna `IdCliente` permite asociar una venta con un registro de `Cliente`; ventas antiguas sin asociación siguen siendo válidas y se muestran como “Sin cliente”.