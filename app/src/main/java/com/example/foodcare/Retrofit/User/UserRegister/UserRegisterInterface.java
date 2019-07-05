package com.example.foodcare.Retrofit.User.UserRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRegisterInterface {
    @POST("acc/register")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("user") String username,
                          @Field("password") String password);
}
