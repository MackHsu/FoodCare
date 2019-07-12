package com.example.foodcare.Retrofit.A_entity;

import java.io.Serializable;

public class Account implements Serializable {

    private int id;
    private String name;
    //定的目标 0-保持健康，1-减肥，2-增肌
    private int plan;
    //平均活动水平（0-久坐不动（几乎不锻炼），轻度运动（每周锻炼1-3天），
    // 中度运动（每周适度锻炼和/或进行3-5天的体育运动），重度运动（每周6-7天的剧烈运动），
    // （从事体力劳动或体能训练如每天两次的训练）高强度运动）
    private int level;
    private int age;
    private int sex;          //0是女，1是男
    private String user;
    private String password;
    private Double height;
    private Double weight;
    //用户图像（可自定义）,存路径
    private String picture;

    //体脂率
    private Double fatRate;

    public Account() {
    }

    public Account(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getFatRate() {
        return fatRate;
    }

    public void setFatRate(Double fatRate) {
        this.fatRate = fatRate;
    }
}
