package com.example.foodcare.Retrofit.User.UserInformation;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInformationTest {
    private Account account;
    private Handler handler;
    private final int GET_USERINFO_SUCCESS = 1;
    public void setHandler(Handler handler ){
        this.handler = handler;
    }
    public Account getAccount(){return this.account;}
    public void request(int id,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserInformationInterface post = retrofit.create(UserInformationInterface.class);
        System.out.println("建立post对象");
        Call<Account> call = post.getCall(id);
        System.out.println("getcall");
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                System.out.println("请求成功");
                account = response.body();
                System.out.println(account.getName());
                Message message = new Message();
                message.what = GET_USERINFO_SUCCESS;
                handler.sendMessage(message);
                MyToast.mytoast("请求用户信息成功！",context);
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
