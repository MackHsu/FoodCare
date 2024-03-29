package com.example.foodcare.Retrofit.FoodPackage.GetFoodById;

import com.example.foodcare.Retrofit.A_entity.Food;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetFoodByIdInterface {
    @POST("food/{food_id}")
    Call<Food> getCall(@Path("food_id") int id);
}
