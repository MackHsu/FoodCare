package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.service.autofill.ImageTransformation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodMap;
import com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailAdd.DietDetailAddTest;
import com.example.foodcare.Retrofit.FoodPackage.FoodMap.FoodMapTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.entity.AccountID;
import com.orhanobut.dialogplus.DialogPlus;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;

public class MealInfoActivity extends AppCompatActivity {

    private int foodId;
    private FoodMap foodMap;

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
    ImageButton addButton;

    private final int GET_FOOD_DETAIL_SUCCESS=1;
    private final int GET_FOOD_DETAIL_FAILED=0;

    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;

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
                        foodMap = dataFetcher.getFoodMap();
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
        addButton = (ImageButton) findViewById(R.id.add_meal_button);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAndShowDialog();
            }
        });
    }

    //添加食物弹窗
    private void setAndShowDialog() {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                .create();
        //下拉框
        final NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
        ArrayList<String> meals = new ArrayList<>();
        meals.add("早餐"); meals.add("午餐"); meals.add("晚餐");
        spinner.attachDataSource(meals);
        //文本和图像
        TextView nameTextDialog = (TextView) dialog.findViewById(R.id.food_name);
        TextView energyTextDialog = (TextView) dialog.findViewById(R.id.food_energy);
        ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.image);
        if (foodMap == null) {
            Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
            return;
        }
        nameTextDialog.setText(foodMap.getFood().getName());
        energyTextDialog.setText(foodMap.getFood().getHeat() + "千卡/100克");
        Glide.with(this).load(IP.ip + foodMap.getFood().getPicture_mid()).into(foodImageDialog);

        dialog.show();

        //取消
        Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //确定
        final EditText editText = (EditText) dialog.findViewById(R.id.weight_edit_text);
        Button addButton = (Button) dialog.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DietDetailAddTest dataManager = new DietDetailAddTest();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case NO_RETURN:
                                Toast.makeText(MealInfoActivity.this, "没有返回值", Toast.LENGTH_SHORT).show();
                                break;
                            case UPDATE_SUCCEEDED:
                                Toast.makeText(MealInfoActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case UPDATE_FAILED:
                                Toast.makeText(MealInfoActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                break;
                            case REQUEST_FAILED:
                                Toast.makeText(MealInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                };
                dataManager.setHandler(handler);
                dataManager.request(foodMap.getFood().getId(),
                        Integer.parseInt(editText.getText().toString()),
                        AccountID.getId(),
                        spinner.getSelectedIndex(),
                        MealInfoActivity.this);
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
