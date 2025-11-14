package com.example.new_tsi_moviles.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewCompat;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;

import java.util.ArrayList;
import java.util.List;

public class CursoController extends AppCompatActivity {
    Context ctx;
    Switch switch1;
    ListView listView;
    List<CursoDTO> cursosLocal;
    Button btn_agregar;
    EditText buscar;
    CursoService cursoService;
    CursoAdapter cursoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_cursos);

        ctx = this;
        buscar = findViewById(R.id.text_buscar);
        btn_agregar = findViewById(R.id.btn_add);
        cursosLocal = new ArrayList<>();
        switch1 = findViewById(R.id.switchActivos);
        cursoService = new CursoService(this);
        listView = findViewById(R.id.lista_cursos);

        cursoAdapter = new CursoAdapter(ctx, cursosLocal,0);
        listView.setAdapter(cursoAdapter);

        btn_agregar.setOnClickListener(v -> {
            startActivity(new Intent(ctx, CrearCursoController.class));
        });

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> actualizarLista());
        buscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarLista();
            }
        });

        actualizarLista();
    }

    private void actualizarLista() {
        String ruta = switch1.isChecked() ? "/all" : "/allA";
        if (!buscar.getText().toString().equals("") && !buscar.getText().toString().isEmpty()) {
            ruta += "/"+buscar.getText().toString();
        }

        cursoService.getCursos(new CursosCallback() {
            @Override
            public void onSuccess(List<CursoDTO> cursos) {
                cursosLocal.clear();
                cursosLocal.addAll(cursos);
                cursoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        }, ruta);

        Integer num =0;
        cursosLocal.stream().forEach(curso -> {
            if (!curso.getActivo()){
                listView.getItemAtPosition(num);
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }
}
