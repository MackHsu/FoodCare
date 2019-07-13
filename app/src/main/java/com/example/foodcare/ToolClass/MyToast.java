package com.example.foodcare.ToolClass;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.telecom.Call;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {
    public static void  mytoast(String text,final Context context){
        System.out.println(text);
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void mytoastUpper(String text, final Context context){
        System.out.println(text);
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 50);
        toast.show();
    }
}
