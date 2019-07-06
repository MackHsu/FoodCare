package com.example.foodcare.ToolClass;

public class UserInfo {
    private static int id = -1;
    private static boolean state;
    public static int getId(){return id;}
    public static boolean getState(){return state;}
    public static void setId(int id){id = id;}
    public static void setState(boolean s){state = s;}
}
