package com.example.foodcare.Retrofit.DietPackage.Diet.TodayDiet;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.Diet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TodayDietInterface {
    @POST("acc/diet/list")
    @FormUrlEncoded
    Call<List<Diet>> getCall(@Field("account_id") int account_id);
}
