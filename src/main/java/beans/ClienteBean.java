package beans;

import java.util.*;
import modelos.*;
import org.primefaces.PrimeFaces;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.text.SimpleDateFormat;

@Named(value = "clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;

    private String navegarPaginas = "catalogo.xhtml";

    private List<CompraCliente> listaTodosProductosComprados = new ArrayList();
    private List<CompraCliente> listaProductosCompradosUsuarioActivo = new ArrayList();
    private List<Producto> listaProductosSeleccionados = new ArrayList();

    private CompraCliente compraProducto = new CompraCliente();
    private Producto productoSeleccionadoAComprar = new Producto();

    public ClienteBean() {
    }

    public void onSeleccionarProductoCatalogo(Producto prod) {

        if (this.listaProductosSeleccionados.contains(prod)) {
            MensajesAlertas.showInfo("Producto ya seleccionado", "Ya seleccionaste este producto, primero termina la compra de este");
            return;
        }

        this.listaProductosSeleccionados.add(prod);

        MensajesAlertas.showInfo("Seleccionaste un producto", "Producto seleccionado con exito");

    }

    public void onModalCompletarCompra(Producto producto) {
        this.productoSeleccionadoAComprar = producto;

        PrimeFaces.current().executeScript("PF('dlg1').show();");
    }

    public void onCompletarCompraProducto() {
        if (!this.compraProducto.validarProducto()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            PrimeFaces.current().executeScript("PF('dlg1').show();");
            return;
        }

        boolean validarSiProductoYaFueComprado = this.listaProductosCompradosUsuarioActivo
                .stream().anyMatch(p -> p.getProductoComprado().getIdProducto()
                .equals(this.productoSeleccionadoAComprar.getIdProducto()));

        if (validarSiProductoYaFueComprado) {

            int productoAcualizarTP = this.buscarPosicionProductoEnLista(this.listaTodosProductosComprados);
            int productoAcualizarUA = this.buscarPosicionProductoEnLista(this.listaProductosCompradosUsuarioActivo);

            CompraCliente productoActualizarDatos = this.listaTodosProductosComprados.get(productoAcualizarTP);

            productoActualizarDatos.setCantidadComprada(productoActualizarDatos.getCantidadComprada() + 1);
            // Multiplicamos la cantidad de veces que compraron el producto por el precio unitario del producto
            productoActualizarDatos.setPrecioTotal(productoActualizarDatos.getCantidadComprada() * productoActualizarDatos.getProductoComprado().getPrecio());

            this.listaTodosProductosComprados.set(productoAcualizarTP, productoActualizarDatos);
            this.listaProductosCompradosUsuarioActivo.set(productoAcualizarUA, productoActualizarDatos);
            this.listaProductosSeleccionados.remove(this.productoSeleccionadoAComprar);
            this.productoSeleccionadoAComprar = new Producto();
            this.compraProducto = new CompraCliente();

            MensajesAlertas.showInfo("Compraste este producto", "Producto comprado con exito");
            return;

        }

        this.compraProducto.setCantidadComprada(1);
        this.compraProducto.setEstadoCompra("Pendiente");
        this.compraProducto.setFechaCompra(this.saberFechaActual());
        this.compraProducto.setPrecioTotal(this.productoSeleccionadoAComprar.getPrecio());
        this.compraProducto.setUsuario(this.usuarioBean.getUsuarioActivoCliente());
        this.compraProducto.setProductoComprado(this.productoSeleccionadoAComprar);

        this.listaTodosProductosComprados.add(this.compraProducto);
        this.listaProductosCompradosUsuarioActivo.add(this.compraProducto);
        this.listaProductosSeleccionados.remove(this.productoSeleccionadoAComprar);

        this.productoSeleccionadoAComprar = new Producto();
        this.compraProducto = new CompraCliente();

        MensajesAlertas.showInfo("Compraste este producto", "Producto comprado con exito");

    }

    public int buscarPosicionProductoEnLista(List<CompraCliente> listaProductos) {
        for (int i = 0; i < listaProductos.size(); i++) {
            CompraCliente prodComp = listaProductos.get(i);
            if (prodComp.getProductoComprado().getIdProducto().equals(this.productoSeleccionadoAComprar.getIdProducto())) {
                return i;
            }
        }

        return -1;
    }

    public void actualizarListaProductosCompradosPorXUsuario() {

        for (CompraCliente proComByXUser : this.listaTodosProductosComprados) {

            if (proComByXUser.getUsuario().getId()
                    .equals(this.usuarioBean.getUsuarioActivoCliente().getId())) {

                this.listaProductosCompradosUsuarioActivo.add(proComByXUser);
            }

        }

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

}
