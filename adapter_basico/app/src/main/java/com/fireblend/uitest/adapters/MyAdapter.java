package com.fireblend.uitest.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.AddContactActivity;
import com.fireblend.uitest.ui.ContactDetailActivity;
import com.fireblend.uitest.ui.MainActivity;
import com.fireblend.uitest.utils.ConstantsApp;

import java.util.List;

/**
 * El patrón ViewHolder se utiliza para mejorar el rendimiento de la aplicación. Esto porque en él, cuando se crea un ViewHolder, se hace el find de los ids que se necesitan.
 * Y este llamado solo se hace una vez, pues ya después se puede ingregar a los elementos por medio del ViewHolder.
 * El ViewHolder ayuda a la implementación del RecyclerView porque es el encargado de representar cada uno de los items de la RecyclerView en forma de view.
 * Entonces se va a crear un ViewHolder por cada elemento que haya en la lista del RecyclerView.
 *
 * */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Contact> myData;
    private SharedPreferences prefs;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public ViewHolder(View v){
            super(v);
            view = v;
        }
    }

    public MyAdapter(List<Contact> myContacts){
        myData = myContacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        ViewHolder vh = new ViewHolder(row);

        prefs = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TextView name, age, phone, email, city;

        name = (TextView) holder.view.findViewById(R.id.name);
        age = (TextView) holder.view.findViewById(R.id.age);
        phone = (TextView) holder.view.findViewById(R.id.phone);
        email = (TextView) holder.view.findViewById(R.id.email);
        city =  (TextView) holder.view.findViewById(R.id.city);

        Button btn = (Button) holder.view.findViewById(R.id.row_btn);

        //Basandonos en la posicion provista en este metodo, proveemos los datos
        //correctos para este elemento.
        final int pos = position;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Hola, "+myData.get(pos).name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),ContactDetailActivity.class);
                intent.putExtra(ConstantsApp.LLAVE_NOMBRE,name.getText().toString());
                intent.putExtra(ConstantsApp.LLAVE_EDAD,age.getText().toString());
                intent.putExtra(ConstantsApp.LLAVE_EMAIL,email.getText().toString());
                intent.putExtra(ConstantsApp.LLAVE_TELEFONO,phone.getText().toString());
                intent.putExtra(ConstantsApp.LLAVE_PROVINCIA,city.getText().toString());

                v.getContext().startActivity(intent);
            }
        });

        name.setText(myData.get(position).name);
        age.setText("Edad:" + myData.get(position).age);
        phone.setText("Telefono:"+myData.get(position).phone);
        email.setText("Email:"+myData.get(position).email);
        city.setText("Provincia:"+myData.get(position).city);

        String currentSize = prefs.getString("pref_tamano_letra","15");
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Float.parseFloat(currentSize));
        age.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Float.parseFloat(currentSize));
        phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Float.parseFloat(currentSize));
        email.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Float.parseFloat(currentSize));
        city.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Float.parseFloat(currentSize));


    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

}

