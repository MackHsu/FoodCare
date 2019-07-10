package com.example.foodcare.ToolClass;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisValueFormatter implements IAxisValueFormatter {
    private String[] xStrs = new String[]{"蛋白质", "碳水化合物", "脂肪", "纤维素"};

    @Override

    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        if (position >= 4) {
            position = 0;
        }
        return xStrs[position];
    }
}
