package com.example.new_tsi_moviles.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;
import org.json.JSONException;
import org.json.JSONObject;

public class EditCurso extends AppCompatActivity {

    private CursoDTO curso;

    private CursoService cursoService;
    private Switch stch;
    private EditText nombre,descripcion,dirigido,modalidad,duracion,precio,link;
    private Button actualizar,cancelar;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_curso);

cursoService= new CursoService(this);
        nombre = findViewById(R.id.edit_nombre);
        descripcion = findViewById(R.id.edit_descripcion);
        dirigido = findViewById(R.id.edit_dirigidoa);
        modalidad = findViewById(R.id.edit_modalidad);
        duracion = findViewById(R.id.edit_horas);
        precio = findViewById(R.id.edit_precio);
        link = findViewById(R.id.edit_link);
        stch = findViewById(R.id.stch_activo);
        cargar();


        actualizar = findViewById(R.id.btn_actualizar_edit);
        cancelar = findViewById(R.id.btn_cancelar_edit);

        actualizar.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Confirmar actualizacion")
                    .setMessage("¿Está seguro que desea actualizar este curso?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        actualizare();
                        JSONObject json = new JSONObject();
                        try {
                            json.put("id",getIntent().getLongExtra("curso_id",-1L));
                            json.put("precio", Integer.parseInt(precio.getText().toString()));
                            json.put("nombre", nombre.getText().toString());
                            json.put("descripcion", descripcion.getText().toString());
                            json.put("horas", Integer.parseInt(duracion.getText().toString()));
                            json.put("dirigidoa", dirigido.getText().toString());
                            json.put("modalidad", modalidad.getText().toString());
                            json.put("linkPago", link.getText().toString());
                            json.put("activo", stch.isChecked());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        cursoService.updateCursos(new MensajeCallback() {
                            @Override
                            public void onSuccess(String msg) {
                                Toast.makeText(EditCurso.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(EditCurso.this, "Curso no existe", Toast.LENGTH_SHORT).show();
                            }
                        }, json);


                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }
    public void actualizare(){

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
        if (nombre.getText().toString().equals("")){
            Toast.makeText(this, "Nombre Vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dirigido.getText().toString().equals("")){
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
        curso.setId(getIntent().getLongExtra("curso_id",-1L));
        curso.setDescripcion(descripcion.getText().toString());
        curso.setHoras(Integer.parseInt( duracion.getText().toString()));
        curso.setPrecio(Integer.parseInt(precio.getText().toString()));
        curso.setNombre(nombre.getText().toString());
        curso.setDirigidoa(dirigido.getText().toString());
        curso.setLinkPago(link.getText().toString());
        curso.setModalidad(modalidad.getText().toString());
        curso.setActivo(stch.isChecked());

    }

    private void cargar(){
        cursoService.getCurso(new CursoCallback() {
                                  @Override
                                  public void onSuccess(CursoDTO curso2) {
                                      curso = curso2;

                                      nombre.setText(curso.getNombre());
                                      descripcion.setText(curso.getDescripcion());
                                      dirigido.setText(curso.getDirigidoa());
                                      modalidad.setText(curso.getModalidad());
                                      duracion.setText(String.valueOf(curso.getHoras()) );
                                      precio.setText( String.valueOf(curso.getPrecio()));
                                      link.setText(String.valueOf(curso.getLinkPago()));
                                      if (curso.getActivo()){
                                          stch.setChecked(true);
                                      }else {
                                          stch.setChecked(false);
                                      }

                                  }

                                  @Override
                                  public void onError(Exception e) {
                                      Toast.makeText(EditCurso.this, "Error al encontrar curso", Toast.LENGTH_SHORT).show();
                                  }

                              }
                ,getIntent().getLongExtra("curso_id",-1L));
    }

}
