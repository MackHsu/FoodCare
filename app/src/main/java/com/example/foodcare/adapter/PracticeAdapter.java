package com.example.foodcare.adapter;
//许朗铭

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.foodcare.R;

import java.util.List;

public class PracticeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PracticeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.practice_text, item);
    }
}
