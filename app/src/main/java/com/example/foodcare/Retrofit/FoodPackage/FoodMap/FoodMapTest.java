package com.example.foodcare.Retrofit.FoodPackage.FoodMap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.FoodMap;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodMapTest {
    private Handler handler;
    private int GET_FOOD_DETAIL_SUCCESS=1;
    private int GET_FOOD_DETAIL_FAILED=0;
    private FoodMap foodMap;
    public FoodMap getFoodMap(){
        return foodMap;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public void request(int foodid,final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        FoodMapInterface post = retrofit.create(FoodMapInterface.class);
        System.out.println("建立post对象");
        Call<FoodMap> call = post.getFoodMapDetail(foodid);
        System.out.println("getcall");
        call.enqueue(new Callback<FoodMap>() {
            @Override
            public void onResponse(Call<FoodMap> call, Response<FoodMap> response) {
                System.out.println("请求成功");
                foodMap = response.body();
                Message message = new Message();
                message.what = GET_FOOD_DETAIL_SUCCESS;
                MyToast.mytoast("请求成功！！！！！！",context);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<FoodMap> call, Throwable t) {
                System.out.println("请求失败");
                t.printStackTrace();
                Message message = new Message();
                message.what = GET_FOOD_DETAIL_FAILED;
                handler.sendMessage(message);
            }
        });
    }
}
