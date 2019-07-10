//许朗铭 2017302580224
package com.example.foodcare.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.foodcare.Retrofit.Diet.DietDetailAdd.DietDetailAddTest;
import com.example.foodcare.Retrofit.Diet.DietDetailDelete.DietDetailDeleteTest;
import com.example.foodcare.Retrofit.UpdateDietDetail.DietDetailUpdateTest;
import com.example.foodcare.activity.AddFoodActivity;
import com.example.foodcare.activity.MainActivity;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.model.MainFood;
import com.example.foodcare.model.MainGroup;
import com.example.foodcare.R;
import com.orhanobut.dialogplus.DialogPlus;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;

public class MainRecyclerAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<MainGroup> mainGroups;
    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;

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
    public void onBindChildViewHolder(final BaseViewHolder holder, final int groupPosition, final int childPosition) {
        final MainFood mainFood = mainGroups.get(groupPosition).getFoodsThisMeal().get(childPosition);
        Glide.with(mContext).load(mainFood.getImageUrl()).into((ImageView) holder.get(R.id.food_image));
        holder.setText(R.id.food_name_text, mainFood.getFoodName());
        holder.setText(R.id.food_weight_text, mainFood.getFoodWeight() + "克");
        holder.setText(R.id.total_energy_text, mainFood.getTotalEnergy() + "千卡");

        //删除
        holder.get(R.id.delete_food_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("提示");
                dialog.setMessage("将要删除" + ((TextView) holder.get(R.id.food_name_text)).getText().toString() + "，是否确定？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DietDetailDeleteTest dataManager = new DietDetailDeleteTest();
                        Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case NO_RETURN:
                                        break;
                                    case UPDATE_SUCCEEDED:
                                        mainGroups.get(groupPosition).getFoodsThisMeal().remove(childPosition);
                                        MainRecyclerAdapter.this.notifyChildRemoved(groupPosition, childPosition);
                                        break;
                                    case UPDATE_FAILED:
                                        break;
                                    case REQUEST_FAILED:
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                        dataManager.setHandler(handler);
                        dataManager.request(mainFood.getFoodId(), mainGroups.get(groupPosition).getDietId(), mContext);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

        //修改
        holder.get(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                final DialogPlus dialog = DialogPlus.newDialog(mContext)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                        .create();
                //下拉框
                final NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
                spinner.setVisibility(View.GONE);
                //文本和图像
                TextView nameTextDialog = (TextView) dialog.findViewById(R.id.food_name);
                TextView energyTextDialog = (TextView) dialog.findViewById(R.id.food_energy);
                ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.image);
                nameTextDialog.setText(((TextView) v.findViewById(R.id.food_name_text)).getText());
                energyTextDialog.setText(mainGroups.get(groupPosition).getFoodsThisMeal().get(childPosition).getEnergyPerHectogram() + "千卡/100克");
                foodImageDialog.setImageDrawable(((ImageView) v.findViewById(R.id.food_image)).getDrawable());
                //输入框
                final EditText editText = (EditText) dialog.findViewById(R.id.weight_edit_text);

                dialog.show();

                //取消
                Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //确定
                Button modifyButton = (Button) dialog.findViewById(R.id.add);
                modifyButton.setText("修改");
                modifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DietDetailUpdateTest dataManager = new DietDetailUpdateTest();
                        Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case NO_RETURN:
                                        Toast.makeText(mContext, "没有返回值", Toast.LENGTH_SHORT).show();
                                        break;
                                    case UPDATE_SUCCEEDED:
                                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                                        mainGroups.get(groupPosition).getFoodsThisMeal().get(childPosition).setFoodWeight(Integer.parseInt(editText.getText().toString()));
                                        notifyChildChanged(groupPosition, childPosition);
                                        dialog.dismiss();
                                        break;
                                    case UPDATE_FAILED:
                                        Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
                                        break;
                                    case REQUEST_FAILED:
                                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                        dataManager.setHandler(handler);
                        dataManager.request(mainGroups.get(groupPosition).getDietId(),
                                mainGroups.get(groupPosition).getFoodsThisMeal().get(childPosition).getFoodId(),
                                Integer.parseInt(editText.getText().toString()),
                                mContext);
                    }
                });
            }
        });
    }
}
