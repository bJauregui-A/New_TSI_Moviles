package com.example.new_tsi_moviles.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.adapter.InscripcionesAdapter;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.conexion.InscripcionesCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.InscripcionService;
import com.example.new_tsi_moviles.service.PerfilService;

import java.util.ArrayList;
import java.util.List;

public class CertificadosController extends AppCompatActivity {
    Context ctx;
    ListView listView;
    UserDTO userDTO;
    PerfilService perfilService;
    InscripcionesAdapter inscripcionesAdapter;
    List<InscripcionDTO> cursosLocal;
    Button btn_agregar;
    EditText buscar;
    InscripcionService cursoService;
    InscripcionesAdapter cursoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_certificados);

        perfilService = new PerfilService(this);
        ctx = this;
        buscar = findViewById(R.id.text_buscar);
        btn_agregar = findViewById(R.id.btn_add);
        cursosLocal = new ArrayList<InscripcionDTO>();
        cursoService = new InscripcionService(this);
        listView = findViewById(R.id.lista_certificados);

        userDTO = new UserDTO();

        perfilService.getPerfil(new PerfilCallback() {

            @Override
            public void onSuccess(UserDTO userDTO2) {
                userDTO=userDTO2;
                cursoAdapter = new InscripcionesAdapter(ctx, cursosLocal,userDTO);
                listView.setAdapter(cursoAdapter);
                actualizarLista();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(CertificadosController.this,"No se cargó el usuario",Toast.LENGTH_LONG).show();
            }
        });



        btn_agregar.setOnClickListener(v -> {
            startActivity(new Intent(ctx, CrearCursoController.class));
        });



        actualizarLista();
    }

    private void actualizarLista() {



        cursoService.getAll(new InscripcionesCallback() {
            @Override
            public void onSuccess(List<InscripcionDTO> cursos) {
                cursosLocal.clear();
                cursosLocal.addAll(cursos);
                cursoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }
}
