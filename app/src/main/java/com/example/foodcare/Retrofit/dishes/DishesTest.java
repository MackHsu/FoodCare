package com.example.foodcare.Retrofit.dishes;

import com.example.foodcare.Retrofit.Food;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//查询所有菜品OKOKOKOKOKOKOKOKOKOKOKOKOK
public class DishesTest {
    static public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://192.168.137.238:8080/foodcare/")//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        DishesInterface post = retrofit.create(DishesInterface.class);
        System.out.println("建立post对象");
        Call<List<Food>> call = post.getCall();
        System.out.println("getcall");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("请求成功");
                for(int i =0;i<10;i++)
                {
                    System.out.println(response.body().get(i).getName());
                }

            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("请求失败");
                System.out.println(t.toString());
            }
        });
    }
}