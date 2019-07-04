//许朗铭 2017302580224
//使用github上的GroupedRecyclerAdapter来实现分组列表  https://github.com/donkingliang/GroupedRecyclerViewAdapter
//使用github上的CircleTextImageView来做圆形头像框 https://github.com/CoolThink/CircleTextImageView
package com.example.foodcare.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Px;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.foodcare.R;
import com.example.foodcare.adapter.MainRecyclerAdapter;
import com.example.foodcare.model.MainGroup;
import com.example.foodcare.presenter.MainPresenter;
import com.example.foodcare.util.CommonUtil;
import com.example.foodcare.view.HeaderAnimatedScrollView;
import com.example.foodcare.view.IMainView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainView {

    ImageButton menuButton;
    DrawerLayout mainDrawerLayout;
//    Button addFoodButton;
//    Button analysisButton;
    Button calendarButton;
    ImageButton cameraButton;
    CircleTextImageView UserInformation;
    RecyclerView mainRecycler;
//    SearchView searchView;
    FloatingActionButton addButton;
    FloatingActionButton analysisButton;
    FloatingActionButton searchButton;
    TextView recommendedIntakeText;
    TextView intakeText;
    TextView consumptionText;
    TextView restText;
    Toolbar toolbar;
    private Uri imageUri;

    RelativeLayout mainHeaderLayout;
    HeaderAnimatedScrollView scrollView;
    RelativeLayout centerLayout;
    TextView restTodayLabel;
    TextView recommendedIntakeLabel;
    TextView intakeLabel;
    TextView consumptionLabel;
    LinearLayout mainBgLayout;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        menuButton = (ImageButton) findViewById(R.id.main_menu_button);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        addFoodButton = (Button) findViewById(R.id.mian_add_button);
//        analysisButton = (Button) findViewById(R.id.main_analysis_button);
        calendarButton = (Button) findViewById(R.id.calendar) ;
        UserInformation = (CircleTextImageView) findViewById(R.id.avatar);
        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        cameraButton = (ImageButton)findViewById(R.id.main_camera_button);
//        searchView = (SearchView)findViewById(R.id.mainsearchView);
        addButton = (FloatingActionButton) findViewById(R.id.floating_button_add);
        analysisButton = (FloatingActionButton) findViewById(R.id.floating_button_analysis);
        searchButton = (FloatingActionButton) findViewById(R.id.floating_button_search);
        recommendedIntakeText = (TextView) findViewById(R.id.recommended_intake);
        intakeText = (TextView) findViewById(R.id.intake_today);
        consumptionText = (TextView) findViewById(R.id.consumption_today);
        restText = (TextView) findViewById(R.id.rest_today_text);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mainHeaderLayout = (RelativeLayout) findViewById(R.id.main_header_layout);
        scrollView = (HeaderAnimatedScrollView) findViewById(R.id.scroll_view);
        centerLayout = (RelativeLayout) findViewById(R.id.header_center_layout);
        restTodayLabel = (TextView) findViewById(R.id.rest_today_label);
        recommendedIntakeLabel = (TextView) findViewById(R.id.recommended_intake_label);
        intakeLabel = (TextView) findViewById(R.id.intake_today_label);
        consumptionLabel = (TextView) findViewById(R.id.consumption_today_label);
        mainBgLayout = (LinearLayout) findViewById(R.id.main_bg);

        initHeadAnimation();

        mainPresenter = new MainPresenter(this);
        mainPresenter.refreshTodayMealListAndEnergy();

        //标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_button);
        }

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

        //点击相机图片进入照相界面
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadPictureActivity.class);
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
    }

//    //向标题栏添加Camera按钮
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar, menu);
//        return true;
//    }
//
//    //标题栏的点击事件
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.camera:
//                Intent intent = new Intent(MainActivity.this, UploadPictureActivity.class);
//                startActivity(intent);
//                break;
//            case android.R.id.home:
//                mainDrawerLayout.openDrawer(Gravity.START);
//                break;
//            default:
//        }
//        return true;
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
}
