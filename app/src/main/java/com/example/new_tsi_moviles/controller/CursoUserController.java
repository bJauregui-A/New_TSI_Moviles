package com.example.new_tsi_moviles.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;

import java.util.ArrayList;
import java.util.List;

public class CursoUserController extends AppCompatActivity {
    Context ctx;
    ListView listView;
    List<CursoDTO> cursosLocal;

    EditText buscar;
    CursoService cursoService;
    CursoAdapter cursoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_cursos);

        ctx = this;
        buscar = findViewById(R.id.text_buscar);
        cursosLocal = new ArrayList<>();
        cursoService = new CursoService(this);
        listView = findViewById(R.id.lista_cursos);

        // Crear adapter una sola vez
        cursoAdapter = new CursoAdapter(ctx, cursosLocal,1);
        listView.setAdapter(cursoAdapter);


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



        cursoService.getCursos(new CursosCallback() {
            @Override
            public void onSuccess(List<CursoDTO> cursos) {
                cursosLocal.clear();
                cursoAdapter.notifyDataSetChanged();
                cursosLocal.addAll(cursos);
                cursoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        }, "/allA");
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }
}
