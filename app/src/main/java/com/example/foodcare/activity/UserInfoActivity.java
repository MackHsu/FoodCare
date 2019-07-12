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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.User.UpdateUserInfo.UpdateUserInfoTest;
import com.example.foodcare.Retrofit.User.UploadAvatar.UploadAvatarTest;
import com.example.foodcare.Retrofit.User.UserInformation.UserInformationTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.UserInfo;
import com.example.foodcare.entity.AccountID;
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
    private final int ACCOUNT_GET_SUCCESS=8;
    private final int ACCOUNT_GET_FAILE=9;
    private final int ACCOUNT_UPDATA_SUCCESS=10;
    private final int ACCCOUNT_UPDATA_FAILE=11;
    private boolean changeInfo=false;
    private TextView name_account;
    private TextView age_account;
    private TextView weight_account;
    private TextView height_account;
    private TextView fat_rate_account;
    private TextView my_goal_account;
    private TextView my_sex_account;
    private TextView my_sport_account;
    private TextView back_text_view_info;
    private ImageView back_button_info;
    private Uri imageUri;
    private CircleTextImageView imageView;
    private int account_id;
    private Button button_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        account_id= AccountID.getId();
        name_account=(TextView)findViewById(R.id.name_account_r);
        age_account=(TextView)findViewById(R.id.age_account_r);
        weight_account=(TextView)findViewById(R.id.weight_account_r);
        height_account=(TextView)findViewById(R.id.height_account_r);
        fat_rate_account=(TextView)findViewById(R.id.fat_rate_account_r);
        my_goal_account=(TextView)findViewById(R.id.my_goal_account_r);
        my_sex_account=(TextView)findViewById(R.id.sex_account_r);
        my_sport_account=(TextView)findViewById(R.id.my_sport_account_r);
        button_submit=(Button)findViewById(R.id.submit_info);
        back_text_view_info=(TextView)findViewById(R.id.back_text_view_info);
        back_button_info=(ImageView)findViewById(R.id.back_button_info);
        back_button_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        back_text_view_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();

            }
        });

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
        //设置目标
        my_goal_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    final DialogPlus dialog = DialogPlus.newDialog(UserInfoActivity.this)
                            .setContentHolder(new ViewHolder(R.layout.dialog_item_account_goal))
                            .create();
                    Button button_keep = (Button) dialog.findViewById(R.id.bao_chi_jian_kang_info);
                    button_keep.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            my_goal_account.setText("保持健康");
                            changeInfo = true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_upper = (Button) dialog.findViewById(R.id.wo_yao_jian_zeng_zhong_info);
                    button_upper.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            my_goal_account.setText("我要增重");
                            changeInfo = true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_down = (Button) dialog.findViewById(R.id.wo_yao_jian_fei_info);
                    button_down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            my_goal_account.setText("我要减肥");
                            changeInfo = true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //设置性别
        my_sex_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    final DialogPlus dialog = DialogPlus.newDialog(UserInfoActivity.this)
                            .setContentHolder(new ViewHolder(R.layout.dialog_item_account_sex))
                            .create();
                    Button button_boy = (Button) dialog.findViewById(R.id.my_boy_info);
                    button_boy.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sex_account.setText("男");
                            changeInfo = true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_girl=(Button)dialog.findViewById(R.id.my_girl_info);
                    button_girl.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sex_account.setText("女");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("TAG","设置性别这出错了");
                }
            }
        });

        //设置运动类型
        my_sport_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    final DialogPlus dialog = DialogPlus.newDialog(UserInfoActivity.this)
                            .setContentHolder(new ViewHolder(R.layout.dialog_item_account_sport))
                            .create();
                    Button button_zero=(Button)dialog.findViewById(R.id.jiu_zuo_bu_dong_info);
                    button_zero.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sport_account.setText("久坐不动");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_one=(Button)dialog.findViewById(R.id.little_sport_info);
                    button_one.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sport_account.setText("轻度运动");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_two=(Button)dialog.findViewById(R.id.middle_sport_info);
                    button_two.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sport_account.setText("中度运动");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_three=(Button)dialog.findViewById(R.id.intensity_sport_info);
                    button_three.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sport_account.setText("重度运动");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    Button button_four=(Button)dialog.findViewById(R.id.high_sport_info);
                    button_four.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            my_sport_account.setText("高强度度运动");
                            changeInfo=true;
                            button_submit.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("TAG","运动设置点击事件这出错了");
                }
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                UpdataInfo();
            }
        });
        getAccountInfo(account_id);
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

    //
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

    //初始化界面
    private void getAccountInfo(final int _account_id){
        if(_account_id>0){
            final UserInformationTest userInformationTest=new UserInformationTest();
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    switch(msg.what)
                    {
                        case ACCOUNT_GET_SUCCESS:
                            String _use="";
                            //昵称那
                            try{
                                _use=userInformationTest.account.getName();
                                if(_use.equals("")){
                                    name_account.setText("请填写");
                                }
                                else{
                                    name_account.setText(_use);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                name_account.setText("请填写");
                            }

                            //是年龄那
                            try{
                                _use=userInformationTest.account.getAge()+"";
                                if(_use.equals("0")){
                                    age_account.setText("请填写");
                                }
                                else{
                                    age_account.setText(_use+"岁");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                age_account.setText("请填写");
                            }
                            //体重那
                            try{
                                _use=userInformationTest.account.getWeight()+"";
                                if(_use.equals("null")){
                                    weight_account.setText("请填写");
                                }
                                else{
                                    weight_account.setText(_use+"kg");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                weight_account.setText("请填写");
                            }
                            //身高那
                            try{
                                _use=userInformationTest.account.getHeight()+"";
                                if(_use.equals("null")){
                                    height_account.setText("请填写");
                                }
                                else{
                                    height_account.setText(_use+"cm");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                height_account.setText("请填写");
                            }
                            //体脂率那
                            try{
                                _use=userInformationTest.account.getFatRate()+"";
                                if(_use.equals("null")){
                                    fat_rate_account.setText("请填写");
                                }
                                else{
                                    fat_rate_account.setText(_use+"%");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                fat_rate_account.setText("请填写");
                            }
                            //目标那
                            int goal=userInformationTest.account.getPlan();
                            switch (goal){
                                case 0:
                                    my_goal_account.setText("保持健康");
                                    break;
                                case 1:
                                    my_goal_account.setText("我想减肥");
                                    break;
                                case 2:
                                    my_goal_account.setText("我想增重");
                                    break;
                                    default:
                                        break;
                            }
                            //性别这
                            int _info=-1;
                            try{
                                _info=userInformationTest.account.getSex();
                                switch (_info){
                                    case 0:
                                        my_sex_account.setText("女");
                                        break;
                                    case 1:
                                        my_sex_account.setText("男");
                                        break;
                                        default:
                                            my_sex_account.setText("请填写");//如果没出错的话，是执行不到这的
                                            break;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                my_sex_account.setText("请填写");
                            }

                            //运动这
                            _info=-1;
                            try{
                                _info=userInformationTest.account.getLevel();
                                switch (_info){
                                    case 0:
                                        my_sport_account.setText("久坐不动");
                                        break;
                                    case 1:
                                        my_sport_account.setText("轻度运动");
                                        break;
                                    case 2:
                                        my_sport_account.setText("中度运动");
                                        break;
                                    case 3:
                                        my_sport_account.setText("重度运动");
                                        break;
                                    case 4:
                                        my_sport_account.setText("高强度运动");
                                        break;
                                        default:
                                            my_sport_account.setText("请填写");
                                            break;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.i("TAG","在个人信息界面，请求运动者出错了");
                                my_sport_account.setText("请填写");
                            }
                            //头像这
                           try{
                                String _picture= IP.ip+userInformationTest.account.getPicture();
                                System.out.println(_picture);
                                Glide.with(getApplicationContext()).load(_picture).into(imageView);
                            }catch (Exception e){
                              // Glide.with(getApplicationContext()).load("https://cn.bing.com/images/search?view=detailV2&ccid=ZFeZmWyt&id=3E1BD5A3E0751E891E36CDAD65A71AA8F3CF185B&thid=OIP.ZFeZmWytRtwrRsuEXhSTUAHaEo&mediaurl=http%3a%2f%2fwww.desktopwallpaperhd.net%2fwallpapers%2f10%2fd%2fdongwushijie-animal-tupian-107405.jpg&exph=1200&expw=1920&q=tupian&simid=608003712619185345&selectedIndex=0&ajaxhist=0").into(imageView);
                                Log.i("TAG","头像出错");
                                e.printStackTrace();
                            }

                            break;
                        case ACCOUNT_GET_FAILE:
                            MyToast.mytoast("获取用户信息失败",UserInfoActivity.this);
                    }
                }
            };
            userInformationTest.setHandler(handler);
            userInformationTest.request(_account_id,getApplicationContext());
        }
        else{
            MyToast.mytoast("用户id出错",getApplicationContext());
        }

    }

    //这个是改变头像的，即改即提交
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

    //启动下个界面的函数
    private void myStartActivityForREsult(String title,int code){
        //两个参数一个是下个界面的标题，一个是下个界面的返回码
        Intent intent=new Intent(getApplicationContext(),ChangeAccountInfoActivity.class);
        intent.putExtra("change_title",title);
        startActivityForResult(intent,code);
    }

    //判断从上个界面回来的时候是否改变信息
    private boolean judgeChange(String s){
        if(s.equals("未确定")){
            return false;
        }
        changeInfo=true;
        button_submit.setVisibility(View.VISIBLE);
        return true;
    }

    private void UpdataInfo(){
        Account account=new Account();
        account.setId(AccountID.getId());

        String info=age_account.getText().toString().trim();
        if(info.equals("请填写")){
            account.setAge(0);
        }
        else{
            String[] _age=info.split("岁");
            account.setAge(Integer.valueOf(_age[0]));
        }

        account.setName(name_account.getText().toString().trim());

        info=weight_account.getText().toString().trim();
        if(info.equals("请填写")){
            account.setWeight(0.0);
        }else{
            String[] _weight=info.split("kg");
            account.setWeight(Double.valueOf(_weight[0]));
        }

        info=height_account.getText().toString().trim();
        if(info.equals("请填写")){
            account.setHeight(0.0);
        }
        else{
            String[] _height=info.split("cm");
            account.setHeight(Double.valueOf(_height[0]));
        }

        info=fat_rate_account.getText().toString().trim();
        if(info.equals("请填写")){
            account.setFatRate(1.0);
        }
        else{
            String[] _fat_rate=info.split("%");
            account.setFatRate(Double.valueOf(_fat_rate[0]));
        }

        String _goal=my_goal_account.getText().toString().trim();
        int goal=-1;
        switch (_goal){
            case "保持健康":
                goal=0;
                break;
            case "我要增重":
                goal=2;
                break;
            case "我要减肥":
                goal=1;
                break;
        }
        account.setPlan(goal);
        String _sex=my_sex_account.getText().toString().trim();
        int sex=-1;
        switch (_sex){
            case "男":
                sex=1;
                break;
            case "女":
                sex=0;
                break;
            case "请填写":
                sex=0;
                break;
                default:
                    sex=0;
                    break;
        }
        account.setSex(sex);
        String _sport=my_sport_account.getText().toString().trim();
        int sport=-1;
        switch (_sport){
            case "久坐不动":
                sport=0;
                break;
            case "轻度运动":
                sport=1;
                break;
            case "中度运动":
                sport=2;
                break;
            case "重度运动":
                sport=3;
                break;
            case "高强度运动":
                sport=4;
                break;
            case "请填写":
                sport=1;
                break;
                default:
                    sport=0;
                    break;
        }
        account.setLevel(sport);
        UpdateUserInfoTest updateUserInfoTest=new UpdateUserInfoTest();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch(msg.what)
                {
                    case ACCOUNT_UPDATA_SUCCESS:
                        MyToast.mytoast("保存成功！",UserInfoActivity.this);
                        break;
                    case ACCCOUNT_UPDATA_FAILE:
                        MyToast.mytoast("保存失败",UserInfoActivity.this);
                }
            }
        };
        updateUserInfoTest.setHandler(handler);
        updateUserInfoTest.request(account,getApplicationContext());
    }
}
