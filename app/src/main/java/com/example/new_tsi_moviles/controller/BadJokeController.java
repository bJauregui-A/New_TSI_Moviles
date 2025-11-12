package com.example.new_tsi_moviles.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.JokeCallback;
import com.example.new_tsi_moviles.conexion.JokeConexion;
import com.example.new_tsi_moviles.dto.JokeDTO;

public class BadJokeController extends AppCompatActivity {
    private JokeDTO jokeDTO;
    private TextView enJoke,esJoke,enPunchline,esPunchline;
    private Button ver;

    JokeConexion jokeConexion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bad_joke); // <- Debe existir

        JokeConexion jokeConexion = new JokeConexion(this);

        enJoke = findViewById(R.id.joke);
        esJoke = findViewById(R.id.jokeES);
        enPunchline = findViewById(R.id.punchline);
        esPunchline = findViewById(R.id.punchlineES);
        ver = findViewById(R.id.ver_remate);

        jokeConexion.getJoke(new JokeCallback() {
            @Override
            public void onSuccess(JokeDTO res) {
                jokeDTO = res;
                try {
                    enJoke.setText(jokeDTO.getSetup());
                    enPunchline.setText(jokeDTO.getPunchline());
                }catch (Exception e){
                    Toast.makeText(BadJokeController.this,
                            "No se recibio el chiste, por lo que no hay nada que mostrar",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BadJokeController.this,"Error al obtener el chiste",Toast.LENGTH_LONG).show();
            }
        });





    }
}
