package com.example.foodcare.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.User.UploadAvatar.UploadAvatarTest;
import com.example.foodcare.ToolClass.MyToast;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UserInfoActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    public static final int USER_NAME=3;
    public static final int USER_AGE=4;
    public static final int USER_WEIGHT=5;
    public static final int USER_HEIGHT=6;
    public static final int USER_FAT_RATE=7;
    private final int UPLOAD_SUCCESS=0;
    private final int UPLOAD_FAILE =1;
    private TextView name_account;
    private TextView age_account;
    private TextView weight_account;
    private TextView height_account;
    private TextView fat_rate_account;
    private Uri imageUri;
    private CircleTextImageView imageView;
    private int account_id = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        name_account=(TextView)findViewById(R.id.name_account_r);
        age_account=(TextView)findViewById(R.id.age_account_r);
        weight_account=(TextView)findViewById(R.id.weight_account_r);
        height_account=(TextView)findViewById(R.id.height_account_r);
        fat_rate_account=(TextView)findViewById(R.id.fat_rate_account_r);
        name_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivityForREsult("用户名/昵称",USER_NAME);
            }
        });
        age_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivityForREsult("年龄",USER_AGE);
            }
        });
        height_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myStartActivityForREsult("身高",USER_HEIGHT);
            }
        });
        weight_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myStartActivityForREsult("体重",USER_WEIGHT);
            }
        });
        fat_rate_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myStartActivityForREsult("体脂率",USER_FAT_RATE);
            }
        });


        imageView=(CircleTextImageView)findViewById(R.id.profile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(UserInfoActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_items))
                        .create();

                Button buttonTake=(Button)dialog.findViewById(R.id.take_photo_item_dialog);
                Button buttonSelect=(Button)dialog.findViewById(R.id.select_photo_item_dialog);
                //拍照监听器
                buttonTake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //创建缓存拍照文件
                        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
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
                            try{
                                imageUri= FileProvider.getUriForFile(UserInfoActivity.this,"com.example.search.fileprovider",outputImage);
                            }
                            catch (Exception e){
                                Toast.makeText(UserInfoActivity.this,"faile to get image ",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            imageUri=Uri.fromFile(outputImage);
                        }
                        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
                        dialog.dismiss();
                    }
                });
                //选择相册监听器
                buttonSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(UserInfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        }
                        else{
                            openAlbum();
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this,"everything ok",Toast.LENGTH_SHORT).show();
                    }catch (FileNotFoundException e){
                        Toast.makeText(this,"something wrong",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }
                    else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case USER_NAME:
                if(resultCode==RESULT_OK){
                    String s=data.getStringExtra("info");
                    if(judgeChange(s)){
                        name_account.setText(s);
                    }
                }
                break;
            case USER_AGE:
                if(resultCode==RESULT_OK){
                    String s=data.getStringExtra("info");
                    if(judgeChange(s)){
                        age_account.setText(s+"岁");
                    }
                }
                break;
            case USER_WEIGHT:
                if(resultCode==RESULT_OK){
                    String s=data.getStringExtra("info");
                    if(judgeChange(s)){
                        weight_account.setText(s+"kg");
                    }
                }
                break;
            case USER_HEIGHT:
                if(resultCode==RESULT_OK){
                    String s=data.getStringExtra("info");
                    if(judgeChange(s)){
                        height_account.setText(s+"cm");
                    }
                }
                break;
            case USER_FAT_RATE:
                if(resultCode==RESULT_OK){
                    String s=data.getStringExtra("info");
                    if(judgeChange(s)){
                        fat_rate_account.setText(s+"%");
                    }
                }
                break;
            default:
                break;
        }
    }
//相册选择相关
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docID=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docID.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath=getImagePath(contentUri,null);
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }
//相册选择相关
    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(((Cursor) cursor).moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(final String imagePath){
        if(imagePath!=null){
            //先上传头像到服务器，上传成功再显示头像
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    switch(msg.what)
                    {
                        case UPLOAD_SUCCESS:
                            MyToast.mytoast("修改头像！",UserInfoActivity.this);
                            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
                            imageView.setImageBitmap(bitmap);
                            break;
                        case UPLOAD_FAILE:
                            MyToast.mytoast("修改失败",UserInfoActivity.this);
                    }
                }
            };

            UploadAvatarTest uploadhere = new UploadAvatarTest();
            uploadhere.setHandler(handler);
            uploadhere.request(imagePath,account_id,UserInfoActivity.this);
        }else{
            Toast.makeText(this,"faile to get image",Toast.LENGTH_SHORT).show();
        }
    }
    private void myStartActivityForREsult(String title,int code){
        //两个参数一个是下个界面的标题，一个是下个界面的返回码
        Intent intent=new Intent(getApplicationContext(),ChangeAccountInfoActivity.class);
        intent.putExtra("change_title",title);
        startActivityForResult(intent,code);
    }
    private boolean judgeChange(String s){
        if(s.equals("未确定")){
            return false;
        }
        return true;
    }
}
