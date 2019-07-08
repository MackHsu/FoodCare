//许朗铭 2017302580224
package com.example.foodcare.model;

public class MainFood {

    //主界面每一个列表格（食物）的数据结构，包括名称、分量和每百克热量
    private String imageUrl;
    private String foodName;
    private double foodWeight;
    private double energyPerHectogram;

    public MainFood(String imageUrl, String foodName, double foodWeight, double energyPerHectogram) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.foodWeight = foodWeight;
        this.energyPerHectogram = energyPerHectogram;
    }

    public String getImageUrl() {
        return imageUrl;
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
