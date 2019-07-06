package com.example.foodcare.Retrofit.User.UserRegister;

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

public class UserRegisterTest {

    static public void request(String name,String password,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserRegisterInterface post = retrofit.create(UserRegisterInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(name,password);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                String text = "";
                text = response.body()+"/n";
                System.out.println("请求成功");
                System.out.println(text);
                Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

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
