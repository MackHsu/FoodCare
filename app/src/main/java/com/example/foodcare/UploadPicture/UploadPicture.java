package com.example.foodcare.UploadPicture;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UploadPicture {
    ///@GET("sports/admin/team/list")
    //@Multipart
    @POST("food/reg1")
    @FormUrlEncoded
    Call<ReturnInfo> getCall(@Field("img") byte[] picture);
}

