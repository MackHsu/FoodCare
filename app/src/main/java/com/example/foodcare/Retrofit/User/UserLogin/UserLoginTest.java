package com.example.foodcare.Retrofit.User.UserLogin;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.AccountLog;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginTest {
    static Account account;
    static public void request(String name,String password,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://192.168.137.238:8080/foodcare/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserLoginInterface post = retrofit.create(UserLoginInterface.class);
        System.out.println("建立post对象");
        Call<AccountLog> call = post.getCall(name,password);
        System.out.println("getcall");
        call.enqueue(new Callback<AccountLog>() {
            @Override
            public void onResponse(Call<AccountLog> call, Response<AccountLog> response) {
                System.out.println("请求成功");
                account = response.body().getAccount();
                System.out.println(account.getName());
                String text = "";
                    text = account.getName()+"/n";
                    Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

            }

            @Override
            public void onFailure(Call<AccountLog> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
