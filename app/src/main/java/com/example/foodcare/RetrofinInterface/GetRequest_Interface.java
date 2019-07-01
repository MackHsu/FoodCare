package com.example.foodcare.RetrofinInterface;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest_Interface {
    @GET("/sdas")
    Call<Reception> getCall();
}
