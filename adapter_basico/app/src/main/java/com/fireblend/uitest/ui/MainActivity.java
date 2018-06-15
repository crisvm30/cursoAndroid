package com.fireblend.uitest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.fireblend.uitest.R;
import com.fireblend.uitest.adapters.MyAdapter;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.utils.ConstantsApp;

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

    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    private void setupList() {

        list = (RecyclerView)findViewById(R.id.lista_contactos);

        myLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(myLayoutManager);

        contacts = new ArrayList();

        //Lista ejemplo con datos estaticos. Normalmente, estos serían recuperados de una BD o un REST API.
       /* contacts.add(new Contact("Sergio", 28, "sergiome@gmail.com", "88854764","Alajuela"));
        contacts.add(new Contact("Andres", 1, "alex@gmail.com", "88883644","San Jose"));
        contacts.add(new Contact("Andrea", 2, "andrea@gmail.com", "98714764","Heredia"));
        contacts.add(new Contact("Fabian", 3, "fabian@gmail.com", "12345678","Cartago"));
        contacts.add(new Contact("Ivan", 4, "ivan@gmail.com", "87654321","Cartago"));
        contacts.add(new Contact("Gabriela", 5, "gabriela@gmail.com", "09871234","Limon"));
        contacts.add(new Contact("Alex", 6, "sergiome@gmail.com", "43215678","Puntarenas"));*/

        myAdapter = new MyAdapter(contacts);
        list.setAdapter(myAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO) != null && getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL) != null){
            contacts.add(new Contact(getIntent().getStringExtra(ConstantsApp.LLAVE_NOMBRE), Integer.parseInt(getIntent().getStringExtra(ConstantsApp.LLAVE_EDAD)), getIntent().getStringExtra(ConstantsApp.LLAVE_EMAIL), getIntent().getStringExtra(ConstantsApp.LLAVE_TELEFONO),getIntent().getStringExtra(ConstantsApp.LLAVE_PROVINCIA)));
        }

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
