package modelos;

public class CompraCliente {
    private Producto productoComprado;
    private int cantidadComprada;
    private double precioTotal;
    private String fechaCompra;
    private String fechaConfirmacion;
    private String direccionEnvio;
    private String estadoCompra;
    private String descripcionProducto;
    private String idOperador;
    private Usuario usuario;

    public CompraCliente() {
    }

    public CompraCliente(Producto productoComprado, int cantidadComprada, double precioTotal, String fechaCompra, String fechaConfirmacion, String direccionEnvio, String estadoCompra, String descripcionProducto, String idOperador, Usuario usuario) {
        this.productoComprado = productoComprado;
        this.cantidadComprada = cantidadComprada;
        this.precioTotal = precioTotal;
        this.fechaCompra = fechaCompra;
        this.fechaConfirmacion = fechaConfirmacion;
        this.direccionEnvio = direccionEnvio;
        this.estadoCompra = estadoCompra;
        this.descripcionProducto = descripcionProducto;
        this.idOperador = idOperador;
        this.usuario = usuario;
    }
    
    public boolean validarDireccion() {
        return direccionEnvio != null && !direccionEnvio.isEmpty();
    }
    
    public boolean validarProducto() {
        return direccionEnvio != null && !direccionEnvio.isEmpty()
                && descripcionProducto != null && !descripcionProducto.isEmpty()
                && idOperador != null && !idOperador.isEmpty()
                ;
    }

    public Producto getProductoComprado() {
        return productoComprado;
    }

    public void setProductoComprado(Producto productoComprado) {
        this.productoComprado = productoComprado;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(String fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(String estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(String idOperador) {
        this.idOperador = idOperador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "CompraCliente{" + "productoComprado=" + productoComprado + ", cantidadComprada=" + cantidadComprada + ", precioTotal=" + precioTotal + ", fechaCompra=" + fechaCompra + ", fechaConfirmacion=" + fechaConfirmacion + ", direccionEnvio=" + direccionEnvio + ", estadoCompra=" + estadoCompra + ", descripcionProducto=" + descripcionProducto + ", idOperador=" + idOperador + '}';
    }
    
}
