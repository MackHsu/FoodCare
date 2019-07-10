package com.example.foodcare.Retrofit.DietPackage.UpdateDietDetail;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietDetailUpdateTest {
    Handler handler;
    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void request(int diet_id, int food_id, int quantity, final Context context) {//1 高蛋白 2 高酒精
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        DietDetailUpdateInterface post = retrofit.create(DietDetailUpdateInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(diet_id,food_id,quantity);
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
                    message.what = UPDATE_SUCCEEDED;
                    handler.sendMessage(message);
                }else{
                    MyToast.mytoast("添加失败",context);
                    Message message = new Message();
                    message.what = UPDATE_FAILED;
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
