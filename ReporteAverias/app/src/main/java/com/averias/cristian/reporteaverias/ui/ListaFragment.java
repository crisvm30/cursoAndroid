package com.averias.cristian.reporteaverias.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.adapters.RViewAdapter;
import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.service.AveriaServicio;
import com.averias.cristian.reporteaverias.service.GestorAveriaServicio;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaFragment extends Fragment {



    RecyclerView lista;
    Button btnCrearAveria;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myLayoutManager;


    private ListaListener mListener;

    public ListaFragment() {
        // Required empty public constructor
    }

    public interface ListaListener{
        public List<Post> getListaPosts();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_lista, container, false);

        lista = (RecyclerView)view.findViewById(R.id.lista_averias);
        btnCrearAveria = view.findViewById(R.id.btn_agregar_averia);

        myLayoutManager = new LinearLayoutManager(view.getContext());
        lista.setLayoutManager(myLayoutManager);
        myAdapter = new RViewAdapter(mListener.getListaPosts());
        lista.setAdapter(myAdapter);


        btnCrearAveria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CrearAveriaActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListaListener) {
            mListener = (ListaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListaListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
