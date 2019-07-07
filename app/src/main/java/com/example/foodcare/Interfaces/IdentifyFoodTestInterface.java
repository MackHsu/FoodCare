package com.example.foodcare.Interfaces;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodRank;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IdentifyFoodTestInterface {
    @Multipart
    @POST("food/reggg")//")
    Call<List<FoodRank>> getCall(@Part MultipartBody.Part file);
}
