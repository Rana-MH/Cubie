package com.example.cubie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Timer extends AppCompatActivity {
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;
    MediaPlayer soundEffect;
    TextView worst , best , timeView;
    ImageView pause, start, reset;
    char[] charsTV, charsbest, charsworst;
    Animation scaleup, scaledown;

    String Scramble;
    char[] movesArray;
    TextView displaymoves;
    String[] movesarray= {"R", "U", "F", "R'", "U'", "F'", "R2", "U2", "F2" ,"D", "D'","L","L'"};
    SharedPreferences prefs3 ;
    String bestData ;

    SharedPreferences prefs4 ;
    String worstData ;


    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
        setContentView(R.layout.activity_timer);
        //Best record textview
        best=findViewById(R.id.textView54);
        pause = findViewById(R.id.pause);
        reset = findViewById(R.id.reset);
        start = findViewById(R.id.play);
        //worst record textview
        worst=findViewById(R.id.textView55);
        //Timer textview
        timeView= (TextView) findViewById(R.id.time_view);
        // three char arraylists for the 3 textviews
//        charsTV = new ArrayList<Character>();
//        charsbest = new ArrayList<Character>();
//        charsworst = new ArrayList<Character>();

        // Bottom Navigation bar code
        BottomNavigationView bottomnav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        displaymoves=findViewById(R.id.displayMoves);
        displaymoves.setText("");
        Scramble = "";

        prefs3 = getSharedPreferences("prefs3",MODE_PRIVATE);
        bestData = prefs3.getString("bestData", "00:00:00");
        best.setText(prefs3.getString("bestData", "00:00:00"));


        prefs4 = getSharedPreferences("prefs4",MODE_PRIVATE);
        worstData = prefs4.getString("worstData", "00:00:00");
        worst.setText(prefs4.getString("worstData", "00:00:00"));


        for(int i=0; i < 15; i++){
            Random notActuallyARandomNumber =  new Random();
            int index = notActuallyARandomNumber.nextInt(13); //random number from {0,12}
            Scramble += movesarray[index];
            Scramble += " ";
        }

        displaymoves.setText(Scramble);
        bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_1:
                        soundEffect = MediaPlayer.create(Timer.this,R.raw.colorscound);
                        soundEffect.start();
                        Intent intent1 = new Intent(Timer.this, Home.class);
                        startActivity(intent1);
                        break;
                    case R.id.page_2:
                        soundEffect = MediaPlayer.create(Timer.this,R.raw.colorscound);
                        soundEffect.start();
                        Intent intent2 = new Intent(Timer.this, Intro.class);
                        startActivity(intent2);
                        break;
//
                    case R.id.page_3:
                        soundEffect = MediaPlayer.create(Timer.this,R.raw.colorscound);
                        soundEffect.start();
                        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                            try {
                                Intent i = new Intent(getBaseContext(), Login.class);
                                startActivity(i);
                                finish();
                            } catch (Exception e) {
                            }}
                        else{
                            Intent intent3 = new Intent(Timer.this, Profile.class);
                            startActivity(intent3);
                            break;}
//
                    case R.id.page_4:
                    {   soundEffect = MediaPlayer.create(Timer.this,R.raw.colorscound);
                        soundEffect.start();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(Timer.this, Login.class);
                        startActivity(intent4);
                        break;}

                }


                return false;
            }
        });




        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();

    }



    //back button
    public void back(View view) {
        soundEffect = MediaPlayer.create(Timer.this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i = new Intent(getBaseContext(), Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
        }
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
        scaleup = AnimationUtils.loadAnimation(this,
                R.anim.scale_up);
        scaledown = AnimationUtils.loadAnimation(this,
                R.anim.scale_down);

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    start.startAnimation(scaledown);

                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    start.startAnimation(scaleup);
                }
                return false;
            }
        });
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    public void onClickStop(View view) {


        scaleup = AnimationUtils.loadAnimation(this,
                R.anim.scale_up);
        scaledown = AnimationUtils.loadAnimation(this,
                R.anim.scale_down);

        pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    pause.startAnimation(scaledown);

                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    pause.startAnimation(scaleup);
                }
                return false;
            }
        });
        running = false;
        charsTV = timeView.getText().toString().toCharArray();
        charsbest = best.getText().toString().toCharArray();
        charsworst = worst.getText().toString().toCharArray();
//        for (int i = 0; i < timeView.getText().length(); i++){
//            charsTV.add(timeView.getText().charAt(i));}
//        for (int i = 0; i < best.getText().length(); i++){
//            charsbest.add(best.getText().charAt(i));}
//        for (int i = 0; i < worst.getText().length(); i++){
//            charsworst.add(worst.getText().charAt(i));}
//        if((best.getText().equals("") )&& (worst.getText().equals(""))) {
//            best.setText(timeView.getText());
//            SharedPreferences prefs3 = getSharedPreferences("prefs3",MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs3.edit();
//            editor.putString("bestData",best.getText().toString());
//            editor.apply();
//
//
//            worst.setText(timeView.getText());
//            SharedPreferences prefs4 = getSharedPreferences("prefs4",MODE_PRIVATE);
//            SharedPreferences.Editor editor2 = prefs4.edit();
//            editor2.putString("worstData",worst.getText().toString());
//            editor2.apply();
//        }




        //not working why idk
//        else
        if(!(charsTV[0]=='0' && charsTV[1]=='0'&& charsTV[3]=='0' && charsTV[4]=='0' && charsTV[6]=='0' && charsTV[7]=='0')){
            for (int i = 0; i < 8; i++) {
//                if(charsbest.get(i).equals(':') || charsbest.get(i).equals(charsTV.get(i)) )
//                    continue;

                if(best.getText().toString().equals("00:00:00"))
                {
                    best.setText(timeView.getText());
                    SharedPreferences prefs3 = getSharedPreferences("prefs3",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs3.edit();
                    editor.putString("bestData",best.getText().toString());
                    editor.apply();
                }

                if (charsbest[i] < charsTV[i])
                    break;

                else if (charsbest[i] > charsTV[i] ) {
                    best.setText(timeView.getText());
                    SharedPreferences prefs3 = getSharedPreferences("prefs3",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs3.edit();
                    editor.putString("bestData",best.getText().toString());
                    editor.apply();

                    break;

                }}

            for (int i = 0; i < 8; i++) {

                if (charsworst[i] < charsTV[i])

                {
                    worst.setText((timeView.getText()));
                    SharedPreferences prefs4 = getSharedPreferences("prefs4",MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = prefs4.edit();
                    editor2.putString("worstData",worst.getText().toString());
                    editor2.apply();

                    break;
                }
                else if(charsworst[i]> charsTV[i])
                {
                    break;
                }

            }

        }
    }


//        else for(int i=0;i< charsTV.size();i++){
//            for(int  j=0; j<charsbest.size();j++){
//                if ((charsTV.get(i).equals(':'))&& (charsbest.get(j).equals(':')))
//                    continue;
//                int numericValue = Character.getNumericValue(charsTV.get(i));
//                int numericValue2=Character.getNumericValue(charsbest.get(j));
//                if (numericValue<numericValue2){
//                    best.setText(timeView.getText());
//                }
//
//            }
//        }



    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;

        displaymoves.setText("");
        Scramble = "";
        for(int i=0; i < 15; i++){
            Random notActuallyARandomNumber =  new Random();
            int index = notActuallyARandomNumber.nextInt(13); //random number from {0,12}
            Scramble += movesarray[index];
            Scramble += " ";
        }

        displaymoves.setText(Scramble);

        scaleup = AnimationUtils.loadAnimation(this,
                R.anim.scale_up);
        scaledown = AnimationUtils.loadAnimation(this,
                R.anim.scale_down);

        reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    reset.startAnimation(scaledown);

                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    reset.startAnimation(scaleup);
                }
                return false;
            }
        });


    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer() {

        // Get the text view.


        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run() {
//                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                int mili=secs/1000;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%02d:%02d:%02d",
                                mili, minutes,secs );

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 10);
            }
        });
    }

}