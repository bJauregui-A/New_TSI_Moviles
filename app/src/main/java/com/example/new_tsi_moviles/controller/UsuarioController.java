package com.example.new_tsi_moviles.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.adapter.UsuarioAdapter;
import com.example.new_tsi_moviles.conexion.UserConexion;
import com.example.new_tsi_moviles.conexion.UsersCallback;
import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController extends AppCompatActivity {

    private Context ctx;
    private Switch switchActivos;
    private ListView listView;
    private List<UserDTO> usuariosLocal;
    private Button btnAgregar;
    private EditText buscar;
    private UserConexion userConexion;
    private UsuarioAdapter usuarioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_usuarios);

        ctx = this;
        buscar = findViewById(R.id.text_buscar);
        btnAgregar = findViewById(R.id.btn_add);
        switchActivos = findViewById(R.id.switchActivos);
        listView = findViewById(R.id.lista_cursos); // Cambiar id si es necesario

        usuariosLocal = new ArrayList<>();
        userConexion = new UserConexion(ctx);

        usuarioAdapter = new UsuarioAdapter(ctx, usuariosLocal);
        listView.setAdapter(usuarioAdapter);

        // ================= Botón Agregar =================
        btnAgregar.setOnClickListener(v -> {
            startActivity(new Intent(ctx, CreateUserController.class));
        });

        // ================= Switch activos/inactivos =================
        switchActivos.setOnCheckedChangeListener((buttonView, isChecked) -> actualizarLista());

        // ================= EditText buscar =================
        buscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarLista();
            }
        });

        // ================= Cargar lista inicialmente =================
        actualizarLista();
    }

    private void actualizarLista() {
        boolean activos = !switchActivos.isChecked();
        String palabra = buscar.getText().toString();

        userConexion.getUsers(new UsersCallback() {
            @Override
            public void onSuccess(List<UserDTO> users) {
                usuariosLocal.clear();
                usuariosLocal.addAll(users);
                usuarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
            }
        }, activos, palabra);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }
}
