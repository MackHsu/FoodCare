package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.adapter.IdentifyLabelDetailAdapter;
import com.example.foodcare.adapter.SpaceItemDecoration;
import com.google.gson.Gson;

public class IdentifyLabelDetailActivity extends AppCompatActivity {
    private FoodReg foodReg;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try{
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_identify_label_detail);

           Intent intent = getIntent();
           String data=intent.getStringExtra("FoodReg");
           Gson gson =new Gson();

           foodReg =gson.fromJson(data,FoodReg.class);

           recyclerView = (RecyclerView) findViewById(R.id.identifylabeldetailrecycler);
           recyclerView.addItemDecoration(new SpaceItemDecoration(19));
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           final IdentifyLabelDetailAdapter adapter = new IdentifyLabelDetailAdapter(R.layout.identify_label_detail_item, foodReg.getFoods());
           recyclerView.setAdapter(adapter);

           adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
               @Override
               public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                   if(view.getId() == R.id.identifylabelbutton) {
                       Intent intent;
                       Log.i("TAG",foodReg.getFoods().get(0).getGroup()+"");
                       Log.i("TAG",foodReg.getFoods().get(0).getName());
                       if(foodReg.getFoods().get(position).getGroup()== 1 )//菜品
                       {
                            intent = new Intent(IdentifyLabelDetailActivity.this, MealInfoActivity.class);
                       }
                       else{
                           intent = new Intent(IdentifyLabelDetailActivity.this, DishesInfoActivity.class);
                       }
                       intent.putExtra("foodId",foodReg.getFoods().get(position).getId());
                       startActivity(intent);
                   } else {
                       MyToast.mytoast("请点击按钮进入食物详情界面！",IdentifyLabelDetailActivity.this);
                   }
               }
           });
           //得到并且解析数据

           if(foodReg.getFoods()==null)
           {
               MyToast.mytoast("标签搜索结果为空",IdentifyLabelDetailActivity.this);
           }
           else if(foodReg.getFoods().size()==0){
               MyToast.mytoast("标签搜索结果为0",IdentifyLabelDetailActivity.this);
           }
           else
           {
               for(Food food :foodReg.getFoods())
               {
                   System.out.println(food.getName());
                   System.out.println(food.getPicture_mid());
               }
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
}
