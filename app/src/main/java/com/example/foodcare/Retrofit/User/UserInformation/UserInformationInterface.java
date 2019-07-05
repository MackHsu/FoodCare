package com.example.foodcare.Retrofit.User.UserInformation;

import com.example.foodcare.Retrofit.A_entity.Account;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInformationInterface {
    @POST("acc/info")
    @FormUrlEncoded
    Call<Account> getCall(@Field("account_id") int id
    );
}
