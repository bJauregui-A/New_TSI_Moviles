package com.example.new_tsi_moviles.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.MainActivity;
import com.example.new_tsi_moviles.R;

public class Principal extends AppCompatActivity {

    Button btnCursos,btnPerfil,bntnCertificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal); // <- Debe existir

        btnCursos = findViewById(R.id.btn_cursos);
        btnPerfil = findViewById(R.id.btn_perfil);
        bntnCertificados = findViewById(R.id.btn_certificados);

        btnCursos.setOnClickListener(v -> {
            Intent intent = new Intent(this, CursoController.class);

            startActivity(intent);
            finish();
        });

    }
}
