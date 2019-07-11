package com.example.foodcare.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.Interfaces.IdentifyFoodInterface;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.UriToPathTool;
import com.example.foodcare.adapter.IdentifyAdapter;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UplaodPictureActivity extends AppCompatActivity {

    private ImageView imageView;            //要识别的图片
    private ImageButton back_button_upload;//返回的按钮，左上角
    private RecyclerView recyclerView;    //识别结果列表
    private RotateLoading loading;         //加载缓冲
    private List<FoodReg> foodRegList;      //识别结果的列表
    private Handler handler;
    private final int TAKE_PHOTO=1;
    private final int CHOOSE_PHOTO=2;
    private final int UPLOAD_SUCCESS = 1;
    private final int UPLOAD_FAILED = 0;
    public void setHandler(Handler handler){
        this.handler = handler;
    } //异步处理器
    private IdentifyAdapter adapter;

    private String photodir = "/storage/emulated/0/FoodCarePhoto";
    private String photoPath ="/storage/emulated/0/FoodCarePhoto/CachePhoto.jpg";
    private String realFilrPath;
    private Bitmap selectdBitmap;
    private  int MAX_SIZE = 769;

    private String filepath;

    Uri UriOnScreen;

    UplaodPictureActivity thisupload = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_picture);
        back_button_upload=(ImageButton)findViewById(R.id.back_button_upload);
        recyclerView=(RecyclerView)findViewById(R.id.identify_recycler_upload);
        loading=(RotateLoading)findViewById(R.id.identify_loading_upload);
        imageView=(ImageView)findViewById(R.id.picture_for_upload);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new IdentifyAdapter(R.layout.identify_item,foodRegList);
        recyclerView.setAdapter(adapter);

        //点击某一表项时获得该食物的id并且传入intent参数中
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.identifybutton) {
                    Intent intent = new Intent(getApplicationContext(), IdentifyLabelDetailActivity.class);

                    Gson gson=new Gson();
                    String jsonData=gson.toJson(foodRegList.get(position));
                    //String jsonData = gson.toJson(easyFoods);
                    intent.putExtra("FoodReg",jsonData);
                    MyToast.mytoast("成功进入标签详情界面",getApplicationContext());
                    startActivity(intent);
                } else if(view.getId()==R.id.identifylayout){
                    Intent intent = new Intent(getApplicationContext(), IdentifyLabelDetailActivity.class);

                    Gson gson=new Gson();
                    String jsonData=gson.toJson(foodRegList.get(position));
                    //String jsonData = gson.toJson(easyFoods);
                    intent.putExtra("FoodReg",jsonData);
                    MyToast.mytoast("成功进入标签详情界面",getApplicationContext());
                    startActivity(intent);
                }else{
                    MyToast.mytoast("请点击按钮进入标签详情界面！",getApplicationContext());
                }
            }
        });

        back_button_upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });   //返回键返回

        Intent intent=getIntent();
        String data=intent.getStringExtra("WAY");          //在这里直接进入拍照或选相册的界面
        if(data.equals("TAKE_PHOTO")){
            //TODO 启动拍照功能
            takePhoto();
        } else if(data.equals("SELECT_PICTURE")){
            //TODO 去相册选取
            selectAlbum();
        } else{
            Log.i("TAG","从主界面传过来的数据传输有误");
        }



    }


    private void selectAlbum(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Browser Image..."),CHOOSE_PHOTO);
    }

    private void takePhoto(){
        File photoDir = new File(photodir);
        if (!photoDir.exists()){
            photoDir.mkdirs();
        }
        //在该文件夹下（覆盖）创建一个文件
        File photoFile=new File(photodir,"CachePhoto.jpg");
        try{
            if(photoFile.exists()){
                photoFile.delete();
            }
            photoFile.createNewFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            try{
                //检测动态权限
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    return;
                }//"com.example.urltest.provider"
                UriOnScreen= FileProvider.getUriForFile(getApplicationContext(),"com.example.search.fileprovider",photoFile);
                //textofpath.setText(UriOnScreen.toString());
                //上面这个是一个显示路径的就不显示了,
                Log.i("TAG",UriOnScreen.toString());
                for(int i =0;i<100;i++)
                {
                    System.out.println("此处为uri"+UriOnScreen.toString());
                }
            }
            catch (Exception e){
                System.out.println("获取uri失败！！！！！！！！！！！！！！！！！！");
                e.printStackTrace();
            }
        }
        else{
            UriOnScreen= Uri.fromFile(photoFile);
            System.out.println("hhhhhhhhhhhhhhhhh"+UriOnScreen.toString());
        }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");//MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION| FLAG_GRANT_WRITE_URI_PERMISSION);//允许临时的读和写

        //检查用户是否将该文件夹删除
        intent.putExtra(MediaStore.EXTRA_OUTPUT,UriOnScreen);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    private void uploadImage(){
       //filepath 这个变量传过去了
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    /*String url = UriOnScreen.toString();//getRealFilePath(thisupload,UriOnScreen);
                    textofpath.setText(url);*/
                    //检测动态权限
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                        return;
                    }
                    //将uri转换为path
                    //filepath = UriToPathTool.getRealFilePath(thisupload,UriOnScreen);
                    filepath = photoPath;
                    realFilrPath = filepath;
                    //将path显示出来
                    System.out.println(filepath);
                    //path.setText(filepath);
                    //上一行也只是将路径显示出来
                    Log.i("TAG",filepath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    try{
                        //通过uri从流中读入图片
                        InputStream inputStream = getContentResolver().openInputStream(UriOnScreen);
                        //解码流
                        selectdBitmap = BitmapFactory.decodeStream(inputStream,null,options);
                        //关闭流
                        inputStream.close();
                        //将选择的图片显示在界面上
                        imageView.setImageBitmap(selectdBitmap);
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                    startIdentify(filepath);
                }
                else if(resultCode==RESULT_CANCELED){
                    finish();
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK && data != null){
                    Uri uri = data.getData();
                    UriOnScreen = uri;
                    String url = UriOnScreen.toString();//getRealFilePath(thisupload,UriOnScreen);
                    //textofpath.setText(url);
                    Log.i("TAG",url);
                    //filepath = GetPathFromUri4kitkat.getPath(thisactivity,UriOnScreen);
                    //filepath = GetPathFromUri.getPath(thisupload,UriOnScreen);
                    //thisupload.grantUriPermission("com.example.urltest",UriOnScreen,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //检测动态权限
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                        return;
                    }
                    //将uri转换为path
                    filepath = UriToPathTool.getRealFilePath(thisupload,UriOnScreen);
                    //将path显示出来
                    System.out.println(filepath);
                    //path.setText(filepath);
                    Log.i("TAG",filepath);
                    realFilrPath = filepath;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    try{
                        //通过uri从流中读入图片
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        //解码流
                        BitmapFactory.decodeStream(inputStream,null,options);
                        //关闭流
                        inputStream.close();
                        //图片参数
                        int height = options.outHeight;
                        int width = options.outWidth;
                        int sampleSize = 1;
                        int max = Math.max(height,width);
                        if (max>MAX_SIZE){
                            int nw = width/2;
                            int nh = height/2;
                            while ((nw/sampleSize)> MAX_SIZE || (nh / sampleSize)>MAX_SIZE){
                                sampleSize *=2;
                            }
                        }
                        options.inSampleSize = sampleSize;
                        options.inJustDecodeBounds = false;
                        selectdBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,options);

                        //将选择的图片显示在界面上
                        imageView.setImageBitmap(selectdBitmap);
                    }catch (IOException ioe){
                        //Log.i(TAG, ioe.getMessage());
                    }
                    startIdentify(filepath);
                }
                else if(resultCode==RESULT_CANCELED){
                    finish();
                }
                break;
        }
    }


    private void startIdentify(String _filepath){
        Handler handlerhere = new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case UPLOAD_SUCCESS:
                        //识别成功，停止等待旋转
                        loading.stop();
                        //foodName.setText(intent.getStringExtra("foodName"));

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
        this.request(_filepath);
        //开始旋转
        loading.start();
    }

    public void request(String url) {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP.ip) // 设置 网络请求 Url
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
                    String text = "";

                    if(response.body()==null) {
                        MyToast.mytoast("识别失败！(识别结果为空)", getApplicationContext());
                        Message message = new Message();
                        message.what = UPLOAD_FAILED;
                        handler.sendMessage(message);
                    }
                    else{
                        foodRegList = response.body();
                        MyToast.mytoast("识别成功！",getApplicationContext());
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
                    MyToast.mytoast("上传失败，请检查网络情况！",getApplicationContext());
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