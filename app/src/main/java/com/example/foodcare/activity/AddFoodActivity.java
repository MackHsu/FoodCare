package com.example.foodcare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.foodcare.R;
import com.example.foodcare.adapter.AddFoodAdapter;
import com.example.foodcare.entity.AddFood;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView recyclerView;

    private List<AddFood> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //TODO: 以List<AddFood>的方式装好数据，替换掉initFoods()
        initFoods();

        //初始化
        backButton = (ImageButton) findViewById(R.id.back_button);
        recyclerView = (RecyclerView) findViewById(R.id.add_food_recycler);
        //返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });

        //显示列表数据
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AddFoodAdapter adapter = new AddFoodAdapter(foodList);
        recyclerView.setAdapter(adapter);
    }

    //前端测试用
    private void initFoods() {
        foodList.add(new AddFood("米饭", 150));
        foodList.add(new AddFood("鸡腿", 400));
        foodList.add(new AddFood("豆浆", 50));
        foodList.add(new AddFood("煮鸡蛋", 100));
    }
}
