package com.example.foodcare.UploadPicture;

import java.util.List;

public class ReturnInfo {
    List<FoodRank> resultInfo;
    public String show(){
        String result="";
        for(int i=0;i<resultInfo.size();i++){
            result="\n食品： "+resultInfo.get(i).getFoodname()+"      相似率：    "+resultInfo.get(i).getProbability()+"%\n";
        }
        return result;
    }
}
class FoodRank {

    private String foodname;
    private Double probability;

    public FoodRank() {
    }

    public FoodRank(String foodname, Double probability) {
        this.foodname = foodname;
        this.probability = probability;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}

