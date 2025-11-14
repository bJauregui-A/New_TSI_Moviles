package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class PerfilConexion {

    Context context;

    String ip,strinToken,url;
    public PerfilConexion(Context context) {
        this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        strinToken = prefs.getString("token", null); // Obtenemos el token como String

        url = ip + "/usuarios/perfil";
    }
public void getPerfil(PerfilCallback perfilCallback){
    RequestQueue queue = Volley.newRequestQueue(this.context);

    JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.GET,
            url , // La URL final se construye aquí
            null,
            response -> {
                UserDTO userDTO = new UserDTO();
                try {
                    Log.d("UserRecibido: ",response.toString());
                    JSONArray rolesJSON = response.getJSONArray("roles");

                    Set<String> rolesSet = new HashSet<>();
                    for (int i = 0; i < rolesJSON.length(); i++) {
                        rolesSet.add(rolesJSON.getString(i));
                    }
                    userDTO.setEmail(response.getString("email"));
                    userDTO.setNombre(response.getString("nombre"));
                    userDTO.setApellido(response.getString("apellido"));
                    userDTO.setRoles(rolesSet);
                    userDTO.setId(response.getLong("id"));

                    perfilCallback.onSuccess(userDTO);
                } catch (JSONException e) {
                    perfilCallback.onError(e);
                }
            },
            error -> {
                Log.d("Error: ", error.getMessage());
                perfilCallback.onError(error);
            }
    ) {
        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = new HashMap<>();
            if (strinToken != null && !strinToken.isEmpty()) {
                // --- SOLUCIÓN ---
                // Usar la variable 'strinToken' que contiene el JWT real.
                headers.put("Authorization", "Bearer " + strinToken);
            }
            return headers;
        }
    };

    queue.add(request);

}

}
