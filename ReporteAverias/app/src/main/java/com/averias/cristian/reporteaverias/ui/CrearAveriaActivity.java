package com.averias.cristian.reporteaverias.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.entities.Ubicacion;
import com.averias.cristian.reporteaverias.entities.UsuarioPost;
import com.averias.cristian.reporteaverias.service.AveriaServicio;
import com.averias.cristian.reporteaverias.service.GestorAveriaServicio;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearAveriaActivity extends AppCompatActivity {

    @BindView(R.id.boton_crear)
    Button btnEditar;

    @BindView(R.id.nombre_crear)
    EditText nombreAveria;

    @BindView(R.id.descripcion_crear)
    EditText descripcion;

    @BindView(R.id.fecha_crear)
    EditText fecha;

    @BindView(R.id.id_crear)
    EditText id;

    @BindView(R.id.tipo_crear)
    EditText tipo;

    @BindView(R.id.cedula_crear)
    EditText cedula;

    @BindView(R.id.email_crear)
    EditText email;

    @BindView(R.id.nombrePersona_crear)
    EditText nombrePersona;


    @BindView(R.id.telefono_crear)
    EditText telefono;

    @BindView(R.id.ubicacion_lat)
    EditText lat;

    @BindView(R.id.ubicacion_lon)
    EditText lon;

    @BindView(R.id.imagen_lugar)
    ImageView imageView;


    Post postRespuesta;

    private Uri mUri;

    private static final int CODIGO_PERMISO = 200;
    private static final int TAKE_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_averia);
        ButterKnife.bind(this);


        if(getIntent().getStringExtra("lat") != null && getIntent().getStringExtra("lon") != null){
            lat.setText(getIntent().getStringExtra("lat"));
            lon.setText(getIntent().getStringExtra("lon"));
        }
    }

    private void verificarPermisos(){

        int revisarPermiso = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(revisarPermiso == PackageManager.PERMISSION_GRANTED){
            continuar();
        }else{
            pedirPermiso();
        }
    }

    private void pedirPermiso(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_PERMISO);

    }

    private void continuar(){

        File archivo = crearArchivo();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mUri = FileProvider.getUriForFile(this,"com.averias.cristian.reporteaverias.ui",archivo);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);

        startActivityForResult(takePictureIntent, TAKE_PHOTO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            verificarPermisos();
        } else {
            finish();
        }
    }

    @OnClick(R.id.boton_crear)
    public void crearAveria(){

        AveriaServicio servicio  = GestorAveriaServicio.generarServicios();
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setMessage("Espere un momento");
        final AlertDialog alertDialog = builderAlertDialog.create();
        alertDialog.show();

        Call<Post> llamadaPost = servicio.crearAveria(new Post(id.getText().toString(),tipo.getText().toString(),new UsuarioPost(nombrePersona.getText().toString(),email.getText().toString(),telefono.getText().toString(),cedula.getText().toString()),"",new Ubicacion(lat.getText().toString(),lon.getText().toString()),descripcion.getText().toString(),nombreAveria.getText().toString(),fecha.getText().toString()));

        final Context context = this;
        llamadaPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    postRespuesta = response.body();

                    alertDialog.hide();
                    AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
                    builderAlertDialog.setMessage("Avería creada con éxito!");
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
                    Toast.makeText(CrearAveriaActivity.this, "Hubo un error a la hora de crear la avería!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(CrearAveriaActivity.this, "Hubo un error a la hora de crear la avería!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.imagen_lugar)
    public void tomarFoto(){
        verificarPermisos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {

            try {

               /* Uri contentURI = Uri.parse(data.getDataString());
                ContentResolver cr = getContentResolver();
                InputStream in = cr.openInputStream(mUri);
                Bitmap pic = BitmapFactory.decodeStream(in,null,null);*/

               // Uri imageUri = data.getData();
               // imageView.setImageURI(mUri);
                Bitmap imageBitmap =
                        MediaStore.Images.Media.
                                getBitmap(getContentResolver(), mUri);


                imageView.setImageBitmap(imageBitmap);
            }catch(Exception e){
               e.printStackTrace();
            }

        }
    }


    private File crearArchivo() {
        try {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String imageFileName = "JPEG_" + timeStamp;

            File storageDir =
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );

            return image;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
