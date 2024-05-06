package services;

import api.Api;
import beans.MensajesAlertas;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Usuario;

public class UsuarioServices {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    Jsonb jsonb = JsonbBuilder.create();

    public UsuarioServices() {
    }

    public Usuario registrarUsuario(Usuario usuario) {
        try {

            Usuario usuarioDB = new Usuario();

            String url = Api.post_usuario;

            jsonb = JsonbBuilder.create();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonb.toJson(usuario)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                jsonb = JsonbBuilder.create();

                usuarioDB = jsonb.fromJson(response.body(), Usuario.class);

                return usuarioDB;
            } else {
                MensajesAlertas.showError("", "Error al guardar usuario");
                System.err.println("Error al guardar usuario. Código de respuesta: " + response.body());
                System.err.println("Error al guardar usuario. Código de respuesta: " + response.headers().toString());
                return null;
            }

        } catch (IOException ex) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<Usuario> obtenerUsuarios() {

        List<Usuario> listaUsuarios = new ArrayList<>();

        try {
            String url = Api.get_usuarios;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            listaUsuarios = jsonb.fromJson(response.body(), new ArrayList<Usuario>() {
            }.getClass().getGenericSuperclass());

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
        }

        return listaUsuarios;

    }

    public Usuario iniciarSesionUsuario(String email, String password) {

        try {

            Usuario usuario = new Usuario();

            JsonObject usuarioJson = Json.createObjectBuilder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            // Convertir el JSON a una cadena
            String url = Api.get_usuario;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(usuarioJson.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            usuario = jsonb.fromJson(response.body(), Usuario.class);

            return usuario;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public Usuario obtenerUsuario(Long idUsuario) {

        try {

            Usuario usuario = new Usuario();

            String url = Api.get_usuario + "/" + idUsuario;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            jsonb = JsonbBuilder.create();

            usuario = jsonb.fromJson(response.body(), Usuario.class);

            return usuario;

        } catch (Exception e) {
            Logger.getLogger(UsuarioServices.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
