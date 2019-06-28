package com.example.foodcare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodcare.entity.AddFood;
import com.example.foodcare.R;

import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {

    private List<AddFood> foodList;

    static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView nameText;
        TextView energyText;

        public ViewHolder(View view) {
            //ImageView初始化
            super(view);
            nameText = (TextView) view.findViewById(R.id.food_name_text);
            energyText = (TextView) view.findViewById(R.id.food_energy_text);
        }
    }

    public AddFoodAdapter(List<AddFood> foodList) {
        this.foodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_food_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AddFood food = foodList.get(position);
        viewHolder.nameText.setText(food.getFoodName());
        viewHolder.energyText.setText(food.getEnergyPerHectogram() + "千卡/克");
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
