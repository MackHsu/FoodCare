package com.example.foodcare.Retrofit.Diet.AnyDayDiet;

import com.example.foodcare.Retrofit.A_entity.Diet;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AnyDayDietStringInterface {
//    @POST("acc/diet/find")
////    @FormUrlEncoded
//    Call<List<Diet>> getCall(@Field("account_id") int account_id,
//                             @Body Date date);

    @POST("acc/diet/find")
    @FormUrlEncoded
    Call<List<Diet>> getCall(@Field("account_id") int account_id,
                             @Field("date") String date);
}
