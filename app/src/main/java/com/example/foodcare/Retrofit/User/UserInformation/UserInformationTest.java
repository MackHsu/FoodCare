package com.example.foodcare.Retrofit.User.UserInformation;

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

public class UserInformationTest {
    public Account account;
    public final int ACCOUNT_GET_SUCCESS=8;
    public  final int ACCOUNT_GET_FAILE=9;
    public Handler handler;
    public void setHandler(Handler handler){this.handler = handler;}
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
                if(response.body()!=null){
                    account = response.body();
                    System.out.println(account.getName());
                    String text = "";
                    text = account.getName()+"/n";
                    Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Message message = new Message();
                    message.what = ACCOUNT_GET_SUCCESS;
                    handler.sendMessage(message);
                }
                else{
                    Log.i("TAG","返回数据为空");
                }
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
