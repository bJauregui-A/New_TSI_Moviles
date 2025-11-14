package com.example.new_tsi_moviles.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.InscripcionCallback;
import com.example.new_tsi_moviles.controller.VistaCurso;
import com.example.new_tsi_moviles.controller.VistaCursoUser;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.model.CursoUserState;
import com.example.new_tsi_moviles.service.InscripcionService;

import java.util.List;

public class InscripcionesAdapter extends BaseAdapter {
    private Context context;
    private List<InscripcionDTO> inscripciones;
    private LayoutInflater inflater;
    private UserDTO user;

    private Integer a;

    public InscripcionesAdapter(Context context2, List<InscripcionDTO> cursos2,UserDTO user2) {
        context = context2;
        inscripciones = cursos2;
        inflater = LayoutInflater.from(context);
        user = user2;
    }

    @Override
    public int getCount() {
        return inscripciones.size();
    }

    @Override
    public Object getItem(int position) {
        return inscripciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return inscripciones.get(position).getIdCurso();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragmento_curso, parent, false);
            holder = new ViewHolder();
            holder.imagenCurso = convertView.findViewById(R.id.iimagenCurso);
            holder.nombreCurso = convertView.findViewById(R.id.nombreFrag);
            holder.modalidad = convertView.findViewById(R.id.modalidadFrag);
            holder.precio = convertView.findViewById(R.id.precioFrag);
            holder.btnVer = convertView.findViewById(R.id.btn_ver);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InscripcionDTO curso = inscripciones.get(position);

        holder.btnVer.setVisibility(View.INVISIBLE);
        holder.btnVer.setText("Ver");
        holder.btnVer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));


        holder.nombreCurso.setText(curso.getNombreCurso());
        holder.modalidad.setText(curso.getEmailUser());
        holder.precio.setText(curso.getEstado().toString());
        holder.btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, VistaCursoUser.class);
            intent.putExtra("curso_id", curso.getIdCurso());
            context.startActivity(intent);
        });

        holder.imagenCurso.setImageResource(R.drawable.logo);

        return convertView;
    }




    static class ViewHolder {

        ImageButton ekis;
        TextView inactivo;
        ImageView imagenCurso;
        TextView nombreCurso;
        TextView modalidad;
        TextView precio;
        Button btnVer;


    }


}
