package services;

import api.Api;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Producto;

public class CatalogoServices {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private Jsonb jsonb = JsonbBuilder.create();

    public List<Producto> obtenerProductosCatalogo() {

        List<Producto> listaProductos = new ArrayList();

        try {
            String url = Api.get_productos;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            listaProductos = jsonb.fromJson(response.body(), new ArrayList<Producto>() {
            }.getClass().getGenericSuperclass());

            return listaProductos;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public boolean actualizarProducto(Producto producto) {
        try {

            String url = Api.put_producto;

            jsonb = JsonbBuilder.create();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonb.toJson(producto)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            } else {
                System.err.println("Error al actualizar producto. Código de respuesta: " + response.headers());
                System.err.println("Error al actualizar producto. Código de respuesta: " + response.headers().toString());
                return false;
            }

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Producto obtenerProducto(Long id) {

        try {

            Producto producto = new Producto();

            String url = Api.get_producto + "/" + id;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                jsonb = JsonbBuilder.create();
                producto = jsonb.fromJson(response.body(), Producto.class);
                return producto;
            } else {
                System.err.println("Error al obtener producto. Código de respuesta: " + response.headers());
                System.err.println("Error al obtener producto. Código de respuesta: " + response.headers().toString());
                return null;
            }

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

}
