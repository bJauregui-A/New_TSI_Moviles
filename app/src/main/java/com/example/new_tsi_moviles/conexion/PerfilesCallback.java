package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.List;

public interface PerfilesCallback {
    void onSuccess(List<UserDTO> listaUsuarios);

    void onError(Exception e);
}
