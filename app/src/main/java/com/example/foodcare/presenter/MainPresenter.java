package com.example.foodcare.presenter;

import com.example.foodcare.activity.MainActivity;
import com.example.foodcare.model.IMainModel;
import com.example.foodcare.view.IMainView;

public class MainPresenter implements IMainPresenter {

    private IMainView mainView;
    private IMainModel mainModel;

    public MainPresenter(IMainView mainView) {
        this.mainModel = mainModel;
        this.mainView = mainView;
    }

    @Override
    public void refreshTodayMealListAndEnergy() {
        mainView.refresh(mainModel.getMeals(), mainModel.getRecommendedIntake(), mainModel.getIntake(), mainModel.getConsumption());
    }

    @Override
    public void getUser() {

    }
}
