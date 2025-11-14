package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.new_tsi_moviles.conexion.*;
import com.example.new_tsi_moviles.dto.CursoDTO;
import org.json.JSONObject;

import java.util.List;

public class CursoService {

    private CursoConexion cursoConexion;
    private List<CursoDTO> cursosLocal;
    public CursoService(Context context) {
        cursoConexion = new CursoConexion(context);
    }

public void createCurso(MensajeCallback callback, JSONObject cursoJson){

        cursoConexion.createCurso(callback,cursoJson);
}

    public void updateCursos(MensajeCallback cursoCallback, JSONObject cursoJson) {
        cursoConexion.updateCurso( cursoCallback, cursoJson);
    }
    public void getCursos(CursosCallback cursoCallback, String ruta) {
        cursoConexion.getCursos( cursoCallback, ruta);
    }
    public void getCurso(CursoCallback cursoCallback, Long id) {
        cursoConexion.getCurso( cursoCallback, id);
    }
    public void deleteCurso(MensajeCallback cursoCallback, Long id) {
        cursoConexion.deleteCurso( cursoCallback, id);
    }
    public void activarCurso(MensajeCallback cursoCallback, Long id) {
        cursoConexion.activarCurso( cursoCallback, id);
    }

    public void lotiene(ConexionCallback callback,Long id, Long idu){
        cursoConexion.lotiene( callback, id, idu);
    }
}
