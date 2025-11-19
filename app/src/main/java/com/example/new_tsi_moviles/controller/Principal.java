package com.example.new_tsi_moviles.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.MainActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.JokeCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.PerfilService;

public class Principal extends AppCompatActivity {

    Button btnCursos,btnPerfil,bntnCertificados, btn_cursosUser,btn_Joke,btn_users,btn_mapa;
    PerfilService perfilService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        btnCursos = findViewById(R.id.btn_cursos);
        btnPerfil = findViewById(R.id.btn_perfil);
        bntnCertificados = findViewById(R.id.btn_certificados);
        btn_cursosUser = findViewById(R.id.btn_cursos_user);
        btn_Joke = findViewById(R.id.bad_joke);
        btn_users = findViewById(R.id.btn_users);
        perfilService = new PerfilService(this);
        btn_mapa= findViewById(R.id.btn_mapa);

        btnCursos.setOnClickListener(v -> {
            Intent intent = new Intent(this, CursoController.class);
            startActivity(intent);
        });

        btn_cursosUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, CursoUserController.class);
            startActivity(intent);
        });

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilController.class);
            startActivity(intent);
        });

        bntnCertificados.setOnClickListener(v -> {
            Intent intent = new Intent(this, CertificadosController.class);
            startActivity(intent);
        });
        btn_Joke.setOnClickListener(v -> {
            Intent intent = new Intent(this, BadJokeController.class);
            startActivity(intent);
        });
        btn_users.setOnClickListener(v -> {
            Intent intent = new Intent(this, UsuarioController.class);
            startActivity(intent);
        });
        btn_mapa.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapaController.class);
            startActivity(intent);
        });


        perfilService.getPerfil(new PerfilCallback() {
            @Override
            public void onSuccess(UserDTO userDTO) {

                Log.d("User", userDTO.toString());
                // Si roles viene null, evitamos crash
                if (userDTO.getRoles() == null) {
                    btnCursos.setVisibility(View.GONE);
                    bntnCertificados.setVisibility(View.GONE);
                    return;
                }

                boolean esAdmin = userDTO.getRoles().contains("ADMIN");

                if (esAdmin) {
                    btnCursos.setVisibility(View.VISIBLE);
                    bntnCertificados.setVisibility(View.VISIBLE);
                    btn_users.setVisibility(View.VISIBLE);
                } else {
                    btnCursos.setVisibility(View.GONE);
                    bntnCertificados.setVisibility(View.GONE);
                    btn_users.setVisibility(View.GONE);
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(Principal.this, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
