package com.fireblend.uitest.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.fireblend.uitest.utils.DetailEditFragment;
import com.fireblend.uitest.utils.DetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends AppCompatActivity implements  DetailFragment.DetailListener,DetailEditFragment.DetailEditListener{

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);



        DetailFragment fragmentDetails = new DetailFragment();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean btnEliminarHabilitado = prefs.getBoolean("pref_btn_eliminar",true);


        Bundle bundle = new Bundle();
        bundle.putString("nombre", getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE).replace("Nombre:",""));
        bundle.putString("edad", getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD).replace("Edad:",""));
        bundle.putString("telefono", getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO).replace("Telefono:",""));
        bundle.putString("email", getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL).replace("Email:",""));
        bundle.putString("provincia", getIntent().getStringExtra(ConstantsApp.LLAVE_PROVINCIA).replace("Provincia:",""));

        bundle.putBoolean("btnEliminar",btnEliminarHabilitado);
        fragmentDetails.setArguments(bundle);

        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenedorFragment,fragmentDetails);
        ft.commit();


    }

    @Override
    public void onEliminar(String nombre,int edad,String email,String telefono,String provincia) {


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.eliminarContacto(nombre,edad,email,telefono,provincia);


        Intent intent = new Intent(ContactDetailActivity.this,MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void goToEditar() {
        DetailEditFragment fragmentDetails = new DetailEditFragment();

        Bundle bundle = new Bundle();
        bundle.putString("nombre", getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE).replace("Nombre:",""));
        bundle.putString("edad", getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD).replace("Edad:",""));
        bundle.putString("telefono", getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO).replace("Telefono:",""));
        bundle.putString("email", getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL).replace("Email:",""));
        bundle.putString("provincia", getIntent().getStringExtra(ConstantsApp.LLAVE_PROVINCIA).replace("Provincia:",""));

        fragmentDetails.setArguments(bundle);

        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenedorFragment,fragmentDetails);
        ft.commit();
    }

    @Override
    public void onEditar(String oldName, String textoNombre,String textoEdad,String textoEmail,String textoTelefono,String provinciaSeleccionada) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.editarContacto(oldName,textoNombre,Integer.parseInt(textoEdad), textoEmail,textoTelefono, provinciaSeleccionada);
    }
}
