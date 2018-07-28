package com.averias.cristian.reporteaverias.utils;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface imgurAPI {

    @POST("/3/image")
    void postImage(
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            @Query("album") String albumId,
            @Query("account_url") String username
           // @Body TypedFile file,
         //   Callback<ImageResponse> cb
    );
}
