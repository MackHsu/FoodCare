package com.example.foodcare.Retrofit.Page;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodPage;
import com.example.foodcare.Retrofit.A_entity.Page;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PageInterface {

    @POST("food/list/limit")
    Call<FoodPage> getFoodListCall(@Body Page page);

    @POST("food/dishes/type/limit ")
    @FormUrlEncoded
    Call<FoodPage> getDishTypeCall(@Body Page page,@Field("type") String type);

    @POST("food/meal/category/limit")
    Call<FoodPage> getMalCategoryCall(@Body Page page,@Field("catogory") String type);

    @POST("food/frequent/limit ")
    Call<FoodPage> getFrequentCall(@Body Page page);

}
