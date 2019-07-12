package com.example.foodcare.Retrofit.SportPackage;

import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.A_entity.Sport;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
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

public class GetPlayByDateTest {

    Handler handler;
    List<Play> plays;
    public final int GET_PLAYS_NULL = 0;
    public final int GET_PLAYS_SUCCESS = 1;
    public final int REQUEST_FALSE = 2;

    public List<Play> getPlays() {
        return plays;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request(int account_id,String dateString) {

        //创建符合日期格式的 Gson,因为原Gson无法解析太长的日期串
        final Gson builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(builder))
                .build();
        System.out.println("建立retrofit对象");

        SportInterface post = retrofit.create(SportInterface.class);
        System.out.println("建立post对象");
        Call<List<Play>> call = post.getPlayByAccDate(account_id,dateString);
        System.out.println("getcall");
        call.enqueue(new Callback<List<Play>>() {
            @Override
            public void onResponse(Call<List<Play>> call, Response<List<Play>> response) {
                System.out.println("请求成功");
                plays = response.body();
                if(plays == null) {
                    System.out.println("返回错误");
                    Message message = new Message();
                    message.what = GET_PLAYS_NULL;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = GET_PLAYS_SUCCESS;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<List<Play>> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
                Message message = new Message();
                message.what = REQUEST_FALSE;
                handler.sendMessage(message);

            }
        });
    }
}
