package com.example.foodcare.Retrofit.A_entity;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FoodMap implements Serializable {

    private Food food;
    private Map<String,String> ingredients;//主料
    private Map<String,String> excipients;//辅料
    private Map<String,String> seasoning;//配料

    private List<String> practice;//做法

    public FoodMap() {
    }

    public FoodMap(Food food, Map<String, String> ingredients, Map<String, String> excipients, Map<String, String> seasoning, List<String> practice) {
        this.food = food;
        this.ingredients = ingredients;
        this.excipients = excipients;
        this.seasoning = seasoning;
        this.practice = practice;
    }


    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, String> getExcipients() {
        return excipients;
    }

    public void setExcipients(Map<String, String> excipients) {
        this.excipients = excipients;
    }

    public Map<String, String> getSeasoning() {
        return seasoning;
    }

    public void setSeasoning(Map<String, String> seasoning) {
        this.seasoning = seasoning;
    }

    public List<String> getPractice() {
        return practice;
    }

    public void setPractice(List<String> practice) {
        this.practice = practice;
    }
}
