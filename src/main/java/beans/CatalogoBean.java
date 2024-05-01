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

@Named(value = "catalogoBean")
@SessionScoped
public class CatalogoBean implements Serializable {

    private Producto producto = new Producto();
    private List<Producto> listaProductos = new ArrayList();

    public CatalogoBean() {
        Producto produ1 = new Producto("Margaritas Blancas", "Margaritas Blancas", 500000, "Planta", "Paquete x10 Tallos", 10, true);
        Producto produ2 = new Producto("Girasol Pequeño", "Girasol Pequeño", 350000, "Planta", "Paquete x12 Tallos", 10, true);
        Producto produ3 = new Producto("Clavel Rosado", "Clavel Rosado", 120000, "Planta", "Paquete x25 Tallos", 10, true);
        Producto produ4 = new Producto("Pompon Amarillo", "Pompon Amarillo", 650000, "Planta", "Paquete x10 Tallos", 10, true);
        Producto produ5 = new Producto("Astromelia Blanco", "Astromelia Blanco", 550000, "Planta", "Paquete x10 Tallos", 10, true);
        Producto produ6 = new Producto("Orejon Pequeño", "Orejon Pequeño", 700000, "Semilla", "100g", 10, true);
        Producto produ7 = new Producto("Girasol Gigante", "Girasol Gigante", 490000, "Semilla", "10g", 10, true);
        Producto produ8 = new Producto("Orejon Grande", "Orejon Grande", 900000, "Semilla", "10g", 10, true);
        Producto produ9 = new Producto("Rosales tapizantes", "Rosales tapizantes", 455000, "Semilla", "10g", 10, true);
        Producto produ10 = new Producto("Lirios Morados", "Lirios Morados", 800000, "Planta", "Paquete x15 Tallos", 10, true);
        Producto produ11 = new Producto("Tulipanes Rojos", "Tulipanes Rojos", 150000, "Planta", "Paquete x20 Tallos", 10, true);
        Producto produ12 = new Producto("Girasol Mediano", "Girasol Mediano", 250000, "Planta", "Paquete x8 Tallos", 10, true);
        Producto produ13 = new Producto("Caléndulas Naranjas", "Caléndulas Naranjas", 700000, "Planta", "Paquete x10 Tallos", 10, true);
        Producto produ14 = new Producto("Fresias Amarillas", "Fresias Amarillas", 950000, "Planta", "Paquete x30 Tallos", 10, true);

        this.listaProductos.add(produ1);
        this.listaProductos.add(produ2);
        this.listaProductos.add(produ3);
        this.listaProductos.add(produ4);
        this.listaProductos.add(produ5);
        this.listaProductos.add(produ6);
        this.listaProductos.add(produ7);
        this.listaProductos.add(produ8);
        this.listaProductos.add(produ9);
        this.listaProductos.add(produ10);
        this.listaProductos.add(produ11);
        this.listaProductos.add(produ12);
        this.listaProductos.add(produ13);
        this.listaProductos.add(produ14);
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
