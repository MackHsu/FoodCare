package com.example.foodcare.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodMap;
import com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailAdd.DietDetailAddTest;
import com.example.foodcare.Retrofit.FoodPackage.FoodMap.FoodMapTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.adapter.IngredientAdapter;
import com.example.foodcare.adapter.PracticeAdapter;
import com.example.foodcare.adapter.SpaceItemDecoration;
import com.example.foodcare.entity.AccountID;
import com.orhanobut.dialogplus.DialogPlus;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DishesInfoActivity extends AppCompatActivity {

    private int foodId;
    private FoodMap foodMap;
    private Map<String, String> ingredientList;
    private Map<String, String> excipientList;
    private Map<String, String> seasoningList;
    private List<String> practiceList;
    ArrayList<ExpandableLayout> expandables;
    ArrayList<ImageView> expandableIamges;

    TextView foodName;
    ImageView foodImage;
    ImageView lightImage;
    TextView heatText;
    TextView tanshuiText;
    TextView fatText;
    TextView proteinText;
    TextView moreText;
    ImageButton backButton;
    ImageButton addButton;

    CardView ingredientCard;
    ExpandableLayout ingredientExpandable;
    RecyclerView ingredientRecycler;
    BaseQuickAdapter ingredientRecyclerAdapter;
    ImageView ingredientExpandableImage;

    CardView excipientCard;
    ExpandableLayout excipientExpandable;
    RecyclerView excipientRecycler;
    BaseQuickAdapter excipientRecyclerAdapter;
    ImageView excipientExpandableImage;

    CardView seasoningCard;
    ExpandableLayout seasoningExpandable;
    RecyclerView seasoningRecycler;
    BaseQuickAdapter seasoningRecyclerAdapter;
    ImageView seasoningExpandableImage;

    CardView practiceCard;
    ExpandableLayout practiceExpandable;
    RecyclerView practiceRecycler;
    BaseQuickAdapter practiceRecyclerAdapter;
    ImageView practiceExpandableImage;

    CardView cookCard;
    ExpandableLayout cookExpandable;
    TextView cookText;
    ImageView cookExpandableImage;

    private final int GET_FOOD_DETAIL_SUCCESS = 1;
    private final int GET_FOOD_DETAIL_FAILED = 0;

    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_info);
        Intent intent = getIntent();
        foodId = intent.getIntExtra("foodId", -1);

        //初始化
        initViews();
        initExpandables();

        final FoodMapTest dataFetcher = new FoodMapTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_FOOD_DETAIL_SUCCESS:
                        foodMap = dataFetcher.getFoodMap();
                        showInfo(dataFetcher.getFoodMap().getFood());
                        loadMapData(dataFetcher.getFoodMap());
                        initRecyclers();
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
        expandables = new ArrayList<>();
        expandableIamges = new ArrayList<>();
        foodName = (TextView) findViewById(R.id.food_name);
        foodImage = (ImageView) findViewById(R.id.food_image);
        lightImage = (ImageView) findViewById(R.id.light_image);
        heatText = (TextView) findViewById(R.id.heat_text);
        tanshuiText = (TextView) findViewById(R.id.tanshui_text);
        fatText = (TextView) findViewById(R.id.fat_text);
        proteinText = (TextView) findViewById(R.id.protein_text);
        moreText = (TextView) findViewById(R.id.more);
        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishesInfoActivity.this, MoreInfoActivity.class);
                intent.putExtra("foodId", foodId);
                startActivity(intent);
            }
        });
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAndShowDialog();
            }
        });

        ingredientExpandable = (ExpandableLayout) findViewById(R.id.ingredient_expandable);
        expandables.add(ingredientExpandable);
        ingredientCard = (CardView) findViewById(R.id.ingredient_card);
        ingredientRecycler = (RecyclerView) findViewById(R.id.ingredient_recycler);
        ingredientRecycler.addItemDecoration( new SpaceItemDecoration(5));
        ingredientExpandableImage = (ImageView) findViewById(R.id.ingredient_pick);
        expandableIamges.add(ingredientExpandableImage);

        excipientExpandable = (ExpandableLayout) findViewById(R.id.excipient_expandable);
        expandables.add(excipientExpandable);
        excipientCard = (CardView) findViewById(R.id.excipient_card);
        excipientRecycler = (RecyclerView) findViewById(R.id.excipient_recycler);
        excipientRecycler.addItemDecoration( new SpaceItemDecoration(5));
        excipientExpandableImage = (ImageView) findViewById(R.id.excipient_pick);
        expandableIamges.add(excipientExpandableImage);

        seasoningExpandable = (ExpandableLayout) findViewById(R.id.seasoning_expandable);
        expandables.add(seasoningExpandable);
        seasoningCard = (CardView) findViewById(R.id.seasoning_card);
        seasoningRecycler = (RecyclerView) findViewById(R.id.seasoning_recycler);
        seasoningRecycler.addItemDecoration( new SpaceItemDecoration(5));
        seasoningExpandableImage = (ImageView) findViewById(R.id.seasoning_pick);
        expandableIamges.add(seasoningExpandableImage);

        practiceExpandable = (ExpandableLayout) findViewById(R.id.practice_expandable);
        expandables.add(practiceExpandable);
        practiceCard = (CardView) findViewById(R.id.practice_card);
        practiceRecycler = (RecyclerView) findViewById(R.id.practice_recycler);
        practiceRecycler.addItemDecoration( new SpaceItemDecoration(5));
        practiceExpandableImage = (ImageView) findViewById(R.id.practice_pick);
        expandableIamges.add(practiceExpandableImage);

        cookExpandable = (ExpandableLayout) findViewById(R.id.cook_expandable);
        expandables.add(cookExpandable);
        cookCard = (CardView) findViewById(R.id.cook_card);
        cookText = (TextView) findViewById(R.id.cook_text);
        cookExpandableImage = (ImageView) findViewById(R.id.cook_pick);
        expandableIamges.add(cookExpandableImage);
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
                                Toast.makeText(DishesInfoActivity.this, "没有返回值", Toast.LENGTH_SHORT).show();
                                break;
                            case UPDATE_SUCCEEDED:
                                Toast.makeText(DishesInfoActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case UPDATE_FAILED:
                                Toast.makeText(DishesInfoActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                break;
                            case REQUEST_FAILED:
                                Toast.makeText(DishesInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
                        DishesInfoActivity.this);
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
        cookText.setText(food.getCook());
    }

    private void initRecyclers() {
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        ingredientRecycler.setLayoutManager(manager1);
        ingredientRecyclerAdapter = new IngredientAdapter(R.layout.gradient_item, ingredientList);
        ingredientRecycler.setAdapter(ingredientRecyclerAdapter);

        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        excipientRecycler.setLayoutManager(manager2);
        excipientRecyclerAdapter = new IngredientAdapter(R.layout.gradient_item, excipientList);
        excipientRecycler.setAdapter(excipientRecyclerAdapter);

        LinearLayoutManager manager3 = new LinearLayoutManager(this);
        seasoningRecycler.setLayoutManager(manager3);
        seasoningRecyclerAdapter = new IngredientAdapter(R.layout.gradient_item, seasoningList);
        seasoningRecycler.setAdapter(seasoningRecyclerAdapter);

        LinearLayoutManager manager4 = new LinearLayoutManager(this);
        practiceRecycler.setLayoutManager(manager4);
        practiceRecyclerAdapter = new PracticeAdapter(R.layout.practice_item, practiceList);
        practiceRecycler.setAdapter(practiceRecyclerAdapter);
    }

    private void loadMapData(FoodMap foodMap) {
        ingredientList = foodMap.getIngredients();
        excipientList = foodMap.getExcipients();
        seasoningList = foodMap.getSeasoning();
        practiceList = foodMap.getPractice();
    }

    private void initExpandables() {
        ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredientExpandable.isExpanded()) {
                    ingredientExpandable.collapse();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(ingredientExpandableImage);
                }
                else {
                    for (ImageView image: expandableIamges) {
                        Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(image);
                    }
                    for (ExpandableLayout expandable: expandables) {
                        expandable.collapse();
                    }
                    ingredientExpandable.expand();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_collapse).into(ingredientExpandableImage);
                }
            }
        });

        excipientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(excipientExpandable.isExpanded()) {
                    excipientExpandable.collapse();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(excipientExpandableImage);
                }
                else {
                    for (ImageView image: expandableIamges) {
                        Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(image);
                    }
                    for (ExpandableLayout expandable: expandables) {
                        expandable.collapse();
                    }
                    excipientExpandable.expand();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_collapse).into(excipientExpandableImage);
                }
            }
        });

        seasoningCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seasoningExpandable.isExpanded()) {
                    seasoningExpandable.collapse();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(seasoningExpandableImage);
                }
                else {
                    for (ImageView image: expandableIamges) {
                        Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(image);
                    }
                    for (ExpandableLayout expandable: expandables) {
                        expandable.collapse();
                    }
                    seasoningExpandable.expand();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_collapse).into(seasoningExpandableImage);
                }
            }
        });

        practiceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(practiceExpandable.isExpanded()) {
                    practiceExpandable.collapse();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(practiceExpandableImage);
                }
                else {
                    for (ImageView image: expandableIamges) {
                        Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(image);
                    }
                    for (ExpandableLayout expandable: expandables) {
                        expandable.collapse();
                    }
                    practiceExpandable.expand();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_collapse).into(practiceExpandableImage);
                }
            }
        });

        cookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cookExpandable.isExpanded()) {
                    cookExpandable.collapse();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(cookExpandableImage);
                }
                else {
                    for (ImageView image: expandableIamges) {
                        Glide.with(DishesInfoActivity.this).load(R.drawable.ic_pick).into(image);
                    }
                    for (ExpandableLayout expandable: expandables) {
                        expandable.collapse();
                    }
                    cookExpandable.expand();
                    Glide.with(DishesInfoActivity.this).load(R.drawable.ic_collapse).into(cookExpandableImage);
                }
            }
        });
    }
}
