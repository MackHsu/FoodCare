package com.example.foodcare.Retrofit.A_entity;

//import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Menu {

    //TODO 增加拍照后的菜单图片路径的存储

    private int id;
    private String name;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    //对应的所有者用户
    private Account account;

    //菜单项
    private List<MenuItem> menuItems;

    public Menu() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
