package com.fireblend.uitest.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;

import com.fireblend.uitest.ui.MainActivity;



public class DetailEditFragment extends Fragment implements  AdapterView.OnItemSelectedListener{


    Button btnGuardar;

    Button btnCancelar;

    EditText nombre;

    EditText edad;

    EditText email;

    EditText telefono;

    Spinner spinnerProvincias;

    final String [] provincias = {"-","Alajuela","Cartago","Heredia","Puntarenas","Limon","San Jose", "Guanacaste"};

    private String provinciaSeleccionada ="";

    private DetailEditListener mListener;

    public DetailEditFragment() {
        // Required empty public constructor
    }

    public interface DetailEditListener{
        public void onEditar(String oldName,String textoNombre,String textoEdad,String textoEmail,String textoTelefono,String provinciaSeleccionada);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail_edit, container, false);

        nombre = (EditText) view.findViewById(R.id.nombre_texto);
        edad = (EditText)view.findViewById(R.id.edad_texto);
        telefono = (EditText)view.findViewById(R.id.telefono_texto);
        email = (EditText)view.findViewById(R.id.email_texto);
        spinnerProvincias = (Spinner) view.findViewById(R.id.spinner_provincia);

        btnGuardar = (Button)view.findViewById(R.id.boton_guardar);

        btnCancelar = (Button)view.findViewById(R.id.boton_cancelar);

        Bundle args = getArguments();
        final String nameText = args.getString("nombre", "");
        final String edadText = args.getString("edad", "");
        final String telefonoText = args.getString("telefono", "");
        final String emailText = args.getString("email", "");
        final String provinciaText = args.getString("provincia", "");

        provinciaSeleccionada = provinciaText;
        nombre.setText(nameText);
        edad.setText(edadText);
        telefono.setText(telefonoText);
        email.setText(emailText);

        int pos = 0;

        while(!provincias[pos].equals(provinciaText) && pos < provincias.length ){
            ++pos;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,provincias);
        spinnerProvincias.setAdapter(adapter);
        spinnerProvincias.setSelection(pos);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = (Activity) view.getContext();
                act.finish();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNombre = nombre.getText().toString();
                String textoEdad = edad.getText().toString();
                String textoEmail = email.getText().toString();
                String textoTelefono = telefono.getText().toString();

                if(!textoNombre.isEmpty() && !textoEdad.isEmpty() && !textoEmail.isEmpty() && !textoTelefono.isEmpty() && !provinciaSeleccionada.isEmpty() && !provinciaSeleccionada.equals("-")){


                    mListener.onEditar(nameText,textoNombre,textoEdad,textoEmail,textoTelefono,provinciaSeleccionada);

                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    view.getContext().startActivity(intent);
                }else{
                    Toast.makeText(view.getContext(),"Debe llenar todos los espacios!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailEditListener) {
            mListener = (DetailEditListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DetailEditListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        provinciaSeleccionada = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
