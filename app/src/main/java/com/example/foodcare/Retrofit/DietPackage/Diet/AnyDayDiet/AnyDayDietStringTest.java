package com.example.foodcare.Retrofit.DietPackage.Diet.AnyDayDiet;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnyDayDietStringTest {
    private List<Diet> Diets;
    private Handler handler;
    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<Diet> getDiets() {
        return Diets;
    }

    public void request(int account_id, String date, final Context context){

        //创建符合日期格式的 Gson,因为原Gson无法解析太长的日期串
        final Gson builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip) // 设置 网络请求 Url
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(builder)) //设置使用Gson解析(记得加入依赖)
                .build();
        System.out.println("建立retrofit对象");

        // 步骤5:创建 网络请求接口 的实例
        AnyDayDietInterface request = retrofit.create(AnyDayDietInterface.class);
        //对 发送请求 进行封装
        System.out.println("建立post对象");
        Call<List<Diet>> call=request.getAnyDayDietString(account_id,date);
        System.out.println("getcall");
        try{ //步骤6:发送网络请求(异步)
            call.enqueue(new Callback<List<Diet>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<List<Diet>> call, Response<List<Diet>> response) {
                    // 步骤7：处理返回的数据结果
                    System.out.println("请求成功");
                    String text = "请求成功！！";
                    if(response.body()==null){
                        text = text+"查询结果为空！！！";
                        MyToast.mytoast(text,context);
                        Message message = new Message();
                        message.what = DATA_NULL;
                        handler.sendMessage(message);
                    }
                    else{
                        Diets = response.body();
                        Message message = new Message();
                        message.what = DATA_UPDATED;
                        handler.sendMessage(message);
                    }
                }

                //请求/失败时回调
                @Override
                public void onFailure(Call<List<Diet>> call, Throwable throwable) {
                    System.out.println("连接失败");
                    MyToast.mytoast("请求失败！！",context);
                    throwable.printStackTrace();
                    Message message = new Message();
                    message.what = FAILED;
                    handler.sendMessage(message);
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
