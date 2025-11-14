package com.example.new_tsi_moviles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.dto.CursoDTO;

import java.util.ArrayList;
import java.util.List;

public class CursoBusquedaAdapter extends RecyclerView.Adapter<CursoBusquedaAdapter.ViewHolder> {

    private List<CursoDTO> listaOriginal;
    private List<CursoDTO> listaFiltrada;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CursoDTO curso);
    }

    public CursoBusquedaAdapter(List<CursoDTO> cursos, OnItemClickListener listener) {
        this.listaOriginal = cursos;
        this.listaFiltrada = new ArrayList<>(cursos);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_curso_busqueda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CursoDTO curso = listaFiltrada.get(position);

        holder.txtNombre.setText(curso.getNombre());
        holder.txtDescripcion.setText(curso.getDescripcion());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(curso));
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
            for (CursoDTO c : listaOriginal) {
                if (c.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    listaFiltrada.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtCursoNombre);
            txtDescripcion = itemView.findViewById(R.id.txtCursoDescripcion);
        }
    }
}
