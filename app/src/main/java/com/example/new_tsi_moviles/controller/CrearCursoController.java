package com.example.new_tsi_moviles.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;
import org.json.JSONException;
import org.json.JSONObject;

public class CrearCursoController extends AppCompatActivity {

    private CursoDTO curso;

    private CursoService cursoService ;
    private Button cancelar, guardar;
    private EditText nombreS, descripcion, dirigidoa, modalidad, duracion, precio, link;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_curso);

        cursoService= new CursoService(this);

        curso= new CursoDTO();
        cancelar = findViewById(R.id.btn_cancelar_agregar);
        guardar= findViewById(R.id.btn_guardar);
        nombreS= findViewById(R.id.nombreCurso);
        descripcion= findViewById(R.id.descripcion);
        dirigidoa= findViewById(R.id.dirigidoA);
        modalidad= findViewById(R.id.Modalidad);
        duracion= findViewById(R.id.horas);
        precio= findViewById(R.id.precio);
        link= findViewById(R.id.link);

        guardar.setOnClickListener(v->{

            if (descripcion.getText().toString().equals("")){
                Toast.makeText(this, "Descripcion Vacía", Toast.LENGTH_SHORT).show();
                return;
            }
            if (duracion.getText().toString().equals("")){
                Toast.makeText(this, "Duracion Vacía", Toast.LENGTH_SHORT).show();
                return;
            }
            if (precio.getText().toString().equals("")){
                Toast.makeText(this, "Precio sin valor", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nombreS.getText().toString().equals("")){
                Toast.makeText(this, "Nombre Vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dirigidoa.getText().toString().equals("")){
                Toast.makeText(this, " Sin dirigidos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (link.getText().toString().equals("")){
                Toast.makeText(this, "Sin link", Toast.LENGTH_SHORT).show();
                return;
            }
            if (modalidad.getText().toString().equals("")){
                Toast.makeText(this, "Sin modalidad", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject cursoJson = new JSONObject();
            try {
                cursoJson.put("nombre", nombreS.getText().toString());
                cursoJson.put("descripcion", descripcion.getText().toString());
                cursoJson.put("precio", Integer.parseInt(precio.getText().toString()));
                cursoJson.put("horas", Integer.parseInt(duracion.getText().toString()));
                cursoJson.put("dirigidoa", dirigidoa.getText().toString());
                cursoJson.put("modalidad", modalidad.getText().toString());
                cursoJson.put("linkPago", link.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al crear JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            cursoService.createCurso(new MensajeCallback() {
                @Override
                public void onSuccess(String mensaje) {
                    Toast.makeText(CrearCursoController.this, mensaje, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(CrearCursoController.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            },cursoJson);
        });

    }

}
