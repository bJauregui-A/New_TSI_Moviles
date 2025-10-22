package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.CursoDTO;

import java.util.List;

public interface MensajeCallback {
    void onSuccess(String mensaje);
    void onError(Exception e);
}
