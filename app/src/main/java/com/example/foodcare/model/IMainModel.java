package com.example.foodcare.model;

import com.example.foodcare.entity.MainGroup;

import java.util.ArrayList;

public interface IMainModel {

    //获取今日推荐摄入
    double getRecommendedIntake();

    //获取今日饮食
    ArrayList<MainGroup> getMeals();

    //获取今日摄入总量
    double getIntake();

    //获取今日运动消耗
    double getConsumption();
}
