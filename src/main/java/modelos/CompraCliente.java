package modelos;

public class CompraCliente {
    private Long idCompraCliente;
    private int cantidadComprada;
    private double precioTotal;
    private String fechaCompra;
    private String fechaConfirmacion;
    private String direccionEnvio;
    private String estadoCompra;
    private String descripcionProducto;
    private String idOperador;
    private Usuario operador;
    private Usuario cliente;
    private Producto producto;

    public CompraCliente() {
    }

    public CompraCliente(Long idCompraCliente, int cantidadComprada, double precioTotal, String fechaCompra, String fechaConfirmacion, String direccionEnvio, String estadoCompra, String descripcionProducto, String idOperador, Usuario operador, Usuario cliente, Producto producto) {
        this.idCompraCliente = idCompraCliente;
        this.cantidadComprada = cantidadComprada;
        this.precioTotal = precioTotal;
        this.fechaCompra = fechaCompra;
        this.fechaConfirmacion = fechaConfirmacion;
        this.direccionEnvio = direccionEnvio;
        this.estadoCompra = estadoCompra;
        this.descripcionProducto = descripcionProducto;
        this.idOperador = idOperador;
        this.operador = operador;
        this.cliente = cliente;
        this.producto = producto;
    }
    
    public boolean validarDireccion() {
        return direccionEnvio != null && !direccionEnvio.isEmpty();
    }
    
    public boolean validarProducto() {
        return direccionEnvio != null && !direccionEnvio.isEmpty()
                && descripcionProducto != null && !descripcionProducto.isEmpty();
    }

    public Long getIdCompraCliente() {
        return idCompraCliente;
    }

    public void setIdCompraCliente(Long idCompraCliente) {
        this.idCompraCliente = idCompraCliente;
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

    public Usuario getOperador() {
        return operador;
    }

    public void setOperador(Usuario operador) {
        this.operador = operador;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(String idOperador) {
        this.idOperador = idOperador;
    }

    @Override
    public String toString() {
        return "CompraCliente{" + "idCompraCliente=" + idCompraCliente + ", cantidadComprada=" + cantidadComprada + ", precioTotal=" + precioTotal + ", fechaCompra=" + fechaCompra + ", fechaConfirmacion=" + fechaConfirmacion + ", direccionEnvio=" + direccionEnvio + ", estadoCompra=" + estadoCompra + ", descripcionProducto=" + descripcionProducto + ", idOperador=" + idOperador + ", operador=" + operador + ", cliente=" + cliente + ", producto=" + producto + '}';
    }
}
