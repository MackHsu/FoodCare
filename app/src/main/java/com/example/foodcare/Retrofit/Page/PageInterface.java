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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PageInterface {

    @POST("food/list/limit")
    Call<FoodPage> getFoodListCall(@Body Page page);

//    @Multipart
//    @POST("food/dishes/type/limit ")
//    Call<FoodPage> getDishTypeCall(@Part("page") Page page,@Part("type") String type );

    @Multipart
    @POST("food/dishes/type/limit ")
    Call<FoodPage> getDishTypeCall(@Part("start") int start,@Part("type") String type );


//    @Multipart
//    @POST("food/meal/category/limit")
//    Call<FoodPage> getMalCategoryCall(@Part("page") Page page,@Part("category") String category);

    @Multipart
    @POST("food/meal/category/limit")
    Call<FoodPage> getMalCategoryCall(@Part("start") int start,@Part("category") String category);


    @POST("food/list/frequent/limit")
    Call<FoodPage> getFrequentCall(@Body Page page);

//    @Multipart
//    @POST("food/search/limit")
//    Call<FoodPage> getSearchCall(@Part("name") String name,@Part("page") Page page);

    @Multipart
    @POST("food/search/limit")
    Call<FoodPage> getSearchCall(@Part("name") String name,@Part("start") int start);


}
