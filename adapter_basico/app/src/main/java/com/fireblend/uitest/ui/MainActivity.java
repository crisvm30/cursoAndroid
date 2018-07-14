package com.fireblend.uitest.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.fireblend.uitest.R;
import com.fireblend.uitest.adapters.MyAdapter;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.utils.ConstantsApp;
import com.fireblend.uitest.utils.DatabaseHelper;
import com.fireblend.uitest.utils.MyPreferencesActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {

    RecyclerView list;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myLayoutManager;

    @BindView(R.id.btn_agregar_contacto)
    Button agregarContacto;

    @BindView(R.id.btn_prefs)
    Button btnPrefs;

    List<Contact> contacts;

    DatabaseHelper dbHelper = null;
    Dao<Contact,Integer> contactDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupList();
        agregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddContactActivity.class);

                startActivity(intent);
            }
        });

        btnPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyPreferencesActivity.class);
                startActivity(intent);
            }
        });




    }

    private void setupList() {

        try {

            contactDao = dbHelper.getContactDao();

            if(getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL) != null){
                contactDao.create(new Contact(getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE), Integer.parseInt(getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD)), getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL), getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO),getIntent().getStringExtra(ConstantsApp.LLAVE_PROVINCIA)));

            }


            if(contactDao.queryForAll().isEmpty()){

               contactDao.create(new Contact("Sergio", 28, "sergiome@gmail.com", "88854764","Alajuela"));
                contactDao.create(new Contact("Andres", 1, "alex@gmail.com", "88883644","San Jose"));
                contactDao.create(new Contact("Andrea", 2, "andrea@gmail.com", "98714764","Heredia"));
                contactDao.create(new Contact("Fabian", 3, "fabian@gmail.com", "12345678","Cartago"));
                contactDao.create(new Contact("Ivan", 4, "ivan@gmail.com", "87654321","Cartago"));
                contactDao.create(new Contact("Gabriela", 5, "gabriela@gmail.com", "09871234","Limon"));
                contactDao.create(new Contact("Alex", 6, "sergiome@gmail.com", "43215678","Puntarenas"));

            }

            contacts = contactDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list = (RecyclerView)findViewById(R.id.lista_contactos);

        myLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(myLayoutManager);


        //Lista ejemplo con datos estaticos. Normalmente, estos serían recuperados de una BD o un REST API.

        myAdapter = new MyAdapter(contacts);
        list.setAdapter(myAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();

        list.setAdapter(myAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
