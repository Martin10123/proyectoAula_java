package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modelos.CompraCliente;
import modelos.Producto;
import org.primefaces.PrimeFaces;
import services.CatalogoServices;
import services.CompraClienteServices;

@Named(value = "operadorBean")
@SessionScoped
public class OperadorBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;
    @Inject
    private ClienteBean clienteBean;
    @Inject
    private CatalogoBean catalogoBean;

    private CatalogoServices catalogoServices = new CatalogoServices();
    
    private CompraClienteServices operadorServices = new CompraClienteServices();

    private List<CompraCliente> listaProductosOperadorActivo = new ArrayList<>();
    private List<CompraCliente> listaTodosLosProductosEsteUsuario = new ArrayList<>();
    private List<CompraCliente> listaProductosVendidos = new ArrayList<>();

    private CompraCliente productoSeleccionAEnviar = new CompraCliente();
    private CompraCliente productoSeleccionACancelar = new CompraCliente();

    private String navegarPaginas = "entregar.xhtml";

    public OperadorBean() {
    }

    public void obtieneProductosPendienteOperador(Long id) {

        this.listaProductosOperadorActivo = this.operadorServices.obtenerCompraDetalleDeUnUsuario(id, "operador")
                .stream().filter(p -> p.getEstadoCompra().equals("Pendiente")).collect(Collectors.toList());

        this.obtenerTodosLosProductosOperador(id);
        this.mostrarProductosVendidos(id);
    }

    public void obtenerTodosLosProductosOperador(Long id) {

        this.listaTodosLosProductosEsteUsuario.clear();
        
        this.listaTodosLosProductosEsteUsuario = this.operadorServices.obtenerCompraDetalleDeUnUsuario(id, "operador");

    }

    public void mostrarProductosVendidos(Long id) {

        this.listaProductosVendidos.clear();
        
        this.listaProductosVendidos = this.operadorServices.obtenerCompraDetalleDeUnUsuario(id, "operador")
                .stream().filter(p -> p.getEstadoCompra().equals("Vendido")).collect(Collectors.toList());

    }

    public void enviarProductoAcliente(CompraCliente comCliProducto) {
        this.productoSeleccionAEnviar = comCliProducto;
        PrimeFaces.current().executeScript("PF('dlg2').show();");
    }

    public void completarEnvioProducto() {
        if (!this.productoSeleccionAEnviar.validarDireccion()) {
            MensajesAlertas.showError("Llene el campo", "Complete la información de la dirección");
            return;
        }
        
        Producto productoModiCatalogo = this.catalogoServices.obtenerProducto(this.productoSeleccionAEnviar.getProducto().getIdProducto());

        if (this.productoSeleccionAEnviar.getProducto().getCantidadAlmacenamiento() == 0
                || productoModiCatalogo.getCantidadAlmacenamiento() < this.productoSeleccionAEnviar.getCantidadComprada()) {
            MensajesAlertas.showWarn("Producto agotado", "Este producto esta agotado o no hay la cantidad solicitada, revise el catalogo y luego cancele el pedido explicando el motivo");
            return;
        }

        productoModiCatalogo.setCantidadAlmacenamiento(productoModiCatalogo.getCantidadAlmacenamiento() - this.productoSeleccionAEnviar.getCantidadComprada());

        this.productoSeleccionAEnviar.setEstadoCompra("Vendido");
        this.productoSeleccionAEnviar.setFechaConfirmacion(this.catalogoBean.saberFechaActual());

        if (productoModiCatalogo.getCantidadAlmacenamiento() == 0) {
            productoModiCatalogo.setDisponibilidad(false);
        }

        this.catalogoServices.actualizarProducto(productoModiCatalogo);
        this.operadorServices.actualizarDetalleCompra(this.productoSeleccionAEnviar);
        this.obtieneProductosPendienteOperador(this.productoSeleccionAEnviar.getOperador().getIdUsuario());
        this.catalogoBean.actualizarCatalogoDespuesCompra();

        this.productoSeleccionAEnviar = new CompraCliente();
        this.productoSeleccionACancelar = new CompraCliente();

    }

    public void onAbrirModalCancelarProducto(CompraCliente comCliProducto) {
        this.productoSeleccionACancelar = comCliProducto;
        PrimeFaces.current().executeScript("PF('dlg3').show();");
    }

    public void cancelarProductoCliente() {

        CompraCliente prodCliACancelar = this.operadorServices.obtenerUnaCompraDetalle(this.productoSeleccionACancelar.getIdCompraCliente());

        prodCliACancelar.setEstadoCompra("Cancelado por el operador: " + this.productoSeleccionACancelar.getEstadoCompra());
        prodCliACancelar.setFechaConfirmacion(this.catalogoBean.saberFechaActual());

        this.operadorServices.actualizarDetalleCompra(prodCliACancelar);
        this.obtieneProductosPendienteOperador(this.productoSeleccionACancelar.getOperador().getIdUsuario());
        this.catalogoBean.actualizarCatalogoDespuesCompra();

        this.productoSeleccionACancelar = new CompraCliente();
        this.productoSeleccionAEnviar = new CompraCliente();

    }

    public String saberMenuSeleccionado(String paginaActual) {
        if (this.navegarPaginas.equals(paginaActual)) {
            return "menu_seleccionado";
        } else {
            return "";
        }
    }

    public void navegarEntrePaginasOperador(String pagina) {
        this.navegarPaginas = pagina;
    }

    public String getNavegarPaginas() {
        return navegarPaginas;
    }

    public void setNavegarPaginas(String navegarPaginas) {
        this.navegarPaginas = navegarPaginas;
    }

    public List<CompraCliente> getListaProductosOperadorActivo() {
        return listaProductosOperadorActivo;
    }

    public List<CompraCliente> getListaTodosLosProductosEsteUsuario() {
        return listaTodosLosProductosEsteUsuario;
    }

    public List<CompraCliente> getListaProductosVendidos() {
        return listaProductosVendidos;
    }

    public void setListaProductosVendidos(List<CompraCliente> listaProductosVendidos) {
        this.listaProductosVendidos = listaProductosVendidos;
    }

    public void setListaTodosLosProductosEsteUsuario(List<CompraCliente> listaTodosLosProductosEsteUsuario) {
        this.listaTodosLosProductosEsteUsuario = listaTodosLosProductosEsteUsuario;
    }

    public void setListaProductosOperadorActivo(List<CompraCliente> listaProductosOperadorActivo) {
        this.listaProductosOperadorActivo = listaProductosOperadorActivo;
    }

    public CompraCliente getProductoSeleccionAEnviar() {
        return productoSeleccionAEnviar;
    }

    public void setProductoSeleccionAEnviar(CompraCliente productoSeleccionAEnviar) {
        this.productoSeleccionAEnviar = productoSeleccionAEnviar;
    }

    public CompraCliente getProductoSeleccionACancelar() {
        return productoSeleccionACancelar;
    }

    public void setProductoSeleccionACancelar(CompraCliente productoSeleccionACancelar) {
        this.productoSeleccionACancelar = productoSeleccionACancelar;
    }

}
