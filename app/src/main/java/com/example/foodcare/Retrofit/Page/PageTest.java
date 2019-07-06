package com.example.foodcare.Retrofit.Page;

import com.example.foodcare.Retrofit.A_entity.FoodPage;
import com.example.foodcare.Retrofit.A_entity.Page;
import com.example.foodcare.ToolClass.NullOnEmptyConverterFactory;
import com.example.foodcare.ToolClass.IP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PageTest {
    private Page page;
    int times = 2;
    public PageTest() {
        page = new Page();
        page.setStart(0);
    }

    public void request() {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IP.ip)//http://fanyi.youdao.com/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("建立retrofit对象");

        PageInterface post = retrofit.create(PageInterface.class);
        System.out.println("建立post对象");
        Call<FoodPage> call = post.getCall(page);
        System.out.println("getcall");
        call.enqueue(new Callback<FoodPage>() {
            @Override
            public void onResponse(Call<FoodPage> call, Response<FoodPage> response) {
                System.out.println("请求成功");//此处对象为空？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？/
                if(response.body()==null)
                {
                    System.out.println("对象为空！！！！！！！！！！！！！");
                }
                else
                {
                    System.out.println(page.getStart());
                    for(int i =0;i<response.body().getFoods().size();i++)
                    {
                        System.out.println(response.body().getFoods().get(i).getName());
                    }
                    if(times>1){
                        times--;
                        page = response.body().getPage();
                        System.out.println(page.getStart());
                        request();
                    }

                }

            }

            @Override
            public void onFailure(Call<FoodPage> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
    }
}
