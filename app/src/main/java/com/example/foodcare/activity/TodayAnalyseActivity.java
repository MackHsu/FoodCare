//********曾志昊于2019.6.29 23:08最后修改
//***********创建者：曾志昊**************
package com.example.foodcare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.foodcare.R;
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

import java.util.ArrayList;
import java.util.List;

public class TodayAnalyseActivity extends AppCompatActivity {
    //饼状图
    PieChart pieChart;
    //柱状图
    RadarChart radarChart;
    int TodayRecommended;
    int TodayIntake;
    int TodaySport;
    int TodayLeft;
   // DayDetail today;
    TextView viewTodayRecommended;
    TextView viewTodayIntake;
    TextView viewTodaySport;
    TextView viewTodayLeft;

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
        entries.add(new PieEntry(40,"午餐"));
        entries.add(new PieEntry(20,"早餐"));
        entries.add(new PieEntry(30,"晚餐"));
        entries.add(new PieEntry(10,"加餐"));
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
        TodayRecommended = 2000;
        TodayIntake = 1500;
        TodaySport = 200;
        TodayLeft = TodayRecommended - TodayIntake;

    }

    public void setTodayIntake(int todayIntake){
        TodayIntake = todayIntake;
    }
    public void setTodayRecommended(int todayrRcommended){
        TodayRecommended = todayrRcommended;
    }
    public void setTodaySport(int todaySport){
        TodaySport = todaySport;
    }
    public void setTodayLeft(int todayLeft){
        TodayLeft = todayLeft;
    }

}