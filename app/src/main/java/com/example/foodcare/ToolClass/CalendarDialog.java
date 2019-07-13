package com.example.foodcare.ToolClass;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.foodcare.R;
import com.example.foodcare.activity.CalendarActivity;
import com.example.foodcare.activity.MainActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarDialog {
    private Handler handler;
    private final int DATE_PICKED = 1;

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    public void popCalendarDialog(final Context context){
        System.out.println("进入pop");
        final DialogPlus dialog = DialogPlus.newDialog(context)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.calendar_dialog_sheet))
                .create();
        final MaterialCalendarView calendarView = (MaterialCalendarView) dialog.findViewById(R.id.dialogCalendar);


        //取消按钮
        Button cancelButton = (Button) dialog.findViewById(R.id.dialogcancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)   //设置每周开始的第一天
                .setMinimumDate(CalendarDay.from(2010, 4, 3))  //设置可以显示的最早时间
                .setMaximumDate(CalendarDay.from(2020, 5, 12))//设置可以显示的最晚时间
                .setCalendarDisplayMode(CalendarMode.MONTHS)//设置显示模式，可以显示月的模式，也可以显示周的模式
                .commit();// 返回对象并保存
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                //跳转到当日的饮食情况  ************************************

                int monthint =  date.getMonth()+1;
                String year = date.getYear()+"";
                String month = monthint+"";
                String day = date.getDay()+"";
                if(monthint<10)
                {
                    month = "0"+month;
                }
                if(date.getDay()<10) {
                    day = "0" + day;
                }
                String datepicked = year+"-"+month+"-"+day;

                System.out.println("日历修改前"+Day.getDateString());
                System.out.println("日历  年"+date.getYear());
                System.out.println("日历  月"+date.getMonth());
                System.out.println("日历"+datepicked);
                Day.setDateString(datepicked);
                Message message = new Message();
                message.what = DATE_PICKED;
                handler.sendMessage(message);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
