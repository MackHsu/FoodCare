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

    private String[] mStrs = {"苹果", "米饭", "香蕉", "香菜"};

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
        lListView = (ListView) findViewById(R.id.listView);
        lListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
        lListView.setTextFilterEnabled(true);
        backButton = (ImageButton) findViewById(R.id.back_button);

        initFruits();

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
                if (!TextUtils.isEmpty(newText)) {
                    lListView.setFilterText(newText);
                } else {
                    lListView.clearTextFilter();
                }
                return false;
            }
        });

        /*
         * 上面是搜索用到的参数
         * 下面是搜索结果
         * */

    }

    private void searchSomething(String search){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", search);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        searchFruits(search);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        //RecyclerView.Adapter.notifyDataSetChanged();
       /* DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
        diffResult.dispatchUpdatesTo(mAdapter);
        fruitList.clear();
        String pattern = ".*"+search+".*";
        for(int i=0;i<StaticVariable.AllFruitList.size();i++){
            if(Pattern.matches(pattern, StaticVariable.AllFruitList.get(i).getName())){
                fruitList.add(StaticVariable.AllFruitList.get(i));
            }
        }*/

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
        for(int i=0;i<3;i++){
            Fruit apple=new Fruit("Apple",R.drawable.apple_pic);
            StaticVariable.AllFruitList.add(apple);
            //fruitList.add(apple);
            Fruit banana=new Fruit("Banana",R.drawable.banana_pic);
            StaticVariable.AllFruitList.add(banana);
            //fruitList.add(banana);
            Fruit orange=new Fruit("Orange",R.drawable.orange_pic);
            StaticVariable.AllFruitList.add(orange);
            //fruitList.add(orange);
            Fruit watermelon=new Fruit("Watermelon",R.drawable.watermelon_pic);
            StaticVariable.AllFruitList.add(watermelon);
            //fruitList.add(watermelon);
            Fruit pear=new Fruit("Pear",R.drawable.pear_pic);
            StaticVariable.AllFruitList.add(pear);
            //fruitList.add(pear);
            Fruit grape=new Fruit("Grape",R.drawable.grape_pic);
            StaticVariable.AllFruitList.add(grape);
            //fruitList.add(grape);
            Fruit pineapple=new Fruit("Pineapple",R.drawable.pineapple_pic);
            StaticVariable.AllFruitList.add(pineapple);
            //fruitList.add(pineapple);
            Fruit strawberry=new Fruit("Strawberry",R.drawable.strawberry_pic);
            StaticVariable.AllFruitList.add(strawberry);
            //fruitList.add(strawberry);
            Fruit cherry=new Fruit("Cherry",R.drawable.cherry_pic);
            StaticVariable.AllFruitList.add(cherry);
            //fruitList.add(cherry);
            Fruit mango=new Fruit("Mango",R.drawable.mango_pic);
            StaticVariable.AllFruitList.add(mango);
            //fruitList.add(mango);


        }
    }

    public static Context getContext(){
        return mContent;
    }
}
