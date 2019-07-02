package com.example.foodcare.entity;

public class Food {

    private int id;
    //食物组别（0-菜品 or 1-食品）
    private int group;
    private String name;
    //图片
    private Byte[] picture;
    //食品类别
    private String category;
    //菜品类别（广东菜）
    private String type;

    private int heat;

    private Double tanshui;
    private Double fat;
    private Double protein;
    private Double cellulose;
    private Double vitaminA;
    private Double vitaminC;
    private Double vitaminE;
    private Double carotene;
    private Double liuan;
    private Double hehuang;
    private Double yansuan;
    //胆固醇
    private int cholesterol;
    private Double mei;
    private Double gai;
    private Double tie;
    private Double xin;
    private Double tong;
    private Double meng;
    private Double jia;
    private Double lin;
    private Double na;
    private Double xi;
    //度量方法（单位）
    private String measure;
    //评价
    private String evaluate;
    //主料
    private String ingredient;
    //辅料
    private String excipients;
    //菜品做法
    private String pratice;

    public Food() {
    }

    public Food(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public Double getTanshui() {
        return tanshui;
    }

    public void setTanshui(Double tanshui) {
        this.tanshui = tanshui;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCellulose() {
        return cellulose;
    }

    public void setCellulose(Double cellulose) {
        this.cellulose = cellulose;
    }

    public Double getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(Double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public Double getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(Double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public Double getVitaminE() {
        return vitaminE;
    }

    public void setVitaminE(Double vitaminE) {
        this.vitaminE = vitaminE;
    }

    public Double getCarotene() {
        return carotene;
    }

    public void setCarotene(Double carotene) {
        this.carotene = carotene;
    }

    public Double getLiuan() {
        return liuan;
    }

    public void setLiuan(Double liuan) {
        this.liuan = liuan;
    }

    public Double getHehuang() {
        return hehuang;
    }

    public void setHehuang(Double hehuang) {
        this.hehuang = hehuang;
    }

    public Double getYansuan() {
        return yansuan;
    }

    public void setYansuan(Double yansuan) {
        this.yansuan = yansuan;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getMei() {
        return mei;
    }

    public void setMei(Double mei) {
        this.mei = mei;
    }

    public Double getGai() {
        return gai;
    }

    public void setGai(Double gai) {
        this.gai = gai;
    }

    public Double getTie() {
        return tie;
    }

    public void setTie(Double tie) {
        this.tie = tie;
    }

    public Double getXin() {
        return xin;
    }

    public void setXin(Double xin) {
        this.xin = xin;
    }

    public Double getTong() {
        return tong;
    }

    public void setTong(Double tong) {
        this.tong = tong;
    }

    public Double getMeng() {
        return meng;
    }

    public void setMeng(Double meng) {
        this.meng = meng;
    }

    public Double getJia() {
        return jia;
    }

    public void setJia(Double jia) {
        this.jia = jia;
    }

    public Double getLin() {
        return lin;
    }

    public void setLin(Double lin) {
        this.lin = lin;
    }

    public Double getNa() {
        return na;
    }

    public void setNa(Double na) {
        this.na = na;
    }

    public Double getXi() {
        return xi;
    }

    public void setXi(Double xi) {
        this.xi = xi;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getExcipients() {
        return excipients;
    }

    public void setExcipients(String excipients) {
        this.excipients = excipients;
    }

    public String getPratice() {
        return pratice;
    }

    public void setPratice(String pratice) {
        this.pratice = pratice;
    }
}