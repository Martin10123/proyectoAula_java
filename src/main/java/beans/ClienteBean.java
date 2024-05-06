package beans;

import java.util.*;
import modelos.*;
import org.primefaces.PrimeFaces;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import services.CompraClienteServices;

@Named(value = "clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;

    private final CompraClienteServices operadorServices = new CompraClienteServices();

    private String navegarPaginas = "catalogo.xhtml";

    private List<CompraCliente> listaTodosProductosComprados = new ArrayList();
    private List<CompraCliente> listaProductosCompradosUsuarioActivo = new ArrayList();
    private List<Producto> listaProductosSeleccionados = new ArrayList();

    private CompraCliente compraProducto = new CompraCliente();
    private Usuario opeSele = new Usuario();

    private Producto productoSeleccionadoAComprar = new Producto();
    private Producto productoSeleccionadoCatalogo = new Producto();

    public ClienteBean() {
    }

    public void onSeleccionarProductoCatalogo(Producto prod) {

        this.productoSeleccionadoCatalogo = prod;

        PrimeFaces.current().executeScript("PF('cantidadProdu').show();");

    }

    public List<Integer> cantidadAComprarProductoCatalogo() {
        List<Integer> cantidadDisponible = new ArrayList<>();

        for (int i = 1; i <= this.productoSeleccionadoCatalogo.getCantidadAlmacenamiento(); i++) {
            cantidadDisponible.add(i);
        }

        return cantidadDisponible;
    }

    public void onCompletarSeleccionProduCatalogo() {

        this.listaProductosSeleccionados.add(this.productoSeleccionadoCatalogo);

        MensajesAlertas.showInfo("", "Seleccionaste este producto");
    }

    public void onModalCompletarCompra(Producto producto, Usuario operaSele) {
        this.productoSeleccionadoAComprar = producto;
        opeSele = operaSele;

        PrimeFaces.current().executeScript("PF('dlg1').show();");
    }

    public void onCompletarCompraProducto() {

        if (!this.compraProducto.validarProducto()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            PrimeFaces.current().executeScript("PF('dlg1').show();");
            return;
        }

        this.compraProducto.setIdCompraCliente(new Random().nextLong());
        this.compraProducto.setEstadoCompra("Pendiente");
        this.compraProducto.setFechaCompra(this.saberFechaActual());
        this.compraProducto.setPrecioTotal(this.productoSeleccionadoAComprar.getPrecio() * this.compraProducto.getCantidadComprada());
        this.compraProducto.setCliente(this.usuarioBean.getUsuarioActivo());
        this.compraProducto.setProducto(this.productoSeleccionadoAComprar);
        this.compraProducto.setOperador(this.opeSele);

        boolean seGuardoCorrectamente = this.operadorServices.guardarDetalleVenta(this.compraProducto);

        if (seGuardoCorrectamente) {
            this.listaProductosCompradosUsuarioActivo.add(compraProducto);
            this.listaProductosSeleccionados.remove(this.productoSeleccionadoAComprar);

            this.productoSeleccionadoAComprar = new Producto();
            this.compraProducto = new CompraCliente();
            MensajesAlertas.showInfo("Compraste este producto", "Producto comprado con exito");
        } else {
            MensajesAlertas.showError("Error al comprar el producto", "Producto no fue comprado con exito");
        }

    }

    public void obtenerComprasClienteActivo(Long id) {

        this.listaProductosCompradosUsuarioActivo = operadorServices.obtenerCompraDetalleDeUnUsuario(id, "cliente");

    }

    public void onQuitarProductoAComprar(Producto prodQuitar) {
        this.listaProductosSeleccionados.remove(prodQuitar);

        MensajesAlertas.showError("Quitaste un producto", "Quitaste este producto con exito");
    }

    public String saberFechaActual() {
        Date fechaHoraActual = new Date();

        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy 'a las' h:mm a", new Locale("es", "CO"));

        return formatoFechaHora.format(fechaHoraActual);
    }

    public String saberMenuSeleccionado(String paginaActual) {
        if (this.navegarPaginas.equals(paginaActual)) {
            return "menu_seleccionado";
        } else {
            return "";
        }
    }

    public void navegarEntrePaginasCliente(String pagina) {
        this.navegarPaginas = pagina;
    }

    public String getNavegarPaginas() {
        return navegarPaginas;
    }

    public void setNavegarPaginas(String navegarPaginas) {
        this.navegarPaginas = navegarPaginas;
    }

    public List<CompraCliente> getListaTodosProductosComprados() {
        return listaTodosProductosComprados;
    }

    public void setListaTodosProductosComprados(List<CompraCliente> listaTodosProductosComprados) {
        this.listaTodosProductosComprados = listaTodosProductosComprados;
    }

    public List<CompraCliente> getListaProductosCompradosUsuarioActivo() {
        return listaProductosCompradosUsuarioActivo;
    }

    public void setListaProductosCompradosUsuarioActivo(List<CompraCliente> listaProductosCompradosUsuarioActivo) {
        this.listaProductosCompradosUsuarioActivo = listaProductosCompradosUsuarioActivo;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Producto getProductoSeleccionadoAComprar() {
        return productoSeleccionadoAComprar;
    }

    public void setProductoSeleccionadoAComprar(Producto productoSeleccionadoAComprar) {
        this.productoSeleccionadoAComprar = productoSeleccionadoAComprar;
    }

    public List<Producto> getListaProductosSeleccionados() {
        return listaProductosSeleccionados;
    }

    public void setListaProductosSeleccionados(List<Producto> listaProductosSeleccionados) {
        this.listaProductosSeleccionados = listaProductosSeleccionados;
    }

    public CompraCliente getCompraProducto() {
        return compraProducto;
    }

    public void setCompraProducto(CompraCliente compraProducto) {
        this.compraProducto = compraProducto;
    }

    public Producto getProductoSeleccionadoCatalogo() {
        return productoSeleccionadoCatalogo;
    }

    public void setProductoSeleccionadoCatalogo(Producto productoSeleccionadoCatalogo) {
        this.productoSeleccionadoCatalogo = productoSeleccionadoCatalogo;
    }

}
