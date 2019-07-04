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

public class ChangeAccountInfoActivity extends AppCompatActivity {
    public String resultInfo="未确定";
    private Toolbar toolbar;
    Intent resultIntent=new Intent();
    private TextView changeTitle;
    private EditText changeInfo;
    private Button changeSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_info);
        toolbar=findViewById(R.id.toolbar_account_info);
        changeTitle=(TextView)findViewById(R.id.change_account_info_activity_class);
        Intent getIntent=getIntent();
        String getData=getIntent.getStringExtra("change_title");
        changeTitle.setText(getData);
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
                backTo();
                finish();
            }
        });
    }

    public void backTo(){
        resultIntent.putExtra("info",resultInfo);
        setResult(RESULT_OK,resultIntent);
    }
}
