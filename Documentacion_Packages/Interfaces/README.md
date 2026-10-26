# Paquete Interfaces

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project.Interfaces`

Define las operaciones de acceso a datos sin acoplar los controladores a SQL:

- `IClienteDao`: operaciones de clientes, incluyendo búsqueda por ID y documento.
- `IProductoDao`: operaciones del catálogo de productos.
- `IVentaDao`: registro de ventas, búsqueda de una venta y consulta del historial.

Las clases que implementan estas interfaces están en `Implements`.