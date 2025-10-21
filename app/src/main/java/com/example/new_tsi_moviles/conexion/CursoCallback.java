package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.CursoDTO;

import java.util.List;

public interface CursoCallback {
    void onSuccess(List<CursoDTO> cursos);
    void onError(Exception e);
}
