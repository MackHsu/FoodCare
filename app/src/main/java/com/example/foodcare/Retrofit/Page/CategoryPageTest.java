package com.example.foodcare.Retrofit.Page;
/********************曾志昊 2017302580214************************/
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

public class CategoryPageTest {
    private int start;
    private boolean end;
    private List<Food> foods;
    private String category = "烤";//默认搜索类别
    private Page page;
    private Handler handler;
    private int RETURN_NULL = 0;
    private int UPDATE_DATA = 1;
    private int UPDATE_FAILURE = 2;
    public int getPage(){return this.page.getStart();}
    public CategoryPageTest(){
        page = new Page();
        page.setStart(0);
        start = 0;
        end = false;
    }
    public CategoryPageTest(String category) {
        this.category = category;
        page = new Page();
        page.setStart(0);
    }

    public boolean getEnd(){
        return page.isEnd();
    }
    public List<Food> getfoods()
    {
        return foods;
    }
    public void request(final Context context) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        PageInterface post = retrofit.create(PageInterface.class);
        System.out.println("建立post对象");
//        category = "零食";
        System.out.println(page+"---------------------");
        Call<FoodPage> call = post.getMalCategoryCall(start,category);
        System.out.println(page+"---------------------");
        System.out.println("getcall");
        call.enqueue(new Callback<FoodPage>() {
            @Override
            public void onResponse(Call<FoodPage> call, Response<FoodPage> response) {
                System.out.println("请求成功");
                if(response.body()==null) {
                    System.out.println("对象为空！！！！！！！！！！！！！");
                } else {
                    Message message = new Message();
                    message.what = UPDATE_DATA;
                    handler.sendMessage(message);
                    System.out.println(page.getStart());
                    page = response.body().getPage();
                    foods = response.body().getFoods();
//                    start += response.body().getFoods().size();
                    start = response.body().getPage().getStart();
                    end = response.body().getPage().isEnd();
//                    foods = response.body().getFoods();
//                    //page = response.body().getPage();
//                    page.setStart(page.getStart()+10);
                    System.out.println(page.getStart());
                }
            }

            @Override
            public void onFailure(Call<FoodPage> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                MyToast.mytoast("请求失败！！",context);
                Message message = new Message();
                message.what = UPDATE_FAILURE;
                handler.sendMessage(message);
                t.printStackTrace();
            }
        });
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getCategory() {
        return category;
    }
}
