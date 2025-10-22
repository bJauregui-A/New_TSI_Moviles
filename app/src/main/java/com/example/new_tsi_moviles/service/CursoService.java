package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.conexion.CursosCallback;
import com.example.new_tsi_moviles.conexion.CursoConexion;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
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

    public void updateCursos(CursoCallback cursoCallback, JSONObject cursoJson) {
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


}
