package api;

public class Api {
    
    private static final String url = "http://localhost:9999/api";
    
    // Usuario
    public static final String get_usuarios = url + "/usuarios";
    public static final String get_usuario = url + "/login";
    public static final String post_usuario = url + "/usuario";
    
    // Producto
    public static final String get_productos = url + "/productos";
    public static final String put_producto = url + "/actualizarProducto";
    
    // CompraCliente
    public static final String get_compras_cliente = url + "/detalles";
    public static final String get_compra_cliente = url + "/detalle/{id}";
    public static final String get_compras_cliente_de_un_usuario = url + "/detalle/";
    public static final String post_compra_cliente = url + "/detalle";
    public static final String put_compra_cliente = url + "/actualizarDetalle";
    
}
