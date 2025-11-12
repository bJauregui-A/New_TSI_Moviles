package com.example.new_tsi_moviles.conexion;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.PixelCopy;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ConexionConect {

    Context context;
    String ip;
    String url;
    String strinToken;

    public ConexionConect(Context context) {
        this.context = context;
        SharedPreferences prefs = context.getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        ip = prefs.getString("ip", null);
        strinToken = prefs.getString("token", null); // Obtenemos el token como String

        url = ip + "/test";
    }

    public void test(ConexionCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(Boolean.TRUE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
                Toast.makeText(context,"No se percibe conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
