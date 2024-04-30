package modelos;

public class Cliente extends Usuario {
    private int productosComprados;
    private int cantidadInvertida;

    public Cliente() {
    }

    public Cliente(int productosComprados, int cantidadInvertida) {
        this.productosComprados = productosComprados;
        this.cantidadInvertida = cantidadInvertida;
    }

    public Cliente(int productosComprados, int cantidadInvertida, String id, String nombreCompleto, String tipoUsuario, String email, String password, String direccion, String telefono) {
        super(id, nombreCompleto, tipoUsuario, email, password, direccion, telefono);
        this.productosComprados = productosComprados;
        this.cantidadInvertida = cantidadInvertida;
    }
    
    public int getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(int productosComprados) {
        this.productosComprados = productosComprados;
    }

    public int getCantidadInvertida() {
        return cantidadInvertida;
    }

    public void setCantidadInvertida(int cantidadInvertida) {
        this.cantidadInvertida = cantidadInvertida;
    }
    
}
