package com.example.new_tsi_moviles.service;

import android.content.Context;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.conexion.PerfilConexion;

public class PerfilService {

    PerfilConexion perfilConexion;
    public PerfilService(Context context) {
        perfilConexion = new PerfilConexion(context);
    }

    public void getPerfil(PerfilCallback perfilCallback){
         perfilConexion.getPerfil(perfilCallback);
    }

}
