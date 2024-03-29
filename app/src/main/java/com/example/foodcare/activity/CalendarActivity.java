package com.example.foodcare.activity;
/********************曾志昊 2017302580214************************/
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.foodcare.R;
import com.example.foodcare.ToolClass.Day;
import com.example.foodcare.ToolClass.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class CalendarActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    CalendarDay currentDate;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (MaterialCalendarView) findViewById(R.id.CalendarView);

        //添加标注的日期
        Collection<CalendarDay> dates=new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse("2019-06-2"));
            CalendarDay calendarhere = new CalendarDay(calendar);
            System.out.println(calendarhere);
            dates.add(new CalendarDay(calendar));

//            dates.add(new CalendarDay(dateFormat.parse("2019-06-2")));
            dates.add(new CalendarDay(dateFormat.parse("2019-06-1")));
            dates.add(new CalendarDay(dateFormat.parse("2019-06-3")));
            dates.add(new CalendarDay(dateFormat.parse("2019-06-5")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("********************************");
        for(CalendarDay day:dates)
        {
            System.out.println(dates.toString());
        }
        calendarView.addDecorators(new EventDecorator(Color.YELLOW,dates));

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)   //设置每周开始的第一天
                .setMinimumDate(CalendarDay.from(2010, 4, 3))  //设置可以显示的最早时间
                .setMaximumDate(CalendarDay.from(2020, 5, 12))//设置可以显示的最晚时间
                .setCalendarDisplayMode(CalendarMode.MONTHS)//设置显示模式，可以显示月的模式，也可以显示周的模式
                .commit();// 返回对象并保存
        //      设置点击日期的监听
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                // 将当前选择的日期装入bundles中作为参数随着intent传入将进入的界面
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
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
                startActivity(intent);
            }
        });
    }

}
