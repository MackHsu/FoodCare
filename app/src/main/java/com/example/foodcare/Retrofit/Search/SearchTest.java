package com.example.foodcare.Retrofit.Search;
/********************曾志昊 2017302580214************************/
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

public class SearchTest {
//模糊搜索OKOKOKOKOKOKOKOKOKOK
    private Handler handler;
    private List<Food> foods;
    private final int SEARCH_CONN_ERR = 2;
    private final int SEARCH_SUCCESS = 1;
    private final int SEARCH_FAILED = 0;

    public List<Food> getFoods(){
        return this.foods;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public void request(String searchname) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");
        SearchInterface post = retrofit.create(SearchInterface.class);
        System.out.println("建立post对象");
        Call<List<Food>> call = post.getCall(searchname);
        System.out.println("getcall");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("请求成功");

                Message message = new Message();
                message.what = SEARCH_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                System.out.println("请求失败");
                Message message = new Message();
                message.what = SEARCH_CONN_ERR;
                handler.sendMessage(message);

                t.printStackTrace();
            }
        });
    }
}
