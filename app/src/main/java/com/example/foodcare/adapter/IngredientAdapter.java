package com.example.foodcare.adapter;
//许朗铭

import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class IngredientAdapter extends BaseQuickAdapter<Map.Entry<String, String>, BaseViewHolder> {
    public IngredientAdapter(int layoutResId, Map<String, String> data) {
        super(layoutResId, new ArrayList<Map.Entry<String, String>>(data.entrySet()));
    }

    @Override
    protected void convert(BaseViewHolder helper, Map.Entry<String, String> item) {
         helper.setText(R.id.name, item.getKey());
         helper.setText(R.id.quantity, item.getValue());
    }
}
