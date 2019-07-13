package com.example.foodcare.Retrofit.FoodPackage.FoodMap;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.FoodMap;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FoodMapInterface {
    @POST("food/map/{food_id}")
    Call<FoodMap> getFoodMapDetail(@Path("food_id") int id);
}
