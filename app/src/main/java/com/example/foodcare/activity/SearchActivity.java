package com.example.foodcare.activity;

import android.app.SearchableInfo;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.Search.SearchTest;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.tools.FruitAdapter;
import com.example.foodcare.tools.StaticVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ImageButton backButton;

    private List<Food> foods;
    private static Context mContent;
    private final int SEARCH_CONN_ERR = 2;
    private final int SEARCH_SUCCESS = 1;
    private final int SEARCH_FAILED = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContent=getApplicationContext();

        mSearchView = (SearchView) findViewById(R.id.searchView);
        backButton = (ImageButton) findViewById(R.id.search_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                //lListView.setTextFilterEnabled(false);
                searchFood(query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO 清除所有的搜索结果
//                if (!TextUtils.isEmpty(newText)) {
//
//
////                    lListView.setFilterText(newText);
//                } else {
////                    lListView.clearTextFilter();
//                }
                return false;
            }
        });

    }

    private void searchFood(final String searchstr){
        //清空当前搜索食物
        foods.clear();
        //TODO request 拿到数据填充到recycler中
        final SearchTest searchtest = new SearchTest();
        Handler handlerhere = new Handler(){
            @Override
            public void handleMessage(Message msg){
                //MyToast.mytoast("进入handler",IdentifyResultActivity.this);
                switch(msg.what)
                {
                    case SEARCH_SUCCESS:
                        //计算每日推荐摄入热量
                        MyToast.mytoast("搜索成功", SearchActivity.this);
                        foods = searchtest.getFoods();
                        //getFoodsFromSearch(searchtest);
                        break;
                    case SEARCH_FAILED:
                        MyToast.mytoast("搜索失败",SearchActivity.this);
                        break;
                    case SEARCH_CONN_ERR:
                        MyToast.mytoast("搜索失败，请检查网络情况",SearchActivity.this);
                        break;
                }
            }
        };
        searchtest.setHandler(handlerhere);
        searchtest.request(searchstr);


//
//        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.searchrecycler);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        FruitAdapter adapter=new FruitAdapter(fruitList);
//        recyclerView.setAdapter(adapter);


    }
    /*
     * 上面是搜索框界面
     * 下面是搜索结果显示
     * 上面由于需要用到网络功能，所以下面的数据先读取本地的
     * */
    private void getFoodsFromSearch(SearchTest searchTest){
        this.foods = searchTest.getFoods();
    }


    public static Context getContext(){
        return mContent;
    }
}
