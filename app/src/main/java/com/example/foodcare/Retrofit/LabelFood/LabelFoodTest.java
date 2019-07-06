package com.example.foodcare.Retrofit.LabelFood;

import com.example.foodcare.Retrofit.A_entity.Label;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//获得标签下所有食物OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK
public class LabelFoodTest {
    static public void request(int labelid) {//1 高蛋白 2 高酒精
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        LabelFoodInterface post = retrofit.create(LabelFoodInterface.class);
        System.out.println("建立post对象");
        Call<List<Label>> call = post.getCall(labelid);
        System.out.println("getcall");
        call.enqueue(new Callback<List<Label>>() {
            @Override
            public void onResponse(Call<List<Label>> call, Response<List<Label>> response) {
                System.out.println("请求成功");//此处对象为空？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？/
                for(int i =0;i<response.body().size();i++)
                {
                    System.out.println(response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Label>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
