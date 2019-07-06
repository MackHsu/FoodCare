package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;

import static android.view.Gravity.CENTER;

public class RegisterActivity extends AppCompatActivity {
    private TextView account;
    private TextView password;
    private TextView pwd_confirm;
    private ImageButton back;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account=(TextView) findViewById(R.id.username_register);
        password=(TextView)findViewById(R.id.password_register);
        pwd_confirm=(TextView)findViewById(R.id.password_register_confirm);
        back=(ImageButton)findViewById(R.id.back_register);
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
                if(registerRight()){
                    String _account=account.getText().toString();
                    String _password=password.getText().toString();
                    Intent intent=new Intent();
                    intent.putExtra("user",_account);
                    intent.putExtra("password",_password);
                    setResult(RESULT_OK,intent);
                    finish();
            }
            }
        });
    }

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

    private boolean registerRight(){
        boolean ok=false;

        return ok;
    }
}
