package com.example.new_tsi_moviles.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.JokeCallback;
import com.example.new_tsi_moviles.conexion.JokeConexion;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.dto.JokeDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BadJokeController extends AppCompatActivity {
    private JokeDTO jokeDTO;
    private static final String API_KEY = "AIzaSyAKRrzHQ3VkZa9lftyq3gQQGopUXkLMv6c";
    private TextView enJoke,esJoke,enPunchline,esPunchline;
    private Button ver;

    JokeConexion jokeConexion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bad_joke); // <- Debe existir

        jokeConexion = new JokeConexion(this);

        enJoke = findViewById(R.id.joke);
        esJoke = findViewById(R.id.jokeES);
        enPunchline = findViewById(R.id.punchline);
        esPunchline = findViewById(R.id.punchlineES);
        ver = findViewById(R.id.ver_remate);

        jokeConexion.getJoke(new JokeCallback() {
            @Override
            public void onSuccess(JokeDTO res) throws JSONException {
                jokeDTO = res;
                try {
                    enJoke.setText(jokeDTO.getSetup());
                    enPunchline.setText(jokeDTO.getPunchline());
                }catch (Exception e){
                    Toast.makeText(BadJokeController.this,
                            "No se recibio el chiste, por lo que no hay nada que mostrar",
                            Toast.LENGTH_LONG).show();
                }

                call(new MensajeCallback() {

                    @Override
                    public void onSuccess(String mensaje) {
                        esJoke.setText(mensaje);
                        Toast.makeText(BadJokeController.this,mensaje,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(BadJokeController.this, "No carga", Toast.LENGTH_SHORT).show();

                    }
                },enJoke.getText().toString());

                call(new MensajeCallback() {

                    @Override
                    public void onSuccess(String mensaje) {
                        esPunchline.setText(mensaje);
                        Toast.makeText(BadJokeController.this,mensaje,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(BadJokeController.this, "No carga", Toast.LENGTH_SHORT).show();
                    }
                },enPunchline.getText().toString());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BadJokeController.this,"Error al obtener el chiste",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void call(MensajeCallback callback,String texto) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent" ;

        // Construcción del JSON igual al -d del cURL
        JSONObject body = new JSONObject();
        JSONArray contents = new JSONArray();
        JSONObject content = new JSONObject();
        JSONArray parts = new JSONArray();
        JSONObject part = new JSONObject();

        part.put("text", "Translate it to spanish and just awnser with the translation: " + texto);
        parts.put(part);
        content.put("parts", parts);
        contents.put(content);
        body.put("contents", contents);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                response -> {

                    try {
                        JSONArray candidates2 = response.getJSONArray("candidates");
                        JSONObject content2 = candidates2.getJSONObject(0);
                        JSONObject content3 = content2.getJSONObject("content");
                        JSONArray parts2 = content3.getJSONArray("parts");
                        JSONObject part2 = parts2.getJSONObject(0);

                        try {
                            callback.onSuccess(part2.getString("text"));
                        }catch (Exception e){
                            callback.onError(e);
                        }

                    } catch (JSONException e) {
                        Log.e(e.toString(), e.toString());

                        Toast.makeText(this, "Error procesando respuesta", Toast.LENGTH_SHORT).show();
                        callback.onError(e);
                    }
                },
                error -> {
                    callback.onError(error); }
        ) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("x-goog-api-key", API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(request);
    }
}
