package com.example.foodcare.ToolClass;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.Retrofit.A_entity.DietDetail;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodReg;
import com.example.foodcare.Retrofit.User.UserInformation.UserInformationTest;
import com.example.foodcare.entity.AccountID;

import java.util.List;

public class TodayAnalyseTool {
    private Account account;
    UserInformationTest info;
    private Context context;
    public final int ACCOUNT_GET_SUCCESS=8;
    public final int ACCOUNT_GET_FAILE=9;
    private int id;
    private int height;
    private int weight;
    private int TodayRecommended;//今日推荐热量
    private int TodayIntake;//今日摄入热量
    private int TodaySport;//今日运动消耗
    private int TodayLeft;//今日剩余可摄入热量
    private int BreakFastEnergy;//早餐总能量
    private int BreakFastRecommended;//早餐推荐量
    private int LunchEnergy;//午餐总能量
    private int LunchRecommended;//午餐推荐量
    private int DinnerEnergy;//晚餐总能量
    private int DinnerRecommended;//晚餐推荐量
    private int ProteinAmount;//蛋白质总量
    private int SugarAmount;//碳水化合物总量
    private int FatAmount;//脂肪总量
    private int SodiumAmount;//钠盐总量
    private List<Diet> diets;

    public TodayAnalyseTool(int TodayRecommended,List<Diet> diets,Context context){
        this.diets = diets;
        this.TodayRecommended = TodayRecommended;
        this.context = context;
    }

    public void analyse(){
        for(Diet diet : diets){
            int dietenergy = 0;
            for(DietDetail dietDetail:diet.getDetailList()){
                Food food = dietDetail.getFood();
                //更新每顿饭能量， 所有食物的单位热量乘摄入量之和
                dietenergy += food.getHeat()*dietDetail.getQuantity()/100;
                //更新所有摄入营养素总量
                ProteinAmount += food.getProtein()*dietDetail.getQuantity()/100;
                SugarAmount += food.getTanshui()*dietDetail.getQuantity()/100;
                FatAmount += food.getFat()*dietDetail.getQuantity()/100;
                SodiumAmount += food.getNa()*dietDetail.getQuantity()/100;
            }
            switch(diet.getGroup())
            {
                case 0:
                    BreakFastEnergy = dietenergy;
                    break;
                case 1:
                    LunchEnergy = dietenergy;
                    break;
                case 2:
                    DinnerEnergy = dietenergy;
                    break;
            }
        }
        //分析推荐摄入量等等
        requestRecommend();

    }

    public void requestRecommend() {

       info = new UserInformationTest();

        android.os.Handler handlerhere = new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case ACCOUNT_GET_SUCCESS:
                        //计算每日推荐摄入热量
                        TodayRecommended = new Double(account.getWeight()*35).intValue();
                        //计算早餐午餐晚餐的推荐比例3:4:3
                        BreakFastRecommended = new Double(TodayRecommended*0.3).intValue();
                        DinnerRecommended = BreakFastRecommended;
                        LunchRecommended = TodayRecommended-2*BreakFastRecommended;
                        break;
                    case ACCOUNT_GET_FAILE:
                        MyToast.mytoast("获取用户个人身体数据失败",context);
                        System.out.println("获取用户个人身体数据失败");
                        break;
                }
            }
        };
        info.setHandler(handlerhere);
        info.request(AccountID.getId(),context);
    }

    private void getAccount(){
    }

    public UserInformationTest getInfo() {
        return info;
    }

    public int getACCOUNT_GET_SUCCESS() {
        return ACCOUNT_GET_SUCCESS;
    }

    public int getACCOUNT_GET_FAILE() {
        return ACCOUNT_GET_FAILE;
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBreakFastRecommended() {
        return BreakFastRecommended;
    }

    public int getLunchRecommended() {
        return LunchRecommended;
    }

    public int getDinnerRecommended() {
        return DinnerRecommended;
    }

    public int getTodayRecommended() {
        return TodayRecommended;
    }

    public void setTodayRecommended(int todayRecommended) {
        TodayRecommended = todayRecommended;
    }

    public int getTodayIntake() {
        return TodayIntake;
    }

    public void setTodayIntake(int todayIntake) {
        TodayIntake = todayIntake;
    }

    public int getTodaySport() {
        return TodaySport;
    }

    public void setTodaySport(int todaySport) {
        TodaySport = todaySport;
    }

    public int getTodayLeft() {
        return TodayLeft;
    }

    public void setTodayLeft(int todayLeft) {
        TodayLeft = todayLeft;
    }

    public int getBreakFastEnergy() {
        return BreakFastEnergy;
    }

    public void setBreakFastEnergy(int breakFastEnergy) {
        BreakFastEnergy = breakFastEnergy;
    }

    public int getLunchEnergy() {
        return LunchEnergy;
    }

    public void setLunchEnergy(int lunchEnergy) {
        LunchEnergy = lunchEnergy;
    }

    public int getDinnerEnergy() {
        return DinnerEnergy;
    }

    public void setDinnerEnergy(int dinnerEnergy) {
        DinnerEnergy = dinnerEnergy;
    }

    public int getProteinAmount() {
        return ProteinAmount;
    }

    public void setProteinAmount(int proteinAmount) {
        ProteinAmount = proteinAmount;
    }

    public int getSugarAmount() {
        return SugarAmount;
    }

    public void setSugarAmount(int sugarAmount) {
        SugarAmount = sugarAmount;
    }

    public int getFatAmount() {
        return FatAmount;
    }

    public void setFatAmount(int fatAmount) {
        FatAmount = fatAmount;
    }

    public int getSodiumAmount() {
        return SodiumAmount;
    }

    public void setSodiumAmount(int sodiumAmount) {
        SodiumAmount = sodiumAmount;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public void setDiets(List<Diet> diets) {
        this.diets = diets;
    }
}
