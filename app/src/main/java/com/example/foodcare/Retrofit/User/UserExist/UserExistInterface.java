package com.example.foodcare.Retrofit.User.UserExist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserExistInterface {
    @POST("acc/exists")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("user") String username);
}
