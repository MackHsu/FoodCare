package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.FoodPackage.FoodMap.FoodMapTest;
import com.example.foodcare.ToolClass.IP;

public class MealInfoActivity extends AppCompatActivity {

    private int foodId;

    ImageButton backButton;
    TextView foodName;
    ImageView foodImage;
    ImageView lightImage;
    TextView heatText;
    TextView tanshuiText;
    TextView fatText;
    TextView proteinText;
    TextView measureText;
    CardView measureCard;
    View measureDivider;
    TextView moreText;

    private final int GET_FOOD_DETAIL_SUCCESS=1;
    private final int GET_FOOD_DETAIL_FAILED=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_info);

        //初始化
        initViews();

        Intent intent = getIntent();
        foodId = intent.getIntExtra("foodId", -1);
        final FoodMapTest dataFetcher = new FoodMapTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_FOOD_DETAIL_SUCCESS:
                        showInfo(dataFetcher.getFoodMap().getFood());
                        break;
                    case GET_FOOD_DETAIL_FAILED:
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        dataFetcher.request(foodId, this);
    }

    private void initViews() {
        foodName = (TextView) findViewById(R.id.food_name);
        foodImage = (ImageView) findViewById(R.id.food_image);
        lightImage = (ImageView) findViewById(R.id.light_image);
        heatText = (TextView) findViewById(R.id.heat_text);
        tanshuiText = (TextView) findViewById(R.id.tanshui_text);
        fatText = (TextView) findViewById(R.id.fat_text);
        proteinText = (TextView) findViewById(R.id.protein_text);
        measureText = (TextView) findViewById(R.id.measure_text);
        measureCard = (CardView) findViewById(R.id.measure_card);
        backButton = (ImageButton) findViewById(R.id.back_button);
        measureDivider = (View) findViewById(R.id.measure_divider);
        moreText = (TextView) findViewById(R.id.more);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealInfoActivity.this, MoreInfoActivity.class);
                intent.putExtra("foodId", foodId);
                startActivity(intent);
            }
        });
    }

    private void showInfo(Food food) {
        foodName.setText(food.getName());
        Glide.with(this).load(IP.ip + food.getPicture_high()).into(foodImage);
        Glide.with(this).load(IP.ip + food.getLight()).into(lightImage);
        heatText.setText(food.getHeat() + "");
        tanshuiText.setText(food.getTanshui() + "克");
        fatText.setText(food.getFat() + "克");
        proteinText.setText(food.getProtein() + "克");
        if(food.getMeasure() == null || food.getMeasure() == "") {
            measureCard.setVisibility(View.GONE);
            measureDivider.setVisibility(View.GONE);
        }
        else measureText.setText(food.getMeasure());
    }
}
