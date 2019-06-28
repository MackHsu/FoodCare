//许朗铭 2017302580224
package com.example.foodcare.entity;

public class MainFood {
    private String foodName;
    private double foodWeight;
    private double energyPerHectogram;

    public MainFood(String foodName, double foodWeight, double energyPerHectogram) {
        this.foodName = foodName;
        this.foodWeight = foodWeight;
        this.energyPerHectogram = energyPerHectogram;
    }

    public String getFoodName(){
        return foodName;
    }

    public double getFoodWeight() {
        return foodWeight;
    }

    public double getEnergyPerHectogram() {
        return energyPerHectogram;
    }

    public double getTotalEnergy() {
        return energyPerHectogram * foodWeight / 100;
    }
}
