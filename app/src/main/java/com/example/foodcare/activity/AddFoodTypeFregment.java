package com.example.foodcare.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.FoodList.FoodList;
import com.example.foodcare.adapter.AddFoodAdapter;
import com.example.foodcare.entity.AddFood;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;


public class AddFoodTypeFregment extends Fragment {
    private final int UPDATE_DATA = 1;
    private String title;
    private FoodList dbFoodData;
    private ArrayList<AddFood> foodList;
    private Context mContext;

    RotateLoading loading;
    RecyclerView recyclerView;

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
        TextView titleText = (TextView) view.findViewById(R.id.title);
        titleText.setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.add_food_recycler);
        foodList = new ArrayList<>();
        final Activity activity = this.getActivity();
        final int UPDATE_DATA = 1;

        loading.start();
        dbFoodData = new FoodList();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_DATA:
                        for(int i = 0; i < 10; i++ ) {
                            foodList.add(new AddFood("http://192.168.137.238:8080/foodcare" + dbFoodData.getData().get(i).getPicture_mid(), dbFoodData.getData().get(i).getName(), dbFoodData.getData().get(i).getFat()));
                        }
                        loading.stop();
                        //显示列表数据
                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                        recyclerView.setLayoutManager(layoutManager);
                        AddFoodAdapter adapter = new AddFoodAdapter(foodList);
                        recyclerView.setAdapter(adapter);
                        break;
                    default:
                        break;
                }
            }
        };
        dbFoodData.setHandler(handler);
        dbFoodData.request();
        return view;
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
}
