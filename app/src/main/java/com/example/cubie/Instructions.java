package com.example.cubie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Instructions extends AppCompatActivity {
    MediaPlayer soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
    }

    public void back(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void intro(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Intro.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void whiteface(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Whiteface.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void oll(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Oll.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void pll(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Pll.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }
}