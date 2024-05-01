package modelos;

public class Producto {

    private String idProducto;
    private String nombreProducto;
    private double precio;
    private String tipo;
    private String cantidad;
    private int cantidadAlmacenamiento;
    private boolean disponibilidad;

    public Producto() {
    }

    public Producto(String idProducto, String nombreProducto, double precio, String tipo, String cantidad, int cantidadAlmacenamiento, boolean disponibilidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.cantidadAlmacenamiento = cantidadAlmacenamiento;
        this.disponibilidad = disponibilidad;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadAlmacenamiento() {
        return cantidadAlmacenamiento;
    }

    public void setCantidadAlmacenamiento(int cantidadAlmacenamiento) {
        this.cantidadAlmacenamiento = cantidadAlmacenamiento;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

}
