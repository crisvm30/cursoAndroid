package com.averias.cristian.reporteaverias.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.db.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroActivity extends AppCompatActivity {


    @BindView(R.id.nombre_texto)
    EditText nombre;

    @BindView(R.id.usuario_texto)
    EditText usuario;

    @BindView(R.id.contrasena_texto)
    EditText contrasena;

    @BindView(R.id.correo_texto)
    EditText correo;

    @BindView(R.id.telefono_texto)
    EditText telefono;

    @BindView(R.id.cedula_texto)
    EditText cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.boton_agregar)
    public void btnAgregar(){

        if(nombre.getText().toString().equals("") || usuario.getText().toString().equals("") || contrasena.getText().toString().equals("")){
            Toast.makeText(this,"Debe llenar todos los campos!",Toast.LENGTH_SHORT).show();
        }else{
            DatabaseHelper dbh = DatabaseHelper.getInstance(this);

            int resultado = dbh.agregarUsuario(nombre.getText().toString(),usuario.getText().toString(),contrasena.getText().toString(),correo.getText().toString(),telefono.getText().toString(),cedula.getText().toString());

            if(resultado == 0){
                Toast.makeText(this,"El nombre de usuario ya existe!",Toast.LENGTH_SHORT).show();
            }else{
                //TODO: Redirigir a pagina principal
                Intent intent = new Intent(this,AveriasActivity.class);
                startActivity(intent);
            }
        }

    }

    @OnClick(R.id.boton_cancelar)
    public void btnCancelar(View view){
        Activity act = (Activity) view.getContext();
        act.finish();
    }
}
