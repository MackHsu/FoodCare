package com.example.foodcare.Retrofit.User.UserLogin;

import com.example.foodcare.Retrofit.A_entity.AccountLog;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserLoginInterface {
    @POST("acc/login")
    @FormUrlEncoded
    Call<AccountLog> getCall(@Field("user") String username,
                             @Field("password") String password);
}
