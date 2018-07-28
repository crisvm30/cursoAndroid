package com.averias.cristian.reporteaverias.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.db.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.usuario_texto)
    EditText usuario;

    @BindView(R.id.contrasena_texto)
    EditText contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_ingresar)
    public void ingresar(){
        if(usuario.getText().toString().equals("") || contrasena.getText().toString().equals("")){
            Toast.makeText(this,"Debe llenar el usuario y la contraseña!",Toast.LENGTH_SHORT).show();
        }else{

            DatabaseHelper dbh = DatabaseHelper.getInstance(this);

            int resultado = dbh.consultarUsuario(usuario.getText().toString(),contrasena.getText().toString());

            if(resultado == 0){
                Toast.makeText(this,"El usuario y/o la contraseña está(n) incorrecta(o)(s)!",Toast.LENGTH_SHORT).show();
            }else{
                //TODO pasar a la pagina principal

                Intent intent = new Intent(this,AveriasActivity.class);
                startActivity(intent);


            }
        }

    }

    @OnClick(R.id.btn_registro)
    public void registrarse(){

        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }


}
