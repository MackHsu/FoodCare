package com.example.foodcare.presenter;

public interface IMainPresenter {

    //通知model获取今日饮食、热量并通知View刷新界面
    void refreshTodayMealListAndEnergy();

    //获取登录的用户信息，包括帐号、用户名、头像
    void getUser();
}
