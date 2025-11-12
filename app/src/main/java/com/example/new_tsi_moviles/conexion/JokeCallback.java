package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.JokeDTO;

public interface JokeCallback {
    void onSuccess(JokeDTO res);
    void onError(Exception e);
}
