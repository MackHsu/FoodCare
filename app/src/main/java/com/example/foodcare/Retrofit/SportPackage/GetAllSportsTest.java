package com.example.foodcare.Retrofit.SportPackage;

import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.Sport;
import com.example.foodcare.Retrofit.FoodPackage.FoodList.FoodListInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllSportsTest {
    Handler handler;
    List<Sport> sports;
    public final int GET_SPORTS_SUCCESS = 1;

    public List<Sport> getSports() {
        return sports;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        SportInterface post = retrofit.create(SportInterface.class);
        System.out.println("建立post对象");
        Call<List<Sport>> call = post.getAllSports();
        System.out.println("getcall");
        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
                System.out.println("请求成功");
                sports = response.body();
                if(sports == null)
                    System.out.println("返回错误");
                Message message = new Message();
                message.what = GET_SPORTS_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<Sport>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }

}