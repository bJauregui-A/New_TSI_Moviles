package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.CreateUserDTO;
import com.example.new_tsi_moviles.dto.UserDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class UserConexion {

    private Context context;
    private String ip, token, baseUrl;

    public UserConexion(Context context) {
        this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        token = prefs.getString("token", null);
        baseUrl = ip + "/usuarios";
    }

    // ===================== Obtener todos los usuarios =====================
    public void getUsers(UsersCallback callback, boolean activos, String palabra) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String ruta = activos ? "/allA" : "/all";
        if (palabra != null && !palabra.isEmpty()) {
            ruta += "/" + palabra;
        }

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                baseUrl + ruta,
                null,
                response -> {
                    List<UserDTO> users = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            users.add(jsonToUserDTO(obj));
                        }
                        callback.onSuccess(users);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                return authHeader();
            }
        };

        queue.add(request);
    }

    // ===================== Obtener perfil =====================
    public void getPerfil(PerfilCallback callback, Long id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl + "/id/"+id.toString();

        Log.d("url", url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        UserDTO user = jsonToUserDTO(response);
                        callback.onSuccess(user);
                    } catch (JSONException e) {
                        Log.e("UserConexion", e.getMessage());
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                return authHeader();
            }
        };

        queue.add(request);
    }

    // ===================== Crear usuario =====================
    public void createUser(MensajeCallback callback,JSONObject json) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl + "/create";


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                response -> callback.onSuccess("Usuario Creado"),
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = authHeader();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        queue.add(request);
    }

    // ===================== Actualizar usuario =====================
    public void updateUser(JSONObject userDTO, MensajeCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl;



        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userDTO,
                response -> callback.onSuccess("Usuario Actualizado"),
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = authHeader();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        queue.add(request);
    }

    // ===================== Eliminar usuario =====================
    public void deleteUser(Long id, MensajeCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl + "/delete/" + id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                response -> callback.onSuccess("Usuario Eliminado"),
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                return authHeader();
            }
        };

        queue.add(request);
    }

    // ===================== Activar usuario =====================
    public void undeleteUser(Long id, MensajeCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl + "/undelete/" + id;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> callback.onSuccess("Usuario Activado"),
                error -> callback.onError(error)
        ){
            @Override
            public Map<String, String> getHeaders() {
                return authHeader();
            }
        };

        queue.add(request);
    }

    // ===================== Convertir JSON a UserDTO =====================
    private UserDTO jsonToUserDTO(JSONObject obj) throws JSONException {
        Log.d("jsonToUserDTO", obj.toString());
        UserDTO user = new UserDTO();
        user.setId(obj.getLong("id"));
        user.setNombre(obj.getString("nombre"));
        user.setApellido(obj.getString("apellido"));
        user.setActivo(obj.getBoolean("active"));
        user.setEmail(obj.getString("email"));

        JSONArray rolesJSON = obj.optJSONArray("roles");
        Set<String> rolesSet = new HashSet<>();
        if (rolesJSON != null) {
            for (int i = 0; i < rolesJSON.length(); i++) {
                rolesSet.add(rolesJSON.getString(i));
            }
        }
        user.setRoles(rolesSet);

        return user;
    }

    // ===================== Headers de autorización =====================
    private Map<String, String> authHeader() {
        Map<String, String> headers = new HashMap<>();
        if (token != null && !token.isEmpty()) {
            headers.put("Authorization", "Bearer " + token);
        }
        return headers;
    }


}
