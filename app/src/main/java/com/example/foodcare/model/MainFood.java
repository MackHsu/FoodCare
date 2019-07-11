//许朗铭 2017302580224
package com.example.foodcare.model;

public class MainFood {

    //主界面每一个列表格（食物）的数据结构，包括名称、分量和每百克热量
    private int foodId;
    private String imageUrl;
    private String foodName;
    private int foodWeight;
    private int energyPerHectogram;

    public MainFood(int foodId, String imageUrl, String foodName, int foodWeight, int energyPerHectogram) {
        this.foodId = foodId;
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.foodWeight = foodWeight;
        this.energyPerHectogram = energyPerHectogram;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFoodName(){
        return foodName;
    }

    public int getFoodWeight() {
        return foodWeight;
    }

    public int getEnergyPerHectogram() {
        return energyPerHectogram;
    }

    public double getTotalEnergy() {
        return (double) energyPerHectogram * (double) foodWeight / 100d;
    }

    public void setFoodWeight(int foodWeight) {
        this.foodWeight = foodWeight;
    }
}
