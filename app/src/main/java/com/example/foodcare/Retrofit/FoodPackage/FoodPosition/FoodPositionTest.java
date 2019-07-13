package com.example.foodcare.Retrofit.FoodPackage.FoodPosition;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.foodcare.Interfaces.IdentifyFoodInterface;
import com.example.foodcare.Retrofit.A_entity.FoodPosition;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.activity.IdentifyFoodActivity;
import com.example.foodcare.activity.IdentifyResultActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodPositionTest {
    private Handler handler;
    private List<FoodPosition> foods = new ArrayList<>();
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public List<FoodPosition> getFoods(){
        return this.foods;
    }
    public void request(final Context context,String url){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        FoodPositionInterface request = retrofit.create(FoodPositionInterface.class);

        File file = new File(url);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("img",file.getName(),requestFile);
        Call<List<FoodPosition>> call = request.getCall(body);
        try{
            call.enqueue(new Callback<List<FoodPosition>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<List<FoodPosition>> call, Response<List<FoodPosition>> response) {
                    // 步骤7：处理返回的数据结果
                    //data = response.body();
                    System.out.println("请求成功");
                    String text = "";
                  /*  Message message = new Message();
                    message.what = GET_DATA_SUCCEEDED;
                    handler.sendMessage(message);*/
                    if(response.body()==null)
                        MyToast.mytoast("识别失败！(识别结果为空)", context);
                    else{
                        MyToast.mytoast("识别成功！",context);
                        Intent intent = new Intent(context, IdentifyResultActivity.class);
                    }
                }
                //请求失败时回调
                @Override
                public void onFailure(Call<List<FoodPosition>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    System.out.println("连接失败");
                    MyToast.mytoast("上传失败，请检查网络情况！",context);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
