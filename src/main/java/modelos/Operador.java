package modelos;

public class Operador extends Usuario {
    private int productosVendidos;
    private int presupuestoGenerado;
    private String categoria;

    public Operador() {
    }

    public Operador(int productosVendidos, int presupuestoGenerado, String categoria) {
        this.productosVendidos = productosVendidos;
        this.presupuestoGenerado = presupuestoGenerado;
        this.categoria = categoria;
    }

    public Operador(int productosVendidos, int presupuestoGenerado, String categoria, String id, String nombreCompleto, String tipoUsuario, String email, String password, String direccion, String telefono) {
        super(id, nombreCompleto, tipoUsuario, email, password, direccion, telefono);
        this.productosVendidos = productosVendidos;
        this.presupuestoGenerado = presupuestoGenerado;
        this.categoria = categoria;
    }
    
    public boolean validarOperador() {
        return categoria != null && !categoria.isEmpty();
    }

    public int getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(int productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public int getPresupuestoGenerado() {
        return presupuestoGenerado;
    }

    public void setPresupuestoGenerado(int presupuestoGenerado) {
        this.presupuestoGenerado = presupuestoGenerado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
