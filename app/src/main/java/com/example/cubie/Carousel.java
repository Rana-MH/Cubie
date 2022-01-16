package com.example.cubie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class Carousel extends AppCompatActivity {
    private int[] mimages = new int[]{
            R.drawable.welcomeintro, R.drawable.solveslide, R.drawable.timerslide,R.drawable.signupslide

    };
//    private String[] titles = new String[]{
//            "cube", "anti", "logo","signup"

//    };
    CarouselView carousel;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFCACACA"));
         carousel = findViewById(R.id.carousel);

        carousel.setPageCount(mimages.length);
        carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                   imageView.setImageResource(mimages[position]);



            }
        });
//        carousel.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(Carousel.this , titles[position], Toast.LENGTH_SHORT).show();
//            }
//        });

    }
// if skip button on carousel clicked it will take him home
    public void skip(View view) {
        try {
            Intent i=new Intent(getBaseContext(),Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }

    }

    public void start(View view) {
        try {
            Intent i=new Intent(getBaseContext(),Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }
}