//许朗铭 2017302580224
//使用github上的GroupedRecyclerAdapter来实现分组列表  https://github.com/donkingliang/GroupedRecyclerViewAdapter
//使用github上的CircleTextImageView来做圆形头像框 https://github.com/CoolThink/CircleTextImageView
package com.example.foodcare.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.foodcare.R;
import com.example.foodcare.UploadPicture.UploadPicture;
import com.example.foodcare.adapter.MainRecyclerAdapter;
import com.example.foodcare.entity.MainFood;
import com.example.foodcare.entity.MainGroup;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton menuButton;
    DrawerLayout mainDrawerLayout;
    ArrayList<MainGroup> groupList;
    Button addFoodButton;
    Button analysisButton;
    Button calendarButton;
    ImageButton cameraButton;
    CircleTextImageView UserInformation;
    RecyclerView mainRecycler;
    SearchView searchView;
private  Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 以ArrayList<MainGroup>的方式装好数据，替换掉initGroupList()
        initGroupList();

        //初始化
        menuButton = (ImageButton) findViewById(R.id.main_menu_button);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addFoodButton = (Button) findViewById(R.id.mian_add_button);
        analysisButton = (Button) findViewById(R.id.main_analysis_button);
        calendarButton = (Button) findViewById(R.id.calendar) ;
        UserInformation = (CircleTextImageView) findViewById(R.id.avatar);
        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        cameraButton = (ImageButton)findViewById(R.id.main_camera_button);
        searchView = (SearchView)findViewById(R.id.mainsearchView);


        //点击左上方的按钮左侧菜单栏滑出
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //点击添加跳转到添加饮食情况界面
        addFoodButton.setOnClickListener(new View.OnClickListener() {
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
        searchView.setOnClickListener(new View.OnClickListener() {
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


        //显示列表
        mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this,groupList);
        mainRecycler.setAdapter(adapter);

        //日历跳转
        //日历界面跳转
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        //添加食物跳转
        analysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TodayAnalyseActivity.class);
                startActivity(intent);
            }
        });
    }


    //前端测试用
    public void initGroupList() {
        groupList = new ArrayList<>();
        ArrayList<MainFood> foodList = new ArrayList<>();

        //早餐
        MainFood mainFood = new MainFood("煮鸡蛋", 50, 100);
        foodList.add(mainFood);
        mainFood = new MainFood("豆浆", 200, 50);
        foodList.add(mainFood);
        MainGroup mainGroup = new MainGroup("早餐", 155, foodList);
        groupList.add(mainGroup);

        //午餐
        foodList = new ArrayList<>();
        mainFood = new MainFood("米饭", 200, 150);
        foodList.add(mainFood);
        mainFood = new MainFood("鸡腿", 50, 400);
        foodList.add(mainFood);
        mainGroup = new MainGroup("午餐", 450, foodList);
        groupList.add(mainGroup);
    }
    /*********************************************************************/
    /**********************搜索框*****************************************/

}
