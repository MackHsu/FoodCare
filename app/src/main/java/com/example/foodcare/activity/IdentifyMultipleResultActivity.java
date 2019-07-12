package com.example.foodcare.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodPosition;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.Retrofit.FoodPackage.FoodPosition.FoodPositionInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.UriToPathTool;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.InvalidMarkException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
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

public class IdentifyMultipleResultActivity extends AppCompatActivity {

    private Handler handler;
    private List<FoodPosition> foodPositions = new ArrayList<>();
    private String fileUrl;
    private final int UPLOAD_SUCCESS = 1;
    private final int UPLOAD_FAILED = 0;
    private RotateLoading loading;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private List<Button> buttons = new ArrayList<>();
    private Bitmap selectdBitmap;

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_multiple_result);

        loading = (RotateLoading) findViewById(R.id.multiple_loading);
        loading.start();

        imageView = (ImageView) findViewById(R.id.multiple_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.multiple_relative);
        Intent intent = getIntent();
        fileUrl = intent.getStringExtra("path");
        Handler handlerhere =  new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case UPLOAD_SUCCESS:
                        //识别成功，停止等待旋转
                        loading.stop();
                        initButtonAndImage();
                        break;
                    case UPLOAD_FAILED:
                        //loading.stop();
                        break;
                }
            }
        };
        this.setHandler(handlerhere);
        request(fileUrl,IdentifyMultipleResultActivity.this);
//        printimage();


    }
int id = 1;
    private void printimage(){
        RelativeLayout newrelative = new RelativeLayout(this);
        Button b1 = new Button(this);
        b1.setX(200);
        b1.setY(200);
        b1.setHeight(30);
        b1.setWidth(30);
        b1.setId(id++);
        relativeLayout.addView(b1);

    }

    private void initButtonAndImage(){
        double Height = 0.0;
        double Width = 0.0;
        if(foodPositions.size() == 0)
        {
            MyToast.mytoast("Sorry，我们未能识别出食物",this);
            return;
        }
        System.out.println("**********************"+foodPositions.get(foodPositions.size()-1).getUrl());
        Glide.with(this)
                .load(IP.ip + foodPositions.get(foodPositions.size()-1).getUrl())
                .into(imageView);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        try{
            File file = new File(fileUrl);
            InputStream inputStream = getContentResolver().openInputStream( Uri.fromFile(file));
            selectdBitmap = BitmapFactory.decodeStream(inputStream,null,options);
            inputStream.close();
//            imageView.setImageBitmap(selectdBitmap);
            Height = 0.0+selectdBitmap.getHeight();
            Width = 0.0+selectdBitmap.getWidth();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        System.out.println("height" + Height + "Width" +Width);
//        double Height = 410;
//        double Width = 410;
        Height = (Height/Width)*imageView.getWidth();
        Width = imageView.getWidth();
        System.out.println("height" + Height + "Width" +Width);

        for (final FoodPosition foodPosition: foodPositions) {
            double ax = foodPosition.getRates()[0];
            double bx = foodPosition.getRates()[1];
            double ay = foodPosition.getRates()[2];
            double by = foodPosition.getRates()[3];
            System.out.println(ax+"  "+ay+"  "+bx+"  "+by+"  ");
            System.out.println(foodPosition.getLabel() + foodPosition.getUrl());
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE); // 画框
            drawable.setStroke(10, Color.BLUE); // 边框粗细及颜色
            drawable.setColor(Color.parseColor("#00000000")); // 边框内部颜色
            Button button = new Button(this);
            button.setBackgroundDrawable(drawable);
            button.setHeight((int)((bx-ax)*Height));
            button.setWidth((int)((by-ay)*Width));
//            button.setHeight((int)((bx-ax)*Height*1.8));
//            button.setWidth((int)((by-ay)*Width/1.5));
            button.setText(foodPosition.getLabel());
            System.out.println( (float) ax * (float) Width);
            System.out.println( (float) ay * (float) Height);
            button.setX((float) ax * (float) Width);
            button.setY((float) ay * (float) Height);
            buttons.add(button);
            int index = buttons.indexOf(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IdentifyMultipleResultActivity.this,IdentifyLabelDetailActivity.class);
                    Gson gson=new Gson();
                    String jsonData=gson.toJson(foodPosition.getFoodReg());
                    intent.putExtra("FoodReg",jsonData);
                    startActivity(intent);
                }
            });
            relativeLayout.addView(button);
        }
    }
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    private void request(String url,final Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .client(client)
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
                    System.out.println("请求成功");

                    if(response.body()==null) {
                        MyToast.mytoast("识别失败！(识别结果为空)", context);
                        System.out.println("识别失败");
                        Message message = new Message();
                        message.what = UPLOAD_FAILED;
                        handler.sendMessage(message);
                    }
                    else{
                        foodPositions = response.body();
                        MyToast.mytoast("识别成功！",context);
                        Message message = new Message();
                        message.what = UPLOAD_SUCCESS;
                        handler.sendMessage(message);
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
