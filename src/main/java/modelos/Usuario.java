package modelos;

public class Usuario {

    private Long idUsuario;
    private String nombreCompleto;
    private String tipoUsuario;
    private String email;
    private String password;
    private String direccion;
    private String telefono;
    private String categoria;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nombreCompleto, String tipoUsuario, String email, String password, String direccion, String telefono, String categoria) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.telefono = telefono;
        this.categoria = categoria;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreCompleto=" + nombreCompleto + ", tipoUsuario=" + tipoUsuario + ", email=" + email + ", password=" + password + ", direccion=" + direccion + ", telefono=" + telefono + ", categoria=" + categoria + '}';
    }

}
