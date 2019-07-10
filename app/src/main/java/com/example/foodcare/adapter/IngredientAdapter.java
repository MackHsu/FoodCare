package com.example.foodcare.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;

import java.util.Dictionary;
import java.util.List;

public class IngredientAdapter extends BaseQuickAdapter<Dictionary<String, String>, BaseViewHolder> {
    public IngredientAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Dictionary<String, String> item) {
//        helper.setText(R.id.name, item.keys())
    }
}
