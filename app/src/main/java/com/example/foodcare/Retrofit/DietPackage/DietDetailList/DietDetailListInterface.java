package com.example.foodcare.Retrofit.DietPackage.DietDetailList;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.DietDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DietDetailListInterface {
    @POST("acc/dietDetail/list")
    @FormUrlEncoded
    Call<List<DietDetail>> getCall(@Field("diet_id") int diet_id);
}
