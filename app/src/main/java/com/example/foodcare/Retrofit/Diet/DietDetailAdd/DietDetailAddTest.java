package com.example.foodcare.Retrofit.Diet.DietDetailAdd;

import android.content.Context;

import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietDetailAddTest {
    static public void request(int food_id, int quantity, int account_id, int group, final Context context) {//1 高蛋白 2 高酒精
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        DietDetailAddInterface post = retrofit.create(DietDetailAddInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(food_id,quantity,account_id,group);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if (response.body() == null){
                    System.out.println("返回值为空");
                    return;
                }
                if(response.body())
                {
                    MyToast.mytoast("成功添加！！",context);
                }else{
                    MyToast.mytoast("添加失败",context);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
