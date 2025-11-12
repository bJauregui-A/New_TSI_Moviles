package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.CursoDTO;

public interface ConexionCallback {
    void onSuccess(Boolean res);
    void onError(Exception e);
}
