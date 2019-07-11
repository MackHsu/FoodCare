package com.example.foodcare.entity;

public class AddFood {

    //添加食物界面的数据结构，包括名称、每百克的热量
    private int foodId;
    private String imageUrl;
    private String foodName;
    private double energyPerHectogram;
    private int group;

    public AddFood(int group, int foodId, String imageUrl, String foodName, double energyPerHectogram) {
        this.group = group;
        this.foodId = foodId;
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.energyPerHectogram = energyPerHectogram;
    }

    public int getGroup() {
        return group;
    }

    public int getFoodId() {
        return foodId;
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
