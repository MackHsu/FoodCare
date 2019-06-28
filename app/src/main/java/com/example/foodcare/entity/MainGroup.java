//许朗铭 2017302580224
package com.example.foodcare.entity;

import java.util.ArrayList;

public class MainGroup {

    private String meal;
    private double expectedEnergyThisMeal;
    private double totalEnergyThisMeal;
    private ArrayList<MainFood> foodsThisMeal;

    public MainGroup(String meal, double expectedEnergyThisMeal, ArrayList<MainFood> foodsThisMeal) {
        this.meal = meal;
        this.expectedEnergyThisMeal = expectedEnergyThisMeal;
        this.foodsThisMeal = foodsThisMeal;
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
