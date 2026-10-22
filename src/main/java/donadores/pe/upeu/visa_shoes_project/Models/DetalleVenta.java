package donadores.pe.upeu.visa_shoes_project.Models;
import java.math.BigDecimal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DetalleVenta {
    private final IntegerProperty iddetalle = new SimpleIntegerProperty();
    private final IntegerProperty idventa = new SimpleIntegerProperty();
    private final ObjectProperty<Producto> producto = new SimpleObjectProperty<>();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal> precioUnit =
            new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> importe =
            new SimpleObjectProperty<>(BigDecimal.ZERO);

    public DetalleVenta() {
    }

    public DetalleVenta(int iddetalle, int idventa, Producto producto, int cantidad,
                        BigDecimal precioUnit, BigDecimal importe) {
        setIddetalle(iddetalle);
        setIdventa(idventa);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnit(precioUnit);
        setImporte(importe);
    }

    public final int getIddetalle() {
        return iddetalle.get();
    }

    public final void setIddetalle(int value) {
        iddetalle.set(value);
    }

    public final IntegerProperty iddetalleProperty() {
        return iddetalle;
    }

    public final int getIdventa() {
        return idventa.get();
    }

    public final void setIdventa(int value) {
        idventa.set(value);
    }

    public final IntegerProperty idventaProperty() {
        return idventa;
    }

    public final Producto getProducto() {
        return producto.get();
    }

    public final void setProducto(Producto value) {
        producto.set(value);
    }

    public final ObjectProperty<Producto> productoProperty() {
        return producto;
    }

    public final int getCantidad() {
        return cantidad.get();
    }

    public final void setCantidad(int value) {
        cantidad.set(value);
    }

    public final IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public final BigDecimal getPrecioUnit() {
        return precioUnit.get();
    }

    public final void setPrecioUnit(BigDecimal value) {
        precioUnit.set(value);
    }

    public final ObjectProperty<BigDecimal> precioUnitProperty() {
        return precioUnit;
    }

    public final BigDecimal getImporte() {
        return importe.get();
    }

    public final void setImporte(BigDecimal value) {
        importe.set(value);
    }

    public final ObjectProperty<BigDecimal> importeProperty() {
        return importe;
    }

    public void calcularImporte() {
        setImporte(getPrecioUnit().multiply(BigDecimal.valueOf(getCantidad())));
    }
}