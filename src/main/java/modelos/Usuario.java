package modelos;

public class Usuario {

    private String id;
    private String nombreCompleto;
    private String tipoUsuario;
    private String email;
    private String password;
    private String direccion;
    private String telefono;

    public Usuario() {
    }

    public Usuario(String id, String nombreCompleto, String tipoUsuario, String email, String password, String direccion, String telefono) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public boolean validarUsuario() {
        return nombreCompleto != null && !nombreCompleto.isEmpty()
                && email != null && !email.isEmpty()
                && password != null && !password.isEmpty()
                && direccion != null && !direccion.isEmpty()
                && telefono != null && !telefono.isEmpty();
    }

    public boolean validarUsuarioIniciar() {
        return email != null && !email.isEmpty()
                && password != null && !password.isEmpty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
