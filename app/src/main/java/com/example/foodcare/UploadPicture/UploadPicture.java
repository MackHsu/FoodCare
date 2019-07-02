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
    @POST("sports/admin/team/list")
    @FormUrlEncoded
    Call<ReturnInfo> getCall(@Field("picture") Byte[] picture);
}