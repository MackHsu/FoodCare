package com.example.foodcare.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.FoodReg;

import java.util.List;

public class IdentifyAdapter extends BaseQuickAdapter<FoodReg, BaseViewHolder> {
    public IdentifyAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodReg item) {
        //Glide.with(mContext).load(item.getFood().getPicture_mid()).crossFade().into((ImageView) helper.getView(R.id.food_image));
        helper.setText(R.id.identifyname, item.getLabel());//识别标签名
        helper.setText(R.id.identifyprobability, item.getProbability()+"");//识别置信度
        helper.addOnClickListener(R.id.identifylayout);
        helper.addOnClickListener(R.id.identifybutton);
    }
}
