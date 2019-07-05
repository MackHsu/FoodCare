package com.example.foodcare.Retrofit.FoodList;

import com.example.foodcare.Retrofit.A_entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface FoodListInterface {
    @POST("food/list")
    Call<List<Food>> getCall();
}
