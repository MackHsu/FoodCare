package com.example.foodcare.Retrofit.Category;


import com.example.foodcare.Retrofit.A_entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CategoryInterface {
    @POST("food/meal/category")
    @FormUrlEncoded
    Call<List<Food>> getCall(@Field("category") String type);
    //肉类 菜类
}
