package com.example.foodcare.ToolClass;

public class EasyFood {
    public EasyFood(String name,int energy,int foodID)
    {
        this.name = name;
        this.energy = energy;
        this.foodID=foodID;
    }
    private String name;
    private int energy;
    private int foodID;
    public void setName(String name){this.name = name;}
    public void setEnergy(int energy){this.energy = energy;}
    public void setFoodID(int foodID){this.foodID = foodID;}
    public String getName(){return name;}
    public int getEnergy(){return energy;}
    public int getFoodID(){return foodID;}
}
