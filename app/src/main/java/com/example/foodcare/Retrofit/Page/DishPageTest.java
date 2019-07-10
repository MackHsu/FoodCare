package com.example.foodcare.Retrofit.Page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.foodcare.Retrofit.A_entity.Food;
import com.example.foodcare.Retrofit.A_entity.FoodPage;
import com.example.foodcare.Retrofit.A_entity.Page;
import com.example.foodcare.Retrofit.Page.PageInterface;
import com.example.foodcare.ToolClass.IP;
import com.example.foodcare.ToolClass.MyToast;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.entity.AddFood;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DishPageTest {
    private List<Food> foods;
    private String type;
    private Page page;
    private Handler handler;
    private Handler selfHandler;
    private final int UPDATE_DATA = 1;
    private int UPDATE_FAILURE = 2;
    public int getPage(){return this.page.getStart();}
    public DishPageTest(String type) {
        this.type = type;
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

//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case UPDATE_DATA:
//                        page = this.
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        };

        PageInterface post = retrofit.create(PageInterface.class);
        System.out.println("建立post对象");
        System.out.println(page.getStart()+"---------------------");
        Call<FoodPage> call = post.getDishTypeCall(page,type);
        System.out.println(page.getStart()+"---------------------");
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
                    foods = response.body().getFoods();
                    page = response.body().getPage();
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
    public void setSelfHandler(Handler handler){
        this.selfHandler= handler;
    }

}
