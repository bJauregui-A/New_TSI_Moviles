package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.example.new_tsi_moviles.adapter.CursoAdapter;
import com.example.new_tsi_moviles.conexion.*;
import org.json.JSONException;
import org.json.JSONObject;

public class InscripcionService {
    private InscripcionConexion inscripcionConexion;


    public InscripcionService(Context context) {
        inscripcionConexion = new InscripcionConexion(context);
    }

public void getByUser(InscripcionesCallback inscripcionCallback, Long id){
        inscripcionConexion.getInscripcionByUser(inscripcionCallback, id);
}


    public void comprar(MensajeCallback callback, JSONObject cursoJson) throws JSONException {
        inscripcionConexion.crearInscripcion(callback, cursoJson);
    }

    public void getCursosByUser(CursosCallback callback, Long id){
        inscripcionConexion.getCursosByUser(callback, id);
    }

    public void getAll(InscripcionesCallback callback){
        inscripcionConexion.getAll(callback);
    }

}
