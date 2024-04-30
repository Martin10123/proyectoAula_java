package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
import modelos.*;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @Inject
    private ClienteBean clienteBean;

    @Inject
    private OperadorBean operadorBean;

    private List<Usuario> listTodosUsuarios = new ArrayList<>();
    private List<Cliente> listTodosClientes = new ArrayList<>();
    private List<Operador> listTodosOperadores = new ArrayList<>();

    private Operador usuarioActivoOperador = new Operador();
    private Cliente usuarioActivoCliente = new Cliente();

    private Usuario usuarioIniciar = new Usuario();
    private Cliente clienteRegistro = new Cliente();

    private boolean isUsuarioActivoNow = false;
    private String navegarPaginas = "iniciarSesion.xhtml";
    private String paginaActualCO = "";

    public UsuarioBean() {
    }

    public void generarUsuarios() {
        this.listTodosOperadores = new ArrayList();

        Operador operador1 = new Operador(0, 0, "Plantas", "carlos1", "Carlos Perez", "Operador", "carlos@gmail.com", "12345", "Direccion1", "3006543450");
        Operador operador2 = new Operador(0, 0, "Plantas", "pedro1", "Pedro Rodriguez", "Operador", "pedro@gmail.com", "12345", "Direccion2", "3005674563");
        Operador operador3 = new Operador(0, 0, "Semillas", "luis1", "Luis Carlos", "Operador", "luis@gmail.com", "12345", "Direccion3", "3005674456");
        Operador operador4 = new Operador(0, 0, "Semillas", "daniel1", "Daniel Ramirez", "Operador", "daniel@gmail.com", "12345", "Direccion4", "3045774563");

        this.listTodosOperadores.add(operador1);
        this.listTodosOperadores.add(operador2);
        this.listTodosOperadores.add(operador3);
        this.listTodosOperadores.add(operador4);
        this.listTodosUsuarios.add(operador1);
        this.listTodosUsuarios.add(operador2);
        this.listTodosUsuarios.add(operador3);
        this.listTodosUsuarios.add(operador4);
    }

    public void registrarUsuarios() {

        if (!this.clienteRegistro.validarUsuario()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            return;
        }

        generarUsuarios();

        if (this.verificarSiExisteEmail(this.clienteRegistro.getEmail())) {
            MensajesAlertas.showError("", "Este email ya esta en uso");
            return;
        }

        this.clienteRegistro.setId(UUID.randomUUID().toString());
        this.clienteRegistro.setTipoUsuario("Cliente");
        this.clienteRegistro.setProductosComprados(0);
        this.clienteRegistro.setCantidadInvertida(0);
        this.listTodosUsuarios.add(this.clienteRegistro);
        this.listTodosClientes.add((Cliente) this.clienteRegistro);

        this.usuarioActivoCliente = this.clienteRegistro;
        this.clienteRegistro = new Cliente();

        MensajesAlertas.showInfo("Registro exitoso", "Se registro correctamente");

        this.isUsuarioActivoNow = true;
        this.paginaActualCO = "cliente/menuCliente.xhtml";

    }

    public void iniciarSesion() {

        if (!this.usuarioIniciar.validarUsuarioIniciar()) {
            MensajesAlertas.showError("Llena todos los campos", "Complete toda la información");
            return;
        }

        generarUsuarios();

        boolean existeUsuario = false;

        for (Usuario usuarioIni : listTodosUsuarios) {
            if (usuarioIni.getEmail().equals(this.usuarioIniciar.getEmail())
                    && usuarioIni.getPassword().equals(this.usuarioIniciar.getPassword())) {

                this.usuarioIniciar = new Usuario();

                if (usuarioIni.getTipoUsuario().equals("Cliente")) {
                    this.usuarioActivoCliente = (Cliente) usuarioIni;
                    this.paginaActualCO = "cliente/menuCliente.xhtml";
                    this.clienteBean.actualizarListaProductosCompradosPorXUsuario(usuarioIni);
                } else {

                    this.usuarioActivoOperador = (Operador) usuarioIni;
                    this.paginaActualCO = "operador/operadorVentas.xhtml";
                    this.operadorBean.obtieneProductosUsuarioActivo();
                }

                this.isUsuarioActivoNow = true;
                existeUsuario = true;
            }
        }

        if (!existeUsuario) {
            MensajesAlertas.showWarn("Revise sus credenciales", "Revisa tu contraseña o email");
        }

    }

    public boolean verificarSiExisteEmail(String emailAVerificar) {

        boolean verificarEmail = false;

        for (Usuario usuario : listTodosUsuarios) {
            if (usuario.getEmail().equals(emailAVerificar)) {
                verificarEmail = true;
            }
        }

        return verificarEmail;
    }

    public void navegarEntrePaginasAuth(String pagina) {
        this.navegarPaginas = pagina;
    }

    public void cerrarSesion() {
        this.limpiarCache();
        
        this.isUsuarioActivoNow = false;
        this.usuarioActivoCliente = new Cliente();
        this.usuarioActivoOperador = new Operador();
        navegarEntrePaginasAuth("iniciarSesion.xhtml");

        //Cliente
        this.clienteBean.getListaProductosSeleccionados().clear();
        this.clienteBean.getListaProductosCompradosUsuarioActivo().clear();
        this.clienteBean.navegarEntrePaginasCliente("catalogo.xhtml");

        //Operador
        this.operadorBean.getListaProductosOperadorActivo().clear();
        
    }

    public void limpiarCache() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }

    public List<Usuario> getListTodosUsuarios() {
        return listTodosUsuarios;
    }

    public void setListTodosUsuarios(List<Usuario> listTodosUsuarios) {
        this.listTodosUsuarios = listTodosUsuarios;
    }

    public List<Cliente> getListTodosClientes() {
        return listTodosClientes;
    }

    public void setListTodosClientes(List<Cliente> listTodosClientes) {
        this.listTodosClientes = listTodosClientes;
    }

    public List<Operador> getListTodosOperadores() {
        return listTodosOperadores;
    }

    public void setListTodosOperadores(List<Operador> listTodosOperadores) {
        this.listTodosOperadores = listTodosOperadores;
    }

    public boolean isIsUsuarioActivoNow() {
        return isUsuarioActivoNow;
    }

    public void setIsUsuarioActivoNow(boolean isUsuarioActivoNow) {
        this.isUsuarioActivoNow = isUsuarioActivoNow;
    }

    public Cliente getClienteRegistro() {
        return clienteRegistro;
    }

    public void setClienteRegistro(Cliente clienteRegistro) {
        this.clienteRegistro = clienteRegistro;
    }

    public Usuario getUsuarioIniciar() {
        return usuarioIniciar;
    }

    public void setUsuarioIniciar(Usuario usuarioIniciar) {
        this.usuarioIniciar = usuarioIniciar;
    }

    public Operador getUsuarioActivoOperador() {
        return usuarioActivoOperador;
    }

    public void setUsuarioActivoOperador(Operador usuarioActivoOperador) {
        this.usuarioActivoOperador = usuarioActivoOperador;
    }

    public Cliente getUsuarioActivoCliente() {
        return usuarioActivoCliente;
    }

    public void setUsuarioActivoCliente(Cliente usuarioActivoCliente) {
        this.usuarioActivoCliente = usuarioActivoCliente;
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

}
