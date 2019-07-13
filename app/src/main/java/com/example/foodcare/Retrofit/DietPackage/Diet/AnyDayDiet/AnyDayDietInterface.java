package com.example.foodcare.Retrofit.DietPackage.Diet.AnyDayDiet;
/********************曾志昊 2017302580214************************/

import com.example.foodcare.Retrofit.A_entity.Diet;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AnyDayDietInterface {

    @GET("acc/diet/find")
    Call<List<Diet>> getAnyDayDietString(@Query("account_id") int account_id,
                                         @Query("date") String date);
}
