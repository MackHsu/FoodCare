package com.example.foodcare.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodRank;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {
    public SearchAdapter(int layoutResId, List data)  {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        //Glide.with(mContext).load(item.getFood().getPicture_mid()).crossFade().into((ImageView) helper.getView(R.id.food_image));
        helper.setText(R.id.searchname, item.getName());//食物名
        helper.setText(R.id.searchenergy, item.getHeat()+"千卡/100克");//食物热量
        helper.addOnClickListener(R.id.searchitemlayout);
    }
}
