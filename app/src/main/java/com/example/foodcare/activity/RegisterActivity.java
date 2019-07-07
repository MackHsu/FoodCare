package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.AccountLog;
import com.example.foodcare.Retrofit.User.UserExist.UserExistInterface;
import com.example.foodcare.Retrofit.User.UserLogin.UserLoginInterface;
import com.example.foodcare.Retrofit.User.UserRegister.UserRegisterInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.tools.SaveFile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.Gravity.CENTER;

public class RegisterActivity extends AppCompatActivity {
    final public int REGISTER=1;
    final public int ACCOUNT_EXIST=3;
    final public int ACCOUNT_ONLY=4;
    final public int REGISTER_ERROR=2;
    private TextView account;
    private TextView password;
    private TextView pwd_confirm;
    private ImageButton back;
    private Button register;
    private TextView exist_or_only;
    private TextView password_length;
    private boolean is_pwd_useable;
    private boolean is_account_useable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account=(TextView) findViewById(R.id.username_register);
        password=(TextView)findViewById(R.id.password_register);
        pwd_confirm=(TextView)findViewById(R.id.password_register_confirm);
        back=(ImageButton)findViewById(R.id.back_register);
        exist_or_only=(TextView)findViewById(R.id.exist_register);
        password_length=(TextView)findViewById(R.id.password_length_register);
        is_pwd_useable=false;
        is_account_useable=false;
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.putExtra("user","");
                intent.putExtra("password","");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        register=(Button)findViewById(R.id.register_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notNull()){
                    return;
                }
                if(!is_account_useable){
                    MyToast.mytoast("账号不符合规则",getApplicationContext());
                    return;
                }
                if(!is_pwd_useable){
                    MyToast.mytoast("密码不符合规则",getApplicationContext());
                    return;
                }
                registerRight();
            }
        });
        password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyToast.mytoast("密码必须包含大小写和数字",getApplicationContext());
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString();
                if ((pwd.length() < 8) || pwd.length() > 14) {
                    password_length.setText("密码长度必须在8-14位之间");
                    password_length.setTextColor(getResources().getColor(R.color.exist_account));
                    password_length.setVisibility(View.VISIBLE);
                    return;
                }
                boolean has_shu_zi = false;
                for (int i = 0; i < pwd.length(); i++) {
                    if (Character.isDigit(pwd.charAt(i))) {
                        has_shu_zi=true;
                        break;
                    }
                }
                if(!has_shu_zi){
                    password_length.setText("密码中必须含有数字");
                    password_length.setTextColor(getResources().getColor(R.color.exist_account));
                    password_length.setVisibility(View.VISIBLE);
                    return;
                }
                char c;
                boolean has_little=false;
                for(int i=0;i<pwd.length();i++){
                    c= pwd.charAt(i);
                    if((c>='a'&&c<='z')){
                        has_little=true;
                        break;
                    }
                }
                if(!has_little){
                    password_length.setText("密码中必须含有小写字母");
                    password_length.setTextColor(getResources().getColor(R.color.exist_account));
                    password_length.setVisibility(View.VISIBLE);
                    return;
                }
                boolean has_large=false;
                for(int i=0;i<pwd.length();i++){
                    c= pwd.charAt(i);
                    if((c>='A'&&c<='Z')){
                        has_large=true;
                        break;
                    }
                }
                if(!has_large){
                    password_length.setText("密码中必须含有大写字母");
                    password_length.setTextColor(getResources().getColor(R.color.exist_account));
                    password_length.setVisibility(View.VISIBLE);
                    return;
                }
                password_length.setText("您设置得密码很安全");
                password_length.setTextColor(getResources().getColor(R.color.only_account));
                password_length.setVisibility(View.VISIBLE);
                is_pwd_useable=true;
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.mytoastUpper("账号必须是5-10位的纯数字",getApplicationContext());
            }
        });
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String _account=s.toString();
                boolean _all_digit=true;
                for(int i=0;i<_account.length();i++){
                    if (!Character.isDigit(_account.charAt(i))) {
                        _all_digit=false;
                        break;
                    }
                }
                if(!_all_digit){
                    Message message=new Message();
                    message.what=ACCOUNT_EXIST;
                    handler.sendMessage(message);
                    MyToast.mytoastUpper("账号必须是5-10位的纯数字",getApplicationContext());
                    return;
                }

                if((_account.length()<5)||(_account.length()>10)){
                    Message message=new Message();
                    message.what=ACCOUNT_EXIST;
                    handler.sendMessage(message);
                    return;
                }

                Exist_or_only(_account);


            }
        });
    }

    public void Exist_or_only(String _account){
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserExistInterface post = retrofit.create(UserExistInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(_account);
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("请求成功");
                if(response.body()==null){
                    MyToast.mytoast("服务器出错",getApplicationContext());
                }
                else{
                    boolean exist=response.body();
                    if(exist){
                        Message message=new Message();
                        message.what=ACCOUNT_EXIST;
                        handler.sendMessage(message);
                    }
                    else{
                        Message message=new Message();
                        message.what=ACCOUNT_ONLY;
                        handler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }

    private Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTER:
                    Intent intent=new Intent();
                    intent.putExtra("user",account.getText().toString().trim());
                    intent.putExtra("password",password.getText().toString().trim());
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
                case REGISTER_ERROR:
                    break;
                case ACCOUNT_EXIST:
                    exist_or_only.setText("用户已存在");
                    exist_or_only.setTextColor(getResources().getColor(R.color.exist_account));
                    exist_or_only.setVisibility(View.VISIBLE);
                    is_account_useable=false;
                    break;
                case ACCOUNT_ONLY:
                    exist_or_only.setText("账号可注册");
                    exist_or_only.setTextColor(getResources().getColor(R.color.only_account));
                    exist_or_only.setVisibility(View.VISIBLE);
                    is_account_useable=true;
                    default:
            }
        }
    };
    private boolean notNull(){
        boolean ok=false;
        String _account=account.getText().toString();
        String _password=password.getText().toString();
        if(_account.equals("")||_password.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"账号或密码空缺",Toast.LENGTH_SHORT);
            toast.setGravity(CENTER,0,0);
            toast.show();
            return ok;
        }
        String _pwd_confirm=pwd_confirm.getText().toString();
        if(_pwd_confirm.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请确认密码",Toast.LENGTH_SHORT);
            toast.setGravity(CENTER,0,0);
            toast.show();
            return ok;
        }
        if(!_pwd_confirm.equals(_password)){
            Toast toast=Toast.makeText(getApplicationContext(),"密码不一致",Toast.LENGTH_SHORT);
            toast.setGravity(CENTER,0,0);
            toast.show();
            return ok;
        }
        ok=true;
        return ok;
    }

    private void registerRight(){
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserRegisterInterface post = retrofit.create(UserRegisterInterface.class);
        System.out.println("建立post对象");
        Call<Boolean> call = post.getCall(account.getText().toString().trim(),password.getText().toString().trim());
        System.out.println("getcall");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                String text = "";
                text = response.body()+"/n";
                System.out.println("请求成功");
                if(response.body()==null){
                    MyToast.mytoast("服务器出错",getApplicationContext());
                    Message message=new Message();
                    message.what=REGISTER_ERROR;
                    handler.sendMessage(message);
                }
                else{
                    Boolean _register=response.body();
                    if(_register){
                        System.out.println(text);
                        MyToast.mytoast("注册成功",getApplicationContext());
                        Message message=new Message();
                        message.what=REGISTER;
                        handler.sendMessage(message);
                    }
                    else{
                        MyToast.mytoast("注册失败",getApplicationContext());
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }

    private boolean Is_Is_pwd_useable(String pwd){
        if ((pwd.length() < 8) || pwd.length() > 14) {
            return false;
        }
        boolean has_shu_zi=false;
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isDigit(pwd.charAt(i))) {
                has_shu_zi=true;
                break;
            }
        }
       if(!has_shu_zi){
           return false;
       }
        char c;
        boolean has_little=false;
        for(int i=0;i<pwd.length();i++){
            c= pwd.charAt(i);
            if((c>='a'&&c<='z')){
                has_little=true;
                break;
            }
        }
        if(!has_little){
            return false;
        }
        for(int i=0;i<pwd.length();i++){
            c= pwd.charAt(i);
            if((c>='A'&&c<='Z')){
                return true;
            }
        }
        return false;
    }
}
