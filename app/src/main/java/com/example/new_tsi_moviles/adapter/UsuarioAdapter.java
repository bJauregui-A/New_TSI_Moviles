package com.example.new_tsi_moviles.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.controller.EditUserController;
import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<UserDTO> {

    private Context context;
    private List<UserDTO> usuarios;

    public UsuarioAdapter(@NonNull Context context, @NonNull List<UserDTO> usuarios) {
        super(context, 0, usuarios);
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_usuario_busueda, parent, false);
        }

        UserDTO usuario = usuarios.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtUsuarioNombre);
        TextView txtApellido = convertView.findViewById(R.id.txtUsuarioApellido);
        TextView txtEmail = convertView.findViewById(R.id.txtUsuarioEmail);
        Button btnVer = convertView.findViewById(R.id.btn_ver); // Asegúrate de poner id="btnVer" en XML

        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtEmail.setText(usuario.getEmail());
        if(usuario.getActivo()){
            btnVer.setText("Ver");
        }else{
            btnVer.setText("Inactivo");
        }

        // ================= Botón "Ver" =================
        btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditUserController.class);
            intent.putExtra("usuarioId", usuario.getId()); // Pasamos el ID del usuario
            context.startActivity(intent);
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Nullable
    @Override
    public UserDTO getItem(int position) {
        return usuarios.get(position);
    }
}
