package com.example.cubie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Intro extends AppCompatActivity {
    TextView text1 ;
    MediaPlayer soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
//        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                FirebaseAuth.getInstance().signOut();
//                try {
//                    Intent i = new Intent(getBaseContext(), Login.class);
//                    startActivity(i);
//                    finish();
//                } catch (Exception e) {
//                }
//                return true;
//            }
//
//        });

       getSupportFragmentManager().beginTransaction()
               .replace(R.id.fragcon, new IntroFragment())
               .commit();
       BottomNavigationView topnav = (BottomNavigationView) findViewById(R.id.top_navigation);
        topnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  Fragment selectedFragment=null;
                switch (item.getItemId()){
                    case R.id.page_1:
                        soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                        soundEffect.start();
                        selectedFragment=new IntroFragment();
                        break;
                    case R.id.page_2:
                        soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                        soundEffect.start();
                        selectedFragment=new WhitefaceFragment();

                        break;

                    case R.id.page_3:
                        soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                        soundEffect.start();
                        selectedFragment=new OllFragment();

                        break;

                    case R.id.page_4:
                        soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                        soundEffect.start();
                        selectedFragment=new PllFragment();

                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragcon , selectedFragment).commit();
                return true;
            }
        });
       BottomNavigationView bottomnav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
       bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.page_1:{
                       soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                       soundEffect.start();
                       Intent intent1 = new Intent(Intro.this, Home.class);
                       startActivity(intent1);
                       break;}
                   case R.id.page_2:
                       soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                       soundEffect.start();
                       Intent intent2 = new Intent(Intro.this, Intro.class);
                       startActivity(intent2);
                       break;

                   case R.id.page_3:
                       soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                       soundEffect.start();
                       if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                           try {
                               Intent i = new Intent(getBaseContext(), Login.class);
                               startActivity(i);
                               finish();
                           } catch (Exception e) {
                           }}
                           else{
                       Intent intent3 = new Intent(Intro.this, Profile.class);
                       startActivity(intent3);
                       break;}

                    case R.id.page_4:
                    {  soundEffect = MediaPlayer.create(Intro.this,R.raw.colorscound);
                        soundEffect.start();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(Intro.this, Login.class);
                        startActivity(intent4); break;}


               }


               return false;
           }
       });
    }


    public void back(View view) {
        try {

            Intent i=new Intent(getBaseContext(), Instructions.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

}