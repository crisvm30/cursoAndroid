package com.fireblend.uitest.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.utils.ConstantsApp;
import com.fireblend.uitest.utils.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends AppCompatActivity {

    @BindView(R.id.boton_eliminar)
    Button btnEliminar;

    @BindView(R.id.nombre_texto)
    TextView nombre;

    @BindView(R.id.edad_texto)
    TextView edad;

    @BindView(R.id.email_texto)
    TextView email;

    @BindView(R.id.telefono_texto)
    TextView telefono;

    @BindView(R.id.text_provincia)
    TextView textoProvincia;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);

        nombre.setText(getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE).replace("Nombre:",""));
        edad.setText(getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD).replace("Edad:",""));
        telefono.setText(getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO).replace("Telefono:",""));
        email.setText(getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL).replace("Email:",""));
        textoProvincia.setText(getIntent().getStringExtra(ConstantsApp.LLAVE_PROVINCIA).replace("Provincia:",""));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        boolean btnEliminarHabilitado = prefs.getBoolean("pref_btn_eliminar",true);

        btnEliminar.setEnabled(btnEliminarHabilitado);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(view.getContext());
                builderAlertDialog.setMessage("Está seguro que desea eliminar el Contacto?");
                builderAlertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
                        dbHelper.eliminarContacto(nombre.getText().toString(),Integer.parseInt(edad.getText().toString()),email.getText().toString(),telefono.getText().toString(),textoProvincia.getText().toString());


                        Intent intent = new Intent(view.getContext(),MainActivity.class);
                        view.getContext().startActivity(intent);

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
        });


    }
}
