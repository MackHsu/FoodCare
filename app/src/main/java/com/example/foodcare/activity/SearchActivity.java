package com.example.foodcare.activity;

import android.content.Context;
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
import com.example.foodcare.tools.FruitAdapter;
import com.example.foodcare.tools.StaticVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ListView lListView;
    private ImageButton backButton;

    private static Context mContent;

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
                searchSomething(query);
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

    private void searchSomething(String search){

        //TODO request 拿到数据填充到recycler中
        //searchFruits(search);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.searchrecycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


    }
    /*
     * 上面是搜索框界面
     * 下面是搜索结果显示
     * 上面由于需要用到网络功能，所以下面的数据先读取本地的
     * */
    private List<Fruit> fruitList=new ArrayList<>();

    private void searchFruits(String search){
        fruitList.clear();
        String pattern = ".*"+search+".*";
        for(int i=0;i<StaticVariable.AllFruitList.size();i++){
            if(Pattern.matches(pattern, StaticVariable.AllFruitList.get(i).getName())){
                fruitList.add(StaticVariable.AllFruitList.get(i));
            }
        }
    }

    private void initFruits(){

    }

    public static Context getContext(){
        return mContent;
    }
}
