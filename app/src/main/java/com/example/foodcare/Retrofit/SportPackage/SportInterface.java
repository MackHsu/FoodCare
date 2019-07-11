package com.example.foodcare.Retrofit.SportPackage;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodPage;
import com.example.foodcare.Retrofit.A_entity.Page;
import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.A_entity.Sport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SportInterface {

    @POST("acc/sport/list")
    Call<List<Sport>> getAllSports();

    @Multipart
    @POST("acc/play/list")
    Call<List<Play>> getPlayByAccDate(@Part("account_id") int account_id,
                                      @Part("date") String date);

    @POST("acc/play/add")
    Call<Boolean> addPlay(@Body Play play);

    @Multipart
    @POST("acc/play/delete")
    Call<Boolean> deletePlay(@Part("account_id") int account_id,
                                @Part("sport_id") int sport_id,
                                @Part("date") String date);

    @POST("acc/play/update")
    Call<Boolean> updatePlay(@Body Play play);


}
