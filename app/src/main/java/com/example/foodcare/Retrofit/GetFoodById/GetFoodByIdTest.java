package com.example.foodcare.Retrofit.GetFoodById;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.Label;
import com.example.foodcare.Retrofit.LabelFood.LabelFoodInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import java.security.KeyStore;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetFoodByIdTest {
    private Handler handler;
    private int GET_FOOD_DETAIL_SUCCESS=1;
    private int GET_FOOD_DETAIL_FAILED=0;
    private Food food;
    public Food getFood(){
        return food;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public void request(int foodid,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        GetFoodByIdInterface post = retrofit.create(GetFoodByIdInterface.class);
        System.out.println("建立post对象");
        Call<Food> call = post.getCall(foodid);
        System.out.println("getcall");
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                System.out.println("请求成功");
                food = response.body();
                Message message = new Message();
                message.what = GET_FOOD_DETAIL_SUCCESS;
                MyToast.mytoast("请求成功！！！！！！",context);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                System.out.println("请求失败");
                t.printStackTrace();
                Message message = new Message();
                message.what = GET_FOOD_DETAIL_FAILED;
                handler.sendMessage(message);
            }
        });
    }
}
