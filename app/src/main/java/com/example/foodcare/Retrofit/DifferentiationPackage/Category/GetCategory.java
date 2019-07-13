package com.example.foodcare.Retrofit.DifferentiationPackage.Category;
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

//分种类食物测试OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK
public class GetCategory {
    static public void request(String type){
        //步骤4:创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip) // 设置 网络请求 Url
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        System.out.println("建立retrofit对象");

        // 步骤5:创建 网络请求接口 的实例
        CategoryInterface request = retrofit.create(CategoryInterface.class);
        /*BitmapDrawable temp =(BitmapDrawable) pic;
        Bitmap bitmap = temp.getBitmap();*/
        //对 发送请求 进行封装
        System.out.println("建立post对象");
        Call<List<Food>> call=request.getCall(type);
        System.out.println("getcall");
        try{ //步骤6:发送网络请求(异步)
            call.enqueue(new Callback<List<Food>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                    // 步骤7：处理返回的数据结果
                    System.out.println("请求成功");
                    for(int i = 0;i<response.body().size();i++)
                    {
                        System.out.println(response.body().get(i).getName());
                    }
                    //setTxetOfResult(response.body().show());
                }

                //请求失败时回调
                @Override
                public void onFailure(Call<List<Food>> call, Throwable throwable) {
                    System.out.println("连接失败");
                    System.out.println(throwable.toString());
                    throwable.printStackTrace();
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
