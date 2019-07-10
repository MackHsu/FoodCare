package com.example.foodcare.Retrofit.A_entity;

import java.io.Serializable;

public class Sport implements Serializable {

    private int id;
    private String name;
    private int consume;

    public Sport() {
    }

    public String getPicUrl(){
        return "";
    }
    public Sport(int id) {
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

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }
}
