package com.example.foodcare.entity;

public class AddFood {

    private String foodName;
    private double energyPerHectogram;

    public AddFood(String foodName, double energyPerHectogram) {
        this.foodName = foodName;
        this.energyPerHectogram = energyPerHectogram;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getEnergyPerHectogram() {
        return energyPerHectogram;
    }
}
