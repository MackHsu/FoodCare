//许朗铭 2017302580224
//使用github上的GroupedRecyclerAdapter来实现分组列表  https://github.com/donkingliang/GroupedRecyclerViewAdapter
//使用github上的CircleTextImageView来做圆形头像框 https://github.com/CoolThink/CircleTextImageView
package com.example.foodcare.activity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Px;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.FoodRank;
import com.example.foodcare.Retrofit.Diet.AnyDayDiet.AnyDayDietStringTest;
import com.example.foodcare.Retrofit.Diet.AnyDayDiet.AnyDayDietTest;
import com.example.foodcare.Retrofit.Page.PageTest;
import com.example.foodcare.Retrofit.User.UpdateUserInfo.UpdateUserInfoTest;
import com.example.foodcare.Retrofit.User.UserInformation.UserInformationTest;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.Retrofit.A_entity.DietDetail;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.Diet.TodayDiet.TodayDietTest;
import com.example.foodcare.Retrofit.DietDetailList.DietDetailListTest;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.adapter.MainRecyclerAdapter;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.model.MainFood;
import com.example.foodcare.model.MainGroup;
import com.example.foodcare.presenter.MainPresenter;
import com.example.foodcare.tools.SaveFile;
import com.example.foodcare.util.CommonUtil;
import com.example.foodcare.view.HeaderAnimatedScrollView;
import com.example.foodcare.view.IMainView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.thinkcool.circletextimageview.CircleTextImageView;
import com.victor.loading.rotate.RotateLoading;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements IMainView {

    private long exit_time;

    ImageButton menuButton;
    DrawerLayout mainDrawerLayout;
    Button calendarButton;
    Button Cancellation_main;
    ImageButton cameraButton;
    CircleTextImageView UserInformation;
    RecyclerView mainRecycler;
    FloatingActionButton addButton;
    FloatingActionButton analysisButton;
    FloatingActionButton searchButton;
    TextView recommendedIntakeText;
    TextView intakeText;
    TextView consumptionText;
    TextView restText;
    Toolbar toolbar;
    private Uri imageUri;
    TextView dateText;
    Date date;

    ImageView lastday;
    ImageView nextday;
    RelativeLayout mainHeaderLayout;
    HeaderAnimatedScrollView scrollView;
    RelativeLayout centerLayout;
    TextView restTodayLabel;
    TextView recommendedIntakeLabel;
    TextView intakeLabel;
    TextView consumptionLabel;
    LinearLayout mainBgLayout;
    ImageButton uploadPictureButton;
    RotateLoading loading;
    MainPresenter mainPresenter;
    CircleTextImageView avatar;
    TextView username;
    TextView accounttext;
    TextView passageText;
    private final int GET_USERINFO_SUCCESS = 1;

    ArrayList<MainGroup> groupList;
    List<Diet> diets;

    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;
    SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        menuButton = (ImageButton) findViewById(R.id.main_menu_button);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        calendarButton = (Button) findViewById(R.id.calendar) ;
        Cancellation_main=(Button)findViewById(R.id.Cancellation_main);
        UserInformation = (CircleTextImageView) findViewById(R.id.avatar);
        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        addButton = (FloatingActionButton) findViewById(R.id.floating_button_add);
        analysisButton = (FloatingActionButton) findViewById(R.id.floating_button_analysis);
        searchButton = (FloatingActionButton) findViewById(R.id.floating_button_search);
        recommendedIntakeText = (TextView) findViewById(R.id.recommended_intake);
        intakeText = (TextView) findViewById(R.id.intake_today);
        consumptionText = (TextView) findViewById(R.id.consumption_today);
        restText = (TextView) findViewById(R.id.rest_today_text);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loading = (RotateLoading) findViewById(R.id.loading);
        passageText = (TextView) findViewById(R.id.Passage_main);
        username = (TextView) findViewById(R.id.username);
        accounttext = (TextView) findViewById(R.id.accounttext);

        dateText = (TextView) findViewById(R.id.date);

        mainHeaderLayout = (RelativeLayout) findViewById(R.id.main_header_layout);
        scrollView = (HeaderAnimatedScrollView) findViewById(R.id.scroll_view);
        centerLayout = (RelativeLayout) findViewById(R.id.header_center_layout);
        restTodayLabel = (TextView) findViewById(R.id.rest_today_label);
        recommendedIntakeLabel = (TextView) findViewById(R.id.recommended_intake_label);
        intakeLabel = (TextView) findViewById(R.id.intake_today_label);
        consumptionLabel = (TextView) findViewById(R.id.consumption_today_label);
        mainBgLayout = (LinearLayout) findViewById(R.id.main_bg);
        uploadPictureButton = (ImageButton) findViewById(R.id.main_camera_button);
        lastday = (ImageView) findViewById(R.id.last_day);
        nextday = (ImageView )findViewById(R.id.next_day);

        //动画
        initHeadAnimation();



        //获取今日日期
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        //更新今日日期
        refreshDate();

        //mainPresenter = new MainPresenter(this);
        loading.start();
        getTodayData();


        //标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_button);
        }


//        final UserInformationTest info = new UserInformationTest();
//        info.request(AccountID.getId(),MainActivity.this);
//
//        Handler handlerhere = new Handler(){
//            @Override
//            public void handleMessage(Message msg){
//                switch(msg.what)
//                {
//                    case GET_USERINFO_SUCCESS:
//                        Account account = info.getAccount();
//                        UserInformation.setImageDrawable(null);
//                        username.setText(account.getName());
//                        accounttext.setText(account.getId());
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        info.setHandler(handlerhere);

        //点击左上方的按钮左侧菜单栏滑出
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //点击添加跳转到添加饮食情况界面
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });

        //点击左侧界面中的用户头像跳转到用户详细信息界面
        UserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        //搜索框点击跳转到搜索界面
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //日历跳转
        //日历界面跳转
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        //分析跳转
        analysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TodayAnalyseActivity.class);
                startActivity(intent);
            }
        });

        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IdentifyFoodActivity.class);
                startActivity(intent);
            }
        });

        /*PageTest pagetest = new PageTest();
        pagetest.request();*/
        Cancellation_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean flag= SaveFile.save(getApplicationContext(), "","","no",-1);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        //点击跳转到健康快讯界面
        passageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PassageActivity.class);
                startActivity(intent);
            }
        });

        lastday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE,-1);
                date=calendar.getTime();
                refreshDate();
                //TODO : 将界面中的diet根据更新后的日期进行更新
                getTodayData();
            }
        });

        nextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                System.out.println(date.toString());
                calendar.add(Calendar.DATE,1);
                date=calendar.getTime();
                System.out.println(date.toString());
                refreshDate();
                getTodayData();
                //TODO : 将界面中的diet根据更新后的日期进行更新
            }
        });

    }


    private void refreshDate() {
        System.out.println("日期变化");

        Intent intent = getIntent();
        String datestring = intent.getStringExtra("date");
        if (datestring == null){
            System.out.println("intent中参数为空");
            dateText.setText(simpleDateFormat.format(date));
        }
        else{
            System.out.println("日期变化"+datestring);

            System.out.println(datestring);
            dateText.setText(datestring);
            try{

                date = simpleDateFormat.parse(datestring);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshDate();
        getTodayData();
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//// TODO Auto-generated method stub
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }

    @Override
    public void refresh(ArrayList<MainGroup> groupList, double recommendedIntake, double intake, double consumption) {
        //显示列表
        mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this,groupList);
        mainRecycler.setAdapter(adapter);

        //热量
        recommendedIntakeText.setText(recommendedIntake + "");
        intakeText.setText(intake + "");
        consumptionText.setText(consumption + "");
        double rest = recommendedIntake - intake + consumption;
        restText.setText(rest + "");
    }

    @Override
    public void onLogin() {

    }

    /*********************************************************************/
    /**********************搜索框*****************************************/

    private void initHeadAnimation() {
        final float maxHeight = CommonUtil.dp2px(this, 180f);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mainHeaderLayout.getLayoutParams();
        final float maxRestLabelTextSize = restTodayLabel.getTextSize();
        final float maxRestTextTextSize = restText.getTextSize();
        final float maxRecommendedLabelTextSize = recommendedIntakeLabel.getTextSize();
        final float maxRecommendedTextTextSize = recommendedIntakeText.getTextSize();
        final float maxIntakeLabelTextSize = intakeLabel.getTextSize();
        final float maxIntakeTextTextSize = intakeText.getTextSize();
        final float maxConsumptionLabelTextSize = consumptionLabel.getTextSize();
        final float maxConsumptionTextTextSize = consumptionText.getTextSize();

        scrollView.setOnScrollListener(new HeaderAnimatedScrollView.OnScrollChangeListener() {
            @Override
            public synchronized void onScrollChanged(int action, final float dy) {
                float scale = (maxHeight - dy) > 0 ? (maxHeight - dy) / maxHeight : 0;

                //背景
                ViewGroup.MarginLayoutParams mainBgLayoutParams = (ViewGroup.MarginLayoutParams) mainBgLayout.getLayoutParams();
                float newHeight = maxHeight * scale;
                mainBgLayoutParams.height = (int) newHeight;
                mainBgLayout.setLayoutParams(mainBgLayoutParams);
                mainBgLayout.setAlpha(scale);

                //中间
                ViewGroup.MarginLayoutParams centerLayoutParams = (ViewGroup.MarginLayoutParams) centerLayout.getLayoutParams();
                newHeight = maxHeight * scale;
                centerLayoutParams.width = (int) newHeight;
                centerLayoutParams.height = (int) newHeight;
                centerLayout.setLayoutParams(centerLayoutParams);
                float newSize = maxRestLabelTextSize * scale;
                restTodayLabel.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
                newSize = maxRestTextTextSize * scale;
                restText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
                newSize = maxRecommendedLabelTextSize * scale;
                recommendedIntakeLabel.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
                newSize = maxRecommendedTextTextSize * scale;
                recommendedIntakeText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);

                //左边
                newSize = maxIntakeLabelTextSize * scale;
                intakeLabel.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
                newSize = maxIntakeTextTextSize * scale;
                intakeText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);

                //右边
                newSize = maxConsumptionLabelTextSize * scale;
                consumptionLabel.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
                newSize = maxConsumptionTextTextSize * scale;
                consumptionText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, newSize);
            }
        });
    }


    //按两次back键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //获取按键并比较两次按back的时间大于2s不退出，否则退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exit_time > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exit_time = System.currentTimeMillis();
            } else {
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getTodayData() {
        loading.start();
        final AnyDayDietStringTest dataFetcher = new AnyDayDietStringTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case DATA_NULL:
                        Toast.makeText(MainActivity.this, "用户今日Diet数据为空", Toast.LENGTH_SHORT).show();
                        break;
                    case DATA_UPDATED:
                        List<Diet> diets = dataFetcher.getDiets();
                        loading.stop();
                        refreshDiets(diets);
                        break;
                    case FAILED:
                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        dataFetcher.setHandler(handler);
        int id = AccountID.getId();
        String dateStr = simpleDateFormat.format(date);
        dataFetcher.request(id, dateStr, this);
    }

    private void refreshDiets(List<Diet> diets) {
        //测试：写定每餐推荐量
        this.diets = diets;
        groupList = new ArrayList<>();
//        groupList.add(new MainGroup("早餐", 1000, new ArrayList<MainFood>())) ;
//        groupList.add(new MainGroup("午餐", 1000, new ArrayList<MainFood>()));
//        groupList.add(new MainGroup("晚餐", 1000, new ArrayList<MainFood>()));
        for (Diet diet: diets) {
            MainGroup group = new MainGroup(diet.getId(), diet.getGroup(), diet.getGroup() * 100, new ArrayList<MainFood>());
            List<DietDetail> details = diet.getDetailList();

            for (DietDetail detail: details) {
                Food food = detail.getFood();
                group.getFoodsThisMeal().add(new MainFood(detail.getFood().getId(), IP.ip + detail.getFood().getPicture_mid(), detail.getFood().getName(), detail.getQuantity(), detail.getFood().getHeat()));
            }
            groupList.add(group);
//            refreshDetails(diet.getId(), diet.getGroup());
        }
        Collections.sort(groupList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(manager);
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this, groupList);
        mainRecycler.setAdapter(adapter);
        loading.stop();
    }

//    private void refreshDetails(int dietId, final int group) {
//        final DietDetailListTest dataFetcher = new DietDetailListTest();
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case DATA_NULL:
//                        break;
//                    case DATA_UPDATED:
//                        List<DietDetail> details = dataFetcher.getDetails();
//                        for (DietDetail detail: details) {
//                            groupList.get(group).getFoodsThisMeal()
//                                    .add(new MainFood(detail.getFood().getName(),
//                                            detail.getQuantity(),
//                                            detail.getFood().getHeat()));
//                        }
//
//                        break;
//                    case FAILED:
//                        break;
//                }
//            }
//        };
//        dataFetcher.setHandler(handler);
//        dataFetcher.request(dietId, this);
//    }
}
