package com.example.foodcare.RequestAndTransaction;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.foodcare.R;

public class UploadActivity extends AppCompatActivity {
    Uri uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

   /* private void upLoad() {
        File file = new File(uriImage);//filePath 图片地址
        String token = "ASDDSKKK19990SDDDSS";//用户token
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart(ParamKey.TOKEN, token);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("imgfile", file.getName(), imageBody);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        uploadTool.uploadMemberIcon(parts).enqueue(new Callback<Result<String>>() {//返回结果
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                //uploadTool.showToastInfo(context, response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                //uploadTool.showToastInfo(getApplicationContext(), "头像上传失败");
            }
        });
    }*/
}
