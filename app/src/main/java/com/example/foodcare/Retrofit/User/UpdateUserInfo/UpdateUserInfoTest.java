package com.example.foodcare.Retrofit.User.UpdateUserInfo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateUserInfoTest {
    public final int ACCOUNT_UPDATA_SUCCESS=10;
    public final int ACCCOUNT_UPDATA_FAILE=11;
    public Handler handler;
    public void setHandler(Handler handler){this.handler = handler;}
    public void request(Account account,final Context context) {

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UpdateUserInfoInterface post = retrofit.create(UpdateUserInfoInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(account);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");//此处对象为空？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？/
                if (response.body() != null) {
                    if(response.body())
                    {
                        System.out.println("信息上传更新成功");
                        String text = "信息上传更新成功";
                        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Message message = new Message();
                        message.what = ACCOUNT_UPDATA_SUCCESS;
                        handler.sendMessage(message);
                    }
                    else
                    {
                        System.out.println("信息上传失败");
                        String text = "信息上传失败";
                        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Message message = new Message();
                        message.what = ACCCOUNT_UPDATA_FAILE;
                        handler.sendMessage(message);
                    }
                }
                else{
                    Log.i("TAG","信息更改失败");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
