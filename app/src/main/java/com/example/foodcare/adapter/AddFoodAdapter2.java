package com.example.foodcare.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.entity.AddFood;

import java.util.List;

public class AddFoodAdapter2 extends BaseQuickAdapter<AddFood, BaseViewHolder> {
    public AddFoodAdapter2(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddFood item) {
        Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.food_image));
        helper.setText(R.id.food_name_text, item.getFoodName());
        helper.setText(R.id.food_energy_text, item.getEnergyPerHectogram() + "千卡/100克");
    }
}
