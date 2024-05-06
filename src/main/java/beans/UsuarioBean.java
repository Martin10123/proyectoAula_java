package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;
import modelos.*;
import services.UsuarioServices;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @Inject
    private ClienteBean clienteBean;

    @Inject
    private OperadorBean operadorBean;

    private UsuarioServices usuarioServices = new UsuarioServices();

    private List<Usuario> listTodosUsuarios = new ArrayList<>();
    private List<Usuario> listTodosOperadores = new ArrayList<>();

    private Usuario usuarioActivo = new Usuario();

    private Usuario usuarioIniciar = new Usuario();
    private Usuario clienteRegistro = new Usuario();

    private boolean isUsuarioActivoNow = false;
    private String navegarPaginas = "iniciarSesion.xhtml";
    private String paginaActualCO = "";

    public UsuarioBean() {
    }

    public void cargarUsuarios() {
        this.listTodosUsuarios = new ArrayList<>();
        
        this.listTodosUsuarios = this.usuarioServices.obtenerUsuarios();
    }

    public void obtenerListTodosOperadores() {
        
        this.listTodosOperadores = new ArrayList<>();

        for (Usuario usuOpe : this.listTodosUsuarios) {

            if (usuOpe.getTipoUsuario().equals("Operador")) {
                this.listTodosOperadores.add(usuOpe);
            }
        }

    }

    public void registrarUsuarios() {

        if (!this.clienteRegistro.validarUsuario()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            return;
        }

        this.clienteRegistro.setTipoUsuario("Cliente");

        // Guardar en base de datos
        Usuario usuarioRegistrado = this.usuarioServices.registrarUsuario(this.clienteRegistro);

        if (usuarioRegistrado != null) {
            this.usuarioActivo = usuarioRegistrado;
            this.clienteRegistro = new Usuario();

            this.cargarUsuarios();
            this.obtenerListTodosOperadores();

            this.isUsuarioActivoNow = true;
            this.paginaActualCO = "cliente/menuCliente.xhtml";

            MensajesAlertas.showInfo("Registro exitoso", "Se registro correctamente");
        }
    }

    public void iniciarSesion() {

        if (!this.usuarioIniciar.validarUsuarioIniciar()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            return;
        }

        Usuario usuarioEncontrado = this.usuarioServices.iniciarSesionUsuario(
                this.usuarioIniciar.getEmail(), this.usuarioIniciar.getPassword());

        this.cargarUsuarios();

        if (usuarioEncontrado != null) {
            this.isUsuarioActivoNow = true;
            this.usuarioActivo = usuarioEncontrado;
            
            if (usuarioEncontrado.getTipoUsuario().equals("Cliente")) {
                this.paginaActualCO = "cliente/menuCliente.xhtml";
                this.obtenerListTodosOperadores();
                this.clienteBean.obtenerComprasClienteActivo(usuarioEncontrado.getIdUsuario());
            } else {
                this.paginaActualCO = "operador/operadorVentas.xhtml";
                this.operadorBean.obtieneProductosPendienteOperador(usuarioEncontrado.getIdUsuario());
            }
        }
        
        this.usuarioIniciar = new Usuario();

    }

    public String usuarioAConocerOperador(Long idUsuario) {

        Usuario usuarioOperador = this.usuarioServices.obtenerUsuario(idUsuario);

        return usuarioOperador.getNombreCompleto();

    }

    public void navegarEntrePaginasAuth(String pagina) {
        this.navegarPaginas = pagina;
    }

    public void cerrarSesion() {
        this.isUsuarioActivoNow = false;
        this.usuarioActivo = new Usuario();
        navegarEntrePaginasAuth("iniciarSesion.xhtml");

        //Cliente
        this.clienteBean.getListaProductosSeleccionados().clear();
        this.clienteBean.getListaProductosCompradosUsuarioActivo().clear();
        this.clienteBean.navegarEntrePaginasCliente("catalogo.xhtml");

        //Operador
        this.operadorBean.getListaProductosOperadorActivo().clear();

    }

    public List<Usuario> getListTodosUsuarios() {
        return listTodosUsuarios;
    }

    public void setListTodosUsuarios(List<Usuario> listTodosUsuarios) {
        this.listTodosUsuarios = listTodosUsuarios;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public Usuario getUsuarioIniciar() {
        return usuarioIniciar;
    }

    public void setUsuarioIniciar(Usuario usuarioIniciar) {
        this.usuarioIniciar = usuarioIniciar;
    }

    public boolean isIsUsuarioActivoNow() {
        return isUsuarioActivoNow;
    }

    public void setIsUsuarioActivoNow(boolean isUsuarioActivoNow) {
        this.isUsuarioActivoNow = isUsuarioActivoNow;
    }

    public String getNavegarPaginas() {
        return navegarPaginas;
    }

    public void setNavegarPaginas(String navegarPaginas) {
        this.navegarPaginas = navegarPaginas;
    }

    public String getPaginaActualCO() {
        return paginaActualCO;
    }

    public void setPaginaActualCO(String paginaActualCO) {
        this.paginaActualCO = paginaActualCO;
    }

    public Usuario getClienteRegistro() {
        return clienteRegistro;
    }

    public void setClienteRegistro(Usuario clienteRegistro) {
        this.clienteRegistro = clienteRegistro;
    }

    public List<Usuario> getListTodosOperadores() {
        return listTodosOperadores;
    }

    public void setListTodosOperadores(List<Usuario> listTodosOperadores) {
        this.listTodosOperadores = listTodosOperadores;
    }
}
