package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.FoodPackage.GetFoodById.GetFoodByIdTest;
import com.example.foodcare.ToolClass.IP;
import com.victor.loading.rotate.RotateLoading;

public class FoodInfoActivity extends AppCompatActivity {
    private TextView foodName;
    private ImageButton backButton;
    private RotateLoading loading;
    private ImageView foodImage;
    private ImageView lightImage;
    private TextView heatText;
    private TextView fatText;
    private TextView tanshuiText;
    private TextView proteinText;
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
    private TextView meiLabel;
    private TextView gaiText;
    private TextView tieText;
    private TextView xinText;
    private TextView tongText;
    private TextView mengText;
    private TextView jiaText;
    private TextView linText;
    private TextView naText;
    private TextView xiText;
    private TextView measureText;
    private TextView evaluateText;
    private TextView ingredientText;
    private TextView excipientText;
    private TextView practiceText;
    private TextView cookText;

    private final int GET_FOOD_DETAIL_SUCCESS = 1;
    private final int GET_FOOD_DETAIL_FAILED = 0;
    int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
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
        dataFetcher.request(foodId, FoodInfoActivity.this);
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
        meiLabel = (TextView) findViewById(R.id.mei_label);
        gaiText = (TextView) findViewById(R.id.gai_text);
        tieText = (TextView) findViewById(R.id.tie_text);
        xinText = (TextView) findViewById(R.id.xin_text);
        tongText = (TextView) findViewById(R.id.tong_text);
        mengText = (TextView) findViewById(R.id.meng_text);
        jiaText = (TextView) findViewById(R.id.jia_text);
        linText = (TextView) findViewById(R.id.lin_text);
        naText = (TextView) findViewById(R.id.na_text);
        xiText = (TextView) findViewById(R.id.xi_text);
        measureText = (TextView) findViewById(R.id.measure_text);
    }

    private void loadData(Food food) {
        if(food == null) {
            Toast.makeText(this, "返回错误", Toast.LENGTH_SHORT).show();
            return;
        }
        foodName.setText(food.getName());
        Glide.with(this).load(IP.ip + food.getPicture_high()).into(foodImage);
        Glide.with(this).load(IP.ip + food.getLight()).into(lightImage);
        heatText.setText(food.getHeat() + "千卡/100克");
        fatText.setText(food.getFat() == null ? "-" : food.getFat() + "克/100克");
        tanshuiText.setText(food.getTanshui() == null ? "-" : food.getTanshui() + "克/100克");
        proteinText.setText(food.getProtein() == null ? "-" : food.getProtein() + "克/100克");
        celluloseText.setText(food.getCellulose() == null ? "-" : food.getCellulose() + "克/100克");
        vAText.setText(food.getVitaminA() == null ? "-" : food.getVitaminA() + "克/100克");
        vCText.setText(food.getVitaminC() == null ? "-" : food.getVitaminC() + "克/100克");
        vEText.setText(food.getVitaminE() == null ? "-" : food.getVitaminE() + "克/100克");
        caroteneText.setText(food.getCarotene() == null ? "-" : food.getCarotene() + "克/100克");
        liuanText.setText(food.getLiuan() == null ? "-" : food.getLiuan() + "克/100克");
        hehuangText.setText(food.getHehuang() == null ? "-" : food.getHehuang() + "克/100克");
        yansuanText.setText(food.getYansuan() == null ? "-" : food.getYansuan() + "克/100克");
        cholesterolText.setText(food.getCholesterol() == null ? "-" : food.getCholesterol() + "克/100克");
        meiText.setText(food.getMei() == null ? "-" : food.getMei() + "克/100克");
        gaiText.setText(food.getGai() == null ? "-" : food.getGai() + "克/100克");
        tieText.setText(food.getTie() == null ? "-" : food.getTie() + "克/100克");
        xinText.setText(food.getXin() == null ? "-" : food.getXin() + "克/100克");
        tongText.setText(food.getTong() == null ? "-" : food.getTong() + "克/100克");
        mengText.setText(food.getMeng() == null ? "-" : food.getMeng() + "克/100克");
        jiaText.setText(food.getJia() == null ? "-" : food.getJia() + "克/100克");
        linText.setText(food.getLin() == null ? "-" : food.getLin() + "克/100克");
        naText.setText(food.getNa() == null ? "-" : food.getNa() + "克/100克");
        xiText.setText(food.getXi() == null ? "-" : food.getXi() + "克/100克");
        measureText.setText(food.getMeasure() == null ? "-" : food.getMeasure() + "");
        evaluateText.setText(food.getEvaluate() == null ? "-" : food.getEvaluate());
        ingredientText.setText(food.getIngredient() == null ? "-" : food.getIngredient() + "");
        excipientText.setText(food.getExcipient() == null ? "-" : food.getExcipient() + "");
        practiceText.setText(food.getPractice() == null ? "-" : food.getPractice() + "");
        cookText.setText(food.getCook() == null ? "-" : food.getCook() + "");
    }
}
