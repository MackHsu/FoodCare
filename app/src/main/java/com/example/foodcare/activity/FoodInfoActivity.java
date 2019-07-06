package com.example.foodcare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodcare.R;
import com.example.foodcare.Retrofit.A_entity.Food;

import org.w3c.dom.Text;

public class FoodInfoActivity extends AppCompatActivity {
    private TextView foodName;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        foodName = (TextView) findViewById(R.id.food_name);
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Glide.with(this).load(R.drawable.apple).into((ImageView) findViewById(R.id.food_image));
        Intent intent = getIntent();
        foodName.setText(intent.getStringExtra("foodName"));


    }
}
