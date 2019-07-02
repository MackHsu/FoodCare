package com.example.foodcare.entity;

public class UserLabel {

    //食物标签名（辣，高甜，高脂肪），也可以是口味(甜)，初始化时输入
    private Label label;

    //标签相对于一个用户的权重
    private Double weight;

    //相对的用户
    private Account account;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserLabel() {
    }

}
