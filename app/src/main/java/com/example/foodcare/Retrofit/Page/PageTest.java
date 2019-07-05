package com.example.foodcare.Retrofit.Page;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.Page;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PageTest {
    static public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://192.168.137.238:8080/foodcare/")//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        PageInterface post = retrofit.create(PageInterface.class);
        System.out.println("建立post对象");
        Page page = new Page();
        page.setStart(0);
        Call<List<Food>> call = post.getCall(page);
        System.out.println("getcall");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("请求成功");//此处对象为空？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？/

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
