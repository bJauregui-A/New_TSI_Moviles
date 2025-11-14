package com.example.new_tsi_moviles.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBusquedaAdapter extends RecyclerView.Adapter<UsuarioBusquedaAdapter.ViewHolder> {

    private List<UserDTO> listaOriginal;
    private List<UserDTO> listaFiltrada;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(UserDTO user);
    }

    public UsuarioBusquedaAdapter(List<UserDTO> usuarios, OnItemClickListener listener) {
        this.listaOriginal = usuarios;
        this.listaFiltrada = new ArrayList<>(usuarios);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario_busueda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO user = listaFiltrada.get(position);

        holder.txtNombre.setText(user.getNombre());
        holder.txtEmail.setText(user.getEmail());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(user));
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public void filtrar(String texto) {
        listaFiltrada.clear();

        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            for (UserDTO u : listaOriginal) {
                if (u.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    listaFiltrada.add(u);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtUsuarioNombre);
            txtEmail = itemView.findViewById(R.id.txtUsuarioEmail);
        }
    }
}
