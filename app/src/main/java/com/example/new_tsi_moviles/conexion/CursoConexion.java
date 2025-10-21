package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.CursoDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CursoConexion {

    private String ip,url,strinToken;
    private JSONObject token;
    Context context;
    public CursoConexion(Context context) {
         this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        strinToken = prefs.getString("token",null);

        if (strinToken != null) {
            try {
                JSONObject token = new JSONObject(strinToken);

            }catch (JSONException e){
            e.printStackTrace();}
        }

        url = ip+"/cursos";
    }

    public void getCursos(CursoCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + "/allA",
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
                                    .dirigidoa(cursoJson.getString("dirigidoa")) // <- nombre correcto
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
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // opcional, depende de tu API
                if (strinToken != null && !strinToken.isEmpty()) {
                    headers.put("Authorization", "Bearer " + token);
                }
                return headers;
            }
        }
                ;

        queue.add(request);
    }


}
