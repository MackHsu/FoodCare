package com.example.foodcare.Retrofit.User.UserCancel;
/********************曾志昊 2017302580214************************/
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserCancelInterface {
    @POST("acc/cancel")
    @FormUrlEncoded
    Call<Boolean> getCall(@Field("account_id") int userid);
}
