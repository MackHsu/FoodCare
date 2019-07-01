package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodcare.R;
import com.google.gson.Gson;

public class FruitActivity extends AppCompatActivity {

    private Fruit fruit;
    private ImageView fruit_image;
    private TextView fruit_name;
    private TextView fruit_energy_text;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        toolbar=findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        Intent intent=getIntent();
        String data=intent.getStringExtra("FRUIT");
        Gson gson =new Gson();
        fruit =gson.fromJson(data,Fruit.class);
        //fruit=new Fruit("苹果",R.drawable.apple_pic);
        fruit_image=(ImageView)findViewById(R.id.image_fruit);
        fruit_image.setImageResource(fruit.getImageId());
        fruit_name=(TextView)findViewById(R.id.name_fruit);
        fruit_energy_text=(TextView)findViewById(R.id.info_fruit);
        fruit_name.setText(fruit.getName());
        //makeToastByHandlerSendMessage("OK");
        // fruit_image.


        toolbar.setTitle("<  "+fruit.getName());
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String info="热量     "+fruit.getHeat()+"\n\n碳水化合物      "+fruit.getCHO()+"\n\n蛋白质     "+fruit.getProtein()+
                "\n\n脂肪      "+fruit.getZhifang()+ "\n\n纳        "+fruit.getNa()+"\n\n维生素A     "+fruit.getVitaminA()+
                "\n\n维生素B       "+fruit.getVitaminB()+"\n\n维生素C       "+fruit.getVitaminC();
        fruit_energy_text.setText(info);
    }


}
