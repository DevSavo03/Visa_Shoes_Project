package donadores.pe.upeu.visa_shoes_project.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty documento = new SimpleStringProperty();
    private final StringProperty celular = new SimpleStringProperty();

    public Persona() {
    }

    public Persona(String nombre, String apellidos, String documento, String celular) {
        setNombre(nombre);
        setApellidos(apellidos);
        setDocumento(documento);
        setCelular(celular);
    }

    public final String getnombre() {
        return nombre.get();
    }

    public final void setNombre(String value) {
        nombre.set(value);
    }

    public final StringProperty nombreProperty() {
        return nombre;
    }

    public final String getApellidos() {
        return apellidos.get();
    }

    public final void setApellidos(String value) {
        apellidos.set(value);
    }

    public final StringProperty apellidosProperty() {
        return apellidos;
    }

    public final String getDocumento() {
        return documento.get();
    }

    public final void setDocumento(String value) {
        documento.set(value);
    }

    public final StringProperty documentoProperty() {
        return documento;
    }

    public final String getCelular() {
        return celular.get();
    }

    public final void setCelular(String value) {
        celular.set(value);
    }

    public final StringProperty celularProperty() {
        return celular;
    }
}
