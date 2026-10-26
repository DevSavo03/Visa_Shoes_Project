# Visa Shoes — guía de paquetes

Esta carpeta documenta los paquetes Java del proyecto y puede compartirse como archivo independiente.

## Paquetes

- [Aplicación](Application/README.md): arranque y ventana principal.
- [Controllers](Controllers/README.md): navegación y control de vistas.
- [Implements](Implements/README.md): operaciones SQLite de los DAO.
- [Interfaces](Interfaces/README.md): contratos de acceso a datos.
- [Models](Models/README.md): entidades del dominio.
- [Utils](Utils/README.md): conexión y preparación de SQLite.
- [test](test/README.md): herramientas de comprobación manual.
- [Recursos visuales](Resources/README.md): vistas, estilos e imagen de fondo.

## Ejecución

Desde la carpeta `Visa_Shoes_project`, con JDK 21 instalado, ejecuta `mvnw.cmd javafx:run` en Windows. La conexión usa `C:/sqlite/db/Visa_Shoes_DataBase.db` por defecto. Se puede cambiar con la propiedad `-Dvisa.shoes.db=ruta/a/la/base.db`.

## Fondo de bienvenida

La imagen actual está en `src/main/resources/donadores/pe/upeu/visa_shoes_project/images/fondo-zapateria.jpg`. El inicio la carga desde el banner de `home-view.fxml`; si el recurso no está, conserva el degradado.