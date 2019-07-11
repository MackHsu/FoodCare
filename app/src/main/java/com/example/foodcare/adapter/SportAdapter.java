package com.example.foodcare.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Sport;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

public class SportAdapter  extends BaseQuickAdapter<Sport, BaseViewHolder>{
    public SportAdapter(int layoutResId, List data)
    {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Sport item) {
        Glide.with(mContext).load(IP.ip + item.getUrl()).crossFade().into((ImageView) helper.getView(R.id.sport_image));
        helper.setText(R.id.sport_name_text, item.getName());//运动名
        helper.setText(R.id.sport_energy_text, item.getConsume() + "千卡/小时");//运动消耗
        helper.addOnClickListener(R.id.sport_item_layout);
    }
}
