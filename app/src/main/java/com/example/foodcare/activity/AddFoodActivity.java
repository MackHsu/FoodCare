package com.example.foodcare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.FoodList.FoodList;
import com.example.foodcare.Retrofit.Page.PageTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.adapter.AddFoodAdapter2;
import com.example.foodcare.entity.AddFood;
import com.flyco.tablayout.SlidingTabLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.victor.loading.rotate.RotateLoading;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    private Context mContext = this;
    private RotateLoading loading;
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private FoodList dbFoodData;
    private List<AddFood> foodList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"常见", "食材", "菜品"};
    private PageRecyclerAdapter mAdapter;
    private SlidingTabLayout slide;
    private ImageButton camera;

    public final int UPDATE_DATA = 1;
    public final int UPDATE_FAILURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //初始化
        backButton = (ImageButton) findViewById(R.id.back_button);
        recyclerView = (RecyclerView) findViewById(R.id.add_food_recycler);
        loading = (RotateLoading) findViewById(R.id.loading);
        camera = (ImageButton) findViewById(R.id.camera_add);
//        loading.start();
        //返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });

        //分类显示方式
//        for(String title: mTitles) {
//            mFragments.add(AddFoodTypeFregment.getInstant(this, title));
//        }
//
//        View decorView = getWindow().getDecorView();
//        ViewPager pager = (ViewPager) decorView.findViewById(R.id.view_pager);
//        mAdapter = new PageRecyclerAdapter(getSupportFragmentManager());
//        pager.setAdapter(mAdapter);
//        slide = decorView.findViewById(R.id.slide);
//        slide.setViewPager(pager);

        loading.start();
        final AddFoodAdapter2 adapter = new AddFoodAdapter2(R.layout.add_food_item, foodList);
        final PageTest dataFetcher = new PageTest();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_DATA:
                        for (Food food: dataFetcher.getfoods()) {
                            adapter.addData(new AddFood(food.getId(), IP.ip + food.getPicture_mid(), food.getName(), food.getHeat()));
                        }
                        loading.stop();
                        adapter.loadMoreComplete();
                        if(dataFetcher.getEnd()){
                            adapter.loadMoreEnd();
                        }
                        break;
                    case UPDATE_FAILURE:
                        loading.stop();
                        adapter.loadMoreFail();
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.item_layout) {
                    //弹窗
                    final DialogPlus dialog = DialogPlus.newDialog(AddFoodActivity.this)
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                            .create();
                    //下拉框
                    NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
                    ArrayList<String> meals = new ArrayList<>();
                    meals.add("早餐"); meals.add("午餐"); meals.add("晚餐");
                    spinner.attachDataSource(meals);
                    //文本和图像
                    TextView nameTextDialog = (TextView) dialog.findViewById(R.id.food_name);
                    TextView energyTextDialog = (TextView) dialog.findViewById(R.id.food_energy);
                    ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.image);
                    nameTextDialog.setText(((TextView) view.findViewById(R.id.food_name_text)).getText());
                    energyTextDialog.setText(((TextView) view.findViewById(R.id.food_energy_text)).getText());
                    foodImageDialog.setImageDrawable(((ImageView) view.findViewById(R.id.food_image)).getDrawable());

                    dialog.show();

                    //取消
                    Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    Intent intent = new Intent(AddFoodActivity.this, FoodInfoActivity.class);
                    intent.putExtra("foodId", foodList.get(position).getFoodId());
                    startActivity(intent);
                }
            }
        });
        dataFetcher.request(AddFoodActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求一次");
                dataFetcher.setHandler(handler);
                dataFetcher.request(AddFoodActivity.this);
                //判断是否请求到头
            }
        }, recyclerView);

//点击相机按钮跳转进入牌照识别
    camera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddFoodActivity.this, IdentifyFoodActivity.class);
            startActivity(intent);
        }
    });

    }

    //前端测试用
    private void initFoods() {
        foodList.add(new AddFood(0, "", "米饭", 150));
        foodList.add(new AddFood(1, "", "鸡腿", 400));
        foodList.add(new AddFood(2, "", "豆浆", 50));
        foodList.add(new AddFood(3, "", "煮鸡蛋", 100));
        for(int i = 0; i < 30; i++) {
            foodList.add(new AddFood(i, "", i + "", i));
        }
    }

    private class PageRecyclerAdapter extends FragmentPagerAdapter {
        public PageRecyclerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }
    }
}

