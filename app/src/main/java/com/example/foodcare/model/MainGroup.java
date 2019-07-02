//许朗铭 2017302580224
package com.example.foodcare.model;

import java.util.ArrayList;

public class MainGroup {

    //主界面列表分组的数据结构，包括餐名、推荐热量、食物ArrayList和总热量（通过食物算出）
    private String meal;
    private double expectedEnergyThisMeal;
    private double totalEnergyThisMeal;
    private ArrayList<MainFood> foodsThisMeal;

    public MainGroup(String meal, double expectedEnergyThisMeal, ArrayList<MainFood> foodsThisMeal) {
        this.meal = meal;
        this.expectedEnergyThisMeal = expectedEnergyThisMeal;
        this.foodsThisMeal = foodsThisMeal;

        //计算总热量
        totalEnergyThisMeal = 0;
        for (MainFood mainFood: foodsThisMeal) {
            totalEnergyThisMeal += mainFood.getTotalEnergy();
        }
    }

    public String getMeal() {
        return meal;
    }

    public double getExpectedEnergyThisMeal() {
        return expectedEnergyThisMeal;
    }

    public double getTotalEnergyThisMeal() {
        return totalEnergyThisMeal;
    }

    public ArrayList<MainFood> getFoodsThisMeal() {
        return foodsThisMeal;
    }
}
