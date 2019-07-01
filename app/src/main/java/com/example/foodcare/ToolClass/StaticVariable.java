package com.example.foodcare.ToolClass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaticVariable {
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;

    static public Retrofit CreateRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    //下面函数用于将Byte转换成Bitmap，之后ImageView的实例调用setImageBitmap(bitmap)方法，该方法的参数是下面这个方法的返回值
    static public Bitmap BytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    static public Byte[] BitmapToByte(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Byte[] bytes=new Byte[byteArray.length];
        for(int i=0;i<byteArray.length;i++){
            bytes[i]=new Byte(byteArray[i]);
        }
        return bytes;
    }
    /*static public byte[] TabkePhoto(View v){
        Uri imageUri;

        File outputImage=new File(getExternalCacheDir(),"output_iamge.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(v.getContext(),"com.example.search.fileprovider"，)
        }
        else{
            imageUri=Uri.fromFile(outputImage);
        }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
    }*/
}
