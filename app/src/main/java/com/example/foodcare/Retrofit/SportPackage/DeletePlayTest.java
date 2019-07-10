package com.example.foodcare.Retrofit.SportPackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeletePlayTest {
    private Handler handler;

    private final int NO_RETURN = 0;
    private final int DELETE_PLAY_SUCCESS = 1;
    private final int DELETE_PLAY_FAILED = 2;
    private final int CONN_ERR = 3;
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request(int account_id, int sport_id, String dateString, final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        SportInterface post = retrofit.create(SportInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.deletePlay(account_id,sport_id,dateString);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if (response.body() == null){
                    System.out.println("无返回");
                    Message message = new Message();
                    message.what = NO_RETURN;
                    handler.sendMessage(message);
                    return;
                }
                if(response.body())
                {
                    MyToast.mytoast("成功删除！！",context);
                    Message message = new Message();
                    message.what = DELETE_PLAY_SUCCESS;
                    handler.sendMessage(message);

                }else{
                    MyToast.mytoast("删除失败",context);
                    Message message = new Message();
                    message.what = DELETE_PLAY_FAILED;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请检查网络连接");
                MyToast.mytoast("请检查网络连接",context);
                Message message = new Message();
                message.what = CONN_ERR;
                handler.sendMessage(message);
                t.printStackTrace();
            }
        });
    }
}
