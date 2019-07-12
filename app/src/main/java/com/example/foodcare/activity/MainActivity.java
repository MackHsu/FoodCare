//许朗铭 2017302580224
//使用github上的GroupedRecyclerAdapter来实现分组列表  https://github.com/donkingliang/GroupedRecyclerViewAdapter
//使用github上的CircleTextImageView来做圆形头像框 https://github.com/CoolThink/CircleTextImageView
package com.example.foodcare.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Account;
import com.example.foodcare.Retrofit.A_entity.Play;
import com.example.foodcare.Retrofit.DietPackage.Diet.AnyDayDiet.AnyDayDietStringTest;
import com.example.foodcare.Retrofit.DietPackage.Diet.DietDetailDelete.DietDetailDeleteTest;
import com.example.foodcare.Retrofit.SportPackage.DeletePlayTest;
import com.example.foodcare.Retrofit.SportPackage.GetPlayByDateTest;
import com.example.foodcare.Retrofit.SportPackage.UpdatePlayTest;
import com.example.foodcare.Retrofit.User.UserInformation.UserInformationTest;
import com.example.foodcare.ToolClass.CalendarDialog;
import com.example.foodcare.ToolClass.Day;
import com.example.foodcare.ToolClass.HeatAlgrithom;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.Retrofit.A_entity.Diet;
import com.example.foodcare.Retrofit.A_entity.DietDetail;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.adapter.MainRecyclerAdapter;
import com.example.foodcare.adapter.MainSportAdapter;
import com.example.foodcare.entity.AccountID;
import com.example.foodcare.model.MainFood;
import com.example.foodcare.model.MainGroup;
import com.example.foodcare.ToolClass.SaveFile;
import com.example.foodcare.ToolClass.CommonUtil;
import com.example.foodcare.view.HeaderAnimatedScrollView;
import com.example.foodcare.view.IMainView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.thinkcool.circletextimageview.CircleTextImageView;
import com.victor.loading.rotate.RotateLoading;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity implements IMainView {

    private final int ACCOUNT_GET_SUCCESS=8;
    private final int ACCOUNT_GET_FAILE=9;

    private final int RETURN_ANALYSE=20;

    private final int DATE_PICKED = 1;

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
    CircleTextImageView avatar;
    TextView username;
    TextView accounttext;
    FloatingActionButton sportButton;

    LinearLayout sportDivider;
    RecyclerView sportRecycler;

    private final int GET_USERINFO_SUCCESS = 1;

    ArrayList<MainGroup> groupList;
    List<Diet> diets;
    int consumptionToday;
    int intakeToday;
    int recommendedToday;

    private final int DATA_NULL = 0;
    private final int DATA_UPDATED = 1;
    private final int FAILED = 2;
    SimpleDateFormat simpleDateFormat;

    public final int GET_PLAYS_NULL = 0;
    public final int GET_PLAYS_SUCCESS = 1;
    public final int REQUEST_FALSE = 2;

    private final int NO_RETURN = 0;
    private final int UPDATE_PLAY_SUCCESS = 1;
    private final int UPDATE_PLAY_FAILED = 2;
    private final int CONN_ERR = 3;

    private final int DELETE_PLAY_SUCCESS = 1;
    private final int DELETE_PLAY_FAILED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        menuButton = (ImageButton) findViewById(R.id.main_menu_button);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        calendarButton = (Button) findViewById(R.id.calendar) ;
        Cancellation_main=(Button)findViewById(R.id.Cancellation_main);
        UserInformation = (CircleTextImageView) findViewById(R.id.avatar);         //头像
        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        addButton = (FloatingActionButton) findViewById(R.id.floating_button_add);
        analysisButton = (FloatingActionButton) findViewById(R.id.floating_button_analysis);
        searchButton = (FloatingActionButton) findViewById(R.id.floating_button_search);
        recommendedIntakeText = (TextView) findViewById(R.id.recommended_intake);
        intakeText = (TextView) findViewById(R.id.intake_today);
        consumptionText = (TextView) findViewById(R.id.consumption_today);
        restText = (TextView) findViewById(R.id.rest_today_text);
        loading = (RotateLoading) findViewById(R.id.loading);
        username = (TextView) findViewById(R.id.username);                                //用户名
        accounttext = (TextView) findViewById(R.id.accounttext);                          //用户的那个id，自己设置得那个
        sportButton = (FloatingActionButton) findViewById(R.id.floating_button_workout);//添加运动悬浮按钮
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

        sportDivider = (LinearLayout) findViewById(R.id.sport_divider);
        sportRecycler = (RecyclerView) findViewById(R.id.main_sport_recycler);
        sportDivider.setVisibility(View.GONE);

        //动画
        initHeadAnimation();



        //获取今日日期
        Day.setDate(new Date());
//        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        date = new Date();
        //更新今日日期
        refreshDate();

        //mainPresenter = new MainPresenter(this);
        loading.start();
//        在OnResume获取今日数据
//        getTodayDietData();

//
//        //标题栏
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.menu_button);
//        }


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

        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,AddSportActivity.class);
                startActivity(intent);
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
                intent.putExtra("CT",consumptionToday);//消耗量
                startActivityForResult(intent,RETURN_ANALYSE);

            }
        });

        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_items_take_photo))
                        .create();

                Button buttonTake=(Button)dialog.findViewById(R.id.take_photo_item_dialog_main);
                Button buttonSelect=(Button)dialog.findViewById(R.id.select_photo_item_dialog_main);
                Button buttonMultiple=(Button)dialog.findViewById(R.id.multiple_item_dialog_main);
                buttonTake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),UplaodPictureActivity.class);
                        intent.putExtra("WAY","TAKE_PHOTO");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                buttonSelect.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent=new Intent(getApplicationContext(),UplaodPictureActivity.class);
                        intent.putExtra("WAY","SELECT_PICTURE");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                buttonMultiple.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent=new Intent(getApplicationContext(),IdentifyFoodActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.show();
              /*  Intent intent = new Intent(MainActivity.this, IdentifyFoodActivity.class);
                startActivity(intent);
                最后如果出什么问题就用这个
                */
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

        lastday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day.lastDay();
                refreshDate();
                getTodayDietData();
            }
        });

        nextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day.nextDay();
                refreshDate();
                getTodayDietData();
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDialog calendarDialog = new CalendarDialog();
                //用于更新挑选的日期
                Handler handlerhere = new Handler(){
                    public void handleMessage(Message msg) {
                        System.out.println("进入handler");
                        switch(msg.what) {
                            case DATE_PICKED:
                                refreshDate();
                                getTodayDietData();
                                break;
                            default:
                                break;
                        }
                    }
                };
                calendarDialog.setHandler(handlerhere);
                calendarDialog.popCalendarDialog(MainActivity.this);
            }
        });

    }


    private void refreshDate() {
        System.out.println("日期变化");
        dateText.setText(Day.getDateString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainDrawerLayout.closeDrawers();
        refreshDate();
//        getTodayDietData();
        initInfo();
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
        int rest = recommendedToday - intakeToday + consumptionToday;
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
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {

            //获取按键并比较两次按back的时间大于2s不退出，否则退出
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - exit_time > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    exit_time = System.currentTimeMillis();
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);    //这个是用来标识从这个界面退出的两种的方式的
                    finish();
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
    }

    //获取Diet数据
    public void getTodayDietData() {
        loading.start();
        final AnyDayDietStringTest dataFetcher = new AnyDayDietStringTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case DATA_NULL:
                        Toast.makeText(MainActivity.this, "用户今日Diet数据为空", Toast.LENGTH_SHORT).show();
                        intakeToday = 0;
                        intakeText.setText(intakeToday + "");
                        loading.stop();
                        getTodaySportData();
                        break;
                    case DATA_UPDATED:
                        List<Diet> diets = dataFetcher.getDiets();
                        getTodaySportData();
                        loading.stop();
//                        intakeText.setText(refreshDiets(diets) + "");
                        intakeText.setText(refreshDiets(diets) + "");
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
//        String dateStr = simpleDateFormat.format(Day.getDate());
        System.out.println("当前前往请求的日期是"+Day.getDateString());
        dataFetcher.request(id, Day.getDateString(), this);
    }

    //获取今日运动
    private void getTodaySportData() {
        loading.start();
        final GetPlayByDateTest dataFetcher = new GetPlayByDateTest();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_PLAYS_NULL:
                        sportDivider.setVisibility(View.GONE);
                        consumptionToday = 0;
                        consumptionText.setText(0 + "");
                        loading.stop();
                        break;
                    case GET_PLAYS_SUCCESS:
                        sportDivider.setVisibility(View.VISIBLE);
                        List plays = dataFetcher.getPlays();
                        consumptionText.setText(refreshSports(dataFetcher.getPlays()) + "");
                        restText.setText(recommendedToday - intakeToday + consumptionToday + "");
                        loading.stop();
                        break;
                    case REQUEST_FALSE:
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

    //将获取的play数据填入表格
    private int refreshSports(List<Play> playsData) {
        List<Play> plays = new ArrayList<>();
        consumptionToday = 0;
        if (playsData.size() != 0) {
            for (Play play: playsData) {
                consumptionToday += play.getTime() * play.getSport().getConsume() / 60d;
                plays.add(play);
            }
        }
        else {
            sportDivider.setVisibility(View.GONE);
        }
        MainSportAdapter adapter = new MainSportAdapter(R.layout.main_play_item_layout, plays);
        sportRecycler.setAdapter(adapter);
        initSportsListener(adapter, plays);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        sportRecycler.setLayoutManager(manager);
        return consumptionToday;
    }

    private void initSportsListener(final MainSportAdapter adapter, final List<Play> plays) {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                //修改弹窗
                final DialogPlus dialog = DialogPlus.newDialog(MainActivity.this)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.add_sport_button_sheet))
                        .create();
                //文本和图像
                TextView nameTextDialog = (TextView) dialog.findViewById(R.id.sport_sheet_name);
                TextView energyTextDialog = (TextView) dialog.findViewById(R.id.sport_sheet_energy);
                ImageView sportImageDialog = (ImageView) dialog.findViewById(R.id.sport_sheet_image);
                nameTextDialog.setText(((TextView) view.findViewById(R.id.sport_name_text)).getText());
                energyTextDialog.setText(((TextView) view.findViewById(R.id.total_energy_text)).getText());
                sportImageDialog.setImageDrawable(((ImageView) view.findViewById(R.id.sport_image)).getDrawable());

                dialog.show();

                //取消
                Button cancelButton = (Button) dialog.findViewById(R.id.sport_sheet_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //确定
                Button modifyButton = (Button) dialog.findViewById(R.id.sport_sheet_modify);
                modifyButton.setText("修改");
                modifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdatePlayTest dataManager = new UpdatePlayTest();
                        Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case NO_RETURN:
                                        Toast.makeText(MainActivity.this, "没有返回值", Toast.LENGTH_SHORT).show();
                                        break;
                                    case UPDATE_PLAY_SUCCESS:
                                        Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        MainActivity.this.getTodaySportData();  //修改同时更新首页统计数据
                                        dialog.dismiss();
                                        break;
                                    case UPDATE_PLAY_FAILED:
                                        Toast.makeText(MainActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        break;
                                    case CONN_ERR:
                                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                        dataManager.setHandler(handler);
                        dataManager.request(plays.get(position), MainActivity.this);
                    }
                });
            }
        });

        adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, final int position) {
                //长按删除
                        Vibrator vibrator=(Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[]{100, 200, 100, 200},-1);
                        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("将要删除" + ((TextView)view.findViewById(R.id.sport_name_text)).getText().toString() + "，是否确定？");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeletePlayTest dataManager = new DeletePlayTest();
                                Handler handler = new Handler() {
                                    @Override
                                    public void handleMessage(Message msg) {
                                        switch (msg.what) {
                                            case NO_RETURN:
                                                break;
                                            case DELETE_PLAY_SUCCESS:
                                                (MainActivity.this).getTodaySportData();  //删除同时更新首页统计数据
                                                break;
                                            case DELETE_PLAY_FAILED:
                                                break;
                                            case CONN_ERR:
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                };
                                dataManager.setHandler(handler);
                                dataManager.request(AccountID.getId(),
                                        plays.get(position).getSport().getId(),
                                        Day.getDateString(),
                                        MainActivity.this);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        return true;
                    }
        });
    }

    //将获取的Diet数据填入表格
    private int refreshDiets(List<Diet> diets) {
        //测试：写定每餐推荐量
        //TODO: 推荐量
        intakeToday = 0;
        this.diets = diets;
        groupList = new ArrayList<>();
        for (Diet diet: diets) {
            if(diet.getDetailList() == null) break;
            Double rate;
            if (diet.getGroup() == 0 || diet.getGroup() == 2) rate = 0.3d;
            else rate = 0.4d;
            int num = ((Double)(recommendedToday * rate)).intValue(); //保留两位小数
            MainGroup group = new MainGroup(diet.getId(), diet.getGroup(), num, new ArrayList<MainFood>());
            List<DietDetail> details = diet.getDetailList();

            for (DietDetail detail: details) {
                group.getFoodsThisMeal().add(new MainFood(detail.getFood().getId(), IP.ip + detail.getFood().getPicture_mid(), detail.getFood().getName(), detail.getQuantity(), detail.getFood().getHeat()));
            }
            groupList.add(group);
            intakeToday += group.getTotalEnergyThisMeal();
//            refreshDetails(diet.getId(), diet.getGroup());
        }
        Collections.sort(groupList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(manager);
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this, groupList);
        mainRecycler.setAdapter(adapter);
        loading.stop();
        return intakeToday;
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

    //获取用户信息
    private void initInfo(){
        final UserInformationTest userInformationTest=new UserInformationTest();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ACCOUNT_GET_SUCCESS:
                        Account account = userInformationTest.account;
                        if(account.getName()==null){
                            username.setText("昵称");
                        }
                        else{
                            username.setText(account.getName());
                        }
                        accounttext.setText(account.getUser());

                        try{
                            String url=IP.ip+account.getPicture();
                            Glide.with(getApplicationContext()).load(url).into(UserInformation);
                        }catch (Exception e){
                            Log.i("TAG","没有请求到图片");
                            e.printStackTrace();
                        }

                        //判断推荐热量需要的数据是否已经填写
                        if (account.getAge() == 0 || account.getHeight() == null || account.getWeight() == null) {
                            showNormalDialog();
                            recommendedToday = 0;
                            getTodayDietData();
                        }
                        else {
                            recommendedToday = HeatAlgrithom.TotalHeat(account.getSex(), account.getAge(), account.getWeight(), account.getHeight().intValue(), account.getLevel(), account.getPlan()).intValue();
                            recommendedIntakeText.setText(recommendedToday + "");
                            getTodayDietData();
                        }
                        break;
                }
            }
        };
        userInformationTest.setHandler(handler);
        userInformationTest.request(AccountID.getId(),getApplicationContext());
    }


    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.drawable.warn_info);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("请先完善个人信息");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyToast.mytoast("请尽快完善个人信息",getApplicationContext());
                        recommendedIntakeText.setText("请先完善信息");
                    }
                });
        // 显示
        normalDialog.show();
    }
}
