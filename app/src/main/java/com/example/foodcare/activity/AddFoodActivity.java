package com.example.foodcare.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.FoodList.FoodList;
import com.example.foodcare.entity.AddFood;
import com.flyco.tablayout.SlidingTabLayout;
import com.victor.loading.rotate.RotateLoading;

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
    private String[] mTitles = {"肉类", "蔬菜", "水果"};
    private PageRecyclerAdapter mAdapter;
    private SlidingTabLayout slide;

    public final int UPDATE_DATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //初始化
        backButton = (ImageButton) findViewById(R.id.back_button);
//        recyclerView = (RecyclerView) findViewById(R.id.add_food_recycler);
//        loading = (RotateLoading) findViewById(R.id.loading);
//        loading.start();
        //返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });

        for(String title: mTitles) {
            mFragments.add(AddFoodTypeFregment.getInstant(this, title));
        }

        View decorView = getWindow().getDecorView();
        ViewPager pager = (ViewPager) decorView.findViewById(R.id.view_pager);
        mAdapter = new PageRecyclerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        slide = decorView.findViewById(R.id.slide);
        slide.setViewPager(pager);

//        dbFoodData = new FoodList();
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case UPDATE_DATA:
//                        for(int i = 0; i < 10; i++ ) {
//                            foodList.add(new AddFood("http://192.168.137.238:8080/foodcare/" + dbFoodData.getData().get(i).getPicture_mid(), dbFoodData.getData().get(i).getName(), dbFoodData.getData().get(i).getFat()));
//                        }
//                        loading.stop();
//                        //显示列表数据
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(AddFoodActivity.this);
//                        recyclerView.setLayoutManager(layoutManager);
//                        AddFoodAdapter adapter = new AddFoodAdapter(foodList);
//                        recyclerView.setAdapter(adapter);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        dbFoodData.setHandler(handler);
//        dbFoodData.request();


    }

    //前端测试用
    private void initFoods() {
        foodList.add(new AddFood("", "米饭", 150));
        foodList.add(new AddFood("", "鸡腿", 400));
        foodList.add(new AddFood("", "豆浆", 50));
        foodList.add(new AddFood("", "煮鸡蛋", 100));
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

