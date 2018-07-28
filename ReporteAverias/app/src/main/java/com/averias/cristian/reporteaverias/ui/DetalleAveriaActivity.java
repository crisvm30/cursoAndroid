package com.averias.cristian.reporteaverias.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.service.AveriaServicio;
import com.averias.cristian.reporteaverias.service.GestorAveriaServicio;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAveriaActivity extends AppCompatActivity {


    @BindView(R.id.boton_editar)
    Button btnEditar;

    @BindView(R.id.nombre_texto)
    TextView nombreAveria;

    @BindView(R.id.descripcion_texto)
    TextView descripcion;

    @BindView(R.id.fecha_texto)
    TextView fecha;

    @BindView(R.id.id_texto)
    TextView id;

    @BindView(R.id.tipo_texto)
    TextView tipo;

    @BindView(R.id.cedula_texto)
    TextView cedula;

    @BindView(R.id.email_texto)
    TextView email;

    @BindView(R.id.nombrePersona_texto)
    TextView nombrePersona;


    @BindView(R.id.telefono_texto)
    TextView telefono;

    @BindView(R.id.ubicacion_texto)
    TextView ubicacion;

    @BindView(R.id.loading_icon_detalles)
    ProgressBar progressBar;

    Post postRespuesta;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_averia);
        ButterKnife.bind(this);
        obtenerDetalleAveria();

    }


    private void obtenerDetalleAveria(){
        AveriaServicio servicio  = GestorAveriaServicio.generarServicios();

        Call<Post> llamadaPost = servicio.obtenerDetalleAveria(getIntent().getStringExtra("idPost"));

        llamadaPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    postRespuesta = response.body();
                    progressBar.setVisibility(View.GONE);

                    nombreAveria.setText(postRespuesta.nombre);
                    descripcion.setText(postRespuesta.descripcion);
                    fecha.setText(postRespuesta.fecha);
                    id.setText(postRespuesta.id);
                    tipo.setText(postRespuesta.tipo);
                    cedula.setText(postRespuesta.usuario.cedula);
                    email.setText(postRespuesta.usuario.correo);
                    nombrePersona.setText(postRespuesta.usuario.nombre);
                    telefono.setText(postRespuesta.usuario.tel);
                    ubicacion.setText(""+postRespuesta.ubicacion.getLat()+":"+postRespuesta.ubicacion.getLon());

                }else{
                    Toast.makeText(DetalleAveriaActivity.this, "Hubo un error a la hora de obtener las averías!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(DetalleAveriaActivity.this, "Hubo un error a la hora de obtener las averías!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.boton_editar)
    public void irEditar(){

        Intent intent = new Intent(this,EditarAveriaActivity.class);

            intent.putExtra("nombrePost",nombreAveria.getText());
            intent.putExtra("descripcion",descripcion.getText());
            intent.putExtra("fecha",fecha.getText());
            intent.putExtra("id",id.getText());
            //intent.putExtra("imagen",imagen.getText());
            intent.putExtra("tipo",tipo.getText());
            intent.putExtra("cedula",cedula.getText());
            intent.putExtra("correo",email.getText());
            intent.putExtra("nombrePersona",nombrePersona.getText());
            intent.putExtra("telefono",telefono.getText());
            intent.putExtra("lat",ubicacion.getText().toString().split(":")[0]);
            intent.putExtra("lon",ubicacion.getText().toString().split(":")[1]);
            startActivity(intent);
    }

    @OnClick(R.id.boton_cancelar)
    public void btnCancelar(){
        Intent intent = new Intent(this,AveriasActivity.class);
        startActivity(intent);
    }
}
