package com.example.foodcare.Retrofit;

public class Account {

    private int id;
    private String name;
    //定的目标 0-保持健康，1-减肥，2-增肌
    private int plan;
    private int age;
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
