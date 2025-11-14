package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.UserDTO;

import java.util.List;

public interface UsersCallback {
    void onSuccess(List<UserDTO> users);
    void onError(Exception e);
}