package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.model.CursoUserState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscripcionConexion {
Context context;
String ip,strinToken,url;
    public InscripcionConexion(Context context) {
        this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        strinToken = prefs.getString("token", null); // Obtenemos el token como String

        url = ip + "/compra";
    }

    public  void getCursosByUser(CursosCallback callback,Long id){
        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url +"/cursoByUser/"+id.toString(), // La URL final se construye aquí
                null,
                response -> {
                    List<CursoDTO> cursos = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject cursoJson = response.getJSONObject(i);
                            cursos.add(CursoDTO.builder()
                                    .id(cursoJson.getLong("id"))
                                    .descripcion(cursoJson.getString("descripcion"))
                                    .horas(cursoJson.getInt("horas"))
                                    .dirigidoa(cursoJson.getString("dirigidoa"))
                                    .linkPago(cursoJson.getString("linkPago"))
                                    .modalidad(cursoJson.getString("modalidad"))
                                    .nombre(cursoJson.getString("nombre"))
                                    .precio(cursoJson.getInt("precio"))
                                    .build());
                        }
                        callback.onSuccess(cursos);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                if (strinToken != null && !strinToken.isEmpty()) {

                    headers.put("Authorization", "Bearer " + strinToken);
                }
                return headers;
            }
        };

        queue.add(request);
    }

    public void crearInscripcion(MensajeCallback  callback, JSONObject inscripcion ){
        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest request = new StringRequest(Request.Method.POST, url+"/create", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
                Toast.makeText(context,"aaaa", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return inscripcion.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + strinToken);
                return headers;
            }
        };

        queue.add(request);
    }

    public void getInscripcion(InscripcionCallback callback,Long id) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "/id/"+id.toString(), // La URL final se construye aquí
                null,
                response -> {

                    try {

                        JSONObject cursoJson = response;
                        InscripcionDTO inscripcionDTO = InscripcionDTO.builder()
                                .estado(CursoUserState.valueOf( cursoJson.getString("estado")))
                                .idCurso(cursoJson.getLong("idCurso"))
                                .idUser(cursoJson.getLong("idUser"))
                                .emailUser(cursoJson.getString("emailUser"))
                                .nombreUser(cursoJson.getString("nombreUser"))
                                .nombreCurso(cursoJson.getString("nombreCurso"))
                                .build();

                        callback.onSuccess(inscripcionDTO);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
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

    public void getInscripcionByCurso(InscripcionCallback callback,Long id) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "/curso/"+id.toString(), // La URL final se construye aquí
                null,
                response -> {

                    try {

                        JSONObject cursoJson = response;
                        InscripcionDTO inscripcionDTO = InscripcionDTO.builder()
                                .estado(CursoUserState.valueOf( cursoJson.getString("estado")))
                                .idCurso(cursoJson.getLong("idCurso"))
                                .idUser(cursoJson.getLong("idUser"))
                                .emailUser(cursoJson.getString("emailUser"))
                                .nombreUser(cursoJson.getString("nombreUser"))
                                .nombreCurso(cursoJson.getString("nombreCurso"))
                                .build();

                        callback.onSuccess(inscripcionDTO);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
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

    public void getInscripcionByUser(InscripcionesCallback callback,Long id) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + "/user/"+id.toString(), // La URL final se construye aquí
                null,
                response -> {
                    List<InscripcionDTO> cursos = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject cursoJson = response.getJSONObject(i);
                            cursos.add(InscripcionDTO.builder()
                                            .nombreCurso(cursoJson.getString("nombreCurso"))
                                            .idUser(cursoJson.getLong("idUser"))
                                            .idCurso(cursoJson.getLong("idCurso"))
                                    .emailUser(cursoJson.getString("emailUser"))
                                    .nombreUser(cursoJson.getString("nombreUser"))
                                            .estado(CursoUserState.valueOf(cursoJson.getString("estado")))
                                    .build());
                        }
                        callback.onSuccess(cursos);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
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

    public void getAll(InscripcionesCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + "/all",
                null,
                response -> {
                    List<InscripcionDTO> cursos = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject cursoJson = response.getJSONObject(i);
                            cursos.add(InscripcionDTO.builder()
                                    .nombreCurso(cursoJson.getString("nombreCurso"))
                                    .idUser(cursoJson.getLong("idUser"))
                                    .idCurso(cursoJson.getLong("idCurso"))
                                    .emailUser(cursoJson.getString("emailUser"))
                                    .nombreUser(cursoJson.getString("nombreUser"))
                                    .estado(CursoUserState.valueOf(cursoJson.getString("estado")))
                                    .build());
                        }
                        callback.onSuccess(cursos);
                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                if (strinToken != null && !strinToken.isEmpty()) {

                    headers.put("Authorization", "Bearer " + strinToken);
                }
                return headers;
            }
        };

        queue.add(request);
    }
}
