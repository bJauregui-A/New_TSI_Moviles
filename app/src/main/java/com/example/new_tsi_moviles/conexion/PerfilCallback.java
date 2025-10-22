package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.UserDTO;

public interface PerfilCallback {
    void onSuccess(UserDTO userDTO);
    void onError(Exception e);
}
