package com.example.foodcare.Interfaces;



import com.example.foodcare.Retrofit.A_entity.FoodRank;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IdentifyFoodInterface {
    @Multipart
    @POST("food/reg")//gg")
    Call<List<FoodReg>> getCall(//@Part("description") String string,
                                @Part MultipartBody.Part file);
}
