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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.FoodPackage.FoodList.FoodList;
import com.example.foodcare.entity.AddFood;
import com.flyco.tablayout.SlidingTabLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
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
        //不弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //初始化
        backButton = (ImageButton) findViewById(R.id.back_button);
        loading = (RotateLoading) findViewById(R.id.loading);
        camera = (ImageButton) findViewById(R.id.camera_add);
        searchView = (SearchView) findViewById(R.id.addfoodsearch_view);

        //返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });
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
                final DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_items_take_photo))
                        .create();

                Button buttonTake=(Button)dialog.findViewById(R.id.take_photo_item_dialog_main);
                Button buttonSelect=(Button)dialog.findViewById(R.id.select_photo_item_dialog_main);
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

                dialog.show();
                /*Intent intent = new Intent(AddFoodActivity.this, IdentifyFoodActivity.class);
                startActivity(intent);*/
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
    }

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

