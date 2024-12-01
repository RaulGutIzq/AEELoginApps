package com.example.aeeloginapps.APILogin;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String API_URL = "https://sandbox.phpulse.es/index.php?action=usuarios";

    public static Usuario validarUser(String correo, String pass) {
        Usuario[] usuarios = obtenerUsuarios();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(correo) && u.getPassword().equals(pass)) {
                return u;
            }
        }
        return null;
    }

    public static Usuario[] obtenerUsuarios() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.d("RESPUESTA", String.valueOf(conn.getResponseCode()));
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONArray jsonArray = new JSONArray(response.toString());
                Log.d("RESPUESTA",response.toString());
                Usuario[] usuarios = new Usuario[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    Usuario usuario = new Usuario();
                    usuario.setId(obj.getString("id"));
                    usuario.setNombre(obj.getString("nombre"));
                    usuario.setEmail(obj.getString("email"));
                    usuario.setPassword(obj.getString("password"));
                    usuario.setCreado_en(obj.getString("creado_en"));
                    usuarios[i] = usuario;
                    Log.d("USUARIO: "+i, String.valueOf(usuario));
                }
                return usuarios;
            } else {
                System.out.println("Error: " + conn.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String obtenerUsuarioPorId(int id) {
        try {
            URL url = new URL(API_URL + "&id=" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;


                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void crearUsuario(String nombreUsuario, String email, String contraseña) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"entidad\":\"usuarios\",\"nombre\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}", nombreUsuario, email, contraseña);
            System.out.println(jsonInputString);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            if (conn.getResponseCode() == 200) {
                System.out.println("Usuario creado con éxito.");
            } else {
                System.out.println("Error al crear usuario: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarUsuario(int id, String nombreUsuario, String email) {
        try {
            URL url = new URL(API_URL + "&id=" + id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"entidad\":\"usuarios\",\"nombre\":\"%s\", \"email\":\"%s\"}", nombreUsuario, email);
            System.out.println(jsonInputString);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            if (conn.getResponseCode() == 200) {
                System.out.println("Usuario actualizado con éxito.");

            } else {
                System.out.println("Error al actualizar usuario: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eliminarUsuario(int id) {
        try {
            URL url = new URL(API_URL + "&id=" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() == 200) {
                System.out.println("Usuario eliminado con éxito.");
            } else {
                System.out.println("Error al eliminar usuario: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String creado_en;

    public Usuario(String id, String nombre, String email, String password, String creado_en) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.creado_en = creado_en;
    }

    public Usuario() {
    }


    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCreado_en() {
        return creado_en;
    }

    public void setCreado_en(String creado_en) {
        this.creado_en = creado_en;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creado_en='" + creado_en + '\'' +
                '}';
    }
}