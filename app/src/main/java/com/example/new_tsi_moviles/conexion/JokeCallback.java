package com.example.new_tsi_moviles.conexion;

import com.example.new_tsi_moviles.dto.JokeDTO;
import org.json.JSONException;

public interface JokeCallback {
    void onSuccess(JokeDTO res) throws JSONException;
    void onError(Exception e);
}
