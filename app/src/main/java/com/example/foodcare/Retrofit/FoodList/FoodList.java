package com.example.foodcare.Retrofit.FoodList;

import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//所有食物OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK
public class FoodList {
    Handler handler;
    List<Food> data;
    public final int UPDATE_DATA = 1;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        FoodListInterface post = retrofit.create(FoodListInterface.class);
        System.out.println("建立post对象");
        Call<List<Food>> call = post.getCall();
        System.out.println("getcall");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("请求成功");
//                for(int i =0;i<response.body().size();i++)
//                {
//                    data.add(response.body().get(i));
//                }
                data = response.body();
                if(data == null)
                    System.out.println("返回错误");
                Message message = new Message();
                message.what = UPDATE_DATA;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }


    public List<Food> getData() {
        return data;
    }
}
