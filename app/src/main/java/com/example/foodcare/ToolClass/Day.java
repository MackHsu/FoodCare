package com.example.foodcare.ToolClass;
/********************曾志昊 2017302580214************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Day {
    private static String dateString;
    private static Date date;
    private static SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
//    private static String dateformate = "yyyy-MM-dd";

    public Day() {
//        date = new Date();
//        simpleDateFormat = new SimpleDateFormat(dateformate);
    }

    public static void nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        setDate(calendar.getTime());
    }

    public static void lastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        setDate(calendar.getTime());
    }


    public static String getDateString() {
        return dateString;
    }

    public static void setDateString(String dateString) {
        Day.dateString = dateString;
        try{
            date = simpleDateFormat.parse(dateString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        Day.date = date;
        dateString = simpleDateFormat.format(date);
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

}
