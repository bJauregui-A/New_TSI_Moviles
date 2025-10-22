package com.example.new_tsi_moviles.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.MainActivity;
import com.example.new_tsi_moviles.R;

public class Principal extends AppCompatActivity {

    Button btnCursos,btnPerfil,bntnCertificados, btn_cursosUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        btnCursos = findViewById(R.id.btn_cursos);
        btnPerfil = findViewById(R.id.btn_perfil);
        bntnCertificados = findViewById(R.id.btn_certificados);
        btn_cursosUser = findViewById(R.id.btn_cursos_user);

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

    }
}
