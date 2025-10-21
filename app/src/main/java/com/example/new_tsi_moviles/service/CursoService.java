package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.conexion.CursoConexion;
import com.example.new_tsi_moviles.dto.CursoDTO;

import java.util.ArrayList;
import java.util.List;

public class CursoService {

    private CursoConexion cursoConexion;
private List<CursoDTO> cursosLocal;
    public CursoService(Context context) {
        cursoConexion = new CursoConexion(context);
    }

    public void getCursos(CursoCallback cursoCallback) {
        cursoConexion.getCursos( cursoCallback);

    }

}
