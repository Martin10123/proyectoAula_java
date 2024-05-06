package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import modelos.Producto;
import services.CatalogoServices;

@Named(value = "catalogoBean")
@SessionScoped
public class CatalogoBean implements Serializable {

    private Producto producto = new Producto();
    private List<Producto> listaProductos = new ArrayList();
    
    private CatalogoServices catalogoServices = new CatalogoServices();

    public CatalogoBean() {
        this.listaProductos = this.catalogoServices.obtenerProductosCatalogo();
    }
    
    public String saberFechaActual() {
        Date fechaHoraActual = new Date();

        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy 'a las' h:mm a", new Locale("es", "CO"));

        return formatoFechaHora.format(fechaHoraActual);
    }

    public String precioNormal(double precio) {
        DecimalFormat formatoColombiano = new DecimalFormat("#,###");

        String precioFormateado = formatoColombiano.format(precio);
        return precioFormateado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
