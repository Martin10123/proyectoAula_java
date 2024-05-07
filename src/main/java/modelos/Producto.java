package modelos;

public class Producto {

    private Long idProducto;
    private String imagenProducto;
    private String nombreProducto;
    private double precio;
    private String tipo;
    private String cantidad;
    private int cantidadAlmacenamiento;
    private boolean disponibilidad;

    public Producto() {
    }

    public Producto(Long idProducto, String imagenProducto, String nombreProducto, double precio, String tipo, String cantidad, int cantidadAlmacenamiento, boolean disponibilidad) {
        this.idProducto = idProducto;
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.cantidadAlmacenamiento = cantidadAlmacenamiento;
        this.disponibilidad = disponibilidad;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
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

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", precio=" + precio + ", tipo=" + tipo + ", cantidad=" + cantidad + ", cantidadAlmacenamiento=" + cantidadAlmacenamiento + ", disponibilidad=" + disponibilidad + '}';
    }

}
