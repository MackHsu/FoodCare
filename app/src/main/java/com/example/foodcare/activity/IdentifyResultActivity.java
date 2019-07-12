package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.Interfaces.IdentifyFoodInterface;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.adapter.IdentifyAdapter;
import com.example.foodcare.adapter.SpaceItemDecoration;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentifyResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RotateLoading loading;
    private String filepath ;
    private List<FoodReg> foodRegList;
    private Handler handler;
    private final int UPLOAD_SUCCESS = 1;
    private final int UPLOAD_FAILED = 0;
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_result);

        Intent intent = getIntent();
        filepath = intent.getStringExtra("path");

        recyclerView = (RecyclerView) findViewById(R.id.identifyrecycler);
        recyclerView.addItemDecoration(new SpaceItemDecoration(19));
        loading = (RotateLoading) findViewById(R.id.identifyloading);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final IdentifyAdapter adapter = new IdentifyAdapter(R.layout.identify_item, foodRegList);
        recyclerView.setAdapter(adapter);
        //点击某一表项时获得该食物的id并且传入intent参数中
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.identifybutton) {
                    Intent intent = new Intent(IdentifyResultActivity.this, IdentifyLabelDetailActivity.class);

                    Gson gson=new Gson();
                    String jsonData=gson.toJson(foodRegList.get(position));
                    //String jsonData = gson.toJson(easyFoods);
                    intent.putExtra("FoodReg",jsonData);
                    startActivity(intent);
                } else {
                  MyToast.mytoast("请点击按钮进入标签详情界面！",IdentifyResultActivity.this);
                }
            }
        });


        Handler handlerhere = new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case UPLOAD_SUCCESS:
                        //识别成功，停止等待旋转
                        loading.stop();
                        for (FoodReg foodReg: foodRegList) {
                            System.out.println(foodReg.getLabel()+foodReg.getProbability());
                            for(Food food:foodReg.getFoods())
                            {
                                System.out.println(food.getName()+food.getHeat());
                            }
                            adapter.addData(foodReg);
                        }
                        break;
                    case UPLOAD_FAILED:
                        //loading.stop();
                       break;
                }
            }
        };
        this.setHandler(handlerhere);
        this.request(filepath);
        //开始旋转
        loading.start();
    }
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    public void request(String url) {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip) // 设置 网络请求 Url
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        IdentifyFoodInterface request = retrofit.create(IdentifyFoodInterface.class);
        //构建要上传的文件
        File file = new File(url);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"),file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("this is a image file","img.jpg",requestFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img",file.getName(),requestFile);
        Call<List<FoodReg>> call = request.getCall(body);
        try{
            call.enqueue(new Callback<List<FoodReg>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<List<FoodReg>> call, Response<List<FoodReg>> response) {
                    // 步骤7：处理返回的数据结果
                    //data = response.body();
                    System.out.println("请求成功");

                    if(response.body()==null) {
                        MyToast.mytoast("识别失败！(识别结果为空)", IdentifyResultActivity.this);
                        Message message = new Message();
                        message.what = UPLOAD_FAILED;
                        handler.sendMessage(message);
                    }
                    else{
                        foodRegList = response.body();
                        MyToast.mytoast("识别成功！",IdentifyResultActivity.this);
                        Message message = new Message();
                        message.what = UPLOAD_SUCCESS;
                        handler.sendMessage(message);
                    }

                }
                //请求失败时回调
                @Override
                public void onFailure(Call<List<FoodReg>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    System.out.println("连接失败");
                    MyToast.mytoast("上传失败，请检查网络情况！",IdentifyResultActivity.this);
                    throwable.printStackTrace();
                    Message message = new Message();
                    message.what = UPLOAD_FAILED;
                    handler.sendMessage(message);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
