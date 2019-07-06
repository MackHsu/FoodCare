package com.example.foodcare.Retrofit.A_entity;

import java.util.List;

public class FoodReg {


    private List<Food> foods;

    private Float probability;

    private String label;

    private int heat;

    public FoodReg() {
    }

    public FoodReg(List<Food> foods, Float probability, String label, int heat) {
        this.foods = foods;
        this.probability = probability;
        this.label = label;
        this.heat = heat;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }
}
