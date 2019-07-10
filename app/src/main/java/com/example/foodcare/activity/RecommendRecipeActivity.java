package com.example.foodcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.adapter.RecommendAdapter;

import java.util.List;

public class RecommendRecipeActivity extends AppCompatActivity {
    Handler handler;
    private List<Food> foods;
    private RecyclerView recyclerView;


    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_recipe);

        recyclerView = (RecyclerView) findViewById(R.id.recommend_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecommendAdapter adapter = new RecommendAdapter(R.layout.recommend_item, foods);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.identifylabelbutton) {
                    Intent intent = new Intent(RecommendRecipeActivity.this, MoreInfoActivity.class);
                    intent.putExtra("foodId",foods.get(position).getId());
                    MyToast.mytoast("成功进入食物详情界面",RecommendRecipeActivity.this);
                    startActivity(intent);
                } else {
                    MyToast.mytoast("请点击按钮进入食物详情界面！",RecommendRecipeActivity.this);
                }
            }
        });

    }

    //TODO 添加请求获取推荐食物数据
}
