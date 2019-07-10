package com.example.foodcare.Retrofit.Page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodPage;
import com.example.foodcare.Retrofit.A_entity.Page;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPageTest {
    private int start;
    private boolean end;
    private List<Food> foods;
    private String searchstr;
    private Page page;
    private Handler handler;
    private final int SEARCH_CONN_ERR = 2;
    private final int SEARCH_SUCCESS = 1;
    private final int SEARCH_FAILED = 0;

    public SearchPageTest(String searchstr) {
        this.searchstr = searchstr;
        page = new Page();
        start = 0;
        end = false;
        page.setStart(0);
    }

    public int getStart(){

        return page.getStart();

    }
    public boolean getEnd(){
        return page.isEnd();
    }
    public List<Food> getfoods()
    {
        return foods;
    }
    public void request(final Context context) {
        if(searchstr==null)
        {
            MyToast.mytoast("搜索项为空！！",context);
            return;
        }
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");
        PageInterface post = retrofit.create(PageInterface.class);
        System.out.println("建立post对象");//TODO 删除这行
        Call<FoodPage> call = post.getSearchCall(searchstr,start);
        System.out.println("getcall");
        call.enqueue(new Callback<FoodPage>() {
            @Override
            public void onResponse(Call<FoodPage> call, Response<FoodPage> response) {
                System.out.println("请求成功");
                if(response.body()==null) {
                    System.out.println("对象为空！！！！！！！！！！！！！");
                    MyToast.mytoast("对象为空",context);

                } else {
                    Message message = new Message();
                    message.what = SEARCH_SUCCESS;
                    handler.sendMessage(message);
                    System.out.println(page.getStart());
                    page = response.body().getPage();
                    foods = response.body().getFoods();
//                    start += response.body().getFoods().size();
                    start = response.body().getPage().getStart();
                    end = response.body().getPage().isEnd();
//                    start += response.body().getFoods().size();
//                    start = response.body().getPage().getStart();
//                    end = response.body().getPage().isEnd();
                   // page = response.body().getPage();
                    if(foods.size()==0)
                    {
                        MyToast.mytoast("搜索结果为零",context);
                    }
//                    page.setStart(page.getStart()+foods.size());
//                    page.setEnd(response.body().getPage().isEnd());
//                    page.setStart(page.getStart()+10);
                    System.out.println(page.getStart());
                }
            }

            @Override
            public void onFailure(Call<FoodPage> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                MyToast.mytoast("检查网络问题！！",context);
                Message message = new Message();
                message.what = SEARCH_CONN_ERR;
                handler.sendMessage(message);
                t.printStackTrace();
            }
        });
    }

    public boolean isEnd() {
        return end;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getSearchstr() {
        return searchstr;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
