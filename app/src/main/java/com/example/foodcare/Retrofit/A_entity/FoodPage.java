package com.example.foodcare.Retrofit.A_entity;


import java.io.Serializable;
import java.util.List;

public class FoodPage implements Serializable {

    private Page page;
    private List<Food> foods;

    public FoodPage() {
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }


}
