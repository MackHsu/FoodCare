package com.example.foodcare.model;

import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.presenter.IMainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements IMainModel {

    private ArrayList<MainGroup> groupList;
    private double recommendedIntakeToday;
    private double consumption;
    IMainPresenter presenter;

    public MainModel(IMainPresenter presenter) {
        this.presenter = presenter;
        initGroupList();
        recommendedIntakeToday = 1886;
        consumption = 53;
    }

    @Override
    public ArrayList<MainGroup> getMeals() {
        return groupList;
    }

    @Override
    public double getRecommendedIntake() {
        return recommendedIntakeToday;
    }

    @Override
    public double getConsumption() {
        return consumption;
    }

    @Override
    public double getIntake() {
        double intake = 0;
        for (MainGroup meal: groupList) {
            intake = intake + meal.getTotalEnergyThisMeal();
        }
        return intake;
    }

    //前端测试用
    public void initGroupList() {
        groupList = new ArrayList<>();
        ArrayList<MainFood> foodList = new ArrayList<>();

        //早餐
        MainFood mainFood = new MainFood("煮鸡蛋", 50, 100);
        foodList.add(mainFood);
        mainFood = new MainFood("豆浆", 200, 50);
        foodList.add(mainFood);
        MainGroup mainGroup = new MainGroup("早餐", 155, foodList);
        groupList.add(mainGroup);

        //午餐
        foodList = new ArrayList<>();
        mainFood = new MainFood("米饭", 200, 150);
        foodList.add(mainFood);
        mainFood = new MainFood("鸡腿", 50, 400);
        foodList.add(mainFood);
        mainGroup = new MainGroup("午餐", 450, foodList);
        groupList.add(mainGroup);
    }

    //查询数据库并整理数据适配adapter，按3:4:3分配三餐热量
    @Override
    public void initGroupList(List<Diet> dietList) {
        groupList.add(new MainGroup("早餐", recommendedIntakeToday * 0.3, new ArrayList<MainFood>()));
        groupList.add(new MainGroup("午餐", recommendedIntakeToday * 0.4, new ArrayList<MainFood>()));
        groupList.add(new MainGroup("晚餐", recommendedIntakeToday * 0.3, new ArrayList<MainFood>()));
        for (Diet diet: dietList)
        {
            //TODO: 完成这一段代码，获取食物相关数据并整理数据
//            String foodName;
//            String foodWeight;
//            double energyPerHectogram;
//            查询数据库得到上面三个数据
//            groupList.get(diet.getGroup()).getFoodsThisMeal().add(new MainFood(foodName, foodWeight, energyPerHectogram));
        }
    }
}
