package com.example.foodcare.Retrofit.User.UpdateUserInfo;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UpdateUserInfoInterface {
    @POST("acc/updateInfo")
    Call<Boolean> getCall(@Body Account account);
}
