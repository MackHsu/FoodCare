package com.example.foodcare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.Diet.DietDetailAdd.DietDetailAddTest;
import com.example.foodcare.Retrofit.Page.CategoryPageTest;
import com.example.foodcare.Retrofit.Page.DishPageTest;
import com.example.foodcare.Retrofit.Page.FrequentPageTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.adapter.AddFoodAdapter2;
import com.example.foodcare.adapter.SpaceItemDecoration;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.entity.AddFood;
import com.orhanobut.dialogplus.DialogPlus;
import com.victor.loading.rotate.RotateLoading;
import com.wx.wheelview.widget.WheelViewDialog;

import com.example.foodcare.Retrofit.A_entity.*;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;


public class AddFoodTypeFregment extends Fragment {
    private String title;
    private ArrayList<AddFood> foodList;
    private Context mContext;
    LinearLayout typeLayout;
    TextView typeText;
    private AddFoodAdapter2 adapter;

    RotateLoading loading;
    RecyclerView recyclerView;

    private final int UPDATE_DATA = 1;
    private final int UPDATE_FAILURE = 2;
    private final int RETURN_NULL = 0;

    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;


    public static AddFoodTypeFregment getInstant(Context context, String title) {
        AddFoodTypeFregment fregment = new AddFoodTypeFregment();
        fregment.title = title;
        return fregment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_food_type_fregment, null);
        loading = (RotateLoading) view.findViewById(R.id.loading);
        recyclerView = (RecyclerView) view.findViewById(R.id.add_food_recycler);
        foodList = new ArrayList<>();
        final int UPDATE_DATA = 1;

        typeLayout = (LinearLayout) view.findViewById(R.id.type_layout);
        typeText = (TextView) view.findViewById(R.id.type);
        if(title == "菜品") {
            typeText.setText("安徽菜");
        } else if (title == "食材") {
            typeText.setText("蛋类、肉类及制品");
        } else {
            typeLayout.setVisibility(View.GONE);
        }

        typeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> typeList = createTypeArrayList();
                WheelViewDialog dialog = new WheelViewDialog(mContext);
                dialog.setTitle("选择种类").setItems(typeList).setButtonText("确定").setDialogStyle(R.color.mainColor).setCount(5).setSelection(typeList.indexOf(typeText.getText()));
                dialog.setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position, String s) {
                        typeText.setText(s.toString());
                        foodList.clear();
                        getData(view);
                    }
                });
                dialog.show();
            }
        });

        getData(view);

        return view;
    }

    private void getData(View view) {
        if(title == "常见") {
            getFrequentFood(view);
        }
        else if(title == "食材"){
            getFoodByCategory(view, typeText.getText().toString());
        }
        else {
            getDishesByType(view, typeText.getText().toString());
        }
    }

    private void getFrequentFood(View view) {
        loading.start();
        final FrequentPageTest dataFetcher = new FrequentPageTest();
        adapter = new AddFoodAdapter2(R.layout.add_food_item_my, foodList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(19));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.food_info_button) {
                    Intent intent = new Intent(mContext, MealInfoActivity.class);
                    intent.putExtra("foodId", foodList.get(position).getFoodId());
                    startActivity(intent);
                }
                else {
                    setAndShowDialog(view, position);
                }
            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RETURN_NULL:
                        Toast.makeText(mContext, "返回对象为空", Toast.LENGTH_SHORT).show();
                        break;
                    case UPDATE_DATA:
                        List<Food> foods = dataFetcher.getfoods();
                        for (Food food: dataFetcher.getfoods()) {
                            adapter.addData(new AddFood(food.getId(), IP.ip + food.getPicture_mid(), food.getName(), food.getHeat()));
                        }
                        loading.stop();
                        adapter.loadMoreComplete();
                        if(dataFetcher.getEnd()){
                            adapter.loadMoreEnd();
                        }
                        break;
                    case UPDATE_FAILURE:
                        loading.stop();
                        adapter.loadMoreFail();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        dataFetcher.request(mContext);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求一次");
                dataFetcher.setHandler(handler);
                dataFetcher.request(mContext);
            }
        }, recyclerView);
    }

    private void getFoodByCategory(View view, String category) {
        loading.start();
        final CategoryPageTest dataFetcher = new CategoryPageTest(category);
        adapter = new AddFoodAdapter2(R.layout.add_food_item_my, foodList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(19));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.food_info_button) {
                    Intent intent = new Intent(mContext, FoodInfoActivity.class);
                    intent.putExtra("foodId", foodList.get(position).getFoodId());
                    startActivity(intent);
                }
                else {
                    setAndShowDialog(view, position);
                }
            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RETURN_NULL:
                        Toast.makeText(mContext, "返回对象为空", Toast.LENGTH_SHORT).show();
                        break;
                    case UPDATE_DATA:
                        List<Food> foods = dataFetcher.getfoods();
                        for (Food food: dataFetcher.getfoods()) {
                            adapter.addData(new AddFood(food.getId(), IP.ip + food.getPicture_mid(), food.getName(), food.getHeat()));
                        }
                        loading.stop();
                        adapter.loadMoreComplete();
                        if(dataFetcher.getEnd()){
                            adapter.loadMoreEnd();
                        }
                        break;
                    case UPDATE_FAILURE:
                        loading.stop();
                        adapter.loadMoreFail();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        dataFetcher.request(mContext);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求一次");
                dataFetcher.setHandler(handler);
                dataFetcher.request(mContext);
            }
        }, recyclerView);
    }

    private void getDishesByType(View view, String type) {

        loading.start();
        final DishPageTest dataFetcher = new DishPageTest(type);
        final AddFoodAdapter2 adapter = new AddFoodAdapter2(R.layout.add_food_item_my, foodList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(19));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.food_info_button) {
                    Intent intent = new Intent(mContext, FoodInfoActivity.class);
                    intent.putExtra("foodId", foodList.get(position).getFoodId());
                    startActivity(intent);
                }
                else {
                    setAndShowDialog(view, position);
                }
            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RETURN_NULL:
                        Toast.makeText(mContext, "返回对象为空", Toast.LENGTH_SHORT).show();
                        break;
                    case UPDATE_DATA:
                        List<Food> foods = dataFetcher.getfoods();
                        for (Food food: dataFetcher.getfoods()) {
                            adapter.addData(new AddFood(food.getId(), IP.ip + food.getPicture_mid(), food.getName(), food.getHeat()));
                        }
                        loading.stop();
                        adapter.loadMoreComplete();
                        if(dataFetcher.getEnd()){
                            adapter.loadMoreEnd();
                        }
                        break;
                    case UPDATE_FAILURE:
                        loading.stop();
                        adapter.loadMoreFail();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        dataFetcher.request(mContext);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求一次");
                dataFetcher.setHandler(handler);
                dataFetcher.request(mContext);
            }
        }, recyclerView);
    }

    private void setAndShowDialog(View view, final int position) {
        //弹窗
        final DialogPlus dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                .create();
        //下拉框
        final NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
        ArrayList<String> meals = new ArrayList<>();
        meals.add("早餐"); meals.add("午餐"); meals.add("晚餐");
        spinner.attachDataSource(meals);
        //文本和图像
        TextView nameTextDialog = (TextView) dialog.findViewById(R.id.food_name);
        TextView energyTextDialog = (TextView) dialog.findViewById(R.id.food_energy);
        ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.image);
        nameTextDialog.setText(((TextView) view.findViewById(R.id.food_name_text)).getText());
        energyTextDialog.setText(((TextView) view.findViewById(R.id.food_energy_text)).getText());
        foodImageDialog.setImageDrawable(((ImageView) view.findViewById(R.id.food_image)).getDrawable());

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
        final EditText editText = (EditText) dialog.findViewById(R.id.weight_edit_text);
        Button addButton = (Button) dialog.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DietDetailAddTest dataManager = new DietDetailAddTest();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case NO_RETURN:
                                Toast.makeText(mContext, "没有返回值", Toast.LENGTH_SHORT).show();
                                break;
                            case UPDATE_SUCCEEDED:
                                Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case UPDATE_FAILED:
                                Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
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
                dataManager.request(foodList.get(position).getFoodId(),
                        Integer.parseInt(editText.getText().toString()),
                        AccountID.getId(),
                        spinner.getSelectedIndex(),
                        mContext);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private ArrayList<String> createTypeArrayList() {
        ArrayList<String> typeList = new ArrayList<>();
        if(title == "菜品") {
            String[] types = {"安徽菜", "北京菜", "滇黔菜", "东北菜", "福建菜", "甘肃菜", "广东菜", "广西菜",
                    "广州菜", "海南菜", "河南菜", "湖北菜", "湖南菜", "家常菜", "江苏菜", "江西菜", "宁夏菜", "清真菜",
                    "山东菜", "山西菜", "陕西菜", "上海菜", "少数民族菜", "私家菜", "四川菜", "素斋菜", "台湾菜", "天津菜",
                    "新疆菜", "浙江菜"};
            for (String type: types) {
                typeList.add(type);
            }
        } else {
            String[] types = {"蛋类、肉类及制品", "炖", "谷薯芋、杂豆、主食", "坚果、大豆及制品", "煎", "烤",
                    "凉拌", "零食、点心、冷饮", "奶类及制品", "清蒸", "砂锅、煮", "食用油、油脂及制品", "蔬果和菌藻", "调味品",
                    "饮料", "炸", "其他"};
            for (String type: types) {
                typeList.add(type);
            }
        }
        return typeList;
    }
}
