package com.example.foodcare.entity;

import android.graphics.BitmapFactory;
import android.view.View;

import com.example.foodcare.R;
import com.example.foodcare.tools.StaticVariable;


@SuppressWarnings("ResourceType")
public class Account {
    private int id;
    private String name;
    private int age;
    private String user;
    private String password;
    private double height;
    private double weight;
    private double fatRate;
    private byte[] picture;

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFatRate() {
        return fatRate;
    }

    public void setFatRate(double fatRate) {
        this.fatRate = fatRate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Account(View v) {
        id=454546;
        name="李鑫超";
        age=20;
        user="账号";
        password="密码";
        height=183.2;
        weight=66.1;
        fatRate=0.01;
        try {
            picture= StaticVariable.BitmapToByte(BitmapFactory.decodeStream(v.getContext().getResources().openRawResource(R.drawable.defult)));
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
