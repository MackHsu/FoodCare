package com.example.foodcare.model;

import com.example.foodcare.entity.MainFood;
import com.example.foodcare.entity.MainGroup;
import com.example.foodcare.presenter.IMainPresenter;

import java.util.ArrayList;

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
}
