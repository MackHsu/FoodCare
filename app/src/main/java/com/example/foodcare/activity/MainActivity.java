//许朗铭 2017302580224
//使用github上的GroupedRecyclerAdapter来实现分组列表  https://github.com/donkingliang/GroupedRecyclerViewAdapter
//使用github上的CircleTextImageView来做圆形头像框 https://github.com/CoolThink/CircleTextImageView
package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.foodcare.R;
import com.example.foodcare.adapter.MainRecyclerAdapter;
import com.example.foodcare.entity.MainFood;
import com.example.foodcare.entity.MainGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton menuButton;
    DrawerLayout mainDrawerLayout;
    ArrayList<MainGroup> groupList;
    Button addFoodButton;

    RecyclerView mainRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGroupList();

        menuButton = (ImageButton) findViewById(R.id.main_menu_button);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addFoodButton = (Button) findViewById(R.id.mian_add_button);
        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });

        mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this,groupList);
        mainRecycler.setAdapter(adapter);
    }

    public void initGroupList() {
        groupList = new ArrayList<>();
        ArrayList<MainFood> foodList = new ArrayList<>();

        //早餐
        MainFood mainFood = new MainFood("煮鸡蛋", 50, 100);
        foodList.add(mainFood);
        mainFood = new MainFood("豆浆", 200, 50);
        foodList.add(mainFood);
        MainGroup mainGroup = new MainGroup("早餐", 155, foodList);
        groupList.add(mainGroup);

        //午餐
        foodList = new ArrayList<>();
        mainFood = new MainFood("米饭", 200, 150);
        foodList.add(mainFood);
        mainFood = new MainFood("鸡腿", 50, 400);
        foodList.add(mainFood);
        mainGroup = new MainGroup("午餐", 450, foodList);
        groupList.add(mainGroup);
    }
}
