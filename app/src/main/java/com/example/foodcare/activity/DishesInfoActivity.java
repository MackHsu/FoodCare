package com.example.foodcare.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.textclassifier.TextClassificationSessionFactory;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.GetFoodById.GetFoodByIdTest;
import com.example.foodcare.ToolClass.IP;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Dictionary;
import java.util.List;

public class DishesInfoActivity extends AppCompatActivity {

    private int foodId;
    private List<Dictionary<String, String>> ingredientList;

    TextView foodName;
    ImageView foodImage;
    ImageView lightImage;
    TextView heatText;
    TextView tanshuiText;
    TextView fatText;
    TextView proteinText;

    RelativeLayout ingredientHeadLayout;
    ExpandableLayout ingredientExpandable;
    RecyclerView ingredientRecycler;
    BaseQuickAdapter ingredientRecyclerAdapter;

    private final int GET_FOOD_DETAIL_SUCCESS=1;
    private final int GET_FOOD_DETAIL_FAILED=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_info);
        Intent intent = getIntent();
        foodId = intent.getIntExtra("foodId", -1);

        //初始化
        initViews();

        final GetFoodByIdTest dataFetcher = new GetFoodByIdTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_FOOD_DETAIL_SUCCESS:
                        showInfo(dataFetcher.getFood());
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
        ingredientExpandable = (ExpandableLayout) findViewById(R.id.ingredient_expandable);
        ingredientHeadLayout = (RelativeLayout) findViewById(R.id.ingredient_head_layout);
        ingredientRecycler = (RecyclerView) findViewById(R.id.ingredient_recycler);
    }

    private void showInfo(Food food) {
        foodName.setText(food.getName());
        Glide.with(this).load(IP.ip + food.getPicture_high()).into(foodImage);
        Glide.with(this).load(IP.ip + food.getLight()).into(lightImage);
        heatText.setText(food.getHeat() + "");
        tanshuiText.setText(food.getTanshui() + "克");
        fatText.setText(food.getFat() + "克");
        proteinText.setText(food.getProtein() + "克");
    }

}
