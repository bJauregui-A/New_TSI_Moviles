package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.dto.JokeDTO;
import com.example.new_tsi_moviles.model.CursoUserState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JokeConexion {
    Context context;
    String ip;
    public JokeConexion(Context context) {
        this.context = context;
        ip = "https://official-joke-api.appspot.com/random_joke";
    }

    public void getJoke(JokeCallback callback){
        RequestQueue queue = Volley.newRequestQueue(this.context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ip, // La URL final se construye aquí
                null,
                response -> {
                    try {
                        JSONObject cursoJson = response;
                        JokeDTO joke = JokeDTO.builder()
                                .id(cursoJson.getInt("id"))
                                .setup(cursoJson.getString("setup"))
                                .type(cursoJson.getString("type"))
                                .punchline(cursoJson.getString("punchline"))
                                .build();
                        callback.onSuccess(joke);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        );
        queue.add(request);
    }
}
