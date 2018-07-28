package com.averias.cristian.reporteaverias.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.entities.Post;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaFragment extends Fragment  implements OnMapReadyCallback{

    private MapaListener mListener;

    private GoogleMap mMap;

    public interface MapaListener{

        public List<Post> getListaPostsMapa();
    }

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        List<Post> listaPosts = mListener.getListaPostsMapa();
        for(int i = 0; i < listaPosts.size(); ++i){

            LatLng position = new LatLng(Double.parseDouble(listaPosts.get(i).ubicacion.getLat()),Double.parseDouble(listaPosts.get(i).ubicacion.getLon()));
            mMap.addMarker(new MarkerOptions().position(position).title(listaPosts.get(i).nombre).snippet("Descripción: " + listaPosts.get(i).descripcion + "\n" + "Tipo: "+listaPosts.get(i).tipo+ "\n" + "Id:"+listaPosts.get(i).id));

        }

      /*  mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getContext(),DetalleAveriaActivity.class);
                String datos = marker.getSnippet();
                String [] array = datos.split(":");
                intent.putExtra("idPost",array[3]);

                getContext().startActivity(intent);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(getContext());
                final String lat = ""+latLng.latitude;
                final String lon = ""+latLng.longitude;
                builderAlertDialog.setMessage("Desea crear una avería?");
                builderAlertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(),CrearAveriaActivity.class);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lon",lon);
                        getContext().startActivity(intent);
                    }
                });

                builderAlertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog1 = builderAlertDialog.create();
                alertDialog1.show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MapaListener) {
            mListener = (MapaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MapaListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
