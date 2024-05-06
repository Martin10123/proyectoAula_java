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
import modelos.CompraCliente;

public class CompraClienteServices {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private Jsonb jsonb = JsonbBuilder.create();

    public boolean guardarDetalleVenta(CompraCliente compraCliente) {
        try {

            String url = Api.post_compra_cliente;

            jsonb = JsonbBuilder.create();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonb.toJson(compraCliente)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            } else {
                System.err.println("Error al guardar el detalle de venta. Código de respuesta: " + response.headers());
                System.err.println("Error al guardar el detalle de venta. Código de respuesta: " + response.headers().toString());
                return false;
            }

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<CompraCliente> obtenerComprasDetalle() {

        try {
            List<CompraCliente> listaDetalleCompra = new ArrayList<>();

            String url = Api.get_compras_cliente;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            listaDetalleCompra = jsonb.fromJson(response.body(), new ArrayList<CompraCliente>() {
            }.getClass().getGenericSuperclass());

            return listaDetalleCompra;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<CompraCliente> obtenerCompraDetalleDeUnUsuario(Long id) {

        try {

            List<CompraCliente> listaDetalleCompraDeUnUsuario = new ArrayList<>();
            
            String url = Api.get_compras_cliente_de_un_usuario + id + "/usuario";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            listaDetalleCompraDeUnUsuario = jsonb.fromJson(response.body(), new ArrayList<CompraCliente>() {
            }.getClass().getGenericSuperclass());

            return listaDetalleCompraDeUnUsuario;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public CompraCliente obtenerUnaCompraDetalle(Long id) {

        try {

            CompraCliente compraCliente = new CompraCliente();

            String url = Api.get_compra_cliente + "/" + id;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            compraCliente = jsonb.fromJson(response.body(), CompraCliente.class);

            return compraCliente;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public boolean actualizarDetalleCompra(CompraCliente compraCliente) {
        try {

            String url = Api.put_compra_cliente;

            jsonb = JsonbBuilder.create();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonb.toJson(compraCliente)))
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

}
