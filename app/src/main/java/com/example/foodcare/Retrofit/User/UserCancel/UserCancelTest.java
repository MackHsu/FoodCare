package com.example.foodcare.Retrofit.User.UserCancel;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//注销用户OKOKOKOKOKOKOKOKOKOKOK
public class UserCancelTest {
    static public void request(int userid,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserCancelInterface post = retrofit.create(UserCancelInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(userid);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if(response.body()){
                    System.out.println("用户注销成功！！！");
                    String text = "用户注销成功！！！";
                    Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if(!response.body()) {
                    System.out.println("用户注销失败！！！");
                    String text = "用户注销失败！！！";
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
