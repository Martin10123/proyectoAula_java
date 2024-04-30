package beans;

import java.text.SimpleDateFormat;
import java.util.*;
import modelos.*;
import org.primefaces.PrimeFaces;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

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
    private Producto productoSeleccionado = new Producto();

    public ClienteBean() {
    }

    public void seleccionarProducto(Producto producto) {
        if (this.listaProductosSeleccionados.contains(producto)) {
            MensajesAlertas.showInfo("Producto ya seleccionado", "Ya seleccionaste este producto, primero termina la compra de este");
            return;
        }

        this.listaProductosSeleccionados.add(producto);

        MensajesAlertas.showInfo("Seleccionaste un producto", "Producto seleccionado con exito");

    }

    public void quitarProductoSeleccionado(Producto productoQ) {
        this.listaProductosSeleccionados.remove(productoQ);
        this.productoSeleccionado = new Producto();
        this.compraProducto = new CompraCliente();
    }

    public void comprarProductoSeleccionado() {
        if (!this.compraProducto.validarProducto()) {
            PrimeFaces.current().executeScript("PF('dlg1').show();");
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            return;
        }

        // Validar si el usuario ya compro este producto
        boolean validarSiProductoYaFueComprado = this.listaProductosCompradosUsuarioActivo
                .stream().anyMatch(p -> p.getProductoComprado().getIdProducto().equals(this.productoSeleccionado.getIdProducto()));

        if (validarSiProductoYaFueComprado) {

            int productoAcualizarTP = buscarPosicionElementoLista(this.listaTodosProductosComprados);
            int productoAcualizarUA = buscarPosicionElementoLista(this.listaProductosCompradosUsuarioActivo);

            CompraCliente productoActualizarDatos = this.listaTodosProductosComprados.get(productoAcualizarTP);

            productoActualizarDatos.setCantidadComprada(productoActualizarDatos.getCantidadComprada() + 1);
            // Multiplicamos la cantidad de veces que compraron el producto por el precio unitario del producto
            productoActualizarDatos.setPrecioTotal(productoActualizarDatos.getCantidadComprada() * productoActualizarDatos.getProductoComprado().getPrecio());

            this.listaTodosProductosComprados.set(productoAcualizarTP, productoActualizarDatos);
            this.listaProductosCompradosUsuarioActivo.set(productoAcualizarUA, productoActualizarDatos);
            this.listaProductosSeleccionados.remove(this.productoSeleccionado);
            this.productoSeleccionado = new Producto();
            this.compraProducto = new CompraCliente();

            MensajesAlertas.showInfo("Compraste este producto", "Producto comprado con exito");
            return;

        }

        this.compraProducto.setCantidadComprada(1);
        this.compraProducto.setEstadoCompra("Pendiente");
        this.compraProducto.setFechaCompra(saberFechaActual());
        this.compraProducto.setPrecioTotal(this.productoSeleccionado.getPrecio());
        
        // Usuario
        this.productoSeleccionado.setUsuario(this.usuarioBean.getUsuarioActivoCliente());
        this.compraProducto.setProductoComprado(this.productoSeleccionado);
        
        this.listaTodosProductosComprados.add(this.compraProducto);
        this.listaProductosCompradosUsuarioActivo.add(this.compraProducto);
        this.listaProductosSeleccionados.remove(this.productoSeleccionado);

        this.productoSeleccionado = new Producto();
        this.compraProducto = new CompraCliente();

    }

    public int buscarPosicionElementoLista(List<CompraCliente> listaProductos) {
        for (int i = 0; i < listaProductos.size(); i++) {
            CompraCliente prodComp = listaProductos.get(i);
            if (prodComp.getProductoComprado().getIdProducto().equals(this.productoSeleccionado.getIdProducto())) {
                return i;
            }
        }

        return -1;
    }

    public void actualizarListaProductosCompradosPorXUsuario(Usuario user) {

        for (CompraCliente proComByXUser : this.listaTodosProductosComprados) {

            if (proComByXUser.getProductoComprado().getUsuario().getId()
                    .equals(this.usuarioBean.getUsuarioActivoCliente().getId())) {

                this.listaProductosCompradosUsuarioActivo.add(proComByXUser);
            }

        }

    }

    public String saberMenuSeleccionado(String paginaActual) {
        if (this.navegarPaginas.equals(paginaActual)) {
            return "menu_seleccionado";
        } else {
            return "";
        }
    }

    public String saberFechaActual() {
        Date fechaHoraActual = new Date();

        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy 'a las' h:mm a", new Locale("es", "CO"));

        return formatoFechaHora.format(fechaHoraActual);
    }

    public void abrirModalCompletarCompra(Producto producto) {
        this.productoSeleccionado = producto;
        
        PrimeFaces.current().executeScript("PF('dlg1').show();");
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

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
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
