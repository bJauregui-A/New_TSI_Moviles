package com.example.new_tsi_moviles.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;

import java.util.ArrayList;
import java.util.List;

public class CursoController extends AppCompatActivity {
    ListView listView;
    List<CursoDTO> cursosLocal;
    CursoService cursoService;
    CursoAdapter cursoAdapter;
    private CursoAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_cursos); // <- Debe existir

        cursoService = new CursoService(this);
        ListView listView = findViewById(R.id.lista_cursos);

        SharedPreferences prefs = getSharedPreferences("AppConfig", MODE_PRIVATE);
        String ip = prefs.getString("ip", null);

        if (ip != null) {
            String url = "http://" + ip + ":8080/test";
            Log.d("APP", "Conectando a: " + url);
        } else {
            Toast.makeText(this, "No se cargó la ip", Toast.LENGTH_LONG).show();
        }

        cursoService.getCursos(new CursoCallback() {
            @Override
            public void onSuccess(List<CursoDTO> cursos) {

                for (CursoDTO c : cursos) {
                    cursosLocal.add(c);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        adapter= new CursoAdapter(this, cursosLocal );
        listView.setAdapter(adapter);

    }
}