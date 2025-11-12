package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.example.new_tsi_moviles.conexion.ConexionCallback;
import com.example.new_tsi_moviles.conexion.ConexionConect;
import kotlinx.coroutines.internal.ContextScope;

public class ConexionService {

    private ConexionConect conexionConect;
    private ConexionService(Context context) {
        this.conexionConect = new ConexionConect(context);
    }

    public void test(ConexionCallback  callback) {
        conexionConect.test(callback);
    }

}
