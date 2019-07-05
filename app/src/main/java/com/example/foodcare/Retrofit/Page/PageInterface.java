package com.example.foodcare.Retrofit.Page;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.Page;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PageInterface {

    @POST("food/list/limit")
    Call<List<Food>> getCall(@Body Page page);
    //@FormUrlEncoded
    // Call<List<Food>> getCall(@Field("page") Page page);
}
