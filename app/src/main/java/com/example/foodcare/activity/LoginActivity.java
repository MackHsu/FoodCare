package com.example.foodcare.activity;

import android.content.Intent;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.tools.NullOnEmptyFactory;
import com.example.foodcare.tools.SaveFile;

import java.io.InputStream;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.Gravity.CENTER;

public class LoginActivity extends AppCompatActivity {
    final public int REGISTER=1;
    private EditText username;
    private EditText userpass;
    private CheckBox checkBox;
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
            if(_land.equals("yes")){
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
                    if (LoginRight(un,up)) {
                        //是否保存密码
                        if (checkBox.isChecked()) {
                            // 保存用户名和密码
                            Log.i("TAG", "需要保存用户名密码");
                            boolean flag= SaveFile.save(getApplicationContext(),un, up,"yes");
                            if(flag){
                                Toast.makeText(getApplicationContext(), "信息保存成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(getApplicationContext(), "登陆成功",
                                Toast.LENGTH_SHORT).show();
                        Login(un,up,"yes");
                    } else {
                        Toast.makeText(getApplicationContext(), "用户名或密码错误",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        reginster=(TextView) findViewById(R.id.register_login);
        reginster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivityForResult(intent,REGISTER);
            }
        });
    }


    private boolean LoginRight(String id,String pwd){
        boolean ok=false;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.238:8080/foodcare/") //TODO 更改这个ip
                .addConverterFactory(new NullOnEmptyFactory())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        
        return ok;
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
                default:
        }
    }

    private void Login(String account,String pwd,String land){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("username",account);
        intent.putExtra("password",pwd);
        intent.putExtra("LandStatus",land);
        startActivity(intent);
    }
}
