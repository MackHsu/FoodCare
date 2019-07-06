package com.example.foodcare.Retrofit.User.UploadAvatar;

import android.content.Context;
import android.icu.lang.UProperty;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Interfaces.IdentifyFoodInterface;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.UploadPicture.UploadPicture;
import com.example.foodcare.activity.IdentifyFoodActivity;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadAvatarTest {
    private Handler handler;
    public void setHandler(Handler handler){this.handler = handler;}
    private int UPLOAD_SUCCESS = 0;
    private int UPLOAD_FAILED = 1;

    public void request(String url,int account_id,final Context context) {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip) // 设置 网络请求 Url
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        UploadAvatarInterface request = retrofit.create(UploadAvatarInterface.class);

        //构建要上传的文件
        File file = new File(url);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("this is a image file","img.jpg",requestFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        Call<Boolean> call = request.getCall(account_id, body);
        try {
            call.enqueue(new Callback<Boolean>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    // 步骤7：处理返回的数据结果
                    //data = response.body();

                    System.out.println("请求成功");
                    String text = "";
                    if (response.body() == null) {
                        MyToast.mytoast("服务器返回空", context);
                        Message message = new Message();
                        message.what = UPLOAD_FAILED;
                        handler.sendMessage(message);
                    } else if (response.body()) {
                        text = text + "/n 更新头像成功";
                        MyToast.mytoast(text, context);
                        Message message = new Message();
                        message.what = UPLOAD_SUCCESS;

                        handler.sendMessage(message);
                    } else {
                        text = text + "/n 更新头像失败";
                        MyToast.mytoast(text, context);
                        Message message = new Message();
                        message.what = UPLOAD_FAILED;
                        handler.sendMessage(message);
                    }
                }

                //请求失败时回调
                @Override
                public void onFailure(Call<Boolean> call, Throwable throwable) {
                    throwable.printStackTrace();
                    System.out.println("连接失败");
                    MyToast.mytoast("上传失败，请检查网络情况！", context);
                    Message message = new Message();
                    message.what = UPLOAD_FAILED;
                    handler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            Message message = new Message();
            message.what = UPLOAD_FAILED;
            handler.sendMessage(message);
            e.printStackTrace();
        }
    }
}
