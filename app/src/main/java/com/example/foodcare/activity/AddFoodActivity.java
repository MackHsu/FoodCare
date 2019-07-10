package com.example.foodcare.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.FoodPackage.FoodList.FoodList;
import com.example.foodcare.entity.AddFood;
import com.flyco.tablayout.SlidingTabLayout;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    private Context mContext = this;
    private RotateLoading loading;
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private FoodList dbFoodData;
    private List<AddFood> foodList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"常见", "食材", "菜品"};
    private PageRecyclerAdapter mAdapter;
    private SlidingTabLayout slide;
    private final int NO_RETURN = 0;
    private final int UPDATE_SUCCEEDED = 1;
    private final int UPDATE_FAILED = 2;
    private final int REQUEST_FAILED = 3;
    private ImageButton camera;
    private SearchView searchView;

    public final int GET_DATA_SUCCEEDED = 1;
    public final int GET_DATA_FAILED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //初始化
        backButton = (ImageButton) findViewById(R.id.back_button);
//        recyclerView = (RecyclerView) findViewById(R.id.add_food_recycler);
        loading = (RotateLoading) findViewById(R.id.loading);
        camera = (ImageButton) findViewById(R.id.camera_add);
        searchView = (SearchView) findViewById(R.id.addfoodsearch_view);

//        loading.start();
        //返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });
//        //点击搜索栏进入搜索界面中
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(AddFoodActivity.this, SearchActivity.class);
                intent.putExtra("SearchStr",query);
                startActivity(intent);
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //点击相机按钮跳转进入识别
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFoodActivity.this, IdentifyFoodActivity.class);
                startActivity(intent);
            }
        });
        //分类显示方式
        for(String title: mTitles) {
            mFragments.add(AddFoodTypeFregment.getInstant(this, title));
        }

        View decorView = getWindow().getDecorView();
        ViewPager pager = (ViewPager) decorView.findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(2);
        mAdapter = new PageRecyclerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        slide = decorView.findViewById(R.id.slide);
        slide.setViewPager(pager);

        //无分类、分页请求、添加和跳转详情
//        loading.start();
//        final AddFoodAdapter2 adapter = new AddFoodAdapter2(R.layout.add_food_item, foodList);
//        final PageTest dataFetcher = new PageTest();
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case GET_DATA_SUCCEEDED:
//                        for (Food food: dataFetcher.getfoods()) {
//                            adapter.addData(new AddFood(food.getId(), IP.ip + food.getPicture_mid(), food.getName(), food.getHeat()));
//                        }
//                        loading.stop();
//                        adapter.loadMoreComplete();
//                        if(dataFetcher.getEnd()){
//                            adapter.loadMoreEnd();
//                        }
//                        break;
//                    case GET_DATA_FAILED:
//                        loading.stop();
//                        adapter.loadMoreFail();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        dataFetcher.setHandler(handler);
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
//                if(view.getId() == R.id.item_layout) {
//                    //弹窗
//                    final DialogPlus dialog = DialogPlus.newDialog(AddFoodActivity.this)
//                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
//                            .create();
//                    //下拉框
//                    final NiceSpinner spinner = (NiceSpinner) dialog.findViewById(R.id.spinner);
//                    ArrayList<String> meals = new ArrayList<>();
//                    meals.add("早餐"); meals.add("午餐"); meals.add("晚餐");
//                    spinner.attachDataSource(meals);
//                    //文本和图像
//                    TextView nameTextDialog = (TextView) dialog.findViewById(R.id.food_name);
//                    TextView energyTextDialog = (TextView) dialog.findViewById(R.id.food_energy);
//                    ImageView foodImageDialog = (ImageView) dialog.findViewById(R.id.image);
//                    nameTextDialog.setText(((TextView) view.findViewById(R.id.food_name_text)).getText());
//                    energyTextDialog.setText(((TextView) view.findViewById(R.id.food_energy_text)).getText());
//                    foodImageDialog.setImageDrawable(((ImageView) view.findViewById(R.id.food_image)).getDrawable());
//
//                    dialog.show();
//
//                    //取消
//                    Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
//                    cancelButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    //确定
//                    final EditText editText = (EditText) dialog.findViewById(R.id.weight_edit_text);
//                    Button addButton = (Button) dialog.findViewById(R.id.add);
//                    addButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            DietDetailAddTest dataManager = new DietDetailAddTest();
//                            Handler handler = new Handler() {
//                                @Override
//                                public void handleMessage(Message msg) {
//                                    switch (msg.what) {
//                                        case NO_RETURN:
//                                            Toast.makeText(AddFoodActivity.this, "没有返回值", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case UPDATE_SUCCEEDED:
//                                            Toast.makeText(AddFoodActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                                            dialog.dismiss();
//                                            break;
//                                        case UPDATE_FAILED:
//                                            Toast.makeText(AddFoodActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case REQUEST_FAILED:
//                                            Toast.makeText(AddFoodActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        default:
//                                            break;
//                                    }
//                                }
//                            };
//                            dataManager.setHandler(handler);
//                            dataManager.request(foodList.get(position).getFoodId(),
//                                    Integer.parseInt(editText.getText().toString()),
//                                    AccountID.getId(),
//                                    spinner.getSelectedIndex(),
//                                    AddFoodActivity.this);
//                        }
//                    });
//                } else {
//                    Intent intent = new Intent(AddFoodActivity.this, MoreInfoActivity.class);
//                    intent.putExtra("foodId", foodList.get(position).getFoodId());
//                    startActivity(intent);
//                }
//            }
//        });
//        dataFetcher.request(AddFoodActivity.this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                System.out.println("请求一次");
//                dataFetcher.setHandler(handler);
//                dataFetcher.request(AddFoodActivity.this);
//                //判断是否请求到头
//            }
//        }, recyclerView);



    }

    //前端测试用
//    private void initFoods() {
//        foodList.add(new AddFood(0, "", "米饭", 150));
//        foodList.add(new AddFood(1, "", "鸡腿", 400));
//        foodList.add(new AddFood(2, "", "豆浆", 50));
//        foodList.add(new AddFood(3, "", "煮鸡蛋", 100));
//        for (int i = 0; i < 30; i++) {
//            foodList.add(new AddFood(i, "", i + "", i));
//        }
//    }

    private class PageRecyclerAdapter extends FragmentPagerAdapter {
        public PageRecyclerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }
    }
}

