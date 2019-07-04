package com.example.foodcare.Retrofit.A_entity;


import java.util.List;

public class Label {

    private int id;
    private String name;

    private List<FoodLabel> foodLabels;

    public Label() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodLabel> getFoodLabels() {
        return foodLabels;
    }

    public void setFoodLabels(List<FoodLabel> foodLabels) {
        this.foodLabels = foodLabels;
    }
}
