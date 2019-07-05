package com.example.foodcare.Retrofit.GetLabel;

import com.example.foodcare.Retrofit.A_entity.Label;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//获得所有标签测试成功OKOKOKOKOKOKOKOKOKOKOK
public class GetLabelTest {
    static public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        GetLabelInterface post = retrofit.create(GetLabelInterface.class);
        System.out.println("建立post对象");
        Call<List<Label>> call = post.getCall();
        System.out.println("getcall");
        call.enqueue(new Callback<List<Label>>() {
            @Override
            public void onResponse(Call<List<Label>> call, Response<List<Label>> response) {
                System.out.println("请求成功");
                for(int i =0;i<response.body().size();i++)
                {
                    System.out.print(response.body().get(i).getName());
                    System.out.println(response.body().get(i).getId());
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
