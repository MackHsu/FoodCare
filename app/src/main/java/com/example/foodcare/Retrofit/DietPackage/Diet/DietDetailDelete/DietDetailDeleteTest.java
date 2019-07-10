package com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailDelete;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietDetailDeleteTest {
    private Handler handler;

    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;
    private final int REQUEST_FAILED = 3;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request(int food_id, int diet_id, final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        DietDetailDeleteInterface post = retrofit.create(DietDetailDeleteInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(food_id,diet_id);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if (response.body() == null){
                    System.out.println("返回值为空");
                    Message message = new Message();
                    message.what = DATA_NULL;
                    handler.sendMessage(message);
                    return;
                }
                if(response.body())
                {
                    MyToast.mytoast("成功删除！！",context);
                    Message message = new Message();
                    message.what = DATA_UPDATED;
                    handler.sendMessage(message);
                }else{
                    MyToast.mytoast("删除失败",context);
                    Message message = new Message();
                    message.what = FAILED;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
                Message message = new Message();
                message.what = REQUEST_FAILED;
                handler.sendMessage(message);
            }
        });
    }
}
