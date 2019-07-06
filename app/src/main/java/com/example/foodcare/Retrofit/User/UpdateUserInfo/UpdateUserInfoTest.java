package com.example.foodcare.Retrofit.User.UpdateUserInfo;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateUserInfoTest {
    public static void request(final Context context) {
        Account account = new Account();
        account.setAge(10);
        account.setId(5);
        account.setWeight(200d);
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
                if(response.body())
                {
                    System.out.println("信息上传更新成功");
                    String text = "信息上传更新成功";
                    Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    System.out.println("信息上传失败");
                    String text = "信息上传失败";
                    Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
