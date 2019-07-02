package com.example.foodcare.view;

import com.example.foodcare.entity.MainGroup;

import java.util.ArrayList;

public interface IMainView {

    //刷新今日饮食列表和热量
    void refresh(ArrayList<MainGroup> groupList, double recommendedIntake, double intake, double consumption);

    //刷新登录状态
    void onLogin();
}
