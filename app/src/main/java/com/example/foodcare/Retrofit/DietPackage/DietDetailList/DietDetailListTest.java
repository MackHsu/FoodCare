package com.example.foodcare.Retrofit.DietPackage.DietDetailList;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.DietDetail;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietDetailListTest {
    private List<DietDetail> details;
    private Handler handler;
    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<DietDetail> getDetails() {
        return details;
    }

    public void request(int diet_id, final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        DietDetailListInterface post = retrofit.create(DietDetailListInterface.class);
        System.out.println("建立post对象");
        Call<List<DietDetail>> call = post.getCall(diet_id);
        System.out.println("getcall");
        call.enqueue(new Callback<List<DietDetail>>() {
            @Override
            public void onResponse(Call<List<DietDetail>> call, Response<List<DietDetail>> response) {
                System.out.println("请求成功");
                if (response.body() == null){
                    System.out.println("返回值为空");
                    Message message = new Message();
                    message.what = DATA_NULL;
                    handler.sendMessage(message);
                    return;
                }
                details = response.body();
                Message message = new Message();
                message.what = DATA_UPDATED;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<DietDetail>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
                Message message = new Message();
                message.what = FAILED;
                handler.sendMessage(message);
            }
        });
    }
}
