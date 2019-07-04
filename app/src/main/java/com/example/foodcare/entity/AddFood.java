package com.example.foodcare.entity;

public class AddFood {

    //添加食物界面的数据结构，包括名称、每百克的热量
    private String imageUrl;
    private String foodName;
    private double energyPerHectogram;

    public AddFood(String imageUrl, String foodName, double energyPerHectogram) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.energyPerHectogram = energyPerHectogram;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getEnergyPerHectogram() {
        return energyPerHectogram;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
