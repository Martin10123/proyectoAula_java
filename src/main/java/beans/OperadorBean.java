package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelos.CompraCliente;
import modelos.Producto;
import org.primefaces.PrimeFaces;

@Named(value = "operadorBean")
@SessionScoped
public class OperadorBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;
    @Inject
    private ClienteBean clienteBean;
    @Inject
    private CatalogoBean catalogoBean;

    private List<CompraCliente> listaProductosOperadorActivo = new ArrayList<>();
    private List<CompraCliente> listaTodosLosProductosEsteUsuario = new ArrayList<>();
    private List<CompraCliente> listaProductosEnviados = new ArrayList<>();

    private CompraCliente productoSeleccionAEnviar = new CompraCliente();
    private CompraCliente productoSeleccionACancelar = new CompraCliente();

    private String navegarPaginas = "entregar.xhtml";

    public OperadorBean() {
    }

    public void obtieneProductosUsuarioActivo() {

        for (CompraCliente prodComCli : this.clienteBean.getListaTodosProductosComprados()) {
            if (prodComCli.getIdOperador()
                    .equals(this.usuarioBean.getUsuarioActivoOperador().getId())) {

                if (prodComCli.getEstadoCompra().equals("Pendiente")) {
                    this.listaProductosOperadorActivo.add(prodComCli);
                }
            }
        }

        this.obtenerTodosLosProductos();
        this.mostrarProductosEnviados();
    }

    public void obtenerTodosLosProductos() {

        this.listaTodosLosProductosEsteUsuario.clear();

        for (CompraCliente prodComCli : this.clienteBean.getListaTodosProductosComprados()) {
            if (prodComCli.getIdOperador()
                    .equals(this.usuarioBean.getUsuarioActivoOperador().getId())) {
                this.listaTodosLosProductosEsteUsuario.add(prodComCli);
            }
        }
    }

    public void mostrarProductosEnviados() {

        this.listaProductosEnviados.clear();

        for (CompraCliente proEnviados : this.clienteBean.getListaTodosProductosComprados()) {
            if (proEnviados.getEstadoCompra().equals("Enviado")) {
                listaProductosEnviados.add(proEnviados);
            }
        }

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

        int posicionProductoCatalogo = this.catalogoBean.getListaProductos()
                .indexOf(this.productoSeleccionAEnviar.getProductoComprado());

        Producto productoModiCatalogo = this.catalogoBean.getListaProductos().get(posicionProductoCatalogo);

        if (productoModiCatalogo.getCantidadAlmacenamiento() == 0) {
            MensajesAlertas.showError("Producto agotado", "Este producto esta agotado");
            return;
        }

        // Producto del catalogo
        productoModiCatalogo.setCantidadAlmacenamiento(productoModiCatalogo.getCantidadAlmacenamiento() - 1);

        // Buscamos la posicion de este detalle en la lista de los productos comprados
        int posicionPCC = this.clienteBean.getListaTodosProductosComprados().indexOf(this.productoSeleccionAEnviar);

        // Detalle de venta
        this.productoSeleccionAEnviar.setEstadoCompra("Enviado");
        this.productoSeleccionAEnviar.setFechaConfirmacion(this.catalogoBean.saberFechaActual());

        // Cambiar disponibilidad del producto si ya se vendio todo
        productoModiCatalogo.setDisponibilidad(false);

        // Guardar el producto del catalogo que compraron
        this.catalogoBean.getListaProductos().set(posicionProductoCatalogo, productoModiCatalogo);

        // Guardar el detalle de compra del cliente
        this.clienteBean.getListaTodosProductosComprados().set(posicionPCC, this.productoSeleccionAEnviar);

        this.productoSeleccionAEnviar = new CompraCliente();
        this.productoSeleccionACancelar = new CompraCliente();

        // Limpiamos la lista de los usuarios para luego volverla a llenar con los que no esten cancelados
        this.listaProductosOperadorActivo.clear();

        this.obtieneProductosUsuarioActivo();

    }

    public void onAbrirModalCancelarProducto(CompraCliente comCliProducto) {
        this.productoSeleccionACancelar = comCliProducto;
        PrimeFaces.current().executeScript("PF('dlg3').show();");
    }

    public void cancelarProductoCliente() {

        int posicionPCC = this.clienteBean.getListaTodosProductosComprados().indexOf(this.productoSeleccionACancelar);

        CompraCliente prodCliACancelar = this.clienteBean.getListaTodosProductosComprados().get(posicionPCC);

        prodCliACancelar.setEstadoCompra(this.productoSeleccionACancelar.getEstadoCompra());
        prodCliACancelar.setFechaConfirmacion(this.catalogoBean.saberFechaActual());

        this.clienteBean.getListaTodosProductosComprados().set(posicionPCC, this.productoSeleccionACancelar);

        this.productoSeleccionACancelar = new CompraCliente();
        this.productoSeleccionAEnviar = new CompraCliente();

        // Limpiamos la lista de los usuarios para luego volverla a llenar con los que no esten cancelados
        this.listaProductosOperadorActivo.clear();

        this.obtieneProductosUsuarioActivo();

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

    public List<CompraCliente> getListaProductosEnviados() {
        return listaProductosEnviados;
    }

    public void setListaProductosEnviados(List<CompraCliente> listaProductosEnviados) {
        this.listaProductosEnviados = listaProductosEnviados;
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
