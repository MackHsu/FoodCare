package com.example.foodcare.Retrofit.FoodPackage.GetLabel;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.Label;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface GetLabelInterface {
    @POST("food/label/list")
    Call<List<Label>> getCall();
}
