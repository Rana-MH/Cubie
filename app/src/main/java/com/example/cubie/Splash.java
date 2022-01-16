package com.example.cubie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
public class Splash extends AppCompatActivity {
    boolean carFirstStart;
    SharedPreferences prefs2 , prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
        /* Create Thread that will sleep for 5 seconds*/
         prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
         carFirstStart = prefs2.getBoolean("carFirstStart", true);
        if (carFirstStart) {
            Thread background = new Thread() {
                public void run() {
                    try {
                        // Thread will sleep for 5 seconds
                        sleep(4 * 1000);

                        // After 5 seconds redirect to another intent
//                         prefs = getSharedPreferences("prefs2",MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs2.edit();
                        editor.putBoolean("carFirstStart",false);
                        editor.apply();

                        Intent i = new Intent(getBaseContext(), Carousel.class);
                        startActivity(i);

                        //Remove activity
                        finish();
                    } catch (Exception e) {
                    }
                }
            };
            // start thread
            background.start();
        }

        else {
            Thread background = new Thread() {
                public void run() {
                    try {


                        // Thread will sleep for 5 seconds
                        sleep(4 * 1000);

                        // After 5 seconds redirect to another intent

                        Intent i = new Intent(getBaseContext(), Home.class);
                        startActivity(i);

                        //Remove activity
                        finish();
                    } catch (Exception e) {
                    }
                }
            };
            // start thread
            background.start();

        }

    }

}
//package com.example.cubie;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Window;
//import android.view.WindowManager;
//
//public class Splash extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
//        Window window = getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
//
//
//        /** Create Thread that will sleep for 5 seconds**/
//        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
//        boolean carFirstStart = prefs2.getBoolean("carFirstStart", true);
//        if (carFirstStart) {
//            Thread background = new Thread() {
//                public void run() {
//                    try {
//
//
//                        // Thread will sleep for 5 seconds
//                        sleep(4 * 1000);
//
//                        // After 5 seconds redirect to another intent
//                        SharedPreferences prefs = getSharedPreferences("prefs2",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = prefs2.edit();
//                        editor.putBoolean("carFirstStart",false);
//                        editor.apply();
//
//                        Intent i = new Intent(getBaseContext(), Carousel.class);
//                        startActivity(i);
//
//                        //Remove activity
//                        finish();
//                    } catch (Exception e) {
//                    }
//                }
//            };
//            // start thread
//            background.start();
//        }
//
//        else {
//            Thread background = new Thread() {
//                public void run() {
//                    try {
//
//
//                        // Thread will sleep for 5 seconds
//                        sleep(4 * 1000);
//
//                        // After 5 seconds redirect to another intent
//
//                        Intent i = new Intent(getBaseContext(), Home.class);
//                        startActivity(i);
//
//                        //Remove activity
//                        finish();
//                    } catch (Exception e) {
//                    }
//                }
//            };
//            // start thread
//            background.start();
//
//        }
//    }
//}