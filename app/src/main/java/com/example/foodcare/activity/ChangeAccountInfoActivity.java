package com.example.foodcare.activity;

import android.content.Intent;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.ToolClass.MyToast;

public class ChangeAccountInfoActivity extends AppCompatActivity {
    public String resultInfo="未确定";
    private Toolbar toolbar;
    Intent resultIntent=new Intent();
    private TextView changeTitle;
    private EditText changeInfo;
    private Button changeSubmit;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_info);
        toolbar=findViewById(R.id.toolbar_account_info);
        changeTitle=(TextView)findViewById(R.id.change_account_info_activity_class);
        Intent getIntent=getIntent();
        String getData=getIntent.getStringExtra("change_title");
        changeTitle.setText(getData);
        title=getData;
        changeInfo=(EditText) findViewById(R.id.change_account_info_activity_text);
        changeSubmit=(Button)findViewById(R.id.change_account_info_activity_submit);
        toolbar.setTitle("< 更改个人信息");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backTo();
                finish();
            }
        });

        changeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeInfo.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "请填写信息",  Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                resultInfo=changeInfo.getText().toString();
                if(!checkInfo(resultInfo)){
                    return;
                }
                backTo();
                finish();
            }
        });
    }

    public void backTo(){
        resultIntent.putExtra("info",resultInfo);
        setResult(RESULT_OK,resultIntent);
    }
    private boolean checkInfo(String info){
        switch (title){
            case "用户名/昵称":
                if(info.length()>10){
                    MyToast.mytoast("您的昵称过长,请少于10个汉字或字母",getApplicationContext());
                    return false;
                }
                else{
                    return true;
                }
            case "年龄":
                return checkAge(info);
            case "体重":
                return checkWeight(info);
            case "身高":
                return checkHeight(info);
            case "体脂率":
                return checkFlat(info);
                default:
                    break;
        }
        return false;
    }
    private boolean checkAge(String info){
        try{
            int age=Integer.valueOf(info);
            if(age>120){
                MyToast.mytoast("您输入的年龄过大",getApplicationContext());
                return false;
            }
            if(age<1){
                MyToast.mytoast("您输入的年龄过小",getApplicationContext());
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            MyToast.mytoast("您输入的不是整数",getApplicationContext());
            return false;
        }
    }

    private boolean checkWeight(String info){
        try{
            double weight=Double.valueOf(info);
            if(weight>200.0){
                MyToast.mytoast("您输入的体重过大",getApplicationContext());
                return false;
            }
            if(weight<5.0){
                MyToast.mytoast("您输入的体重过小",getApplicationContext());
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            MyToast.mytoast("您输入的不是数字",getApplicationContext());
            return false;
        }
    }

    private boolean checkHeight(String info){
        try{
            double height=Double.valueOf(info);
            if(height>250.0){
                MyToast.mytoast("您输入的身高过高",getApplicationContext());
                return false;
            }
            if(height<40.0){
                MyToast.mytoast("您输入的身高过矮",getApplicationContext());
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            MyToast.mytoast("您输入的不是数字",getApplicationContext());
            return false;
        }
    }

    private boolean checkFlat(String info){
        try{
            double flat=Double.valueOf(info);
            if(flat>99.0){
                MyToast.mytoast("您输入的体脂率过高",getApplicationContext());
                return false;
            }
            if(flat<0.0){
                MyToast.mytoast("您输入的体脂率过低",getApplicationContext());
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            MyToast.mytoast("您输入的不是0-100的数字",getApplicationContext());
            return false;
        }
    }
}
