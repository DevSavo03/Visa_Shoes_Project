package donadores.pe.upeu.visa_shoes_project.Models;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Venta {
    private final IntegerProperty idventa = new SimpleIntegerProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final ArrayList<DetalleVenta> detalleVenta = new ArrayList<>();
    private final ObjectProperty<BigDecimal> total =
            new SimpleObjectProperty<>(BigDecimal.ZERO);

    public Venta() {
    }

    public Venta(int idventa, String fecha, BigDecimal total) {
        setIdventa(idventa);
        setFecha(fecha);
        setTotal(total);
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

    public final String getFecha() {
        return fecha.get();
    }

    public final void setFecha(String value) {
        fecha.set(value);
    }

    public final StringProperty fechaProperty() {
        return fecha;
    }

    public ArrayList<DetalleVenta> getDetalleVenta() {
        return detalleVenta;
    }

    public final BigDecimal getTotal() {
        return total.get();
    }

    public final void setTotal(BigDecimal value) {
        total.set(value);
    }

    public final ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void calcularTotal() {
        BigDecimal suma = BigDecimal.ZERO;

        for (DetalleVenta detalle : detalleVenta) {
            suma = suma.add(detalle.getImporte());
        }

        setTotal(suma);
    }
}