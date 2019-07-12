package com.example.foodcare.Retrofit.A_entity;

import java.io.Serializable;
import java.util.List;

public class FoodPosition implements Serializable {

    //食物 id
//    private int id;

    private FoodReg foodReg;

    //标签名，如番茄炒蛋 (要用来搜索的)
    private String label;

    //框选区域的四个点相对于 宽、高的比例，
    // 0-xMin, 1-xMax, 2-yMin, 3-yMax
    private double[] rates;

    //存储 返回图片的 URL（只有最后一个 List的对象存）
    private String url;

    public FoodPosition() {
    }

    public FoodReg getFoodReg(){return foodReg;}

    public void setFoodReg(FoodReg foodReg){this.foodReg = foodReg;}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getRates() {
        return rates;
    }

    public void setRates(double[] rates) {
        this.rates = rates;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
