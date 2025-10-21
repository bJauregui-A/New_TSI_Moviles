package com.example.new_tsi_moviles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.controller.Principal;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText ipTxt, email,password;
    Button btnContinuar,btnUno,btnDiez, btnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);

        EditText email = findViewById(R.id.email);
        EditText ipTxt = findViewById(R.id.ip);
        EditText password = findViewById(R.id.password);
        Button btnContinuar = findViewById(R.id.btn_continuar);
        Button btnUno = findViewById(R.id.btn_uno);
        Button btnDiez = findViewById(R.id.btn_diez);
        Button btnTest = findViewById(R.id.btn_test);


        btnUno.setOnClickListener(v -> {
            ipTxt.setText("192.168.1.90");
        });

        btnDiez.setOnClickListener(v -> {
            ipTxt.setText("10.33.98.179");

        });

        btnTest.setOnClickListener(v -> {
            String ip = ipTxt.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://"+ip+":8080/test";

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Aquí manejas la respuesta del servidor
                            Toast.makeText(MainActivity.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Aquí manejas el error
                            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            queue.add(request);
        });

        btnContinuar.setOnClickListener(v -> {
            if(ipTxt.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty()){
                Toast.makeText(this, "Rellene Correctamente valores", Toast.LENGTH_SHORT).show();;
            }else {

                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String ip = ipTxt.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(this);
                String url ="http://"+ip+":8080/login";
                // Request a string response from the provided URL.
                JsonObjectRequest jsonRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        body,
                        response -> {
                            // Procesar JSON recibido
                            SharedPreferences prefs = getSharedPreferences("AppConfig", MODE_PRIVATE);
                            prefs.edit().putString("ip", "http://"+ip+":8080").apply();

                            prefs.edit().putString("token",response.toString()).apply();

                            Intent intent = new Intent(MainActivity.this, Principal.class);
                            startActivity(intent);
                            finish();
                        },
                        error -> Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_LONG).show()
                );
                queue.add(jsonRequest);
            }
        });

    }


}