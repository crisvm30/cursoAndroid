package com.averias.cristian.reporteaverias.service;

import com.averias.cristian.reporteaverias.entities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestorAveriaServicio {

    private static AveriaServicio averiaServicioSingleton;

    public static AveriaServicio generarServicios(){


        if(averiaServicioSingleton == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fn3arhnwsg.execute-api.us-west-2.amazonaws.com/").addConverterFactory(GsonConverterFactory.create()).build();
            averiaServicioSingleton = retrofit.create(AveriaServicio.class);

        }

        return averiaServicioSingleton;
    }

}
