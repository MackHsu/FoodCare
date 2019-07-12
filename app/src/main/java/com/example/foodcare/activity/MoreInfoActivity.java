package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.FoodPackage.GetFoodById.GetFoodByIdTest;
import com.victor.loading.rotate.RotateLoading;

public class MoreInfoActivity extends AppCompatActivity {
    private TextView foodName;
    private ImageButton backButton;
    private RotateLoading loading;
    private TextView celluloseText;
    private TextView vAText;
    private TextView vCText;
    private TextView vEText;
    private TextView caroteneText;
    private TextView liuanText;
    private TextView hehuangText;
    private TextView yansuanText;
    private TextView cholesterolText;
    private TextView meiText;
    private TextView gaiText;
    private TextView tieText;
    private TextView xinText;
    private TextView tongText;
    private TextView mengText;
    private TextView jiaText;
    private TextView linText;
    private TextView naText;
    private TextView xiText;

    private final int GET_FOOD_DETAIL_SUCCESS = 1;
    private final int GET_FOOD_DETAIL_FAILED = 0;
    int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        initViews();
        Intent intent = getIntent();
        foodId = intent.getIntExtra("foodId", -1);
        final GetFoodByIdTest dataFetcher = new GetFoodByIdTest();
        loading.start();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case GET_FOOD_DETAIL_SUCCESS:
                        loading.stop();
                        loadData(dataFetcher.getFood());
                        break;
                    case GET_FOOD_DETAIL_FAILED:
                        loading.stop();
                        foodName.setText("Error");
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        dataFetcher.request(foodId, MoreInfoActivity.this);
    }

    private void initViews() {
        foodName = (TextView) findViewById(R.id.food_name);
        backButton = (ImageButton) findViewById(R.id.back_button);
        loading = (RotateLoading) findViewById(R.id.loading);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        celluloseText = (TextView) findViewById(R.id.cellulose_text);
        vAText = (TextView) findViewById(R.id.vA_text);
        vCText = (TextView) findViewById(R.id.vC_text);
        vEText = (TextView) findViewById(R.id.vE_text);
        caroteneText = (TextView) findViewById(R.id.carotene_text);
        liuanText = (TextView) findViewById(R.id.liuan_text);
        hehuangText = (TextView) findViewById(R.id.hehuang_text);
        yansuanText = (TextView) findViewById(R.id.yansuan_text);
        cholesterolText = (TextView) findViewById(R.id.cholesterol_text);
        meiText = (TextView) findViewById(R.id.mei_text);
        gaiText = (TextView) findViewById(R.id.gai_text);
        tieText = (TextView) findViewById(R.id.tie_text);
        xinText = (TextView) findViewById(R.id.xin_text);
        tongText = (TextView) findViewById(R.id.tong_text);
        mengText = (TextView) findViewById(R.id.meng_text);
        jiaText = (TextView) findViewById(R.id.jia_text);
        linText = (TextView) findViewById(R.id.lin_text);
        naText = (TextView) findViewById(R.id.na_text);
        xiText = (TextView) findViewById(R.id.xi_text);
    }

    private void loadData(Food food) {
        if(food == null) {
            Toast.makeText(this, "返回错误", Toast.LENGTH_SHORT).show();
            return;
        }
        foodName.setText(food.getName());
        celluloseText.setText(food.getCellulose() == null ? "----" : food.getCellulose() + "克/100克");
        vAText.setText(food.getVitaminA() == null ? "----" : food.getVitaminA() + "毫克/100克");
        vCText.setText(food.getVitaminC() == null ? "----" : food.getVitaminC() + "毫克/100克");
        vEText.setText(food.getVitaminE() == null ? "----" : food.getVitaminE() + "毫克/100克");
        caroteneText.setText(food.getCarotene() == null ? "----" : food.getCarotene() + "毫克/100克");
        liuanText.setText(food.getLiuan() == null ? "----" : food.getLiuan() + "毫克/100克");
        hehuangText.setText(food.getHehuang() == null ? "----" : food.getHehuang() + "毫克/100克");
        yansuanText.setText(food.getYansuan() == null ? "----" : food.getYansuan() + "毫克/100克");
        cholesterolText.setText(food.getCholesterol() == null || food.getCholesterol() == 0 ? "----" : food.getCholesterol() + "毫克/100克");
        meiText.setText(food.getMei() == null ? "----" : food.getMei() + "毫克/100克");
        gaiText.setText(food.getGai() == null ? "----" : food.getGai() + "毫克/100克");
        tieText.setText(food.getTie() == null ? "----" : food.getTie() + "毫克/100克");
        xinText.setText(food.getXin() == null ? "----" : food.getXin() + "毫克/100克");
        tongText.setText(food.getTong() == null ? "----" : food.getTong() + "毫克/100克");
        mengText.setText(food.getMeng() == null ? "----" : food.getMeng() + "毫克/100克");
        jiaText.setText(food.getJia() == null ? "----" : food.getJia() + "毫克/100克");
        linText.setText(food.getLin() == null ? "----" : food.getLin() + "毫克/100克");
        naText.setText(food.getNa() == null ? "----" : food.getNa() + "毫克/100克");
        xiText.setText(food.getXi() == null ? "----" : food.getXi() + "毫克/100克");
    }
}
