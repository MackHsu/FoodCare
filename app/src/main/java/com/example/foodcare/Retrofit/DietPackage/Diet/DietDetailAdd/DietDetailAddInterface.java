package com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailAdd;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DietDetailAddInterface {
    @POST("acc/dietDetail/add")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("food_id") int food_id,
                          @Field("quantity") int quantity,
                          @Field("account_id") int account_id,
                          @Field("group") int group);
}
