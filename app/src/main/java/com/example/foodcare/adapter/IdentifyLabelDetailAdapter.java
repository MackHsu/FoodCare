package com.example.foodcare.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

public class IdentifyLabelDetailAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {

    public IdentifyLabelDetailAdapter(int layoutResId, List data) {super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        Glide.with(mContext).load(item.getPicture_mid()).crossFade().into((ImageView) helper.getView(R.id.identifylabelimage));//食品图片
        helper.setText(R.id.identifylabelname, item.getName());//食物名
        helper.setText(R.id.identifylabelenergy, item.getHeat()+"");//能量
        helper.addOnClickListener(R.id.identifylayout);
        helper.addOnClickListener(R.id.identifybutton);
    }
}

