//许朗铭 2017302580224
package com.example.foodcare.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainGroup implements Comparable {

    //主界面列表分组的数据结构，包括餐名、推荐热量、食物ArrayList和总热量（通过食物算出）
    private int dietId;
    private int group;
    private String meal;
    private double expectedEnergyThisMeal;
    private double totalEnergyThisMeal;
    private ArrayList<MainFood> foodsThisMeal;

    public MainGroup(int dietId, int group, double expectedEnergyThisMeal, ArrayList<MainFood> foodsThisMeal) {
        this.dietId = dietId;
        this.group = group;
        this.expectedEnergyThisMeal = expectedEnergyThisMeal;
        this.foodsThisMeal = foodsThisMeal;

        switch (group) {
            case 0:
                this.meal = "早餐";
                break;
            case 1:
                this.meal = "午餐";
                break;
            case 2:
                this.meal = "晚餐";
                break;
        }

        //计算总热量
//        totalEnergyThisMeal = 0;
//        for (MainFood mainFood: foodsThisMeal) {
//            totalEnergyThisMeal += mainFood.getTotalEnergy();
//        }
    }

    public int getDietId() {
        return dietId;
    }

    public int getGroup() {
        return group;
    }

    public String getMeal() {
        return meal;
    }

    public double getExpectedEnergyThisMeal() {
        return expectedEnergyThisMeal;
    }

    //计算总热量
    public double getTotalEnergyThisMeal() {
        double totalEnergyThisMeal = 0;
        for (MainFood mainFood: foodsThisMeal) {
            totalEnergyThisMeal += mainFood.getTotalEnergy();
        }
        BigDecimal bg = new BigDecimal(totalEnergyThisMeal);
        totalEnergyThisMeal = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return totalEnergyThisMeal;
    }

    public ArrayList<MainFood> getFoodsThisMeal() {
        return foodsThisMeal;
    }

    @Override
    public int compareTo(Object o) {
        MainGroup group = (MainGroup) o;
        if(this.getGroup() < group.getGroup()) return -1;
        else if(this.getGroup() > group.getGroup()) return 1;
        else return 0;
    }
}
