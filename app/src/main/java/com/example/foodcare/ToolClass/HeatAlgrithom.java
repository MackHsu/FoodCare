package com.example.foodcare.ToolClass;

import java.util.Map;

//热量计算的类
public class HeatAlgrithom {

    private double weight;
    private int height;
    private int age;
    private int sex; // 0-male, 1-female
    //平均活动水平（0-久坐不动，轻度运动，中度运动，重度运动，高强度运动）
    private int level;
    //个人目标（0-保持健康，1-减肥，2-增重）
    private int plan;

    private static double[] map = {1.2,1.375,1.55,1.725,1.9};

    //计算总热量
    public static Double TotalHeat(int sex,int age,double weight,int height,int level,int plan){
        //个人基础代谢率
        double BMR = 0.0;

        //性别为 女
        if (sex == 0)
            BMR = 9.6*weight + 1.8*height - 4.7 *age + 655;
        else //性别为男
            BMR = 13.7*weight + 5.0 * height + 6.8 * age + 66;

        //使用哈里斯·本尼迪克特公式来计算总能量消耗值
        BMR *= map[level];

        //保持健康
        if (plan == 0)
            BMR += 200;
        //减肥
        else if (plan == 1)
            BMR += 50;
        //增重
        else
            BMR += 500;

        return BMR;
    }


}
