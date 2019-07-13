package com.example.foodcare.Retrofit.DifferentiationPackage.Type;
/********************曾志昊 2017302580214************************/
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//菜系测试OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK
public class TypeTest {
    static public void request(String type) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        TypeInterface post = retrofit.create(TypeInterface.class);
        System.out.println("建立post对象");
        Call<List<Food>> call = post.getCall(type);
        System.out.println("getcall");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("请求成功");
                for(int i =0;i<response.body().size();i++)
                {
                    System.out.println(response.body().get(i).getName());
                }

            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
