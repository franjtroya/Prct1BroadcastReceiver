package com.dam.proyecto.prct1broadcastreceiver;

/**
 * Created by fran on 31/01/18.
 */

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ClienteRest {
    @POST("registro")
    Call<LlamadaInfo> postLLamadaInfo(@Body LlamadaInfo llamadaInfo);
}