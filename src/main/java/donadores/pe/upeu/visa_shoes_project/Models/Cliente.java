package donadores.pe.upeu.visa_shoes_project.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.print.Doc;

public class Cliente extends Persona {
    private final IntegerProperty idCliente = new SimpleIntegerProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final StringProperty correo = new SimpleStringProperty();

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String apellidos, String documento,
                   String celular, String direccion, String correo) {
        super(nombre, apellidos, documento, celular);
        setIdCliente(idCliente);
        setDireccion(direccion);
        setCorreo(correo);
    }

    public final int getIdCliente() { return idCliente.get(); }
    public final void setIdCliente(int value) { idCliente.set(value); }
    public final IntegerProperty idClienteProperty() { return idCliente; }

    public final String getDireccion() { return direccion.get(); }
    public final void setDireccion(String value) { direccion.set(value); }
    public final StringProperty direccionProperty() { return direccion; }

    public final String getCorreo() { return correo.get(); }
    public final void setCorreo(String value) { correo.set(value); }
    public final StringProperty correoProperty() { return correo; }
}