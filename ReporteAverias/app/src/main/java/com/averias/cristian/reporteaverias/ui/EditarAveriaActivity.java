package com.averias.cristian.reporteaverias.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.entities.Ubicacion;
import com.averias.cristian.reporteaverias.entities.UsuarioPost;
import com.averias.cristian.reporteaverias.service.AveriaServicio;
import com.averias.cristian.reporteaverias.service.GestorAveriaServicio;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarAveriaActivity extends AppCompatActivity {


    @BindView(R.id.nombre_editar)
    EditText nombreAveria;

    @BindView(R.id.descripcion_editar)
    EditText descripcion;

    @BindView(R.id.fecha_editar)
    EditText fecha;

    @BindView(R.id.id_editar)
    TextView id;

    @BindView(R.id.tipo_editar)
    EditText tipo;

    @BindView(R.id.cedula_editar)
    EditText cedula;

    @BindView(R.id.email_editar)
    EditText email;

    @BindView(R.id.nombrePersona_editar)
    EditText nombrePersona;


    @BindView(R.id.telefono_editar)
    EditText telefono;

    @BindView(R.id.ubicacion_lat_editar)
    EditText lat;

    @BindView(R.id.ubicacion_lon_editar)
    EditText lon;

    Post postRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_averia);

        ButterKnife.bind(this);

        nombreAveria.setText(getIntent().getStringExtra("nombrePost"));
        descripcion.setText(getIntent().getStringExtra("descripcion"));
        fecha.setText(getIntent().getStringExtra("fecha"));
        id.setText(getIntent().getStringExtra("id"));
        tipo.setText(getIntent().getStringExtra("tipo"));
        cedula.setText(getIntent().getStringExtra("cedula"));
        email.setText(getIntent().getStringExtra("correo"));
        nombrePersona.setText(getIntent().getStringExtra("nombrePersona"));
        telefono.setText(getIntent().getStringExtra("telefono"));
        lat.setText(""+getIntent().getStringExtra("lat"));
        lon.setText(""+getIntent().getStringExtra("lon"));
    }

    @OnClick(R.id.boton_guardar)
    public void guardarAveria(){
        AveriaServicio servicio  = GestorAveriaServicio.generarServicios();
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setMessage("Espere un momento");
        final AlertDialog alertDialog = builderAlertDialog.create();
        alertDialog.show();

        Call<Post> llamadaPost = servicio.editarAveria(id.getText().toString(),new Post(id.getText().toString(),tipo.getText().toString(),new UsuarioPost(nombrePersona.getText().toString(),email.getText().toString(),telefono.getText().toString(),cedula.getText().toString()),"",new Ubicacion(lat.getText().toString(),lon.getText().toString()),descripcion.getText().toString(),nombreAveria.getText().toString(),fecha.getText().toString()));

        final Context context = this;
        llamadaPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    postRespuesta = response.body();

                    alertDialog.hide();
                    AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
                    builderAlertDialog.setMessage("Avería editada con éxito!");
                    builderAlertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(context,DetalleAveriaActivity.class);
                            intent.putExtra("idPost",id.getText().toString());

                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog1 = builderAlertDialog.create();
                    alertDialog1.show();

                }else{
                    Toast.makeText(EditarAveriaActivity.this, "Hubo un error a la hora de editar la avería!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(EditarAveriaActivity.this, "Hubo un error a la hora de editar la avería!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.boton_eliminar_averia)
    public void eliminarAveria(){
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setMessage("Está seguro que desea eliminar la avería?");
        builderAlertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                irAEliminar();
            }
        });
        builderAlertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builderAlertDialog.create();
        alertDialog.show();

    }

    private void irAEliminar(){
        AveriaServicio servicio  = GestorAveriaServicio.generarServicios();
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setMessage("Espere un momento");
        final AlertDialog alertDialog = builderAlertDialog.create();
        alertDialog.show();

        Call<Post> llamadaPost = servicio.eliminarAveria(id.getText().toString());

        final Context context = this;
        llamadaPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){

                    alertDialog.hide();
                    AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
                    builderAlertDialog.setMessage("Avería eliminada con éxito!");
                    builderAlertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(context,AveriasActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog1 = builderAlertDialog.create();
                    alertDialog1.show();

                }else{
                    Toast.makeText(EditarAveriaActivity.this, "Hubo un error a la hora de eliminar la avería!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(EditarAveriaActivity.this, "Hubo un error a la hora de eliminar la avería!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
