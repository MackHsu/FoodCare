package com.example.foodcare.Retrofit.FoodTest;

import com.example.foodcare.Retrofit.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface FoodTestInterface {
    @POST("food/meal")
    Call<List<Food>> getCall();
}
