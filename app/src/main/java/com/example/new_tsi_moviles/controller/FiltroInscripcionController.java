package com.example.new_tsi_moviles.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoBusquedaAdapter;
import com.example.new_tsi_moviles.adapter.UsuarioBusquedaAdapter;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class FiltroInscripcionController extends AppCompatActivity {

    private EditText txtCurso, txtUsuario;
    private Spinner spinnerEstado;
    private RecyclerView rvCursos, rvUsuarios;
    private Button btnAplicar;

    private List<CursoDTO> cursos = new ArrayList<>();
    private List<UserDTO> usuarios = new ArrayList<>();

    private CursoBusquedaAdapter cursoAdapter;
    private UsuarioBusquedaAdapter usuarioAdapter;

    private CursoDTO cursoSeleccionado = null;
    private UserDTO usuarioSeleccionado = null;
    private Switch switc;
    private String estadoSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtro_inscripcion);

        txtCurso = findViewById(R.id.txt_buscar_curso);
        txtUsuario = findViewById(R.id.txt_buscar_usuario);
        spinnerEstado = findViewById(R.id.spinner_estado);
        rvCursos = findViewById(R.id.rv_cursos);
        rvUsuarios = findViewById(R.id.rv_usuarios);
        btnAplicar = findViewById(R.id.btn_aplicar_filtro);
        switc = findViewById(R.id.stch_activo_f);

        // ---- Spinner de estados ----
        String[] estados = {"CURSANDO", "CERTIFICADO", "ELIMINADO"};
        ArrayAdapter<String> adapterEstados =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstados);

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, android.view.View view, int pos, long id) {
                estadoSeleccionado = estados[pos];
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ---- recycler de cursos ----
        rvCursos.setLayoutManager(new LinearLayoutManager(this));
        cursoAdapter = new CursoBusquedaAdapter(cursos, item -> cursoSeleccionado = item);
        rvCursos.setAdapter(cursoAdapter);

        // ---- recycler usuarios ----
        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));
        usuarioAdapter = new UsuarioBusquedaAdapter(usuarios, item -> usuarioSeleccionado = item);
        rvUsuarios.setAdapter(usuarioAdapter);

        // filtros locales
        txtCurso.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                cursoAdapter.filtrar(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        txtUsuario.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                usuarioAdapter.filtrar(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // ---- BOTÓN APLICAR FILTRO ----
        btnAplicar.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("curso", cursoSeleccionado != null ? cursoSeleccionado.getNombre() : "");
            data.putExtra("usuario", usuarioSeleccionado != null ? usuarioSeleccionado.getNombre() : "");
            data.putExtra("estado", estadoSeleccionado);
            setResult(RESULT_OK, data);
            finish();
        });

        cargarDatos();
    }

    private void cargarDatos() {
        // Aquí haces las llamadas a tus servicios reales
        // Por ahora se deja como estructura
    }
}
