package com.example.new_tsi_moviles.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.conexion.UserConexion;
import com.example.new_tsi_moviles.dto.UserDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

public class EditUserController extends AppCompatActivity {

    private UserDTO usuario;
    private UserConexion userConexion;

    private EditText edtNombre, edtApellido, edtEmail, edtRoles;
    private Switch stchActive;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user); // tu layout XML

        userConexion = new UserConexion(this);

        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtEmail = findViewById(R.id.edtEmail);
        edtRoles = findViewById(R.id.edtRoles);
        stchActive = findViewById(R.id.stch_active);
        btnGuardar = findViewById(R.id.btnGuardar);

        cargarUsuario();

        btnGuardar.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar actualización")
                    .setMessage("¿Está seguro que desea actualizar este usuario?")
                    .setPositiveButton("Sí", (dialog, which) -> actualizar())
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void cargarUsuario() {
        long userId = getIntent().getLongExtra("usuarioId", -1L);
        userConexion.getPerfil( new PerfilCallback() {
            @Override
            public void onSuccess(UserDTO user) {
                usuario = user;
                edtNombre.setText(usuario.getNombre());
                edtApellido.setText(usuario.getApellido());
                edtEmail.setText(usuario.getEmail());
                edtRoles.setText(String.join(",", usuario.getRoles()));
                stchActive.setChecked(usuario.getActivo());
            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(EditUserController.this, "Error al cargar usuario", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        },userId);
    }

    private void actualizar() {
        if (!validarCampos()) return;

        usuario.setNombre(edtNombre.getText().toString());
        usuario.setApellido(edtApellido.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setRoles(new HashSet<>(Arrays.asList(edtRoles.getText().toString().split(","))));
        usuario.setActivo(stchActive.isChecked());

        JSONObject json = new JSONObject();
        try {
            json.put("id", getIntent().getLongExtra("usuarioId", -1L));
            json.put("nombre", usuario.getNombre());
            json.put("apellido", usuario.getApellido());
            json.put("email", usuario.getEmail());

            // Convertir Set<String> a JSONArray
            JSONArray rolesArray = new JSONArray();
            for (String rol : usuario.getRoles()) {
                rolesArray.put(rol.trim());
            }
            json.put("roles", rolesArray);

            json.put("active", usuario.getActivo());

            Log.e("json", json.toString());
        } catch (JSONException e) {
            Log.d("EditUser", e.getMessage());
            e.printStackTrace();
        }


        userConexion.updateUser(json, new MensajeCallback() {
            @Override
            public void onSuccess(String msg) {
                Toast.makeText(EditUserController.this, msg, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(EditUserController.this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private boolean validarCampos() {
        if (edtNombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nombre vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtApellido.getText().toString().isEmpty()) {
            Toast.makeText(this, "Apellido vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
