package donadores.pe.upeu.visa_shoes_project.modelo;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Venta {
    private final IntegerProperty idventa = new SimpleIntegerProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final IntegerProperty idCliente = new SimpleIntegerProperty();
    private final StringProperty nombreCliente = new SimpleStringProperty("Sin cliente");
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

    public Venta(int idventa, String fecha, BigDecimal total, int idCliente, String nombreCliente) {
        this(idventa, fecha, total);
        setIdCliente(idCliente);
        setNombreCliente(nombreCliente);
    }

    public final int getIdCliente() { return idCliente.get(); }
    public final void setIdCliente(int value) { idCliente.set(value); }
    public final IntegerProperty idClienteProperty() { return idCliente; }

    public final String getNombreCliente() { return nombreCliente.get(); }
    public final void setNombreCliente(String value) { nombreCliente.set(value); }
    public final StringProperty nombreClienteProperty() { return nombreCliente; }

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
