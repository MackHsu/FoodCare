//********曾志昊于2019.6.29 23:08最后修改
//***********创建者：曾志昊**************
package com.example.foodcare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.activity.MainActivity;
import com.example.foodcare.tools.BackGroundSpan;
import com.example.foodcare.tools.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
            dates.add(new CalendarDay(dateFormat.parse("2019-6-2")));
            dates.add(new CalendarDay(dateFormat.parse("2019-6-1")));
            dates.add(new CalendarDay(dateFormat.parse("2019-6-3")));
            dates.add(new CalendarDay(dateFormat.parse("2019-6-5")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // dates.add(new CalendarDay(new Date(str2long("2017-8-5","yyyy-MM-dd"))));
        calendarView.addDecorators(new com.example.foodcare.tools.EventDecorator(Color.YELLOW,dates));
        //  calendarView.addDecorators(new BackGroundSpan(dates));

        //************

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
                currentDate = date;
                //跳转到当日的饮食情况  ************************************
                // 将当前选择的日期装入bundles中作为参数随着intent传入将进入的界面
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                Bundle bundles = new Bundle();
                bundles.putInt("selectedYear",currentDate.getYear());
                bundles.putInt("selectedMonth",currentDate.getMonth());
                bundles.putInt("selectedDay",currentDate.getDay());
                intent.putExtras(bundles);
                startActivity(intent);
            }
        });
    }
    //可以在某处显示当前选择的日期是多少
    public void getTime(View view) {
        if (currentDate != null) {
            int year = currentDate.getYear();
            int month = currentDate.getMonth() + 1; //月份跟系统一样是从0开始的，实际获取时要加1
            int day = currentDate.getDay();
            Toast.makeText(this, currentDate.toString() + "你选中的是：" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_LONG).show();
        }
    }
}
