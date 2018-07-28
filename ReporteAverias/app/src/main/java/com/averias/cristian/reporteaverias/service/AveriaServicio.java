package com.averias.cristian.reporteaverias.service;

import com.averias.cristian.reporteaverias.entities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AveriaServicio {

    @Headers({
            "Accept:application/json ",
            "x-api-key:rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO"
    })
    @GET("produccion/averias")
    public Call<List<Post>> obtenerAverias();

    @Headers({
            "Accept: application/json",
            "x-api-key: rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO"
    })
    @GET("produccion/averias/{id}")
    public Call<Post> obtenerDetalleAveria(@Path("id") String id);

    @Headers({
            "Accept:application/json ",
            "x-api-key:rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO"
    })
    @POST("produccion/averias")
    public Call<Post> crearAveria(@Body Post post);


    @Headers({
            "Accept:application/json ",
            "x-api-key:rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO"
    })
    @POST("produccion/averias/{id}")
    public Call<Post> editarAveria(@Path("id") String id,@Body Post post);

    @Headers({
            "Accept:application/json ",
            "x-api-key:rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO"
    })
    @DELETE("produccion/averias/{id}")
    public Call<Post> eliminarAveria(@Path("id") String id);



}
