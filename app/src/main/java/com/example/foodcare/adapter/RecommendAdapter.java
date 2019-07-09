package com.example.foodcare.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.FoodRank;

import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<FoodRank, BaseViewHolder> {
    public RecommendAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodRank item) {
        //Glide.with(mContext).load(item.getFood().getPicture_mid()).crossFade().into((ImageView) helper.getView(R.id.food_image));
        helper.setText(R.id.RecommendImage, item.getFoodname());//识别标签名
        helper.setText(R.id.RecommendFoodName, item.getProbability()+"");//识别置信度
        helper.addOnClickListener(R.id.RecommendLayout);
        helper.addOnClickListener(R.id.RecommendButton);
    }
}
