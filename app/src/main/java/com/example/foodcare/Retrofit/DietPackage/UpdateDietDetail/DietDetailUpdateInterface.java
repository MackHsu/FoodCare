package com.example.foodcare.Retrofit.DietPackage.UpdateDietDetail;
/********************曾志昊 2017302580214************************/
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DietDetailUpdateInterface {
    @POST("acc/dietDetail/update")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("diet_id") int diet_id, @Field("food_id") int food_id, @Field("quantity") int quantity);
}
