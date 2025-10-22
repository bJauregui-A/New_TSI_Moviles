package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.CursoConexion;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.conexion.InscripcionConexion;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import org.json.JSONObject;

public class InscripcionService {
    private InscripcionConexion inscripcionConexion;

    public InscripcionService(Context context) {
        inscripcionConexion = new InscripcionConexion(context);
    }


    public void comprar(MensajeCallback callback, JSONObject cursoJson) {
        inscripcionConexion.crearInscripcion(callback, cursoJson);
    }

    public void getCursosByUser(CursosCallback callback, Long id){
        inscripcionConexion.getCursosByUser(callback, id);
    }

}
