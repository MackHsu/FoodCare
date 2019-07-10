package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.AccountLog;
import com.example.foodcare.Retrofit.User.UserLogin.UserLoginInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.ToolClass.SaveFile;

import java.util.Map;

//import pl.droidsonroids.gif.GifTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.Gravity.CENTER;


public class LoginActivity extends AppCompatActivity {
    final public int REGISTER=1;
    final public int LOGIN=2;
    final public int LOGIN_ERROR=3;
    private EditText username;
    private EditText userpass;
    private CheckBox checkBox;
    public Account account;
    //private GifTextView login;
    private Button login;
    private TextView reginster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username_login);
        userpass=(EditText)findViewById(R.id.password_login);
        checkBox=(CheckBox)findViewById(R.id.checkBox_login);
        Map<String, String> map=SaveFile.getSaveFiles(this);
        if(map!=null){
            String _username=map.get("username");
            String _userpass=map.get("userpass");
            String _land=map.get("landing_status");
            String __id=map.get("id").trim();
            int _id;
            try{
                _id=Integer.valueOf(__id);
            }catch (Exception e){
                _id=-1;
            }
            if(_land.equals("yes")){
                AccountID.setId(_id);
                Login(_username,_userpass,_land);
            }
            username.setText(_username);
            userpass.setText(_userpass);
        }

        login=(Button)findViewById(R.id.login_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = username.getText().toString().trim();
                String up = userpass.getText().toString().trim();
                if (TextUtils.isEmpty(un) || TextUtils.isEmpty(up)) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 登陆

                    // 登陆发送消息到服务器，服务器验证是否正确
                    LoginRight(un,up);
                        /*Toast.makeText(getApplicationContext(), "登陆成功",
                                Toast.LENGTH_SHORT).show();
                        Login(un,up,"yes");*/

                }

            }
        });
        /**跳去注册界面*/
        reginster=(TextView) findViewById(R.id.register_login);
        reginster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivityForResult(intent,REGISTER);
            }
        });
    }

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case LOGIN:
                    //是否保存密码
                    if (checkBox.isChecked()) {
                        // 保存用户名和密码
                        Log.i("TAG", "需要保存用户名密码");
                        boolean flag = SaveFile.save(getApplicationContext(), username.getText().toString().trim(), userpass.getText().toString().trim(), "yes", AccountID.getId());
                        if (flag) {
                            Toast.makeText(getApplicationContext(), "信息保存成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Login(username.getText().toString(),userpass.getText().toString(),"yes");
                    break;
                case LOGIN_ERROR:
                    boolean flag=SaveFile.save(getApplicationContext(), "","","no",-1);
                    if(flag){
                        Toast.makeText(getApplicationContext(), "清空信息成功", Toast.LENGTH_SHORT).show();
                    }
                default:
                    break;
            }
        }
    };

    private void LoginRight(String id,String pwd){
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        UserLoginInterface post = retrofit.create(UserLoginInterface.class);
        System.out.println("建立post对象");
        Call<AccountLog> call = post.getCall(username.getText().toString().trim(),userpass.getText().toString().trim());
        System.out.println("getcall");
        call.enqueue(new Callback<AccountLog>() {
            @Override
            public void onResponse(Call<AccountLog> call, Response<AccountLog> response) {
                System.out.println("请求成功");
                if(response.body()==null){
                    MyToast.mytoast("不存在该用户",LoginActivity.this);
                    Message message=new Message();
                    message.what=LOGIN_ERROR;
                    handler.sendMessage(message);
                }else{
                    boolean can_login=response.body().isCanLogin();
                    if(can_login){
                        account = response.body().getAccount();
                        System.out.println(account.getName());
                        Log.i("TAG","下面这个是id");
                        Log.i("TAG",account.getId()+"");
                        AccountID.setId(account.getId());
                        String text = "登陆成功";
                        text = account.getName()+"/n";
                        Toast toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT    );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Message message=new Message();
                        message.what=LOGIN;
                        handler.sendMessage(message);
                    }
                    else{
                        Toast toast=Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT    );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AccountLog> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case REGISTER:
                if(resultCode==RESULT_OK){
                    String userid=data.getStringExtra("user");
                    String pwd=data.getStringExtra("password");
                    if((!userid.equals(""))&&(!pwd.equals(""))){
                        username.setText(userid);
                        userpass.setText(pwd);
                    }
                    else{
                        Toast toast=Toast.makeText(getApplicationContext(),"未注册",Toast.LENGTH_SHORT);
                        toast.setGravity(CENTER,0,0);
                        toast.show();
                    }
                }
                break;
            case LOGIN:
                if(resultCode==RESULT_OK){
                    userpass.setText("");
                    username.setText("");
                }
                else if(resultCode==RESULT_CANCELED){
                    finish();
                }
            default:
        }
    }

    private void Login(String account,String pwd,String land){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("username",account);
        intent.putExtra("password",pwd);
        intent.putExtra("LandStatus",land);
        startActivityForResult(intent,LOGIN);
    }
}
