package com.example.foodcare.Retrofit.LabelFood;

import com.example.foodcare.Retrofit.A_entity.Label;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LabelFoodInterface {
    @POST("food/label/{label_id}")
    Call<List<Label>> getCall(@Path("label_id") int id);
}
