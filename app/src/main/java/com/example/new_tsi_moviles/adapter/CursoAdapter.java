package com.example.new_tsi_moviles.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.content.ContextCompat;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.InscripcionCallback;
import com.example.new_tsi_moviles.controller.VistaCurso;
import com.example.new_tsi_moviles.controller.VistaCursoUser;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.InscripcionDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.InscripcionService;

import java.util.List;


public class CursoAdapter extends BaseAdapter {
    private Context context;
    private List<CursoDTO> cursos;
    private LayoutInflater inflater;
    private UserDTO user;
    private InscripcionService inscripcionService;

private Integer a;
    public CursoAdapter(Context context2, List<CursoDTO> cursos2, Integer opcion) {
        context = context2;
        cursos = cursos2;
        inflater = LayoutInflater.from(context);
        a = opcion;
    }
    public CursoAdapter(Context context2, List<CursoDTO> cursos2, Integer opcion, UserDTO user2) {
        context = context2;
        cursos = cursos2;
        inflater = LayoutInflater.from(context);
        a = opcion;
        user = user2;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cursos.get(position).getId();
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

        CursoDTO curso = cursos.get(position);

        if (!curso.getActivo()){
            holder.btnVer.setText("Inactivo");
            holder.btnVer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#520d24")));
        }else{
            holder.btnVer.setText("Ver");
            holder.btnVer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        }

        holder.nombreCurso.setText(curso.getNombre());
        holder.modalidad.setText(curso.getModalidad());
        holder.btnVer.setOnClickListener(v -> {



            if (a==0){
                Intent intent = new Intent(context, VistaCurso.class);

                intent.putExtra("curso_id", curso.getId());

                context.startActivity(intent);
            }


             else {
                Intent intent = new Intent(context, VistaCursoUser.class);

                intent.putExtra("curso_id", curso.getId());

                context.startActivity(intent);
            }


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
