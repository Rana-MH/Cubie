package com.example.cubie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
 Button solve;
 MediaPlayer mp;
 MediaPlayer soundEffect;
    public static boolean musicOn = true;
    private static final String TAG = null;
    ImageView iv ;
    public static boolean musicSwitch= true;
    public static MediaPlayer player;
    public FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFCACACA"));
        user = FirebaseAuth.getInstance().getCurrentUser();
//        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
//        soundEffect.start();
//        solve= findViewById(R.id.outlinedButton);
//         mp = MediaPlayer.create(this, R.raw.clicksound);
//        solve.setOnClickListener(new View.OnClickListener(){
//            @Override public void onClick(View view){
//                mp.start();
//            }
//        });
//        if(musicOn==true)
//        {
//            player= MediaPlayer.create(this, R.raw.colorscound);
//            musicOn = false;
//        }
//
//        if(!player.isPlaying())
//        {
//
//            player.setLooping(true); // Set looping
//            player.setVolume(100,100);
//            player.start();
//        }
//
//    }
//    public void onStart(Intent intent, int startId) {
//        super.onStart();
//    }
//    public void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//    }
//
//
//    public IBinder onBind(Intent arg0) {
//
//        return null;
//    }
//
//    @Override
//    protected void onPause() {
//
//        super.onPause();
//        player.stop();
//    }
//
//    public void musicOff(View view) {
//        if(musicSwitch == true)
//        {
//            Resources resources = getResources();
//
//            player.pause();
//            iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_volume_off_24));
//
//
//            musicSwitch = false;
//        }
//        else
//        {
//
//            player.start();
//            iv.setImageResource(R.drawable.ic_baseline_volume_up_24);
//            musicSwitch = true;
//
//        }
//
//
//    }

    }

    public void solve(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {
            Intent i=new Intent(getBaseContext(),Solve.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void login(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        if (FirebaseAuth.getInstance().getCurrentUser() == null || !(user.isEmailVerified()) ) {
            try {
                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
            }
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

            builder1.setMessage("You are already logged in, Logout first!");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton("Logout",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseAuth.getInstance().signOut();
                    dialog.cancel();
                    Intent intent4 = new Intent(Home.this, Login.class);
                    startActivity(intent4);
                }
            });



            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }


    public void insta(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Intro.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void timer(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Timer.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    public void pro(View view) {
        try {

            Intent i=new Intent(getBaseContext(),Profile.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }
    public void probtn(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            try {
                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
            }
        } else if (!(user.isEmailVerified())) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

            builder1.setMessage("Your email isn't verified , please verify your email first and login again!");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

        } else {
            try {
                Intent i = new Intent(getBaseContext(), Profile.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
            }
        }
    }




}






