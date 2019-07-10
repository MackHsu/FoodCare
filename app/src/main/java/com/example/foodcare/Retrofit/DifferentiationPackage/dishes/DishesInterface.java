package com.example.foodcare.Retrofit.DifferentiationPackage.dishes;

import com.example.foodcare.Retrofit.A_entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface DishesInterface {
    @POST("food/dishes")
    Call<List<Food>> getCall();
}
