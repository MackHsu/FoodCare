//许朗铭 2017302580224
package com.example.foodcare.adapter;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.foodcare.activity.MainActivity;
import com.example.foodcare.model.MainFood;
import com.example.foodcare.model.MainGroup;
import com.example.foodcare.R;

import java.util.ArrayList;

public class MainRecyclerAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<MainGroup> mainGroups;

    public MainRecyclerAdapter(Context context, ArrayList<MainGroup> groups) {
        super(context);
        mainGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mainGroups == null ? 0 : mainGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<MainFood> foods = mainGroups.get(groupPosition).getFoodsThisMeal();
        return foods == null ? 0 : foods.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.main_header_layout;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.main_footer_layout;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.main_item_layout;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        MainGroup mainGroup = mainGroups.get(groupPosition);
        holder.setText(R.id.main_meal_text, mainGroup.getMeal());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
        MainGroup mainGroup = mainGroups.get(groupPosition);
        holder.setText(R.id.total_text, mainGroup.getTotalEnergyThisMeal() + "千卡");
        holder.setText(R.id.expected_total_text, mainGroup.getExpectedEnergyThisMeal() + "千卡");
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        MainFood mainFood = mainGroups.get(groupPosition).getFoodsThisMeal().get(childPosition);
        Glide.with(mContext).load(mainFood.getImageUrl()).into((ImageView) holder.get(R.id.food_image));
        holder.setText(R.id.food_name_text, mainFood.getFoodName());
        holder.setText(R.id.food_weight_text, mainFood.getFoodWeight() + "克");
        holder.setText(R.id.total_energy_text, mainFood.getTotalEnergy() + "千卡");
    }
}
