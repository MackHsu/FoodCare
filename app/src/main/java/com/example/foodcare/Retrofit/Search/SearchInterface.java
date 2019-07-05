package com.example.foodcare.Retrofit.Search;

import com.example.foodcare.Retrofit.A_entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SearchInterface {
    @POST("food/search")
    @FormUrlEncoded
    Call<List<Food>> getCall(@Field("name") String name);
}
