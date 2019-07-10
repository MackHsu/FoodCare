package com.example.foodcare.Retrofit.A_entity;

import java.util.Date;

public class Play {

    private int account_id;

    private Sport sport;

    private Date date;

    //运动时长，以秒为单位
    private int time;

    public Play() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
