package com.example.foodcare.Retrofit.FoodPackage.FoodPosition;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.FoodPosition;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FoodPositionInterface {
    @Multipart
    @POST("food/multiple/reg")
    Call<List<FoodPosition>> getCall(@Part MultipartBody.Part file);
}
