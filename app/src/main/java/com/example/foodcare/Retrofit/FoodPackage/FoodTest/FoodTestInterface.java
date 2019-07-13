package com.example.foodcare.Retrofit.FoodPackage.FoodTest;
/********************曾志昊 2017302580214************************/

import com.example.foodcare.Retrofit.A_entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface FoodTestInterface {
    @POST("food/meal")
    Call<List<Food>> getCall();
}
