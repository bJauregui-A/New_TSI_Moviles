package com.example.new_tsi_moviles.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.conexion.UserConexion;
import com.example.new_tsi_moviles.dto.CreateUserDTO;
import com.example.new_tsi_moviles.dto.UserDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

public class CreateUserController extends AppCompatActivity {

    private EditText edtNombre, edtApellido, edtEmail, edtPassword, edtRut;
    private Button btnGuardar;

    private CreateUserDTO usuario;
    private UserConexion userConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user); // tu XML con roles

        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnGuardar = findViewById(R.id.btnGuardar);
        edtRut = findViewById(R.id.edtRut);

        usuario = new CreateUserDTO();
        userConexion = new UserConexion(this);

        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmar")
                        .setMessage("¿Desea agregar este usuario?")
                        .setPositiveButton("Sí", (dialog, which) -> agregarUsuario())
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private boolean validarCampos() {
        if (edtNombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtApellido.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese apellido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void agregarUsuario() {
        // Setear datos en DTO
        usuario.setNombre(edtNombre.getText().toString());
        usuario.setApellido(edtApellido.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setPassword(edtPassword.getText().toString());
        usuario.setRut(edtRut.getText().toString());

        // Crear JSON
        JSONObject json = new JSONObject();
        try {
            json.put("nombre", usuario.getNombre());
            json.put("apellido", usuario.getApellido());
            json.put("email", usuario.getEmail());
            json.put("password", usuario.getPassword());
            json.put("rut",usuario.getRut());


            Log.e("jsonEnviado", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        userConexion.createUser(new MensajeCallback() {
            @Override
            public void onSuccess(String msg) {
                Toast.makeText(CreateUserController.this, msg, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(CreateUserController.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
                Log.e("AddUser", e.getMessage());
            }
        }, json);
    }
}
