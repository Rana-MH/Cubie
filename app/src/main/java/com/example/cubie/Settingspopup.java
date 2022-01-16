package com.example.cubie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;

public class Settingspopup extends AppCompatActivity {
MediaPlayer soundEffect;
    ScrollView scrollView;
    LinearLayout lin;
//   public  Solve solve=new Solve();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingspopup);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFCACACA"));
//        scrollView.findViewById(R.id.scrollView);
//        lin.findViewById(R.id.lin);

    }

    public void back(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Solve.class);
            startActivity(i);
        } catch (Exception e) { }
    }

//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            getWindow().getDecorView().setBackgroundColor(Color.BLACK);
//
//        } else {
//            //do something when unchecked
//        }
//    }
//
//    public void onscroll()
//    {
//
//    }

    }
