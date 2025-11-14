package com.example.new_tsi_moviles.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.service.CursoService;

public class VistaCurso extends AppCompatActivity {
    private CursoDTO curso;
    Context ctx;

    private LinearLayout cursoLayout;
    private CursoService cursoService ;
    private TextView nombre,descripcion,dirigido,modalidad,duracion,precio,link,inactivo;
    private Button editar,eliminar,cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.vista_curso);
        cursoLayout = findViewById(R.id.cursoLayout);
        ctx = this;
        nombre = findViewById(R.id.vista_nombre);
        descripcion = findViewById(R.id.vista_descripcion);
        dirigido = findViewById(R.id.vista_dirigidoa);
        modalidad = findViewById(R.id.vista_modalidad);
        duracion = findViewById(R.id.vista_horas);
        precio = findViewById(R.id.vista_precio);
        link=findViewById(R.id.vista_link);
        editar = findViewById(R.id.btn_editar_vista);
        eliminar = findViewById(R.id.btn_borrar_vista);
        cancelar = findViewById(R.id.btn_cancelar_vista);
        inactivo = findViewById(R.id.inactivo);
        cursoService = new CursoService(this);

        cancelar.setOnClickListener(v -> {
            finish();
        });

        actualizar();

        editar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditCurso.class);
            intent.putExtra("curso_id", curso.getId());
            this.startActivity(intent);
        });
    }
    public void actualizar(){

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

                if (!curso.getActivo()) {
                    inactivo.setText("Inactivo");
                    eliminar.setText("Activar");
                    eliminar.setOnClickListener(v -> {
                        new AlertDialog.Builder(ctx)
                                .setTitle("Confirmar activacion")
                                .setMessage("¿Está seguro que desea activar este curso?")
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    cursoService.activarCurso(new MensajeCallback() {
                                        @Override
                                        public void onSuccess(String mensaje) {
                                            Toast.makeText(ctx, "Curso activado", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Toast.makeText(ctx, "Error al activar el curso", Toast.LENGTH_SHORT).show();
                                        }
                                    },curso.getId());

                                    finish();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    });
                }else{
                    eliminar.setOnClickListener(v -> {
                        new AlertDialog.Builder(ctx)
                                .setTitle("Confirmar eliminación")
                                .setMessage("¿Está seguro que desea eliminar este curso?")
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    cursoService.deleteCurso(new MensajeCallback() {
                                        @Override
                                        public void onSuccess(String mensaje) {
                                            Toast.makeText(ctx, "Curso eliminado", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Toast.makeText(ctx, "Error al eliminar el curso", Toast.LENGTH_SHORT).show();
                                        }
                                    },curso.getId());

                                    finish();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    });
                }


            }
            @Override
            public void onError(Exception e) {
            }
        },getIntent().getLongExtra("curso_id",-1L))
        ;


    }
    protected void onResume(){

        super.onResume();
        actualizar();
    }
}