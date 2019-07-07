package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.adapter.IdentifyAdapter2;
import com.example.foodcare.adapter.IdentifyLabelDetailAdapter;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

public class IdentifyLabelDetailActivity extends AppCompatActivity {
    private FoodReg foodReg;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_label_detail);

        //得到并且解析数据
        Intent intent = getIntent();
        String data=intent.getStringExtra("FoodReg");
        Gson gson =new Gson();
        foodReg =gson.fromJson(data,FoodReg.class);


        recyclerView = (RecyclerView) findViewById(R.id.identifylabeldetailrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final IdentifyLabelDetailAdapter adapter = new IdentifyLabelDetailAdapter(R.layout.identify_label_detail_item, foodReg.getFoods());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.identifybutton) {
                    Intent intent = new Intent(IdentifyLabelDetailActivity.this, FoodInfoActivity.class);
                    intent.putExtra("foodId",foodReg.getFoods().get(position));
                    MyToast.mytoast("成功进入食物详情界面",IdentifyLabelDetailActivity.this);
                    startActivity(intent);
                } else {
                    MyToast.mytoast("请点击按钮进入食物详情界面！",IdentifyLabelDetailActivity.this);
                }
            }
        });

        for (Food food: foodReg.getFoods()) {
            adapter.addData(food);
        }
        if(foodReg.getFoods()==null)
        {
            MyToast.mytoast("标签搜索结果为空",IdentifyLabelDetailActivity.this);
        }
        else if(foodReg.getFoods().size()==0){
            MyToast.mytoast("标签搜索结果为0",IdentifyLabelDetailActivity.this);
        }

    }
}