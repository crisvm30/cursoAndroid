package com.fireblend.uitest.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.ui.MainActivity;



public class DetailFragment extends Fragment {

    Button btnEliminar;

    Button btnEditar;

    TextView nombre;

    TextView edad;

    TextView email;

    TextView telefono;

    TextView textoProvincia;


    private DetailListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    public interface DetailListener{
        public void onEliminar(String nombre,int edad,String email,String telefono,String provincia);
        public void goToEditar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        nombre = (TextView)view.findViewById(R.id.nombre_texto);
        edad = (TextView)view.findViewById(R.id.edad_texto);
        telefono = (TextView)view.findViewById(R.id.telefono_texto);
        email = (TextView)view.findViewById(R.id.email_texto);
        textoProvincia = (TextView)view.findViewById(R.id.text_provincia);

        btnEliminar = (Button)view.findViewById(R.id.boton_eliminar);

        btnEditar = (Button)view.findViewById(R.id.boton_editar);

        Bundle args = getArguments();
        final String nameText = args.getString("nombre", "");
        final String edadText = args.getString("edad", "");
        final String telefonoText = args.getString("telefono", "");
        final String emailText = args.getString("email", "");
        final String provinciaText = args.getString("provincia", "");


        nombre.setText(nameText);
        edad.setText(edadText);
        telefono.setText(telefonoText);
        email.setText(emailText);
        textoProvincia.setText(provinciaText);

        btnEliminar.setEnabled(args.getBoolean("btnEliminar", false));

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(view.getContext());
                builderAlertDialog.setMessage("Está seguro que desea eliminar el Contacto?");
                builderAlertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onEliminar(nameText,Integer.parseInt(edadText),emailText,telefonoText,provinciaText);
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


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToEditar();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailListener) {
            mListener = (DetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DetailListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
