package com.example.foodcare.Retrofit.User.UploadAvatar;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadAvatarInterface {
    @Multipart
    @POST("acc/picture")
    Call<Boolean> getCall(@Part("account_id") int account_id,
                          @Part MultipartBody.Part file);
}
