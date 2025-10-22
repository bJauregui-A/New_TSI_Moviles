package com.example.new_tsi_moviles.controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.InscripcionService;
import com.example.new_tsi_moviles.service.PerfilService;

import java.util.ArrayList;
import java.util.List;

public class PerfilController extends AppCompatActivity {
    private TextView tvNombre, tvApellido, tvEmail;
    private ListView lvCursos;
    private Button btnAtras;

    private List<CursoDTO> cursosLocal;

    private InscripcionService  inscripcionService;
    private PerfilService perfilService;
    private CursoAdapter cursoAdapter;

    private UserDTO usuario;

    private ArrayAdapter<String> cursosAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        tvApellido = findViewById(R.id.tv_apellido);
        tvEmail = findViewById(R.id.tvEmail);
        tvNombre = findViewById(R.id.tvNombre);
        lvCursos = findViewById(R.id.tvCursos);
        cursosLocal = new ArrayList<>();
        btnAtras = findViewById(R.id.btn_back);

        inscripcionService = new InscripcionService(this);
        perfilService = new PerfilService(this);

        cursoAdapter = new CursoAdapter(this, cursosLocal,0);
        lvCursos.setAdapter(cursoAdapter);


        btnAtras.setOnClickListener(v -> finish());
        perfilService.getPerfil(new  PerfilCallback(){

            @Override
            public void onSuccess(UserDTO userDTO) {
                usuario = userDTO;
                tvNombre.setText(usuario.getNombre());
                tvApellido.setText(usuario.getApellido());
                tvEmail.setText(usuario.getEmail());
                actualizarLista();

            }

            @Override
            public void onError(Exception e) {

            }
        });


    }
    private void actualizarLista() {

        inscripcionService.getCursosByUser(new CursosCallback() {
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
        }, usuario.getId());
    }
}
