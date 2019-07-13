package com.example.foodcare.activity;
/********************曾志昊 2017302580214************************/
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.Page.SearchPageTest;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.adapter.SearchAdapter;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ImageButton backButton;
    private RotateLoading loading;
    private RecyclerView recyclerView;

    private List<Food> foods;
    private static Context mContent;
    private final int SEARCH_CONN_ERR = 2;
    private final int SEARCH_SUCCESS = 1;
    private final int SEARCH_FAILED = 0;

    public void jumpHere(){
        loading.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContent=getApplicationContext();

        SearchPageTest dataFetcher;


        mSearchView = (SearchView) findViewById(R.id.searchView);
        backButton = (ImageButton) findViewById(R.id.search_back_button);
        loading = (RotateLoading) findViewById(R.id.searchrotate);
        recyclerView = (RecyclerView) findViewById(R.id.searchrecycler);

        foods = new ArrayList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //返回键
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                finish();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                String _query=query.replaceAll(" ","");
                searchFood(_query);
                //将adapter所有的初始化都放在一个函数中，每次更新即重新创建一个adapter
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                String _query=newText.replaceAll(" ","");
                if (!TextUtils.isEmpty(_query)){
                    foods.clear();
                    searchFood(_query);
                }else{
                    foods.clear();
                }
                return false;
              /*  //清除所有的搜索结果
                foods.clear();
                return false;*/
            }
        });
        Intent intent = getIntent();
        String SearchStr = intent.getStringExtra("SearchStr");
        if(SearchStr != null){

            loading.start();
            searchFood(SearchStr);
        }

    }

    private SearchAdapter adapter;

    public void searchFood(String query)
    {
        loading.start();
        final SearchPageTest dataFetcher = new SearchPageTest(query);
        adapter = new SearchAdapter(R.layout.search_food_item,foods);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(SearchActivity.this, DishesInfoActivity.class);
//                if(foods.get(position).getGroup() == 1)
//                    intent = new Intent(SearchActivity.this,MealInfoActivity.class);
//                Gson gson=new Gson();
//                String jsonData=gson.toJson(foods.get(position).getId());
//                intent.putExtra("foodId",jsonData);
//                MyToast.mytoast("成功进入标签详情界面",SearchActivity.this);
//                startActivity(intent);
////                if(view.getId() == R.id.identifybutton) {
////                } else {
////                    MyToast.mytoast("请点击按钮进入标签详情界面！",SearchActivity.this);
////                }
//            }
//        });

        final Handler handlerhere = new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case SEARCH_SUCCESS:
                        //识别成功，停止等待旋转

                        //foodName.setText(intent.getStringExtra("foodName"));
                        for (Food food: dataFetcher.getfoods()) {
                            System.out.println(food.getName()+food.getHeat());
                            adapter.addData(food);
                            loading.stop();
                            adapter.loadMoreComplete();
                            if (dataFetcher.getEnd()) {
                                adapter.loadMoreEnd();
                            }
                            System.out.println("请求时"+dataFetcher.getStart());
                        }

                        break;
                    case SEARCH_CONN_ERR:
                        loading.stop();
                        MyToast.mytoast("请检查网络连接状态",SearchActivity.this);
                        break;
                    default:
                        adapter.loadMoreEnd();
                }
            }
        };

        dataFetcher.setHandler(handlerhere);
        dataFetcher.request(SearchActivity.this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求一次!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                dataFetcher.setHandler(handlerhere);
//                System.out.println("请求前"+dataFetcher.getStart());
                dataFetcher.request(SearchActivity.this);
//                System.out.println("请求后"+dataFetcher.getStart());
                //判断是否请求到头
            }
            }, recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                Intent intent = new Intent(SearchActivity.this, DishesInfoActivity.class);
                if(foods.get(position).getGroup() == 1)
                    intent = new Intent(SearchActivity.this,MealInfoActivity.class);
                intent.putExtra("foodId", foods.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public static Context getContext(){
        return mContent;
    }
}
