package com.example.foodcare.model;

import com.example.foodcare.entity.Diet;

import java.util.ArrayList;
import java.util.List;

public interface IMainModel {

    //获取今日推荐摄入
    double getRecommendedIntake();

    //获取今日饮食
    ArrayList<MainGroup> getMeals();

    //获取今日摄入总量
    double getIntake();

    //获取今日运动消耗
    double getConsumption();

    //查询数据库并重新整理数据
    void initGroupList(List<Diet> dietList);
}
