package com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailDelete;
/********************曾志昊 2017302580214************************/
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DietDetailDeleteInterface {
    @POST("acc/dietDetail/delete")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("food_id") int food_id,
                          @Field("diet_id") int diet_id);
}
