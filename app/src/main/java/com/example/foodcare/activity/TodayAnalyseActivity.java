//********曾志昊于2019.6.29 23:08最后修改
//***********创建者：曾志昊**************
package com.example.foodcare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.Retrofit.A_entity.DietDetail;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.DietPackage.Diet.AnyDayDiet.AnyDayDietStringTest;
import com.example.foodcare.Retrofit.DietPackage.Diet.TodayDiet.TodayDietTest;
import com.example.foodcare.Retrofit.SportPackage.GetPlayByDateTest;
import com.example.foodcare.Retrofit.User.UserInformation.UserInformationTest;
import com.example.foodcare.ToolClass.CalendarDialog;
import com.example.foodcare.ToolClass.Day;
import com.example.foodcare.ToolClass.MyAxisValueFormatter;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.XAxisValueFormatter;
import com.example.foodcare.entity.AccountID;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.victor.loading.rotate.RotateLoading;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.foodcare.ToolClass.HeatAlgrithom.TotalHeat;
import static java.lang.Math.abs;

public class TodayAnalyseActivity extends AppCompatActivity {
    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;
    private final int ACCOUNT_GET_SUCCESS = 8;
    private final int RETURN_ANALYSE = 20;
    private final int ACCOUNT_GET_FAILE = 9;
    public final int GET_PLAYS_NULL = 0;
    public final int GET_PLAYS_SUCCESS = 1;
    public final int REQUEST_FALSE = 2;
    private final int DATE_PICKED = 1;

    RotateLoading loading;
    //折线图
    private LineChart lineChart;
    //饼状图
    PieChart pieChart;
    //柱状图
    RadarChart radarChart;
    double TodayRecommended;     //推荐摄入总量
    double TodayIntake;          //今日摄入能量
    double TodaySport;           //今日运动消耗
    double TodayLeft;             //今日剩余可摄入
    int BreakFastEnergy;        //早餐摄入量
    int LunchEnergy;            //午餐摄入量
    int DinnerEnergy;           //晚餐摄入量
    int AdditionEnergy;         //加餐的量
    double ProteinPercentage;      //蛋白质百分比
    double ProteinAmount;          //蛋白质数量
    double SugarPercentage;        //苏糖百分比
    double SugarAmount;            //碳水化合物
    double FatPercentage;          //脂肪百分比
    double FatAmount;              //脂肪数量
    double cellulosePercentage;       //钠百分比
    double celluloseAmount;           //钠数量
    double height;            //用户的重量
    // DayDetail today;
    TextView viewTodayRecommended;
    TextView viewTodayIntake;
    TextView viewTodaySport;
    TextView viewTodayLeft;
    TextView today_detail_date;
    TextView error_info_analyse;
    ImageView lastday;
    ImageView nextday;
    ImageButton backButton;
    private List<Diet> diets;
    Account account;
    boolean can_updata = false;
    DecimalFormat df = new DecimalFormat( "0.00");

    List<Play> plays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_analyse);
        try {
            Intent intent = getIntent();
            //intent.putExtra("CT",consumptionToday);//消耗量
            TodaySport = intent.getDoubleExtra("CT", 0.0);
        } catch (Exception e) {
            e.printStackTrace();
            TodaySport = 0.0;
        }

        initiation();//首先初始化上面的部分控件
        Log.i("TAG", "第一次初始化控件成功");

        requestRecommend();//初始化account
        Log.i("TAG", "初始化用户");
    }

    //初始化控件变量
    private void initiation() {
        viewTodayRecommended = (TextView) findViewById(R.id.TodayRecommended);
        viewTodayIntake = (TextView) findViewById(R.id.TodayIntake);
        viewTodaySport = (TextView) findViewById(R.id.TodaySport);
        viewTodayLeft = (TextView) findViewById(R.id.TodayLeft);
        //getNumber();
        viewTodayRecommended.setText("今日推荐摄入总量:");
        viewTodayIntake.setText("今日已摄入总量:");
        viewTodaySport.setText("今日运动总消耗:");
        viewTodayLeft.setText("今日剩余可摄入:");
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        today_detail_date = (TextView) findViewById(R.id.today_detail_date_analyze);
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //today_detail_date.setText(df.format(new Date()));
        today_detail_date.setText(Day.getDateString());
        error_info_analyse = (TextView) findViewById(R.id.error_info_analyse);
        lastday = (ImageView) findViewById(R.id.last_day_analyse);
        nextday = (ImageView) findViewById(R.id.next_day_analyse);
        loading = (RotateLoading) findViewById(R.id.loading_analyse);
        /*lastday.setVisibility(View.INVISIBLE);
        nextday.setVisibility(View.INVISIBLE);*/
        lastday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day.lastDay();
                refreshDate();
                requestRecommend();
            }
        });

        nextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day.nextDay();
                refreshDate();
                requestRecommend();
            }
        });

        today_detail_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","点击了日期，选择了日历");
                MyToast.mytoast("点击一次",getApplicationContext());
                CalendarDialog calendarDialog = new CalendarDialog();
                //用于更新挑选的日期
                Handler handlerhere = new Handler(){
                    public void handleMessage(Message msg) {
                        System.out.println("进入handler");
                        switch(msg.what) {
                            case DATE_PICKED:
                                Toast.makeText(getApplicationContext(), "从日期弹窗返回", Toast.LENGTH_SHORT).show();
                                refreshDate();
                                requestRecommend();
                                break;
                            default:
                                break;
                        }
                    }
                };
                calendarDialog.setHandler(handlerhere);
                calendarDialog.popCalendarDialog(TodayAnalyseActivity.this);
            }
        });


    }

    //画图表
    private void drawPieChart() {
        //饼图
        pieChart = (PieChart) findViewById(R.id.PieChart);
        pieChart.setHoleRadius(45f);
        pieChart.setUsePercentValues(true);
        pieChart.setDragDecelerationFrictionCoef(5f);
        pieChart.animateXY(3000, 3000);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.mainColor4));
        //表项
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(LunchEnergy, "午餐"));
        entries.add(new PieEntry(BreakFastEnergy, "早餐"));
        entries.add(new PieEntry(DinnerEnergy, "晚餐"));
        entries.add(new PieEntry(AdditionEnergy, "加餐"));

        PieDataSet dataset = new PieDataSet(entries, "每日进食");
        //dataset.setValueTextColor(Color.GREEN);
        //颜色
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Honeydew3));
        colors.add(getResources().getColor(R.color.gray81));
        colors.add(getResources().getColor(R.color.Azure1));
        colors.add(getResources().getColor(R.color.Beige));
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        //将数字转化为百分比
        data.setDrawValues(true);
       // data.setValueTextColor(Color.GREEN);
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

    public void getTodayData() {
        final AnyDayDietStringTest dataFetcher = new AnyDayDietStringTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_NULL:
                        diets = dataFetcher.getDiets();//此时拿到的是长度为0
                        Log.i("TAG", "请求舒普结果为空");
                        Toast.makeText(getApplicationContext(), "用户今日Diet数据为空实打实", Toast.LENGTH_SHORT).show();
                        break;
                    case DATA_UPDATED:
                        diets = dataFetcher.getDiets();  //此时拿到的长度就是其吃了几顿
                        Log.i("TAG代销", diets.size() + "");
                        if (diets.size() == 0) {
                            error_info_analyse.setText("您今日还没有饮食记录");
                            error_info_analyse.setTextSize(25);
                            error_info_analyse.setVisibility(View.VISIBLE);
                            NullData();
                            getTodaySportData();
                        } else {
                            error_info_analyse.setVisibility(View.INVISIBLE);
                            getTodaySportData();
                            Log.i("TAG","获取到用户的饮食记录");
                            //Toast.makeText(getApplicationContext(), "用户今日Diet数据很多多大", Toast.LENGTH_SHORT).show();
                        }


                        break;
                    case FAILED:
                        Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        int id = AccountID.getId();
        dataFetcher.request(id,Day.getDateString(), getApplicationContext());
    }


    public void requestRecommend() {

        final UserInformationTest info = new UserInformationTest();

        android.os.Handler handlerhere = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch (msg.what) {
                    case ACCOUNT_GET_SUCCESS:
                        //计算每日推荐摄入热量
                        try {
                            int sex = info.account.getSex();
                            int age = info.account.getAge();
                            double weight = info.account.getWeight();
                            double _height = info.account.getHeight();
                            int level = info.account.getLevel();
                            int plan = info.account.getPlan();
                            height = _height;
                            TodayRecommended = TotalHeat(sex, age, weight, _height, level, plan);    //推荐摄入总量
                        } catch (Exception e) {
                            TodayRecommended = 0;
                            e.printStackTrace();
                        }
                        getTodayData();//初始化食谱
                        Log.i("TAG", "初始化食谱成功");

                        break;
                    case ACCOUNT_GET_FAILE:
                        MyToast.mytoast("获取用户个人身体数据失败", getApplicationContext());
                        System.out.println("获取用户个人身体数据失败");
                        break;
                }
            }
        };
        info.setHandler(handlerhere);
        info.request(AccountID.getId(), getApplicationContext());
    }


    private void initPropertyData() {
        try {
            //TodaySport=0;           //今日运动消耗
            TodayIntake = 0;          //今日摄入能量,在后面进行叠加
            for (Diet diet : diets) {
                Log.i("TAG", "正在遍历食谱");
                int dietenergy = 0;
                List<DietDetail> _detailList = diet.getDetailList();
                for (DietDetail dietDetail : _detailList) {
                    Food food = dietDetail.getFood();
                    Log.i("TAG", "food已经实例");
                    //更新每顿饭能量， 所有食物的单位热量乘摄入量之和
                    dietenergy += food.getHeat() * dietDetail.getQuantity() / 100;
                    Log.i("TAG", "视频能量已经获取");
                    //更新所有摄入营养素总量
                    ProteinAmount += food.getProtein() * dietDetail.getQuantity() / 100;
                    Log.i("TAG", "蛋白质");
                    SugarAmount += food.getTanshui() * dietDetail.getQuantity() / 100;
                    Log.i("TAG", "苏糖已经");
                    FatAmount += food.getFat() * dietDetail.getQuantity() / 100;
                    Log.i("TAG", "只当");
                    if (food.getCellulose() == null) {
                        celluloseAmount = 0.0;
                    } else {
                        celluloseAmount += food.getCellulose() * dietDetail.getQuantity() / 100;
                    }
                    Log.i("TAG", celluloseAmount + "");
                }
                switch (diet.getGroup()) {
                    case 0:
                        BreakFastEnergy = dietenergy;
                        TodayIntake = TodayIntake + BreakFastEnergy;
                        break;
                    case 1:
                        LunchEnergy = dietenergy;
                        TodayIntake = TodayIntake + LunchEnergy;
                        break;
                    case 2:
                        DinnerEnergy = dietenergy;
                        TodayIntake = TodayIntake + DinnerEnergy;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "获取信息失败，并且不知道是哪一步出错的");
        }

        AdditionEnergy = (BreakFastEnergy + LunchEnergy + DinnerEnergy) / 10;
        TodayIntake = AdditionEnergy + TodayIntake;
        TodayLeft = TodayRecommended - TodayIntake;
        ProteinPercentage = ProteinAmount * 100.0 / TodayIntake;      //蛋白质百分比
        SugarPercentage = SugarAmount * 100.0 / TodayIntake;        //苏糖百分比
        FatPercentage = FatAmount * 100.0 / TodayIntake;          //脂肪百分比
        cellulosePercentage = celluloseAmount * 100.0 / TodayIntake;       //钠百分比
    }

    private void init() {
        viewTodayRecommended.setText("今日推荐摄入总量:" + (df.format(TodayRecommended) + "千卡"));
        viewTodayIntake.setText("今日已摄入总量:" + (df.format(TodayIntake) + "千卡"));
        viewTodaySport.setText("今日运动总消耗:" + (df.format(TodaySport) + "千卡"));
        if(TodayLeft<0){
            viewTodayLeft.setText("今日剩余可摄入:" + "0千卡");
        }
        else{
            viewTodayLeft.setText("今日剩余可摄入:"+ (df.format(TodayLeft) + "千卡"));
        }

    }

    private void wdnmd() {
        if (abs(TodayRecommended) < 0.001) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            //return;
        }

        initPropertyData();  //初始化上面的数据
        Log.i("TAG", "初始化界面");
        init();        //将数据填充到控件中
        Log.i("TAG", "填充数据成功");
        drawPieChart();   //开始画饼状图
        Log.i("TAG", "画并图成功");
        //drawRadarChart();

        initLineChart();
        Log.i("TAG", "初始化折线图成功");
    }

    private void initLineChart() {
        lineChart = findViewById(R.id.lineChart);
        //lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        //自定义适配器，适配于X轴
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        //自定义适配器，适配于Y轴
        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        lineChart.getAxisRight().setEnabled(false);
        setLineChartData();
    }

    private void setLineChartData() {
        //填充数据，在这里换成自己的数据源
        List<Entry> valsComp1 = new ArrayList<>();
        List<Entry> valsComp2 = new ArrayList<>();

        valsComp1.add(new Entry(0, Float.valueOf(String.valueOf(height * 1.2f))));
        valsComp1.add(new Entry(1, Float.valueOf(String.valueOf(height * 1.4f))));
        valsComp1.add(new Entry(2, Float.valueOf(String.valueOf(height * 0.9f))));
        valsComp1.add(new Entry(3, Float.valueOf(String.valueOf(height * 0.5f))));

        valsComp2.add(new Entry(0, Float.valueOf(String.valueOf(ProteinAmount))));
        valsComp2.add(new Entry(1, Float.valueOf(String.valueOf(SugarAmount))));
        valsComp2.add(new Entry(2, Float.valueOf(String.valueOf(FatAmount))));
        valsComp2.add(new Entry(3, Float.valueOf(String.valueOf(celluloseAmount))));

        //这里，每重新new一个LineDataSet，相当于重新画一组折线
        //每一个LineDataSet相当于一组折线。比如:这里有两个LineDataSet：setComp1，setComp2。
        //则在图像上会有两条折线图，分别表示公司1 和 公司2 的情况.还可以设置更多
        LineDataSet setComp1 = new LineDataSet(valsComp1, "建议摄入量");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(getResources().getColor(R.color.light_blue));
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        LineDataSet setComp2 = new LineDataSet(valsComp2, "你的摄入量");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setDrawCircles(true);
        setComp2.setColor(getResources().getColor(R.color.red));
        setComp2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "执行销毁函数");
    }


    private void getTodaySportData() {
        loading.start();
        final GetPlayByDateTest dataFetcher = new GetPlayByDateTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_PLAYS_NULL:
                        TodaySport=0;
                        loading.stop();
                        Log.i("TAG","用户活动量为0");
                        wdnmd();
                        break;
                    case GET_PLAYS_SUCCESS:
                        plays = dataFetcher.getPlays();
                        TodaySport=sumPlays(plays);
                        Log.i("TAG","获取到用户的运动量");
                        wdnmd();
                        loading.stop();
                        break;
                    case REQUEST_FALSE:
                        Log.i("TAG","请求错误，不再初始化变量");
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        System.out.println(Day.getDateString());
        dataFetcher.request(AccountID.getId(), Day.getDateString());
    }

    private int sumPlays(List<Play> _plays) {
        int sum = 0;
        try {
            for (int i = 0; i < plays.size(); i++) {
                sum=sum+_plays.get(i).getTime()*_plays.get(i).getSport().getConsume()/60;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sum = 0;
        }
        return sum;
    }

    private void refreshDate() {
        System.out.println("日期变化");
        today_detail_date.setText(Day.getDateString());
    }


    private void NullData(){
        viewTodayIntake.setText("今日已摄入总量:暂无数据");
        viewTodaySport.setText("今日运动总消耗:暂无数据");
        viewTodayLeft.setText("今日剩余可摄入:暂无数据");
        BreakFastEnergy=0;        //早餐摄入量
        LunchEnergy=0;            //午餐摄入量
        DinnerEnergy=0;           //晚餐摄入量
        AdditionEnergy=0;         //加餐的量
        ProteinAmount=0;
        SugarAmount=0;
        FatAmount=0;
        celluloseAmount=0;
    }
}
