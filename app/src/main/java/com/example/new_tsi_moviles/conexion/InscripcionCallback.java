package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.InscripcionDTO;

import java.util.List;

public interface InscripcionCallback {
    void onSuccess(InscripcionDTO inscripcion);
    void onError(Exception e);
}
