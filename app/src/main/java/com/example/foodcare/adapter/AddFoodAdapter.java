package com.example.foodcare.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodcare.activity.MoreInfoActivity;
import com.example.foodcare.entity.AddFood;
import com.example.foodcare.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {

    private List<AddFood> foodList;

    static class ViewHolder extends  RecyclerView.ViewHolder {
        CircleTextImageView foodImage;
        TextView nameText;
        TextView energyText;
        ImageButton infoButton;
        RelativeLayout itemLayout;


        public ViewHolder(View view) {
            //ImageView初始化
            super(view);
            foodImage = (CircleTextImageView) view.findViewById(R.id.food_image);
            nameText = (TextView) view.findViewById(R.id.food_name_text);
            energyText = (TextView) view.findViewById(R.id.food_energy_text);
            infoButton = (ImageButton) view.findViewById(R.id.food_info_button);
            itemLayout = (RelativeLayout) view.findViewById(R.id.item_layout);

            //食物详细
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(nameText.getContext(), MoreInfoActivity.class);
                    nameText.getContext().startActivity(intent);
                }
            });

            //添加弹窗
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialog = DialogPlus.newDialog(nameText.getContext())
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                            .create();
                    //下拉框
                    NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
                    ArrayList<String> meals = new ArrayList<>();
                    meals.add("早餐"); meals.add("午餐"); meals.add("晚餐");
                    spinner.attachDataSource(meals);
                    //文本
                    TextView nameText2 = (TextView) dialog.findViewById(R.id.food_name);
                    TextView energyText2 = (TextView) dialog.findViewById(R.id.food_energy);
                    nameText2.setText(nameText.getText());
                    energyText2.setText(energyText.getText());
                    dialog.show();

                    //取消
                    Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
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
        Glide.with(viewHolder.foodImage.getContext()).load(food.getImageUrl()).into(viewHolder.foodImage);
        viewHolder.nameText.setText(food.getFoodName());
        viewHolder.energyText.setText(food.getEnergyPerHectogram() + "千卡/克");
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
