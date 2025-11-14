package com.example.new_tsi_moviles.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.InscripcionesAdapter;
import com.example.new_tsi_moviles.conexion.InscripcionesCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.InscripcionService;
import com.example.new_tsi_moviles.service.PerfilService;

import java.util.ArrayList;
import java.util.List;

public class CertificadosController extends AppCompatActivity {

    private Context ctx;
    private ListView listView;
    private UserDTO userDTO;
    private PerfilService perfilService;
    private List<InscripcionDTO> cursosLocal;
    private InscripcionService cursoService;
    private InscripcionesAdapter cursoAdapter;
    private Button btn_agregar,btn_filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_certificados);

        ctx = this;
        perfilService = new PerfilService(ctx);
        cursoService = new InscripcionService(ctx);

        listView = findViewById(R.id.lista_certificados);
        btn_agregar = findViewById(R.id.btn_add);
        btn_filtro = findViewById(R.id.btn_filtro);
        cursosLocal = new ArrayList<>();

        perfilService.getPerfil(new PerfilCallback() {
            @Override
            public void onSuccess(UserDTO userDTO2) {
                userDTO = userDTO2;
                cursoAdapter = new InscripcionesAdapter(ctx, cursosLocal, userDTO);
                listView.setAdapter(cursoAdapter);
                actualizarLista();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ctx, "No se cargó el usuario", Toast.LENGTH_LONG).show();
            }
        });

        btn_agregar.setOnClickListener(v -> {
            startActivity(new Intent(ctx, CrearCursoController.class));
        });
        btn_filtro.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, FiltroInscripcionController.class);
            startActivity(intent);
        });

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
                Toast.makeText(ctx, "Error al cargar las inscripciones", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userDTO != null) { // 🔹 evitar llamada antes de que el usuario esté cargado
            actualizarLista();
        }
    }
}
