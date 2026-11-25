package donadores.pe.upeu.visa_shoes_project.modelo;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class Producto {
    private final IntegerProperty idproducto = new SimpleIntegerProperty();
    private final StringProperty modelo = new SimpleStringProperty();
    private final StringProperty marca = new SimpleStringProperty();
    private final StringProperty talla = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> precio =
            new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final IntegerProperty stock = new SimpleIntegerProperty();

    public Producto() {
    }

    public Producto(int idproducto, String modelo, String marca, String talla,
                    BigDecimal precio, int stock) {
        setIdproducto(idproducto);
        setModelo(modelo);
        setMarca(marca);
        setTalla(talla);
        setPrecio(precio);
        setStock(stock);
    }

    public final int getIdproducto() {
        return idproducto.get();
    }

    public final void setIdproducto(int value) {
        idproducto.set(value);
    }

    public final IntegerProperty idproductoProperty() {
        return idproducto;
    }

    public final String getModelo() {
        return modelo.get();
    }

    public final void setModelo(String value) {
        modelo.set(value);
    }

    public final StringProperty modeloProperty() {
        return modelo;
    }

    public final String getMarca() {
        return marca.get();
    }

    public final void setMarca(String value) {
        marca.set(value);
    }

    public final StringProperty marcaProperty() {
        return marca;
    }

    public final String getTalla() {
        return talla.get();
    }

    public final void setTalla(String value) {
        talla.set(value);
    }

    public final StringProperty tallaProperty() {
        return talla;
    }

    public final BigDecimal getPrecio() {
        return precio.get();
    }

    public final void setPrecio(BigDecimal value) {
        precio.set(value);
    }

    public final ObjectProperty<BigDecimal> precioProperty() {
        return precio;
    }

    public final int getStock() {
        return stock.get();
    }

    public final void setStock(int value) {
        stock.set(value);
    }

    public final IntegerProperty stockProperty() {
        return stock;
    }
}