package com.example.foodcare.entity;

public class FoodRank implements Comparable<FoodRank>{

    private String foodname;
    private Float probability;

    public FoodRank() {

    }

    public FoodRank(String foodname, Float probability) {
        this.foodname = foodname;
        this.probability = probability;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    @Override
    public int compareTo(FoodRank o) {
        return o.probability.compareTo(this.probability);
    }
}
