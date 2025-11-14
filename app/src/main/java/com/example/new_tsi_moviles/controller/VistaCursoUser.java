package com.example.new_tsi_moviles.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.new_tsi_moviles.R;
import com.example.new_tsi_moviles.conexion.ConexionCallback;
import com.example.new_tsi_moviles.conexion.CursoCallback;
import com.example.new_tsi_moviles.conexion.MensajeCallback;
import com.example.new_tsi_moviles.conexion.PerfilCallback;
import com.example.new_tsi_moviles.dto.CursoDTO;
import com.example.new_tsi_moviles.dto.UserDTO;
import com.example.new_tsi_moviles.service.CursoService;
import com.example.new_tsi_moviles.service.InscripcionService;
import com.example.new_tsi_moviles.service.PerfilService;
import org.json.JSONException;
import org.json.JSONObject;

public class VistaCursoUser extends AppCompatActivity {
    private CursoDTO curso;
    Context ctx;

    private LinearLayout cursoLayout;
    private CursoService cursoService ;

    private UserDTO userDTO;
    private InscripcionService inscripcionService;
    private PerfilService perfilService;

    private TextView nombre,descripcion,dirigido,modalidad,duracion,precio,link,inactivo;
    private Button comprar,cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.vista_curso_user);


        ctx = this;
        nombre = findViewById(R.id.vista_nombre);
        descripcion = findViewById(R.id.vista_descripcion);
        dirigido = findViewById(R.id.vista_dirigidoa);
        modalidad = findViewById(R.id.vista_modalidad);
        duracion = findViewById(R.id.vista_horas);
        precio = findViewById(R.id.vista_precio);
        link=findViewById(R.id.vista_link);
        comprar = findViewById(R.id.btn_comprar_vista);
        cancelar = findViewById(R.id.btn_cancelar_vista);
        inactivo = findViewById(R.id.inactivo);
        comprar.setVisibility(View.GONE);
        perfilService = new PerfilService(this);
        inscripcionService = new InscripcionService(this);
        JSONObject compra = new JSONObject();
         cursoService = new CursoService(this);

        cancelar.setOnClickListener(v -> {
            finish();
        });

        userDTO = new UserDTO();

        perfilService.getPerfil(new PerfilCallback() {

            @Override
            public void onSuccess(UserDTO userDTO2) {
             userDTO=userDTO2;
             actualizar();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(VistaCursoUser.this,"No se cargó el usuario",Toast.LENGTH_LONG).show();
            }
        });



        comprar.setOnClickListener(v -> {
            try {

                compra.put("idUser",userDTO.getId());
                compra.put("idCurso",curso.getId());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar compra")
                    .setMessage("¿Está seguro que desea comprar este curso?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        inscripcionService.comprar(new MensajeCallback() {
                            @Override
                            public void onSuccess(String mensaje) {
                                Toast.makeText(ctx, "Curso Comprado", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        },compra);

                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
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
                }

                cursoService.lotiene(new ConexionCallback() {
                    @Override
                    public void onSuccess(Boolean res) {

                        if(res){

                        }else{
                            comprar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                },curso.getId(),userDTO.getId());

            }
            @Override
            public void onError(Exception e) {
            }
        },getIntent().getLongExtra("curso_id",-1L))
        ;


    }

}