package com.fireblend.uitest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fireblend.uitest.R;
import com.fireblend.uitest.utils.ConstantsApp;


import butterknife.BindView;
import butterknife.ButterKnife;


public class AddContactActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.boton_agregar)
    Button btnAgregar;

    @BindView(R.id.nombre_texto)
    EditText nombre;

    @BindView(R.id.edad_texto)
    EditText edad;

    @BindView(R.id.email_texto)
    EditText email;

    @BindView(R.id.telefono_texto)
    EditText telefono;

    @BindView(R.id.spinner_provincia)
    Spinner spinnerProvincias;

    final String [] provincias = {"-","Alajuela","Cartago","Heredia","Puntarenas","Limon","San Jose", "Guanacaste"};

    private String provinciaSeleccionada ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        btnAgregar.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,provincias);
        spinnerProvincias.setAdapter(adapter);

        spinnerProvincias.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {

        String textoNombre = nombre.getText().toString();
        String textoEdad = edad.getText().toString();
        String textoEmail = email.getText().toString();
        String textoTelefono = telefono.getText().toString();

        if(!textoNombre.isEmpty() && !textoEdad.isEmpty() && !textoEmail.isEmpty() && !textoTelefono.isEmpty() && !provinciaSeleccionada.isEmpty() && !provinciaSeleccionada.equals("-")){

            Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
            intent.putExtra(ConstantsApp.LLAVE_NOMBRE,textoNombre);
            intent.putExtra(ConstantsApp.LLAVE_EDAD,textoEdad);
            intent.putExtra(ConstantsApp.LLAVE_EMAIL,textoEmail);
            intent.putExtra(ConstantsApp.LLAVE_TELEFONO,textoTelefono);
            intent.putExtra(ConstantsApp.LLAVE_PROVINCIA,provinciaSeleccionada);

            startActivity(intent);
        }else{
            Toast.makeText(AddContactActivity.this,"Debe llenar todos los espacios!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        provinciaSeleccionada = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
