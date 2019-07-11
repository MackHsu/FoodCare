package com.example.foodcare.Retrofit.SportPackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailAdd.DietDetailAddInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPlayTest {
    private Handler handler;

    private final int NO_RETURN = 0;
    private final int ADD_PLAY_SUCCESS = 1;
    private final int ADD_PLAY_FAILED = 2;
    private final int CONN_ERR = 3;
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request(Play play,final Context context) {//1 高蛋白 2 高酒精

        //创建符合日期格式的 Gson,因为原Gson无法解析太长的日期串
        final Gson builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(builder))
                .build();
        System.out.println("建立retrofit对象");

        SportInterface post = retrofit.create(SportInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.addPlay(play);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if (response.body() == null){
                    System.out.println("返回值为空");
                    Message message = new Message();
                    message.what = NO_RETURN;
                    handler.sendMessage(message);
                    return;
                }
                if(response.body())
                {
                    MyToast.mytoast("成功添加！！",context);
                    Message message = new Message();
                    message.what = ADD_PLAY_SUCCESS;
                    handler.sendMessage(message);

                }else{
                    MyToast.mytoast("添加失败",context);
                    Message message = new Message();
                    message.what = ADD_PLAY_FAILED;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请检查网络连接");
                MyToast.mytoast("请检查网络连接",context);
                Message message = new Message();
                message.what = CONN_ERR;
                handler.sendMessage(message);
                t.printStackTrace();
            }
        });
    }
}
