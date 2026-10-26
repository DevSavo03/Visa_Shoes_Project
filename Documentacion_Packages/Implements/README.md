# Paquete Implements

**Paquete Java:** `donadores.pe.upeu.visa_shoes_project.Implements`

Implementa los contratos definidos en `Interfaces` con consultas SQLite preparadas:

- `ClienteDaoImp` crea, actualiza, busca, lista y elimina clientes.
- `ProductoDaoImp` crea, actualiza, busca, lista y elimina productos.
- `VentaDaoImp` registra ventas y sus detalles dentro de una transacción, descuenta stock y carga el historial con el cliente asociado.

`VentaDaoImp` guarda `IdCliente` y lee el nombre del cliente mediante una unión con `Cliente`. El stock se valida antes de confirmar la venta; si una operación falla, se revierte la transacción.