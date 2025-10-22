package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.InscripcionDTO;

import java.util.List;

public interface InscripcionesCallback {
    void onSuccess(List<InscripcionDTO> inscripciones);
    void onError(Exception e);
}
