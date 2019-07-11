package com.example.foodcare.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.ToolClass.IP;

import java.util.List;

public class MainSportAdapter extends BaseQuickAdapter<Play, BaseViewHolder> {
    public MainSportAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Play item) {
        helper.setText(R.id.sport_name_text, item.getSport().getName());
        Double consumption = item.getTime() * item.getSport().getConsume() / 60d;
        helper.setText(R.id.total_energy_text, consumption.intValue() + "千卡");
        helper.setText(R.id.sport_time_text,  item.getTime() + "分钟");
        String imageString = IP.ip + item.getSport().getUrl();
        Glide.with(mContext).load(IP.ip + item.getSport().getUrl()).crossFade().into((ImageView) helper.getView(R.id.sport_image));
        helper.addOnClickListener(R.id.item_layout);
        helper.addOnLongClickListener(R.id.item_layout);
    }
}
