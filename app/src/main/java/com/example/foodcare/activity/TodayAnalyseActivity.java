//********曾志昊于2019.6.29 23:08最后修改
//***********创建者：曾志昊**************
package com.example.foodcare.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayAnalyseActivity extends AppCompatActivity {
    //饼状图
    PieChart pieChart;
    //柱状图
    RadarChart radarChart;
    int TodayRecommended;     //推荐摄入总量
    int TodayIntake;          //今日摄入能量
    int TodaySport;           //今日运动消耗
    int TodayLeft;             //今日剩余可摄入
    int BreakFastEnergy;        //早餐摄入量
    int LunchEnergy;            //午餐摄入量
    int DinnerEnergy;           //晚餐摄入量
    int AdditionEnergy;         //加餐的量
    int ProteinPercentage;      //蛋白质百分比
    int ProteinAmount;          //蛋白质数量
    int SugarPercentage;        //苏糖百分比
    int SugarAmount;            //苏糖数量
    int FatPercentage;          //脂肪百分比
    int FatAmount;              //脂肪数量
    int SodiumPercentage;       //钠百分比
    int SodiumAmount;           //钠数量
   // DayDetail today;
    TextView viewTodayRecommended;
    TextView viewTodayIntake;
    TextView viewTodaySport;
    TextView viewTodayLeft;
    TextView today_detail_date;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_analyse);
        initiation();
        drawPieChart();
        drawRadarChart();
    }

    //初始化控件变量
    private void initiation() {
        viewTodayRecommended = (TextView)findViewById(R.id.TodayRecommended);
        viewTodayIntake = (TextView)findViewById(R.id.TodayIntake);
        viewTodaySport = (TextView)findViewById(R.id.TodaySport);
        viewTodayLeft = (TextView)findViewById(R.id.TodayLeft);
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        today_detail_date=(TextView)findViewById(R.id.today_detail_date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today_detail_date.setText(df.format(new Date()));


        //查询数据
        getNumber();

        viewTodayRecommended.setText(String.format(getResources().getString(R.string.today_recommended),TodayRecommended));
        viewTodayIntake.setText(String.format(getResources().getString(R.string.today_intake),TodayIntake));
        viewTodaySport.setText(String.format(getResources().getString(R.string.today_sport),TodaySport));
        viewTodayLeft.setText(String.format(getResources().getString(R.string.today_left),TodayLeft));

    }

    //画图表
    private void drawPieChart()
    {
        //饼图
        pieChart = (PieChart) findViewById(R.id.PieChart);
        pieChart.setHoleRadius(45f);
        pieChart.setUsePercentValues(true);
        pieChart.setDragDecelerationFrictionCoef(5f);
        pieChart.animateXY(3000,3000);
        //表项
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(LunchEnergy,"午餐"));
        entries.add(new PieEntry(BreakFastEnergy,"早餐"));
        entries.add(new PieEntry(DinnerEnergy,"晚餐"));
        entries.add(new PieEntry(AdditionEnergy,"加餐"));
        PieDataSet dataset = new PieDataSet(entries,"label1");
        //颜色
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.pie_orange));
        colors.add(getResources().getColor(R.color.pie_green));
        colors.add(getResources().getColor(R.color.pie_blue));
        colors.add(getResources().getColor(R.color.pie_purple));
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        //将数字转化为百分比
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        //把字体变大
        data.setValueTextSize(12f);
        //消去右下角的说明文字
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setData(data);
        pieChart.invalidate();
    }
    private void drawRadarChart(){
        radarChart = (RadarChart)findViewById(R.id.RadarChart);
        //X轴
        XAxis xAxis = radarChart.getXAxis();
        //字体颜色
        xAxis.setTextSize(12f);
        //Y轴
        YAxis yAxis = radarChart.getYAxis();
        yAxis.setTextSize(20f);
        yAxis.setStartAtZero(true);
        //Y值是否显示在图标上
        yAxis.setDrawLabels(false);
        List<RadarEntry> yvalues = new ArrayList<>();//数据
        List<String> label = new ArrayList<>();
        label.add("能量");label.add("蛋白");label.add("碳水");label.add("脂肪");label.add("钠");
        for(int i =1;i<=5;i++)
        {
            yvalues.add(new RadarEntry(i,0.8f+0.1f*i));
        }
        //dataset
        List<RadarDataSet> datasets = new ArrayList<>();

        RadarDataSet radardataset1 = new RadarDataSet(yvalues,"条形图");
        //实心填充区域颜色
        radardataset1.setColor(R.color.radar_recommended_fillcolor);
        datasets.add(radardataset1);
        //是否颜色填充实心区域
        radardataset1.setDrawFilled(true);

        // 数据线条宽度
        radardataset1.setLineWidth(2f);
        RadarData radardata = new RadarData(radardataset1);
        radarChart.setData(radardata);
        radarChart.animateXY(3000,3000);

        radarChart.invalidate();
    }
    //获取所有的数据
    private void getNumber() {
        Intent intent = getIntent();
        TodayRecommended = intent.getIntExtra("TodayRecommended",-1);
        TodayIntake = intent.getIntExtra("TodayIntake",-1);
        TodaySport = intent.getIntExtra("TodaySport",-1);
        TodayLeft = intent.getIntExtra("TodayLeft",-1);
        BreakFastEnergy = intent.getIntExtra("TodayLeft",-1);
        LunchEnergy = intent.getIntExtra("LunchEnergy",-1);
        DinnerEnergy = intent.getIntExtra("DinnerEnergy",-1);
        AdditionEnergy = intent.getIntExtra("AdditionEnergy",-1);
        ProteinPercentage = intent.getIntExtra("ProteinPercentage",-1);
        ProteinAmount = intent.getIntExtra("ProteinAmount",-1);
        SugarPercentage = intent.getIntExtra("SugarPercentage",-1);
        SugarAmount = intent.getIntExtra("SugarAmount",-1);
        FatPercentage = intent.getIntExtra("FatPercentage",-1);
        FatAmount = intent.getIntExtra("FatAmount",-1);
        SodiumPercentage = intent.getIntExtra("SodiumPercentage",-1);
        SodiumAmount = intent.getIntExtra("SodiumAmount",-1);

    }

}
