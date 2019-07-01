package com.example.foodcare.activity;

import com.example.foodcare.tools.StaticVariable;

public class Fruit {

    private String name;        //食品名称

    private int imageId;            //图片编号

    private String heat;             //热量

    private String CHO;                 //碳水化合物，单位是g

    private String protein;            //蛋白质，g

    private String zhifang;             //脂肪

    private String Na;                  //纳的含量

    private String VitaminA;            //维生素A的含量，单位是mg

    private String VitaminB;            //维生素B

    private String VitaminC;            //维生素C


    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
        this.heat=name+"J/100g";
        this.CHO="100g";
        this.protein="50g";
        this.zhifang="20g";
        this.Na="0.06g";
        this.VitaminA="30mg";
        this.VitaminB="25mg";
        this.VitaminC="14mg";
    }

    public Fruit(String name){    //这个构造函数做测试用，
        this.name=name;
        this.imageId=1;
        this.heat="1000J";
        this.CHO="100g";
        this.protein="50g";
        this.zhifang="20g";
        this.Na="0.06g";
        this.VitaminA="30mg";
        this.VitaminB="25mg";
        this.VitaminC="14mg";
    }

    public static Fruit JsonToFruit(String jsondata){
        return StaticVariable.JsonToFruit(jsondata);
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getHeat(){return heat;}

    public String getCHO(){return CHO;}

    public String getProtein(){return protein;}

    public String getNa(){return Na;}

    public String getZhifang(){return zhifang;}

    public String getVitaminA(){return VitaminA;}

    public String getVitaminB(){return VitaminB;}

    public String getVitaminC(){return VitaminC;}


}