package com.example.new_tsi_moviles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_tsi_moviles.controller.CreateUserController;
import com.example.new_tsi_moviles.controller.Principal;
import com.example.new_tsi_moviles.controller.UsuarioController;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText ipTxt, email,password;
    Boolean puedes;
    Button btnContinuar,btnUno,btnDiez, btnTest,registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


         email = findViewById(R.id.email);
         ipTxt = findViewById(R.id.ip);
         password = findViewById(R.id.password);
         btnContinuar = findViewById(R.id.btn_continuar);
         btnUno = findViewById(R.id.btn_uno);
         btnDiez = findViewById(R.id.btn_diez);
         btnTest = findViewById(R.id.btn_test);
        registro = findViewById(R.id.btn_registro);
puedes=false;

        btnUno.setOnClickListener(v -> {
            ipTxt.setText("192.168.1.105");
        });

        btnDiez.setOnClickListener(v -> {
            ipTxt.setText("10.33.114.227");

        });

        registro.setOnClickListener(v -> {
            test();
            if(puedes){
                Intent intent = new Intent(this, CreateUserController.class);
                startActivity(intent);
            }
        });

        btnTest.setOnClickListener(v -> {
            test();
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
                            String token = null;
                            try {
                                token = response.getString("token");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                                prefs.edit().putString("token",token).apply();

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
private void test(){
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
                    puedes=true;
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
}

}