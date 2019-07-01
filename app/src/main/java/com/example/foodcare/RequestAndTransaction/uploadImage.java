package com.example.foodcare.RequestAndTransaction;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface uploadImage {
    @Multipart
    @POST("/image")
    Call<Result<String>> uploadMemberIcon(@Part List<MultipartBody.Part> partList);
}
