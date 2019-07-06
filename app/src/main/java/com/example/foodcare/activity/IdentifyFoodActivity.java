package com.example.foodcare.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.Interfaces.IdentifyFoodInterface;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.FoodRank;
import com.example.foodcare.Retrofit.RetrofitTools.NullOnEmptyConverterFactory;
import com.example.foodcare.Retrofit.RetrofitTools.UriToPathTool;
import com.example.foodcare.ToolClass.IP;

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

public class IdentifyFoodActivity extends AppCompatActivity {

    Handler handler;
    public final int UPDATE_DATA =1;

    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private String photodir = "/storage/emulated/0/FoodCarePhoto";
    private String photoPath ="/storage/emulated/0/FoodCarePhoto/CachePhoto.jpg";
    private String realFilrPath;
    private Bitmap selectdBitmap;
    private  int MAX_SIZE = 769;
    IdentifyFoodActivity thisupload = this;
    Uri UriOnScreen;
    ImageView imageView;
    Button albumbutton;
    Button uploadbutton;
    Button camerafile;
    TextView textofpath ;
    TextView path;
    IdentifyFoodActivity thisactivity;
    String filepath;
    List<FoodRank> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_food);thisactivity = this;
        imageView = (ImageView) findViewById(R.id.imageviewfilr);
        albumbutton = (Button) findViewById(R.id.albumfile);
        uploadbutton = (Button) findViewById(R.id.uploadbuttonfile);
        textofpath= (TextView) findViewById(R.id.textofpath);
        path = (TextView) findViewById(R.id.path);

        //解决相机拍照问题!!!!!!
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //选择相册监听器
        albumbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Browser Image..."),CHOOSE_PHOTO);
            }
        });
        //拍照监听器
        camerafile = (Button) findViewById(R.id.camerafile);
        camerafile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        UriOnScreen= FileProvider.getUriForFile(IdentifyFoodActivity.this,"com.example.search.fileprovider",photoFile);
                        textofpath.setText(UriOnScreen.toString());
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
        });

        //上传图片
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                //realFilrPath = UriToPathTool.getRealFilePath(thisupload,UriOnScreen);
                System.out.println(realFilrPath);
                System.out.println(filepath);
                //String url = getRealPathFromUri(thisupload,UriOnScreen);
                //textofpath.setText(url);
                request(filepath);
            }
        });

    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case TAKE_PHOTO:
                //选择照相返回
                if ( resultCode == RESULT_OK) {
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
                    path.setText(filepath);
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
                }
                break;
            case CHOOSE_PHOTO:
                //选择相册返回
                if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK && data != null){
                    Uri uri = data.getData();
                    UriOnScreen = uri;
                    String url = UriOnScreen.toString();//getRealFilePath(thisupload,UriOnScreen);
                    textofpath.setText(url);
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
                    path.setText(filepath);
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

                }
                break;
            default:
                break;
        }



    }
    //将uri转换为在文件系统中的真实路径
//    public String getRealFilePath(final Context context, final Uri uri) {
//        if ( null == uri ) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if ( scheme == null )
//            data = uri.getPath();
//        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
//            data = uri.getPath();
//        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
//            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
//            if ( null != cursor ) {
//                if ( cursor.moveToFirst() ) {
//                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
//                    if ( index > -1 ) {
//                        data = cursor.getString( index );
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }
    //新版本-----------------上传图像
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
        Call<List<FoodRank>> call = request.getCall(body);
        try{
            call.enqueue(new Callback<List<FoodRank>>() {
                //请求成功时回调
                @Override
                public void onResponse(Call<List<FoodRank>> call, Response<List<FoodRank>> response) {
                    // 步骤7：处理返回的数据结果
                    //data = response.body();
                    System.out.println("请求成功");
                    String text = "";
                  /*  Message message = new Message();
                    message.what = UPDATE_DATA;
                    handler.sendMessage(message);
*/
                    for (FoodRank foodrank: response.body()) {
                        text = foodrank.getFoodname()+foodrank.getProbability()+"/n";
                        System.out.println(foodrank.getFoodname()+foodrank.getProbability());
                    }
                    Toast toast=Toast.makeText(IdentifyFoodActivity.this,text,Toast.LENGTH_SHORT    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                //请求失败时回调
                @Override
                public void onFailure(Call<List<FoodRank>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    System.out.println("连接失败");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
