package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.CursoDTO;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursoConexion {

    private String ip, url, strinToken;
    private Context context;

    public CursoConexion(Context context) {
        this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        strinToken = prefs.getString("token", null); // Obtenemos el token como String

        url = ip + "/cursos";
    }


    public void getCursos(CursosCallback callback, String ruta) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + ruta, // La URL final se construye aquí
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
                                    .activo(cursoJson.getBoolean("activo"))
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

    public void updateCurso(JSONObject cursoJson,MensajeCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        Log.e("JSON", cursoJson.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url+"/update",
                cursoJson,
                response -> {
                        callback.onSuccess("Curso Actualizado");
                },
                callback::onError
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                if (strinToken != null && !strinToken.isEmpty()) {
                    headers.put("Authorization", "Bearer " + strinToken);
                }
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(request);
    }
    public void createCurso(MensajeCallback callback,JSONObject cursoJson) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, // o PUT
                url+"/create",
                cursoJson, // <-- Aquí va el body
                response -> {
                    callback.onSuccess("Curso Creado");
                },
                error -> {
                    callback.onError(error);
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                if (strinToken != null && !strinToken.isEmpty()) {
                    // --- SOLUCIÓN ---
                    // Usar la variable 'strinToken' que contiene el JWT real.
                    headers.put("Authorization", "Bearer " + strinToken);

                }
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(request);
    }
    public void getCurso(CursoCallback callback,Long id) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "/id/"+id.toString(), // La URL final se construye aquí
                null,
                response -> {

                    try {

                            JSONObject cursoJson = response;
                            CursoDTO curso = CursoDTO.builder()
                                    .id(cursoJson.getLong("id"))
                                    .activo(cursoJson.getBoolean("activo"))
                                    .descripcion(cursoJson.getString("descripcion"))
                                    .horas(cursoJson.getInt("horas"))
                                    .dirigidoa(cursoJson.getString("dirigidoa"))
                                    .linkPago(cursoJson.getString("linkPago"))
                                    .modalidad(cursoJson.getString("modalidad"))
                                    .nombre(cursoJson.getString("nombre"))
                                    .precio(cursoJson.getInt("precio"))
                                    .build();

                        callback.onSuccess(curso);
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

    public void deleteCurso(MensajeCallback callback,Long id) {


        getCurso(new CursoCallback() {
            @Override
            public void onSuccess(CursoDTO curso) {
                RequestQueue queue = Volley.newRequestQueue(context);

                Request<String> request = new Request<String>(
                        Request.Method.DELETE,
                        url + "/delete/"+id, // La URL final se construye aquí
                        null
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

                    @Override
                    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
                        return Response.success("Curso Eliminado", null);
                    }

                    @Override
                    protected void deliverResponse(String response) {
                        callback.onSuccess(response);

                    }
                };

                queue.add(request);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "EL curso ya fue eliminado anteriormente", Toast.LENGTH_LONG).show();
            }
        },id);
    }

    public void activarCurso(MensajeCallback callback,Long id) {


        getCurso(new CursoCallback() {
            @Override
            public void onSuccess(CursoDTO curso) {
                RequestQueue queue = Volley.newRequestQueue(context);

                Request<String> request = new Request<String>(
                        Request.Method.GET,
                        url + "/undelete/"+id, // La URL final se construye aquí
                        null
                ) {

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        if (strinToken != null && !strinToken.isEmpty()) {
                            headers.put("Authorization", "Bearer " + strinToken);
                        }
                        return headers;
                    }

                    @Override
                    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
                        return Response.success("Curso Eliminado", null);
                    }

                    @Override
                    protected void deliverResponse(String response) {
                        callback.onSuccess(response);

                    }
                };

                queue.add(request);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "EL curso ya fue eliminado anteriormente", Toast.LENGTH_LONG).show();
            }
        },id);



    }

    public void lotiene(ConexionCallback callback,Long id,Long idu) {

        RequestQueue queue = Volley.newRequestQueue(context);



        StringRequest request = new StringRequest(
                Request.Method.GET,
                url+"/lotiene/"+id.toString()+"/"+idu.toString(),
                response -> {
                    callback.onSuccess(Boolean.parseBoolean(response.trim()));
                },
                error -> {

                }
        ){
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


        Volley.newRequestQueue(context).add(request);


        queue.add(request);
    }

}