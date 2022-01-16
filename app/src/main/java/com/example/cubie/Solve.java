package com.example.cubie;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.spyhunter99.supertooltips.ToolTipManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;


public class Solve extends AppCompatActivity implements View.OnClickListener{
    Button ybtn1;Button ybtn2;Button ybtn3;Button ybtn4;
    Button rbtn1;Button rbtn2;Button rbtn3;Button rbtn4;
    Button gbtn1;Button gbtn2;Button gbtn3;Button gbtn4;
    Button obtn1;Button obtn2;Button obtn3;Button obtn4;
    Button bbtn1;Button bbtn2;Button bbtn3;Button bbtn4;
    Button wbtn1;Button wbtn2;Button wbtn3;Button wbtn4;

    //temperory array to know the solving algorithm without applying it to the real buttons

    Button ybtn12;Button ybtn22;Button ybtn32;Button ybtn42;
    Button rbtn12;Button rbtn22;Button rbtn32;Button rbtn42;
    Button gbtn12;Button gbtn22;Button gbtn32;Button gbtn42;
    Button obtn12;Button obtn22;Button obtn32;Button obtn42;
    Button bbtn12;Button bbtn22;Button bbtn32;Button bbtn42;
    Button wbtn12;Button wbtn22;Button wbtn32;Button wbtn42;


    RadioGroup rg;
    ImageView reset;
    ImageView scramble;
    ImageView solve;
    RadioButton ybtn ;
    RadioButton rbtn;
    RadioButton gbtn;
    RadioButton obtn;
    RadioButton bbtn;
    RadioButton wbtn;
    Button buttons[] = new Button[24];
    Button buttons2[] = new Button[24];
    MediaPlayer soundEffect;
Button tophint;
    //View v = new View(this);
    boolean startTimer ;
    long timer;
    int y = 0, r = 0, w = 0, o = 0, g = 0, b = 0;
    TextView textView;
    ArrayList<String> moves = new ArrayList();// to hold the move that should be moved
    int movesPointer=0;
    String[] piece;
    String s = new String();
    String temp, temp2, tempface;
    String temp3;
    String t1,t2,t3;
    String t="";
    String solution = "";
    String Scramble;
    int count1,count=0;
    int movesCount = -1;
    String ss = "";
    Handler handler = new Handler();
    boolean goNext = true;
    boolean solvedOrNot;
    boolean placeholder;
    TextView firstMove, secondMove, thirdMove;
    int arraySizeRemain;

    TextView displaymoves, displaymoves2, topface;
    String[] movesArray= {"R", "U", "F", "R'", "U'", "F'", "R2", "U2", "F2" ,"D", "D'","L","L'"};
    int newProgress;

    ProgressBar progressBar;
    ImageView leftArrow ,scramblebtn, solvebtn, resetbtn;
    ImageView rightArrow , help;
    Drawable d;
    GifImageView gifImageView;
    ToolTipManager tooltips;

    TextView top, down, right, left, back;






    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        getSupportActionBar().hide();
        gifImageView=findViewById(R.id.gifImageView);
        gifImageView.setVisibility(View.GONE);
        displaymoves2 = findViewById(R.id.displaymoves2);
        progressBar = findViewById(R.id.progressBar);
        leftArrow = findViewById(R.id.leftArrow);
        rightArrow = findViewById(R.id.rightArrow);
        textView = findViewById(R.id.firstMove);
        displaymoves = findViewById(R.id.displaymoves);
        firstMove = findViewById(R.id.firstMove);
        secondMove = findViewById(R.id.secondMove);
        thirdMove = findViewById(R.id.thirdMove);
        reset = findViewById(R.id.resetbtn);
        scramble = findViewById(R.id.scramblebtn);
        solve = findViewById(R.id.solvebtn);
        top = findViewById(R.id.top);
        down = findViewById(R.id.down);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        back = findViewById(R.id.back);

        displaymoves.setText("");
        topface=findViewById(R.id.right);
//       topface.setVisibility(View.GONE);
        help=findViewById(R.id.help);

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if(firstStart) {
            new TapTargetSequence(this).targets(
                    TapTarget.forView(help, "Instructions", "To make it easier for you to use our solver make sure to check the instructions!")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.90f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40),

                    TapTarget.forView(scramble, "Scramble Button", "Press on the scramble button to generate random moves")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.93f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40),

                    TapTarget.forView(solve, "Solve Button", "Press on the Solve button to start solving the cube!")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.93f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40),
                    TapTarget.forView(rightArrow, "Next Button", "The next button will show after you press the solve button and it will allow you to go through the moves step by step!")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.93f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(15)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40),
                    TapTarget.forView(leftArrow, "Previous Button", "After pressing the next button the previous button will light up it will allow you to go back with the moves!")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.93f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(15)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40),


                    TapTarget.forView(reset, "Reset Button", "Press on the Reset button to return the cube to its original solved state!")
                            .outerCircleColor(R.color.white)
                            .outerCircleAlpha(0.93f)
                            .targetCircleColor(R.color.black)
                            .titleTextSize(30)
                            .titleTextColor(R.color.black)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(40)).listener(new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {

//        Toast.makeText(secondActivity.this,"Sequence Finished",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

//        Toast.makeText(secondActivity.this,"GREAT!",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {

                }
            }).start();
            SharedPreferences prefs2 = getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs2.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }


//                new TapTargetView.Listener(){
//
//                    @Override
//                    public void onTargetClick(TapTargetView view) {
//                        super.onTargetClick(view);
//                        topface.setVisibility(View.GONE);
//
//
//                    }
//                };
//        tooltips = new ToolTipManager(this);
//        findViewById(R.id.help).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ToolTip toolTip = new ToolTip()
//                        .withText("Make")
//                        .withColor(Color.WHITE) //or whatever you want
//                        .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
//                        .withShadow();
//                tooltips.showToolTip(toolTip, v);
//                return true;
//            }
//        });



        // Up face

        ybtn1 = findViewById(R.id.ybtn1);
        ybtn2 = findViewById(R.id.ybtn2);
        ybtn3 = findViewById(R.id.ybtn3);
        ybtn4 = findViewById(R.id.ybtn4);

        ybtn12 = findViewById(R.id.ybtn12);
        ybtn22 = findViewById(R.id.ybtn22);
        ybtn32 = findViewById(R.id.ybtn32);
        ybtn42 = findViewById(R.id.ybtn42);


        // Left face
        rbtn1 = findViewById(R.id.rbtn1);
        rbtn2 = findViewById(R.id.rbtn2);
        rbtn3 = findViewById(R.id.rbtn3);
        rbtn4 = findViewById(R.id.rbtn4);

        rbtn12 = findViewById(R.id.rbtn12);
        rbtn22 = findViewById(R.id.rbtn22);
        rbtn32 = findViewById(R.id.rbtn32);
        rbtn42 = findViewById(R.id.rbtn42);


        // Front face
        gbtn1 = findViewById(R.id.gbtn1);
        gbtn2 = findViewById(R.id.gbtn2);
        gbtn3 = findViewById(R.id.gbtn3);
        gbtn4 = findViewById(R.id.gbtn4);

        gbtn12 = findViewById(R.id.gbtn12);
        gbtn22 = findViewById(R.id.gbtn22);
        gbtn32 = findViewById(R.id.gbtn32);
        gbtn42 = findViewById(R.id.gbtn42);



        // Right face
        obtn1 = findViewById(R.id.obtn1);
        obtn2 = findViewById(R.id.obtn2);
        obtn3 = findViewById(R.id.obtn3);
        obtn4 = findViewById(R.id.obtn4);

        obtn12 = findViewById(R.id.obtn12);
        obtn22 = findViewById(R.id.obtn22);
        obtn32 = findViewById(R.id.obtn32);
        obtn42 = findViewById(R.id.obtn42);



        // Back face
        bbtn1 = findViewById(R.id.bbtn1);
        bbtn2 = findViewById(R.id.bbtn2);
        bbtn3 = findViewById(R.id.bbtn3);
        bbtn4 = findViewById(R.id.bbtn4);

        bbtn12 = findViewById(R.id.bbtn12);
        bbtn22 = findViewById(R.id.bbtn22);
        bbtn32 = findViewById(R.id.bbtn32);
        bbtn42 = findViewById(R.id.bbtn42);



        // Down face
        wbtn1 = findViewById(R.id.wbtn1);
        wbtn2 = findViewById(R.id.wbtn2);
        wbtn3 = findViewById(R.id.wbtn3);
        wbtn4 = findViewById(R.id.wbtn4);

        wbtn12 = findViewById(R.id.wbtn12);
        wbtn22 = findViewById(R.id.wbtn22);
        wbtn32 = findViewById(R.id.wbtn32);
        wbtn42 = findViewById(R.id.wbtn42);




        rg = findViewById(R.id.radiogroup);

        ybtn = findViewById(R.id.yellowbtn);
        rbtn = findViewById(R.id.redbtn);
        gbtn = findViewById(R.id.greenbtn);
        obtn = findViewById(R.id.orangebtn);
        bbtn = findViewById(R.id.bluebtn);
        wbtn = findViewById(R.id.whitebtn);


        ybtn1.setOnClickListener(this);
        ybtn2.setOnClickListener(this);
        ybtn3.setOnClickListener(this);
        ybtn4.setOnClickListener(this);
        rbtn1.setOnClickListener(this);
        rbtn2.setOnClickListener(this);
        rbtn3.setOnClickListener(this);
        rbtn4.setOnClickListener(this);
        gbtn1.setOnClickListener(this);
        gbtn2.setOnClickListener(this);
        gbtn3.setOnClickListener(this);
        gbtn4.setOnClickListener(this);
        bbtn1.setOnClickListener(this);
        bbtn2.setOnClickListener(this);
        bbtn3.setOnClickListener(this);
        bbtn4.setOnClickListener(this);
        wbtn1.setOnClickListener(this);
        wbtn2.setOnClickListener(this);
        wbtn3.setOnClickListener(this);
        wbtn4.setOnClickListener(this);
        obtn1.setOnClickListener(this);
        obtn2.setOnClickListener(this);
        obtn3.setOnClickListener(this);
        obtn4.setOnClickListener(this);

        buttons[0] = ybtn1;
        buttons[1] = ybtn2;
        buttons[2] = ybtn3;
        buttons[3] = ybtn4;
        buttons[4] = rbtn1;
        buttons[5] = rbtn2;
        buttons[6] = rbtn3;
        buttons[7] = rbtn4;
        buttons[8] = gbtn1;
        buttons[9] = gbtn2;
        buttons[10] = gbtn3;
        buttons[11] = gbtn4;
        buttons[12] = obtn1;
        buttons[13] = obtn2;
        buttons[14] = obtn3;
        buttons[15] = obtn4;
        buttons[16] = bbtn1;
        buttons[17] = bbtn2;
        buttons[18] = bbtn3;
        buttons[19] = bbtn4;
        buttons[20] = wbtn1;
        buttons[21] = wbtn2;
        buttons[22] = wbtn3;
        buttons[23] = wbtn4;


        buttons2[0] = ybtn12;
        buttons2[1] = ybtn22;
        buttons2[2] = ybtn32;
        buttons2[3] = ybtn42;
        buttons2[4] = rbtn12;
        buttons2[5] = rbtn22;
        buttons2[6] = rbtn32;
        buttons2[7] = rbtn42;
        buttons2[8] = gbtn12;
        buttons2[9] = gbtn22;
        buttons2[10] = gbtn32;
        buttons2[11] = gbtn42;
        buttons2[12] = obtn12;
        buttons2[13] = obtn22;
        buttons2[14] = obtn32;
        buttons2[15] = obtn42;
        buttons2[16] = bbtn12;
        buttons2[17] = bbtn22;
        buttons2[18] = bbtn32;
        buttons2[19] = bbtn42;
        buttons2[20] = wbtn12;
        buttons2[21] = wbtn22;
        buttons2[22] = wbtn32;
        buttons2[23] = wbtn42;
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);


        BottomNavigationView bottomnav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.page_1:


                        soundEffect.start();
                        Intent intent1 = new Intent(Solve.this, Home.class);
                        startActivity(intent1);
                        break;
                    case R.id.page_2:
                        soundEffect.start();

                        Intent intent2 = new Intent(Solve.this, Intro.class);
                        startActivity(intent2);
                        break;

                    case R.id.page_3:
                    { soundEffect.start();
                        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                            try {
                                Intent i = new Intent(getBaseContext(), Login.class);
                                startActivity(i);
                                finish();
                            } catch (Exception e) {
                            }}
                        else{
                            Intent intent3 = new Intent(Solve.this, Profile.class);
                            startActivity(intent3);

                            break;}}

                    case R.id.page_4:

                    {                         soundEffect.start();

                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(Solve.this, Login.class);
                        startActivity(intent4); break;}

                }



                return false;
            }
        });



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#333333"));


    }


    @Override
    public void onClick(View v) {

        progressBar.setProgress(0);
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();

        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");


        if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please pick a color", Toast.LENGTH_LONG).show();
        } else if (ybtn.isChecked()) {
            v.setBackgroundColor(Color.parseColor("#FFE8E15A"));
            v.setTag("#FFE8E15A");
            moves.clear();
            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");


            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);

        } else if (rbtn.isChecked()) {
            v.setBackgroundColor(Color.parseColor("#FFE30000"));
            v.setTag("#FFE30000");
            moves.clear();
            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");

            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);


        } else if (gbtn.isChecked()) {
            v.setBackgroundColor(Color.parseColor("#FF3A9A3E"));
            v.setTag("#FF3A9A3E");
            moves.clear();

            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");


            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);


        } else if (obtn.isChecked()) {
            v.setBackgroundColor(Color.parseColor("#FFEF8426"));
            v.setTag("#FFEF8426");
            moves.clear();
            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");


            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);


        } else if (bbtn.isChecked()) {
            v.setBackgroundColor(Color.parseColor("#FF1F8FE3"));
            v.setTag("#FF1F8FE3");
            moves.clear();
            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");


            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);


        } else if (wbtn.isChecked()) {
            v.setBackgroundColor(Color.WHITE);
            v.setTag("#FFFFFFFF");
            moves.clear();
            textView.setText("");
            secondMove.setText("");
            thirdMove.setText("");


            leftArrow.setImageResource(R.drawable.leftarrow);
            rightArrow.setImageResource(R.drawable.rightarrow);


        }

    }

    public void reset(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");

        back.setText("Back Face");
        down.setText("Down Face");
        top.setText("Top Face");
        left.setText("Left Face");
        right.setText("Right Face");



        soundEffect = MediaPlayer.create(this,R.raw.reset);
        soundEffect.start();

        ss="";
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");

        leftArrow.setImageResource(R.drawable.leftarrow);
        rightArrow.setImageResource(R.drawable.rightarrow);

        progressBar.setProgress(0);
        moves.clear();



        ybtn1.setBackgroundColor(Color.parseColor("#FFE8E15A"));
        ybtn1.setTag("#FFE8E15A");
        ybtn2.setBackgroundColor(Color.parseColor("#FFE8E15A"));
        ybtn2.setTag("#FFE8E15A");
        ybtn3.setBackgroundColor(Color.parseColor("#FFE8E15A"));
        ybtn3.setTag("#FFE8E15A");
        ybtn4.setBackgroundColor(Color.parseColor("#FFE8E15A"));
        ybtn4.setTag("#FFE8E15A");


        rbtn1.setBackgroundColor(Color.parseColor("#FFE30000"));
        rbtn1.setTag("#FFE30000");
        rbtn2.setBackgroundColor(Color.parseColor("#FFE30000"));
        rbtn2.setTag("#FFE30000");
        rbtn3.setBackgroundColor(Color.parseColor("#FFE30000"));
        rbtn3.setTag("#FFE30000");
        rbtn4.setBackgroundColor(Color.parseColor("#FFE30000"));
        rbtn4.setTag("#FFE30000");

        gbtn1.setBackgroundColor(Color.parseColor("#FF3A9A3E"));
        gbtn1.setTag("#FF3A9A3E");
        gbtn2.setBackgroundColor(Color.parseColor("#FF3A9A3E"));
        gbtn2.setTag("#FF3A9A3E");
        gbtn3.setBackgroundColor(Color.parseColor("#FF3A9A3E"));
        gbtn3.setTag("#FF3A9A3E");
        gbtn4.setBackgroundColor(Color.parseColor("#FF3A9A3E"));
        gbtn4.setTag("#FF3A9A3E");

        obtn1.setBackgroundColor(Color.parseColor("#FFEF8426"));
        obtn1.setTag("#FFEF8426");
        obtn2.setBackgroundColor(Color.parseColor("#FFEF8426"));
        obtn2.setTag("#FFEF8426");
        obtn3.setBackgroundColor(Color.parseColor("#FFEF8426"));
        obtn3.setTag("#FFEF8426");
        obtn4.setBackgroundColor(Color.parseColor("#FFEF8426"));
        obtn4.setTag("#FFEF8426");

        bbtn1.setBackgroundColor(Color.parseColor("#FF1F8FE3"));
        bbtn1.setTag("#FF1F8FE3");
        bbtn2.setBackgroundColor(Color.parseColor("#FF1F8FE3"));
        bbtn2.setTag("#FF1F8FE3");
        bbtn3.setBackgroundColor(Color.parseColor("#FF1F8FE3"));
        bbtn3.setTag("#FF1F8FE3");
        bbtn4.setBackgroundColor(Color.parseColor("#FF1F8FE3"));
        bbtn4.setTag("#FF1F8FE3");

        wbtn1.setBackgroundColor(Color.WHITE);
        wbtn1.setTag("#FFFFFFFF");
        wbtn2.setBackgroundColor(Color.WHITE);
        wbtn2.setTag("#FFFFFFFF");
        wbtn3.setBackgroundColor(Color.WHITE);
        wbtn3.setTag("#FFFFFFFF");
        wbtn4.setBackgroundColor(Color.WHITE);
        wbtn4.setTag("#FFFFFFFF");


    }

    public void solve() {

        if (isSolved())
            return;

        textView.setText("");
        displaymoves2.setText("");

        if(oneMoveCheck())
            return;



        // if the cube is not solved the code will continue


        //if there is no white stickers facing down find the closest one
        if(numberOfStickersFacingDown("#FFFFFFFF")==0)
        {
            if(gbtn22.getTag().equals("#FFFFFFFF")||gbtn42.getTag().equals("#FFFFFFFF"))
                movesKeeper("R'");
            else if(bbtn12.getTag().equals("#FFFFFFFF")||bbtn12.getTag().equals("#FFFFFFFF"))
                movesKeeper("R");
            else if(gbtn12.getTag().equals("#FFFFFFFF")||gbtn32.getTag().equals("#FFFFFFFF"))
                movesKeeper("L");
            else if(bbtn22.getTag().equals("#FFFFFFFF")||bbtn42.getTag().equals("#FFFFFFFF"))
                movesKeeper("L'");
            else if(obtn12.getTag().equals("#FFFFFFFF")||obtn32.getTag().equals("#FFFFFFFF"))
                movesKeeper("F");
            else if(rbtn22.getTag().equals("#FFFFFFFF")|| rbtn42.getTag().equals("#FFFFFFFF"))
                movesKeeper("F'");
            else if(obtn22.getTag().equals("#FFFFFFFF")||obtn42.getTag().equals("#FFFFFFFF"))
                movesKeeper("B'");
            else if(rbtn12.getTag().equals("#FFFFFFFF")|| rbtn22.getTag().equals("#FFFFFFFF"))
                movesKeeper("B");
            else if(ybtn12.getTag().equals("#FFFFFFFF")||ybtn22.getTag().equals("#FFFFFFFF"))
                movesKeeper("B2");
            else if(ybtn32.getTag().equals("#FFFFFFFF")||ybtn42.getTag().equals("#FFFFFFFF"))
                movesKeeper("F2");
        }
        if(wbtn12.getTag().equals("#FFFFFFFF")) //wbtn1 is the target piece
        {
            t1=(String)rbtn42.getTag();
            t2=(String)gbtn32.getTag();

            // find the piece in the back of the target piece and place it in the right position

            //if the piece is above the targe piece
            if((ybtn32.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t1))
                    ||(gbtn12.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t1))
                    || (rbtn22.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t1)))
            {
                movesKeeper("U");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the piece is on the right top of the target piece
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t1))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t1))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t1)))
            {
                movesKeeper("U2");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the other piece is on the back left
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t1))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t1))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t1)))
            {
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the other piece up on the diagonal of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t1))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t1))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t1)))
            {
                movesKeeper("U'");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the other piece down in the back
            else if((wbtn32.getTag().equals("#FFFFFFFF")&&rbtn32.getTag().equals(t1))
                    ||(bbtn42.getTag().equals("#FFFFFFFF")&&wbtn32.getTag().equals(t1))
                    || (rbtn32.getTag().equals("#FFFFFFFF")&&bbtn42.getTag().equals(t1)))
            {
                if(!wbtn32.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("B'U'BU");
                    //if the white is facing up
                    if (ybtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("B'U2BUB'U'B");
                        //if the white is on the left
                    else if (rbtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("LUL'");
                        //if the white is on the back
                    else
                        movesKeeper("B'U'B");
                }

            }
            //if the piece is down  on the diagonal of the target piece
            else if((obtn42.getTag().equals("#FFFFFFFF")&&wbtn42.getTag().equals(t1))
                    ||(bbtn32.getTag().equals("#FFFFFFFF")&&obtn42.getTag().equals(t1))
                    || (wbtn42.getTag().equals("#FFFFFFFF")&&bbtn32.getTag().equals(t1)))
            {
                movesKeeper("BUB'U2");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the piece is down on the right of the target piece
            else if((wbtn22.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t1))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t1))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t1)))
            {
                movesKeeper("RU2R'");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }

            //find the piece that should be on the right of the target piece and place in the right position


            //if the piece is right on the top of the target piece

            if((ybtn32.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t2))
                    ||(gbtn12.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t2))
                    || (rbtn22.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t2))) {
                movesKeeper("U'");

                if (gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                else if (obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                else
                    movesKeeper("F'U2FUF'U'F");


            }
            //if the piece is on the top right
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t2))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t2))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t2))) {
                if (gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                else if (obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                else
                    movesKeeper("F'U2FUF'U'F");
            }
            //if the piece is on back up left
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t2))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t2))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t2))) {
                movesKeeper("U2");
                if (gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                else if (obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                else
                    movesKeeper("F'U2FUF'U'F");
            }
            //if the piece is up diagonal of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t2))
                    ||(obtn22.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t2))
                    || (bbtn12.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t2)))
            {
                movesKeeper("U");
                if (gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                else if (obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                else
                    movesKeeper("F'U2FUF'U'F");

            }
            //if the piece is down in the right place
            else if((wbtn22.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t2))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t2))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t2)))
            {
                if(!wbtn22.getTag().equals("#FFFFFFFF")){
                    movesKeeper("RUR'U'");
                    if (gbtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("F'U'F");
                    else if (obtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("RUR'");
                    else
                        movesKeeper("F'U2FUF'U'F");


                }
            }
            //if the piece down diagonal
            else if((wbtn42.getTag().equals("#FFFFFFFF")&&obtn42.getTag().equals(t2))
                    ||(bbtn32.getTag().equals("#FFFFFFFF")&&wbtn42.getTag().equals(t2))
                    || (obtn42.getTag().equals("#FFFFFFFF")&&bbtn32.getTag().equals(t2)))
            {
                movesKeeper("BUB'");
                if (gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                else if (obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                else
                    movesKeeper("F'U2FUF'U'F");

            }
            //if the piece down back
                                /*else if((wbtn3.getTag().equals("#FFFFFFFF")&&bbtn4.getTag().equals(t2))
                                        ||(bbtn4.getTag().equals("#FFFFFFFF")&&rbtn3.getTag().equals(t2))
                                        || (rbtn3.getTag().equals("#FFFFFFFF")&&bbtn4.getTag().equals(t2)))
                                {
                                        scramble("B'U2B");
                                        if (gbtn2.getTag().equals("#FFFFFFFF"))
                                                scramble("F'U'F");
                                        else if (obtn1.getTag().equals("#FFFFFFFF"))
                                                scramble("RUR'");
                                        else
                                                scramble("F'U2FUF'U'F");

                                }*/

            //find the piece that should be on the diagonal down of the target piece and place it in the right position
            t3=(String)obtn32.getTag();
            //if the piece is in the right place
            if((wbtn42.getTag().equals("#FFFFFFFF")&&obtn42.getTag().equals(t3))
                    ||(obtn42.getTag().equals("#FFFFFFFF")&&bbtn32.getTag().equals(t3))
                    || (bbtn32.getTag().equals("#FFFFFFFF")&&wbtn42.getTag().equals(t3)))
            {
                if(!wbtn42.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("BUB'U'");
                    if(obtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("R'U'R");
                    else if(bbtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("BUB'");
                    else
                        movesKeeper("R'U2RUR'U'R");

                }
            }
            //if the piece is up diagonal of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t3))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t3))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t3)))
            {
                if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                else if(bbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("BUB'");
                else
                    movesKeeper("R'U2RUR'U'R");

            }
            //if the piece is up back of the target piece
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t3))
                    ||(rbtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t3))
                    || (bbtn22.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t3)))
            {
                movesKeeper("U");
                if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                else if(bbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("BUB'");
                else
                    movesKeeper("R'U2RUR'U'R");

            }
            //if the piece is on top of the target piece
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t3))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t3))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t3)))
            {
                movesKeeper("U2");
                if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                else if(bbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("BUB'");
                else
                    movesKeeper("R'U2RUR'U'R");

            }
            //if the piece os on the right top of the target piece
            else if ((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t3))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t3))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t3)))
            {
                movesKeeper("U'");
                if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                else if(bbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("BUB'");
                else
                    movesKeeper("R'U2RUR'U'R");

            }

        }//end of the first if of the four cases
        else if(wbtn22.getTag().equals("#FFFFFFFF")) //wbtn2 is the target piece
        {
            t1=(String)obtn32.getTag();
            t2=(String)gbtn42.getTag();

            //find the piece in the back of the target piece and place it in the right position

            //if the piece is above the target piece
            if((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t1))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t1))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t1)))
            {
                movesKeeper("U'");
                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");

            }
            //if the piece is on the top back of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t1))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t1))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t1)))
            {
                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the other piece is up on the back left
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t1))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t1))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t1)))
            {
                movesKeeper("U");
                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");


            }
            //if the other piece is up on the left of the target piece
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t1))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t1))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t1)))
            {
                movesKeeper("U2");
                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the other piece is in the right place
            else if((wbtn42.getTag().equals("#FFFFFFFF")&&obtn42.getTag().equals(t1))
                    ||(bbtn32.getTag().equals("#FFFFFFFF")&&wbtn42.getTag().equals(t1))
                    || (obtn42.getTag().equals("#FFFFFFFF")&&bbtn32.getTag().equals(t1)))
            {
                if(!wbtn42.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("BUB'U'");
                    //if the white is facing up
                    if(ybtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("R'U2RUR'U'R");
                        //if the white is on the right
                    else if(obtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("R'U'R");
                        //if the white is on the back
                    else
                        movesKeeper("BUB'");
                }
            }
            //if the piece is down  on the diagonal of the target piece
            else if((wbtn32.getTag().equals("#FFFFFFFF")&&bbtn42.getTag().equals(t1))
                    ||(rbtn32.getTag().equals("#FFFFFFFF")&&wbtn32.getTag().equals(t1))
                    || (bbtn42.getTag().equals("#FFFFFFFF")&&rbtn32.getTag().equals(t1)))
            {
                movesKeeper("B'UBU");

                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");

            }
            //if the piece is down on the left of the target piece
            else if((wbtn12.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t1))
                    ||(rbtn42.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t1))
                    || (gbtn32.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t1)))
            {
                movesKeeper("L'U2L");
                //if the white is facing up
                if(ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if(obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }

            //find the piece that should be on the left of the target piece and place it in the right position

            //if the piece is on the top of the target piece

            if((ybtn42.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t2))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t2))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t2)))
            {
                movesKeeper("U");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U2LUL'U'L");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }
            //if the piece is on the front top left
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t2))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t2))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t2)))
            {
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U2LUL'U'L");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");
            }
            //if the piece is up diagonal of the target piece
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t2))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t2))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t2)))
            {
                movesKeeper("U'");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U2LUL'U'L");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }
            //if the piece is up back of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t2))
                    ||(obtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t2))
                    || (bbtn12.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t2)))
            {
                movesKeeper("U2");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U2LUL'U'L");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }
            //if the piece is down in the right place
            else if((wbtn12.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t2))
                    ||(rbtn42.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t2))
                    || (gbtn32.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t2)))
            {
                if(!wbtn12.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("L'U'LU");
                    //if the white is on the top
                    if(ybtn32.getTag().equals("#FFFFFFFF"))
                        movesKeeper("L'U2LUL'U'L");
                        //if the white is on the left
                    else if(rbtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("L'U'L");
                        //if the white is on the front
                    else
                        movesKeeper("FUF'");
                }

            }
            //if the piece down diagonal
            else if((wbtn32.getTag().equals("#FFFFFFFF")&&rbtn32.getTag().equals(t2))
                    ||(rbtn32.getTag().equals("#FFFFFFFF")&&bbtn42.getTag().equals(t2))
                    || (bbtn42.getTag().equals("#FFFFFFFF")&&wbtn32.getTag().equals(t2)))
            {
                movesKeeper("B'U'B");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U2LUL'U'L");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }

            //find the piece that should be on the diagonal down of the target piece and place it in the right position
            t3=(String)rbtn42.getTag();
            //if the piece is in the right place
            if((wbtn32.getTag().equals("#FFFFFFFF")&&rbtn32.getTag().equals(t3))
                    ||(rbtn32.getTag().equals("#FFFFFFFF")&&bbtn42.getTag().equals(t3))
                    || (bbtn42.getTag().equals("#FFFFFFFF")&&wbtn32.getTag().equals(t3)))
            {
                if(!wbtn32.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("B'U'BU");
                    //if the white is on the top
                    if(ybtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("B'U2BUB'U'B");
                        //if the white is on the left
                    else if(rbtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("LUL'");
                        //if the white is on the back
                    else
                        movesKeeper("B'U'B");

                }
            }
            //if the piece is up diagonal of the target piece
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t3))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t3))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t3)))
            {
                //if the white is on the top
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");


            }
            //if the piece is up back of the target piece
            else if((ybtn22.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t3))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t3))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t3)))
            {
                movesKeeper("U'");
                //if the white is on the top
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");


            }
            //if the piece is on top of the target piece
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t3))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t3))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t3)))
            {
                movesKeeper("U2");
                //if the white is on the top
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");


            }
            //if the piece os on the front left top
            else if ((ybtn32.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t3))
                    ||(gbtn12.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t3))
                    || (rbtn22.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t3)))
            {
                movesKeeper("U");
                //if the white is on the top
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("B'U2BUB'U'B");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }



        }
        else if(wbtn32.getTag().equals("#FFFFFFFF")) //wbtn3 is the target piece
        {
            t2 = (String) rbtn32.getTag();
            t1 = (String) bbtn42.getTag();

            //** find the piece that should  be placed to the right of the target piece and place it in the right position

            //if the piece is above the target piece
            if ((ybtn12.getTag().equals("#FFFFFFFF") && bbtn22.getTag().equals(t1))
                    || (rbtn12.getTag().equals("#FFFFFFFF") && ybtn12.getTag().equals(t1))
                    || (bbtn22.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals(t1)))
            {
                movesKeeper("U");
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");

            }
            //if the piece is up right back
            else if ((ybtn22.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals(t1))
                    || (obtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals(t1))
                    || (bbtn12.getTag().equals("#FFFFFFFF") && ybtn22.getTag().equals(t1)))
            {
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the  piece is on the front right up
            else if ((ybtn42.getTag().equals("#FFFFFFFF") && gbtn22.getTag().equals(t1))
                    || (gbtn22.getTag().equals("#FFFFFFFF") && obtn12.getTag().equals(t1))
                    || (obtn12.getTag().equals("#FFFFFFFF") && ybtn42.getTag().equals(t1)))
            {
                movesKeeper("U'");
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the  piece up left front
            else if ((ybtn32.getTag().equals("#FFFFFFFF") && rbtn22.getTag().equals(t1))
                    || (gbtn12.getTag().equals("#FFFFFFFF") && ybtn32.getTag().equals(t1))
                    || (rbtn22.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals(t1)))
            {
                movesKeeper("U2");
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the  piece down left front
            else if ((wbtn12.getTag().equals("#FFFFFFFF") && gbtn32.getTag().equals(t1))
                    || (gbtn32.getTag().equals("#FFFFFFFF") && rbtn42.getTag().equals(t1))
                    || (rbtn42.getTag().equals("#FFFFFFFF") && wbtn12.getTag().equals(t1)))
            {
                movesKeeper("L'U2L");
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");
            }
            //if the piece is down  on the diagonal of the target piece
            else if ((wbtn22.getTag().equals("#FFFFFFFF") && obtn32.getTag().equals(t1))
                    || (gbtn42.getTag().equals("#FFFFFFFF") && wbtn22.getTag().equals(t1))
                    || (obtn32.getTag().equals("#FFFFFFFF") && gbtn42.getTag().equals(t1)))
            {
                movesKeeper("RU'R'U'");
                //if the white is facing up
                if (ybtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U2RUR'U'R");
                    //if the white is on the right
                else if (obtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("R'U'R");
                    //if the white is on the back
                else
                    movesKeeper("BUB'");

            }
            //if the piece is already in the right place
            else if ((wbtn42.getTag().equals("#FFFFFFFF") && bbtn32.getTag().equals(t1))
                    || (obtn42.getTag().equals("#FFFFFFFF") && wbtn42.getTag().equals(t1))
                    || (bbtn32.getTag().equals("#FFFFFFFF") && obtn42.getTag().equals(t1)))
            {

                if (!(wbtn42.getTag().equals("#FFFFFFFF")))
                {
                    movesKeeper("BUB'U'");
                    //if the white is facing up
                    if (ybtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("R'U2RUR'U'R");
                        //if the white is on the right
                    else if (obtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("R'U'R");
                        //if the white is on the back
                    else
                        movesKeeper("BUB'");

                }
            }

            //find the piece that should be on the front of the target piece and place it in the right position


            //if the piece is right on the top of the target piece
            if ((ybtn12.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals(t2))
                    || (rbtn12.getTag().equals("#FFFFFFFF") && bbtn22.getTag().equals(t2))
                    || (bbtn22.getTag().equals("#FFFFFFFF") && ybtn12.getTag().equals(t2)))
            {
                movesKeeper("U'");

                //if the white is facing up
                if (ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the front
                else if (gbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FUF'");
                    //if the white is facing left
                else
                    movesKeeper("L'U'L");


            }
            //if the piece is on the top right of the target piece
            else if ((ybtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals(t2))
                    || (obtn22.getTag().equals("#FFFFFFFF") && ybtn22.getTag().equals(t2))
                    || (bbtn12.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals(t2)))
            {
                movesKeeper("U2");
                //if the white is facing up
                if (ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the front
                else if (gbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FUF'");
                    //if the white is facing left
                else
                    movesKeeper("L'U'L");
            }
            //if the piece is on the front up left
            else if ((ybtn32.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals(t2))
                    || (gbtn12.getTag().equals("#FFFFFFFF") && rbtn22.getTag().equals(t2))
                    || (rbtn22.getTag().equals("#FFFFFFFF") && ybtn32.getTag().equals(t2)))
            {

                //if the white is facing up
                if (ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the front
                else if (gbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FUF'");
                    //if the white is facing left
                else
                    movesKeeper("L'U'L");
            }

            //if the piece is up right front
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t2))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t2))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t2)))
            {
                movesKeeper("U");
                //if the white is facing up
                if (ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the front
                else if (gbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FUF'");
                    //if the white is facing left
                else
                    movesKeeper("L'U'L");

            }
            //if the piece is down in the right place
            else if((wbtn12.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t2))
                    ||(gbtn32.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t2))
                    || (rbtn42.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t2)))
            {
                if(!wbtn12.getTag().equals("#FFFFFFFF"))
                {

                    movesKeeper("FUF'U'");
                    //if the white is facing up
                    if (ybtn32.getTag().equals("#FFFFFFFF"))
                        movesKeeper("FU2F'U'FUF'");
                        //if the white is on the front
                    else if (gbtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("FUF'");
                        //if the white is facing left
                    else
                        movesKeeper("L'U'L");
                }


            }
            //if the piece down front right
            else if((wbtn22.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t2))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t2))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t2)))
            {
                movesKeeper("RUR'");
                //if the white is facing up
                if (ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the front
                else if (gbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FUF'");
                    //if the white is facing left
                else
                    movesKeeper("L'U'L");

            }

            //find the piece that should be on the diagonal down of the target piece and place it in the right position
            t3=(String)gbtn32.getTag();

            //if the piece is in the right place
            if((wbtn22.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t3))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t3))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t3)))
            {
                if(!wbtn22.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("RUR'U'");
                    //if the white is on the right
                    if(obtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("RUR'");
                        //if the whit is on the front
                    else if(gbtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("F'U'F");
                        //if the white is facing up
                    else
                        movesKeeper("RU2R'U'RUR'");

                }
            }
            //if the piece is up right front
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t3))
                    ||(obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t3))
                    || (gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t3)))
            {
                //if the white is on the right
                if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the whit is on the front
                else if(gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                    //if the white is facing up
                else
                    movesKeeper("RU2R'U'RUR'");
            }
            //if the piece is up front left
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t3))
                    ||(gbtn12.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t3))
                    || (rbtn22.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t3)))
            {
                movesKeeper("U'");
                //if the white is on the right
                if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the whit is on the front
                else if(gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                    //if the white is facing up
                else
                    movesKeeper("RU2R'U'RUR'");

            }
            //if the piece is on top of the target piece
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t3))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t3))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t3)))
            {
                movesKeeper("U2");
                //if the white is on the right
                if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the whit is on the front
                else if(gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                    //if the white is facing up
                else
                    movesKeeper("RU2R'U'RUR'");
            }
            //if the piece is on the right up back
            else if ((ybtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t3))
                    ||(obtn22.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t3))
                    || (bbtn12.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t3)))
            {
                movesKeeper("U");
                //if the white is on the right
                if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the whit is on the front
                else if(gbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U'F");
                    //if the white is facing up
                else
                    movesKeeper("RU2R'U'RUR'");

            }
        }
        else if(wbtn42.getTag().equals("#FFFFFFFF")) //wbtn4 is the target piece
        {
            t1=(String)bbtn32.getTag();
            t2=(String)obtn42.getTag();

            //find the piece that should be to the left of the target piece and place it in the right position

            //if the piece is above the target piece
            if((ybtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t1))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t1))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t1)))
            {
                movesKeeper("U'");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the piece is up back left
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t1))
                    ||(rbtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t1))
                    || (bbtn22.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t1)))
            {
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");
            }
            //if the piece was up front  left
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t1))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t1))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t1)))
            {
                movesKeeper("U");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the other piece is up front right
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t1))
                    ||(gbtn22.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t1))
                    || (obtn12.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t1)))
            {
                movesKeeper("U2");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");
            }
            //if the other piece is in the right place
            else if((wbtn32.getTag().equals("#FFFFFFFF")&&bbtn42.getTag().equals(t1))
                    ||(rbtn32.getTag().equals("#FFFFFFFF")&&wbtn32.getTag().equals(t1))
                    || (bbtn42.getTag().equals("#FFFFFFFF")&&rbtn32.getTag().equals(t1)))
            {
                if(!wbtn32.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("LU'L'");
                    //if the white is facing up
                    if(ybtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("LU2L'U'LUL'");
                        //if the white is on the left
                    else if(rbtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("LUL'");
                        //if the white is on the back
                    else
                        movesKeeper("B'U'B");
                }
            }
            //if the piece is down  front left  of the target piece
            else if((wbtn12.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t1))
                    ||(gbtn32.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t1))
                    || (rbtn42.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t1)))
            {
                movesKeeper("FUF'");

                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");

            }
            //if the piece is down front right of the target piece
            else if((wbtn22.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t1))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t1))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t1)))
            {
                movesKeeper("F'U2F");
                //if the white is facing up
                if(ybtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LU2L'U'LUL'");
                    //if the white is on the left
                else if(rbtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("LUL'");
                    //if the white is on the back
                else
                    movesKeeper("B'U'B");
            }

            //find the piece that should be on the front  of the target piece and place it in the right position

            //if the piece is on the top of the target piece

            if((ybtn22.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t2))
                    ||(obtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t2))
                    || (bbtn12.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t2)))
            {
                movesKeeper("U");
                //if the white is on the top
                if(ybtn42.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U2FUF'U'F");
                    //if the white is on the right
                else if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the white is on the front
                else
                    movesKeeper("F'U'F");

            }
            //if the piece is on the front top right
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t2))
                    ||(gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t2))
                    || (obtn12.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t2)))
            {
                //if the white is on the top
                if(ybtn42.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U2FUF'U'F");
                    //if the white is on the right
                else if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the white is on the front
                else
                    movesKeeper("F'U'F");
            }
            //if the piece is up front left
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t2))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t2))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t2)))
            {
                movesKeeper("U'");
                //if the white is on the top
                if(ybtn42.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U2FUF'U'F");
                    //if the white is on the right
                else if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the white is on the front
                else
                    movesKeeper("F'U'F");

            }
            //if the piece is up back left
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t2))
                    ||(bbtn22.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t2))
                    || (rbtn12.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t2)))
            {
                movesKeeper("U2");
                //if the white is on the top
                if(ybtn42.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U2FUF'U'F");
                    //if the white is on the right
                else if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the white is on the front
                else
                    movesKeeper("F'U'F");

            }
            //if the piece is down in the right place
            else if((wbtn22.getTag().equals("#FFFFFFFF")&&obtn32.getTag().equals(t2))
                    ||(gbtn42.getTag().equals("#FFFFFFFF")&&wbtn22.getTag().equals(t2))
                    || (obtn32.getTag().equals("#FFFFFFFF")&&gbtn42.getTag().equals(t2)))
            {
                if(!wbtn22.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("RUR'U'");
                    //if the white is on the top
                    if(ybtn42.getTag().equals("#FFFFFFFF"))
                        movesKeeper("F'U2FUF'U'F");
                        //if the white is on the right
                    else if(obtn12.getTag().equals("#FFFFFFFF"))
                        movesKeeper("RUR'");
                        //if the white is on the front
                    else
                        movesKeeper("F'U'F");
                }

            }
            //if the piece down front left
            else if((wbtn12.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t2))
                    ||(gbtn32.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t2))
                    || (rbtn42.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t2)))
            {
                movesKeeper("L'U'L");
                //if the white is on the top
                if(ybtn42.getTag().equals("#FFFFFFFF"))
                    movesKeeper("F'U2FUF'U'F");
                    //if the white is on the right
                else if(obtn12.getTag().equals("#FFFFFFFF"))
                    movesKeeper("RUR'");
                    //if the white is on the front
                else
                    movesKeeper("F'U'F");

            }

            //find the piece that should be on the diagonal down of the target piece and place it in the right position
            t3=(String)gbtn42.getTag();
            //if the piece is in the right place
            if((wbtn12.getTag().equals("#FFFFFFFF")&&gbtn32.getTag().equals(t3))
                    ||(rbtn42.getTag().equals("#FFFFFFFF")&&wbtn12.getTag().equals(t3))
                    || (gbtn32.getTag().equals("#FFFFFFFF")&&rbtn42.getTag().equals(t3)))
            {
                if(!wbtn12.getTag().equals("#FFFFFFFF"))
                {
                    movesKeeper("FUF'U'");
                    //if the white is on the top
                    if(ybtn32.getTag().equals("#FFFFFFFF"))
                        movesKeeper("FU2F'U'FUF'");
                        //if the white is on the left
                    else if(rbtn22.getTag().equals("#FFFFFFFF"))
                        movesKeeper("L'U'L");
                        //if the white is on the front
                    else
                        movesKeeper("FUF'");

                }
            }
            //if the piece is up front left of the target piece
            else if((ybtn32.getTag().equals("#FFFFFFFF")&&rbtn22.getTag().equals(t3))
                    ||(rbtn22.getTag().equals("#FFFFFFFF")&&gbtn12.getTag().equals(t3))
                    || (gbtn12.getTag().equals("#FFFFFFFF")&&ybtn32.getTag().equals(t3)))
            {
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }
            //if the piece is up right front of the target piece
            else if((ybtn42.getTag().equals("#FFFFFFFF")&&gbtn22.getTag().equals(t3))
                    ||(gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals(t3))
                    || (obtn12.getTag().equals("#FFFFFFFF")&&ybtn42.getTag().equals(t3)))
            {
                movesKeeper("U");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");



            }
            //if the piece is left up back  of the target piece
            else if((ybtn12.getTag().equals("#FFFFFFFF")&&bbtn22.getTag().equals(t3))
                    ||(rbtn12.getTag().equals("#FFFFFFFF")&&ybtn12.getTag().equals(t3))
                    || (bbtn22.getTag().equals("#FFFFFFFF")&&rbtn12.getTag().equals(t3)))
            {
                movesKeeper("U'");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }
            //if the piece os on the right back up
            else if ((ybtn22.getTag().equals("#FFFFFFFF")&&obtn22.getTag().equals(t3))
                    ||(bbtn12.getTag().equals("#FFFFFFFF")&&ybtn22.getTag().equals(t3))
                    || (obtn22.getTag().equals("#FFFFFFFF")&&bbtn12.getTag().equals(t3)))
            {
                movesKeeper("U2");
                //if the white is on the top
                if(ybtn32.getTag().equals("#FFFFFFFF"))
                    movesKeeper("FU2F'U'FUF'");
                    //if the white is on the left
                else if(rbtn22.getTag().equals("#FFFFFFFF"))
                    movesKeeper("L'U'L");
                    //if the white is on the front
                else
                    movesKeeper("FUF'");

            }



        }//  end of the fouth  case of the four cases

        while (!(((String) ybtn12.getTag()).equals("#FFE8E15A")
                && ((String) ybtn22.getTag()).equals("#FFE8E15A")
                && ((String) ybtn32.getTag()).equals("#FFE8E15A")
                && ((String) ybtn42.getTag()).equals("#FFE8E15A"))) {

            if (numberOfStickersFacingUp("#FFE8E15A") == 1) {
                //case one.1
                if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("R'U'RU'R'U2R ");
                }
                //case one.2//
                else if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("U'R'U'RU'R'U2R ");
                }

                //case one.3
                else if (((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("U2R'U'RU'R'U2R ");
                }
                //case one.4
                else if (((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("UR'U'RU'R'U2R ");
                }
                //case two.1
                else if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("LUL'ULU2L' ");
                }
                //case two.2
                else if (((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U'LUL'ULU2L' ");
                }
                //case two.3
                else if (((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U2LUL'ULU2L' ");
                }
                //case two.4
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("ULUL'ULU2L' ");
                }
            }
            else if (numberOfStickersFacingUp("#FFE8E15A") == 0) {

                //case three.1
                if (((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("R2U2RU2R2 ");
                }
                //case three.2
                else if (((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("UR2U2RU2R2 ");
                }
                //case four.1
                else if (((String) gbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("FRUR'U'RUR'U'F'");
                }
                //case four.2
                else if (((String) obtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("UFRUR'U'RUR'U'F'");
                }
                //case four.3
                else if (((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U2FRUR'U'RUR'U'F'");
                }
                //case four.4
                else if (((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U'FRUR'U'RUR'U'F'");
                }
            }
            else {
                //case five.1
                if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("FRUR'U'F'");

                }
                //case five.2
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U2FRUR'U'F'");

                }
                //case five.3
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("UFRUR'U'F'");

                }
                //case five.4
                else if (((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U'FRUR'U'F'");

                }
                //case six.1
                else if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("RUR'U'R'FRF'");

                }
                //case six.2
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("URUR'U'R'FRF'");

                }
                //case six.3
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U2RUR'U'R'FRF'");

                }
                //case six.4
                else if (((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")) {

                    movesKeeper("U'RUR'U'R'FRF'");

                }
                //case seven.1
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) obtn22.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("FRU'R'U'RUR'F'");

                }
                //case seven.2
                else if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn12.getTag()).equals("#FFE8E15A")
                        && ((String) gbtn22.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("U'FRU'R'U'RUR'F'");
                }
                //case seven.3
                else if (((String) ybtn12.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn42.getTag()).equals("#FFE8E15A")
                        && ((String) rbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("U2FRU'R'U'RUR'F'");

                }
                //case seven
                else if (((String) ybtn22.getTag()).equals("#FFE8E15A")
                        && ((String) ybtn32.getTag()).equals("#FFE8E15A")
                        && ((String) bbtn22.getTag()).equals("#FFE8E15A")
                        && ((String) obtn12.getTag()).equals("#FFE8E15A")) {
                    movesKeeper("UFRU'R'U'RUR'F'");

                }
            }
            if (isSolved())
                return;
        } // end of while

        //case one : there is a bar and its already in the right place
        if (bbtn12.getTag()==bbtn22.getTag()&&bbtn12.getTag()==bbtn32.getTag())
        {
            movesKeeper("L'UR'D2RU'R'D2R2");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;

        }
        //case two : there is a bar but needs to be placed
        if (bbtn12.getTag()==bbtn22.getTag()&&bbtn12.getTag()!=bbtn32.getTag()) {
            if (bbtn12.getTag() == obtn32.getTag())
                movesKeeper("D");
            else if (bbtn12.getTag() == gbtn32.getTag())
                movesKeeper("D2");
            else if (bbtn12.getTag() == rbtn32.getTag())
                movesKeeper("D'");
            if (isSolved())
                return;
            movesKeeper("L'UR'D2RU'R'D2R2");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;
        }

        if (rbtn12.getTag() == rbtn22.getTag()) {
            if (rbtn12.getTag() == rbtn32.getTag())
                movesKeeper("UD'");
            else if (rbtn12.getTag() == bbtn32.getTag())
                movesKeeper("U");
            else if (rbtn12.getTag() == obtn32.getTag())
                movesKeeper("UD");
            else
                movesKeeper("UD2");
            if (isSolved())
                return;
            movesKeeper("L'UR'D2RU'R'D2R2");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;
        }
        else if (obtn12.getTag() == obtn22.getTag()) {
            if (obtn12.getTag() == obtn32.getTag())
                movesKeeper("U'D");
            else if (obtn12.getTag() == bbtn32.getTag())
                movesKeeper("U'");
            else if (obtn12.getTag() == gbtn32.getTag())
                movesKeeper("U'D2");
            else
                movesKeeper("U'D'");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;
            movesKeeper("L'UR'D2RU'R'D2R2");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;
        }
        else if (gbtn12.getTag() == gbtn22.getTag()) {
            if (gbtn12.getTag() == gbtn32.getTag())
                movesKeeper("U2D2");
            else if (gbtn12.getTag() == bbtn32.getTag())
                movesKeeper("U2");
            else if (gbtn12.getTag() == rbtn32.getTag())
                movesKeeper("U2D'");
            else
                movesKeeper("U2D");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;
            movesKeeper("L'UR'D2RU'R'D2R2");
            if (isSolved())
                return;
            if(oneMoveCheck())
                return;

// case 3 when there is no bar
        }
        else {
            movesKeeper("L'UR'D2RU'R'D2R2");
            if(oneMoveCheck())
                return;
            movesKeeper("R'L");

            if (rbtn12.getTag().equals(rbtn22.getTag()) ){
                if (rbtn12.getTag() == rbtn32.getTag())
                    movesKeeper("UD'");
                else if (rbtn12.getTag() == bbtn32.getTag())
                    movesKeeper("U");
                else if (rbtn12.getTag() == obtn32.getTag())
                    movesKeeper("UD");
                else
                    movesKeeper("UD2");

                if (isSolved())
                    return;
                movesKeeper("L'UR'D2RU'R'D2R2");
                if (isSolved())
                    return;

            } else if (obtn12.getTag() == obtn22.getTag()) {
                if (obtn12.getTag() == obtn32.getTag())
                    movesKeeper("U'D");
                else if (obtn12.getTag() == bbtn32.getTag())
                    movesKeeper("U'");
                else if (obtn12.getTag() == gbtn32.getTag())
                    movesKeeper("U'D2");
                else
                    movesKeeper("U'D'");
                if (isSolved())
                    return;
                if(oneMoveCheck())
                    return;
                movesKeeper("L'UR'D2RU'R'D2R2");
                if (isSolved())
                    return;
                if(oneMoveCheck())
                    return;

            } else if (gbtn12.getTag() == gbtn22.getTag()) {
                if (gbtn12.getTag() == gbtn32.getTag())
                    movesKeeper("U2D2");
                else if (gbtn12.getTag() == bbtn32.getTag())
                    movesKeeper("U2");
                else if (gbtn12.getTag() == rbtn32.getTag())
                    movesKeeper("U2D'");
                else
                    movesKeeper("U2D");
                if (isSolved())
                    return;
                if(oneMoveCheck())
                    return;
                movesKeeper("L'UR'D2RU'R'D2R2");
                if (isSolved())
                    return;
                if(oneMoveCheck())
                    return;

            }
            else {
                movesKeeper("L'UR'D2RU'R'D2R2");
                if(oneMoveCheck())
                    return;
                return;
            }

        }
        if(oneMoveCheck())
            return;


    }

          /*  public int Numberofstickersfacingup(String hex){
                    arr.clear();
                    int count=0;


                    if(ybtn1.getTag()==hex) {
                            count++;
                            arr.add(ybtn1);
                    }
                    if(ybtn2.getTag()==hex) {
                            count++;
                            arr.add(ybtn2);
                    }
                    if(ybtn3.getTag()==hex) {
                            count++;
                            arr.add(ybtn3);

                    }
                    if(ybtn4.getTag()==hex) {
                            count++;
                            arr.add(ybtn4);
                    }

                    return count;
            }*/

    public void TurnRightCounterClockwise3() {
        temp = (String) ybtn22.getTag();
        temp2 = (String) ybtn42.getTag();
        tempface = (String) obtn12.getTag();

        ybtn42.setBackgroundColor(Color.parseColor((String) bbtn12.getTag()));
        ybtn42.setTag(bbtn12.getTag());
        ybtn22.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));
        ybtn22.setTag(bbtn32.getTag());

        bbtn12.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        bbtn12.setTag(wbtn42.getTag());
        bbtn32.setBackgroundColor(Color.parseColor((String) wbtn22.getTag()));
        bbtn32.setTag(wbtn22.getTag());

        wbtn42.setBackgroundColor(Color.parseColor((String) gbtn42.getTag()));
        wbtn42.setTag(gbtn42.getTag());
        wbtn22.setBackgroundColor(Color.parseColor((String) gbtn22.getTag()));
        wbtn22.setTag(gbtn22.getTag());

        gbtn42.setBackgroundColor(Color.parseColor(temp2));
        gbtn42.setTag(temp2);
        gbtn22.setBackgroundColor(Color.parseColor(temp));
        gbtn22.setTag(temp);

        //turn the right face counter clockwise
        obtn12.setBackgroundColor(Color.parseColor((String) obtn22.getTag()));
        obtn12.setTag(obtn22.getTag());
        obtn22.setBackgroundColor(Color.parseColor((String) obtn42.getTag()));
        obtn22.setTag(obtn42.getTag());
        obtn42.setBackgroundColor(Color.parseColor((String) obtn32.getTag()));
        obtn42.setTag(obtn32.getTag());
        obtn32.setBackgroundColor(Color.parseColor(tempface));
        obtn32.setTag(tempface);
    }

    public void TurnLeftCounterClockwise3() {
        temp = (String) ybtn12.getTag();
        temp2 = (String) ybtn32.getTag();
        tempface = (String) rbtn12.getTag();

        ybtn32.setBackgroundColor(Color.parseColor((String) gbtn32.getTag()));
        ybtn32.setTag(gbtn32.getTag());
        ybtn12.setBackgroundColor(Color.parseColor((String) gbtn12.getTag()));
        ybtn12.setTag(gbtn12.getTag());

        gbtn12.setBackgroundColor(Color.parseColor((String) wbtn12.getTag()));
        gbtn12.setTag(wbtn12.getTag());
        gbtn32.setBackgroundColor(Color.parseColor((String) wbtn32.getTag()));
        gbtn32.setTag(wbtn32.getTag());

        wbtn32.setBackgroundColor(Color.parseColor((String) bbtn22.getTag()));
        wbtn32.setTag(bbtn22.getTag());
        wbtn12.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));
        wbtn12.setTag(bbtn42.getTag());

        bbtn22.setBackgroundColor(Color.parseColor(temp2));
        bbtn22.setTag(temp2);
        bbtn42.setBackgroundColor(Color.parseColor(temp));
        bbtn42.setTag(temp);

        //turn the left face counter clockwise
        rbtn12.setBackgroundColor(Color.parseColor((String) rbtn22.getTag()));
        rbtn12.setTag(rbtn22.getTag());
        rbtn22.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));
        rbtn22.setTag(rbtn42.getTag());
        rbtn42.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));
        rbtn42.setTag(rbtn32.getTag());
        rbtn32.setBackgroundColor(Color.parseColor(tempface));
        rbtn32.setTag(tempface);

    }

    public void TurnFrontCounterClockwise3() {
        temp = (String) ybtn32.getTag();
        temp2 = (String) ybtn42.getTag();
        tempface = (String) gbtn12.getTag();

        ybtn32.setBackgroundColor(Color.parseColor((String) obtn12.getTag()));
        ybtn32.setTag(obtn12.getTag());
        ybtn42.setBackgroundColor(Color.parseColor((String) obtn32.getTag()));
        ybtn42.setTag(obtn32.getTag());

        obtn12.setBackgroundColor(Color.parseColor((String) wbtn22.getTag()));
        obtn12.setTag(wbtn22.getTag());
        obtn32.setBackgroundColor(Color.parseColor((String) wbtn12.getTag()));
        obtn32.setTag(wbtn12.getTag());

        wbtn22.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));
        wbtn22.setTag(rbtn42.getTag());
        wbtn12.setBackgroundColor(Color.parseColor((String) rbtn22.getTag()));
        wbtn12.setTag(rbtn22.getTag());

        rbtn22.setBackgroundColor(Color.parseColor(temp2));
        rbtn22.setTag(temp2);
        rbtn42.setBackgroundColor(Color.parseColor(temp));
        rbtn42.setTag(temp);

        //turn the front face counter clockwise
        gbtn12.setBackgroundColor(Color.parseColor((String) gbtn22.getTag()));
        gbtn12.setTag(gbtn22.getTag());
        gbtn22.setBackgroundColor(Color.parseColor((String) gbtn42.getTag()));
        gbtn22.setTag(gbtn42.getTag());
        gbtn42.setBackgroundColor(Color.parseColor((String) gbtn32.getTag()));
        gbtn42.setTag(gbtn32.getTag());
        gbtn32.setBackgroundColor(Color.parseColor(tempface));
        gbtn32.setTag(tempface);

    }

    public void TurnBackCounterClockwise3() {
        temp = (String) ybtn12.getTag();
        temp2 = (String) ybtn22.getTag();
        tempface = (String) bbtn12.getTag();

        ybtn22.setBackgroundColor(Color.parseColor((String) rbtn12.getTag()));
        ybtn22.setTag(rbtn12.getTag());
        ybtn12.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));
        ybtn12.setTag(rbtn32.getTag());

        rbtn12.setBackgroundColor(Color.parseColor((String) wbtn32.getTag()));
        rbtn12.setTag(wbtn32.getTag());
        rbtn32.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        rbtn32.setTag(wbtn42.getTag());

        wbtn32.setBackgroundColor(Color.parseColor((String) obtn42.getTag()));
        wbtn32.setTag(obtn42.getTag());
        wbtn42.setBackgroundColor(Color.parseColor((String) obtn22.getTag()));
        wbtn42.setTag(obtn22.getTag());

        obtn22.setBackgroundColor(Color.parseColor(temp));
        obtn22.setTag(temp);
        obtn42.setBackgroundColor(Color.parseColor(temp2));
        obtn42.setTag(temp2);

        //turn the back face counter clockwise
        bbtn12.setBackgroundColor(Color.parseColor((String) bbtn22.getTag()));
        bbtn12.setTag(bbtn22.getTag());
        bbtn22.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));
        bbtn22.setTag(bbtn42.getTag());
        bbtn42.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));
        bbtn42.setTag(bbtn32.getTag());
        bbtn32.setBackgroundColor(Color.parseColor(tempface));
        bbtn32.setTag(tempface);


    }

    public void TurnUpCounterClockwise3() {
        temp = (String) gbtn12.getTag();
        temp2 = (String) gbtn22.getTag();
        tempface = (String) ybtn12.getTag();

        gbtn12.setBackgroundColor(Color.parseColor((String) rbtn12.getTag()));
        gbtn12.setTag(rbtn12.getTag());
        gbtn22.setBackgroundColor(Color.parseColor((String) rbtn22.getTag()));
        gbtn22.setTag(rbtn22.getTag());


        rbtn12.setBackgroundColor(Color.parseColor((String) bbtn12.getTag()));
        rbtn12.setTag(bbtn12.getTag());
        rbtn22.setBackgroundColor(Color.parseColor((String) bbtn22.getTag()));
        rbtn22.setTag(bbtn22.getTag());

        bbtn12.setBackgroundColor(Color.parseColor((String) obtn12.getTag()));
        bbtn12.setTag(obtn12.getTag());
        bbtn22.setBackgroundColor(Color.parseColor((String) obtn22.getTag()));
        bbtn22.setTag(obtn22.getTag());

        obtn12.setBackgroundColor(Color.parseColor(temp));
        obtn12.setTag(temp);
        obtn22.setBackgroundColor(Color.parseColor(temp2));
        obtn22.setTag(temp2);

        //turn the up face counter clockwise
        ybtn12.setBackgroundColor(Color.parseColor((String) ybtn22.getTag()));
        ybtn12.setTag(ybtn22.getTag());
        ybtn22.setBackgroundColor(Color.parseColor((String) ybtn42.getTag()));
        ybtn22.setTag(ybtn42.getTag());
        ybtn42.setBackgroundColor(Color.parseColor((String) ybtn32.getTag()));
        ybtn42.setTag(ybtn32.getTag());
        ybtn32.setBackgroundColor(Color.parseColor(tempface));
        ybtn32.setTag(tempface);

    }

    public void TurnDownCounterClockwise3() {
        temp = (String) gbtn32.getTag();
        temp2 = (String) gbtn42.getTag();
        tempface = (String) wbtn12.getTag();

        gbtn32.setBackgroundColor(Color.parseColor((String) obtn32.getTag()));
        gbtn32.setTag(obtn32.getTag());
        gbtn42.setBackgroundColor(Color.parseColor((String) obtn42.getTag()));
        gbtn42.setTag(obtn42.getTag());


        obtn32.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));
        obtn32.setTag(bbtn32.getTag());
        obtn42.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));
        obtn42.setTag(bbtn42.getTag());

        bbtn32.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));
        bbtn32.setTag(rbtn32.getTag());
        bbtn42.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));
        bbtn42.setTag(rbtn42.getTag());

        rbtn32.setBackgroundColor(Color.parseColor(temp));
        rbtn32.setTag(temp);
        rbtn42.setBackgroundColor(Color.parseColor(temp2));
        rbtn42.setTag(temp2);

        //turn the down face counter clockwise
        wbtn12.setBackgroundColor(Color.parseColor((String) wbtn22.getTag()));
        wbtn12.setTag(wbtn22.getTag());
        wbtn22.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        wbtn22.setTag(wbtn42.getTag());
        wbtn42.setBackgroundColor(Color.parseColor((String) wbtn32.getTag()));
        wbtn42.setTag(wbtn32.getTag());
        wbtn32.setBackgroundColor(Color.parseColor(tempface));
        wbtn32.setTag(tempface);

    }

    public void TurnRightClockwise3() {
        temp = (String) gbtn22.getTag();
        temp2 = (String) gbtn42.getTag();

        gbtn22.setTag((String) wbtn22.getTag());
        gbtn22.setBackgroundColor(Color.parseColor((String) gbtn22.getTag()));
        gbtn42.setTag((String) wbtn42.getTag());
        gbtn42.setBackgroundColor(Color.parseColor((String) gbtn42.getTag()));

        wbtn42.setTag((String) bbtn12.getTag());
        wbtn42.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        wbtn22.setTag((String) bbtn32.getTag());
        wbtn22.setBackgroundColor(Color.parseColor((String) wbtn22.getTag()));

        bbtn12.setTag((String) ybtn42.getTag());
        bbtn12.setBackgroundColor(Color.parseColor((String) bbtn12.getTag()));
        bbtn32.setTag((String) ybtn22.getTag());
        bbtn32.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));

        ybtn22.setTag(temp);
        ybtn22.setBackgroundColor(Color.parseColor((temp)));
        ybtn42.setTag(temp2);
        ybtn42.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) obtn12.getTag();
        obtn12.setTag((String) obtn32.getTag());
        obtn12.setBackgroundColor(Color.parseColor((String) obtn12.getTag()));
        obtn32.setTag((String) obtn42.getTag());
        obtn32.setBackgroundColor(Color.parseColor((String) obtn32.getTag()));
        obtn42.setTag((String) obtn22.getTag());
        obtn42.setBackgroundColor(Color.parseColor((String) obtn42.getTag()));
        obtn22.setTag(tempface);
        obtn22.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnLeftClockwise3() {
        temp = (String) gbtn12.getTag();
        temp2 = (String) gbtn32.getTag();

        gbtn12.setTag((String) ybtn12.getTag());
        gbtn12.setBackgroundColor(Color.parseColor((String) gbtn12.getTag()));
        gbtn32.setTag((String) ybtn32.getTag());
        gbtn32.setBackgroundColor(Color.parseColor((String) gbtn32.getTag()));

        ybtn32.setTag((String) bbtn22.getTag());
        ybtn32.setBackgroundColor(Color.parseColor((String) ybtn32.getTag()));
        ybtn12.setTag((String) bbtn42.getTag());
        ybtn12.setBackgroundColor(Color.parseColor((String) ybtn12.getTag()));

        bbtn22.setTag((String) wbtn32.getTag());
        bbtn22.setBackgroundColor(Color.parseColor((String) bbtn22.getTag()));
        bbtn42.setTag((String) wbtn12.getTag());
        bbtn42.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));

        wbtn12.setTag(temp);
        wbtn12.setBackgroundColor(Color.parseColor((temp)));
        wbtn32.setTag(temp2);
        wbtn32.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) rbtn12.getTag();
        rbtn12.setTag((String) rbtn32.getTag());
        rbtn12.setBackgroundColor(Color.parseColor((String) rbtn12.getTag()));
        rbtn32.setTag((String) rbtn42.getTag());
        rbtn32.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));
        rbtn42.setTag((String) rbtn22.getTag());
        rbtn42.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));
        rbtn22.setTag(tempface);
        rbtn22.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnUpClockwise3() {
        temp = (String) gbtn12.getTag();
        temp2 = (String) gbtn22.getTag();
        gbtn12.setTag((String) obtn12.getTag());
        gbtn12.setBackgroundColor(Color.parseColor((String) gbtn12.getTag()));
        gbtn22.setTag((String) obtn22.getTag());
        gbtn22.setBackgroundColor(Color.parseColor((String) gbtn22.getTag()));

        obtn12.setTag((String) bbtn12.getTag());
        obtn12.setBackgroundColor(Color.parseColor((String) obtn12.getTag()));
        obtn22.setTag((String) bbtn22.getTag());
        obtn22.setBackgroundColor(Color.parseColor((String) obtn22.getTag()));

        bbtn12.setTag((String) rbtn12.getTag());
        bbtn12.setBackgroundColor(Color.parseColor((String) bbtn12.getTag()));
        bbtn22.setTag((String) rbtn22.getTag());
        bbtn22.setBackgroundColor(Color.parseColor((String) bbtn22.getTag()));

        rbtn12.setTag(temp);
        rbtn12.setBackgroundColor(Color.parseColor((temp)));
        rbtn22.setTag(temp2);
        rbtn22.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) ybtn12.getTag();
        ybtn12.setTag((String) ybtn32.getTag());
        ybtn12.setBackgroundColor(Color.parseColor((String) ybtn12.getTag()));
        ybtn32.setTag((String) ybtn42.getTag());
        ybtn32.setBackgroundColor(Color.parseColor((String) ybtn32.getTag()));
        ybtn42.setTag((String) ybtn22.getTag());
        ybtn42.setBackgroundColor(Color.parseColor((String) ybtn42.getTag()));
        ybtn22.setTag(tempface);
        ybtn22.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnDownClockwise3() {
        temp = (String) gbtn32.getTag();
        temp2 = (String) gbtn42.getTag();

        gbtn32.setTag(rbtn32.getTag());
        gbtn32.setBackgroundColor(Color.parseColor((String) gbtn32.getTag()));
        gbtn42.setTag(rbtn42.getTag());
        gbtn42.setBackgroundColor(Color.parseColor((String) gbtn42.getTag()));

        rbtn32.setTag(bbtn32.getTag());
        rbtn32.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));
        rbtn42.setTag(bbtn42.getTag());
        rbtn42.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));

        bbtn32.setTag(obtn32.getTag());
        bbtn32.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));
        bbtn42.setTag(obtn42.getTag());
        bbtn42.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));

        obtn32.setTag(temp);
        obtn32.setBackgroundColor(Color.parseColor((temp)));
        obtn42.setTag(temp2);
        obtn42.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) wbtn12.getTag();
        wbtn12.setTag(wbtn32.getTag());
        wbtn12.setBackgroundColor(Color.parseColor((String) wbtn12.getTag()));
        wbtn32.setTag(wbtn42.getTag());
        wbtn32.setBackgroundColor(Color.parseColor((String) wbtn32.getTag()));
        wbtn42.setTag(wbtn22.getTag());
        wbtn42.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        wbtn22.setTag(tempface);
        wbtn22.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void TurnFrontClockwise3() {
        temp = (String) ybtn32.getTag();
        temp2 = (String) ybtn42.getTag();

        ybtn32.setTag((String) rbtn42.getTag());
        ybtn32.setBackgroundColor(Color.parseColor((String) ybtn32.getTag()));
        ybtn42.setTag((String) rbtn22.getTag());
        ybtn42.setBackgroundColor(Color.parseColor((String) ybtn42.getTag()));

        rbtn22.setTag((String) wbtn12.getTag());
        rbtn22.setBackgroundColor(Color.parseColor((String) rbtn22.getTag()));
        rbtn42.setTag((String) wbtn22.getTag());
        rbtn42.setBackgroundColor(Color.parseColor((String) rbtn42.getTag()));

        wbtn12.setTag((String) obtn32.getTag());
        wbtn12.setBackgroundColor(Color.parseColor((String) wbtn12.getTag()));
        wbtn22.setTag((String) obtn12.getTag());
        wbtn22.setBackgroundColor(Color.parseColor((String) wbtn22.getTag()));

        obtn12.setTag(temp);
        obtn12.setBackgroundColor(Color.parseColor((String) obtn12.getTag()));
        obtn32.setTag(temp2);
        obtn32.setBackgroundColor(Color.parseColor((String) obtn32.getTag()));

        tempface = (String) gbtn12.getTag();
        gbtn12.setTag((String) gbtn32.getTag());
        gbtn12.setBackgroundColor(Color.parseColor((String) gbtn12.getTag()));
        gbtn32.setTag((String) gbtn42.getTag());
        gbtn32.setBackgroundColor(Color.parseColor((String) gbtn32.getTag()));
        gbtn42.setTag((String) gbtn22.getTag());
        gbtn42.setBackgroundColor(Color.parseColor((String) gbtn42.getTag()));
        gbtn22.setTag(tempface);
        gbtn22.setBackgroundColor(Color.parseColor((tempface)));


    }

    public void TurnBackClockwise3() {
        temp = (String) ybtn12.getTag();
        temp2 = (String) ybtn22.getTag();

        ybtn12.setTag((String) obtn22.getTag());
        ybtn12.setBackgroundColor(Color.parseColor((String) ybtn12.getTag()));
        ybtn22.setTag((String) obtn42.getTag());
        ybtn22.setBackgroundColor(Color.parseColor((String) ybtn22.getTag()));

        obtn22.setTag((String) wbtn42.getTag());
        obtn22.setBackgroundColor(Color.parseColor((String) obtn22.getTag()));
        obtn42.setTag((String) wbtn32.getTag());
        obtn42.setBackgroundColor(Color.parseColor((String) obtn42.getTag()));

        wbtn42.setTag((String) rbtn32.getTag());
        wbtn42.setBackgroundColor(Color.parseColor((String) wbtn42.getTag()));
        wbtn32.setTag((String) rbtn12.getTag());
        wbtn32.setBackgroundColor(Color.parseColor((String) wbtn32.getTag()));

        rbtn12.setTag(temp2);
        rbtn12.setBackgroundColor(Color.parseColor((String) rbtn12.getTag()));
        rbtn32.setTag(temp);
        rbtn32.setBackgroundColor(Color.parseColor((String) rbtn32.getTag()));

        tempface = (String) bbtn12.getTag();
        bbtn12.setTag((String) bbtn32.getTag());
        bbtn12.setBackgroundColor(Color.parseColor((String) bbtn12.getTag()));
        bbtn32.setTag((String) bbtn42.getTag());
        bbtn32.setBackgroundColor(Color.parseColor((String) bbtn32.getTag()));
        bbtn42.setTag((String) bbtn22.getTag());
        bbtn42.setBackgroundColor(Color.parseColor((String) bbtn42.getTag()));
        bbtn22.setTag(tempface);
        bbtn22.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void TurnRightCounterClockwise() {
        temp = (String) ybtn2.getTag();
        temp2 = (String) ybtn4.getTag();
        tempface = (String) obtn1.getTag();

        ybtn4.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        ybtn4.setTag(bbtn1.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        ybtn2.setTag(bbtn3.getTag());

        bbtn1.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        bbtn1.setTag(wbtn4.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        bbtn3.setTag(wbtn2.getTag());

        wbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        wbtn4.setTag(gbtn4.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        wbtn2.setTag(gbtn2.getTag());

        gbtn4.setBackgroundColor(Color.parseColor(temp2));
        gbtn4.setTag(temp2);
        gbtn2.setBackgroundColor(Color.parseColor(temp));
        gbtn2.setTag(temp);

        //turn the right face counter clockwise
        obtn1.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        obtn1.setTag(obtn2.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        obtn2.setTag(obtn4.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        obtn4.setTag(obtn3.getTag());
        obtn3.setBackgroundColor(Color.parseColor(tempface));
        obtn3.setTag(tempface);
    }

    public void TurnLeftCounterClockwise() {
        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn3.getTag();
        tempface = (String) rbtn1.getTag();

        ybtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        ybtn3.setTag(gbtn3.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        ybtn1.setTag(gbtn1.getTag());

        gbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        gbtn1.setTag(wbtn1.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        gbtn3.setTag(wbtn3.getTag());

        wbtn3.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        wbtn3.setTag(bbtn2.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        wbtn1.setTag(bbtn4.getTag());

        bbtn2.setBackgroundColor(Color.parseColor(temp2));
        bbtn2.setTag(temp2);
        bbtn4.setBackgroundColor(Color.parseColor(temp));
        bbtn4.setTag(temp);

        //turn the left face counter clockwise
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        rbtn1.setTag(rbtn2.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        rbtn2.setTag(rbtn4.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag(rbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor(tempface));
        rbtn3.setTag(tempface);

    }

    public void TurnFrontCounterClockwise() {
        temp = (String) ybtn3.getTag();
        temp2 = (String) ybtn4.getTag();
        tempface = (String) gbtn1.getTag();

        ybtn3.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        ybtn3.setTag(obtn1.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        ybtn4.setTag(obtn3.getTag());

        obtn1.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        obtn1.setTag(wbtn2.getTag());
        obtn3.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        obtn3.setTag(wbtn1.getTag());

        wbtn2.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        wbtn2.setTag(rbtn4.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        wbtn1.setTag(rbtn2.getTag());

        rbtn2.setBackgroundColor(Color.parseColor(temp2));
        rbtn2.setTag(temp2);
        rbtn4.setBackgroundColor(Color.parseColor(temp));
        rbtn4.setTag(temp);

        //turn the front face counter clockwise
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        gbtn1.setTag(gbtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        gbtn2.setTag(gbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag(gbtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor(tempface));
        gbtn3.setTag(tempface);

    }

    public void TurnBackCounterClockwise() {
        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn2.getTag();
        tempface = (String) bbtn1.getTag();

        ybtn2.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        ybtn2.setTag(rbtn1.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        ybtn1.setTag(rbtn3.getTag());

        rbtn1.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        rbtn1.setTag(wbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        rbtn3.setTag(wbtn4.getTag());

        wbtn3.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        wbtn3.setTag(obtn4.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        wbtn4.setTag(obtn2.getTag());

        obtn2.setBackgroundColor(Color.parseColor(temp));
        obtn2.setTag(temp);
        obtn4.setBackgroundColor(Color.parseColor(temp2));
        obtn4.setTag(temp2);

        //turn the back face counter clockwise
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        bbtn1.setTag(bbtn2.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        bbtn2.setTag(bbtn4.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag(bbtn3.getTag());
        bbtn3.setBackgroundColor(Color.parseColor(tempface));
        bbtn3.setTag(tempface);


    }

    public void TurnUpCounterClockwise() {
        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn2.getTag();
        tempface = (String) ybtn1.getTag();

        gbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        gbtn1.setTag(rbtn1.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        gbtn2.setTag(rbtn2.getTag());


        rbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        rbtn1.setTag(bbtn1.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        rbtn2.setTag(bbtn2.getTag());

        bbtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        bbtn1.setTag(obtn1.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        bbtn2.setTag(obtn2.getTag());

        obtn1.setBackgroundColor(Color.parseColor(temp));
        obtn1.setTag(temp);
        obtn2.setBackgroundColor(Color.parseColor(temp2));
        obtn2.setTag(temp2);

        //turn the up face counter clockwise
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn2.getTag()));
        ybtn1.setTag(ybtn2.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));
        ybtn2.setTag(ybtn4.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag(ybtn3.getTag());
        ybtn3.setBackgroundColor(Color.parseColor(tempface));
        ybtn3.setTag(tempface);

    }

    public void TurnDownCounterClockwise() {
        temp = (String) gbtn3.getTag();
        temp2 = (String) gbtn4.getTag();
        tempface = (String) wbtn1.getTag();

        gbtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        gbtn3.setTag(obtn3.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        gbtn4.setTag(obtn4.getTag());


        obtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        obtn3.setTag(bbtn3.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        obtn4.setTag(bbtn4.getTag());

        bbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        bbtn3.setTag(rbtn3.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        bbtn4.setTag(rbtn4.getTag());

        rbtn3.setBackgroundColor(Color.parseColor(temp));
        rbtn3.setTag(temp);
        rbtn4.setBackgroundColor(Color.parseColor(temp2));
        rbtn4.setTag(temp2);

        //turn the down face counter clockwise
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        wbtn1.setTag(wbtn2.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag(wbtn4.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        wbtn4.setTag(wbtn3.getTag());
        wbtn3.setBackgroundColor(Color.parseColor(tempface));
        wbtn3.setTag(tempface);

    }

    public void TurnRightClockwise() {
        temp = (String) gbtn2.getTag();
        temp2 = (String) gbtn4.getTag();

        gbtn2.setTag((String) wbtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        gbtn4.setTag((String) wbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));

        wbtn4.setTag((String) bbtn1.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag((String) bbtn3.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));

        bbtn1.setTag((String) ybtn4.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn3.setTag((String) ybtn2.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));

        ybtn2.setTag(temp);
        ybtn2.setBackgroundColor(Color.parseColor((temp)));
        ybtn4.setTag(temp2);
        ybtn4.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) obtn1.getTag();
        obtn1.setTag((String) obtn3.getTag());
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn3.setTag((String) obtn4.getTag());
        obtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        obtn4.setTag((String) obtn2.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        obtn2.setTag(tempface);
        obtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnLeftClockwise() {
        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn3.getTag();

        gbtn1.setTag((String) ybtn1.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn3.setTag((String) ybtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));

        ybtn3.setTag((String) bbtn2.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn1.setTag((String) bbtn4.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));

        bbtn2.setTag((String) wbtn3.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        bbtn4.setTag((String) wbtn1.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));

        wbtn1.setTag(temp);
        wbtn1.setBackgroundColor(Color.parseColor((temp)));
        wbtn3.setTag(temp2);
        wbtn3.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) rbtn1.getTag();
        rbtn1.setTag((String) rbtn3.getTag());
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        rbtn3.setTag((String) rbtn4.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag((String) rbtn2.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        rbtn2.setTag(tempface);
        rbtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnUpClockwise() {
        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn2.getTag();
        gbtn1.setTag((String) obtn1.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn2.setTag((String) obtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));

        obtn1.setTag((String) bbtn1.getTag());
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn2.setTag((String) bbtn2.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));

        bbtn1.setTag((String) rbtn1.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn2.setTag((String) rbtn2.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));

        rbtn1.setTag(temp);
        rbtn1.setBackgroundColor(Color.parseColor((temp)));
        rbtn2.setTag(temp2);
        rbtn2.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) ybtn1.getTag();
        ybtn1.setTag((String) ybtn3.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));
        ybtn3.setTag((String) ybtn4.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag((String) ybtn2.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));
        ybtn2.setTag(tempface);
        ybtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnDownClockwise() {
        temp = (String) gbtn3.getTag();
        temp2 = (String) gbtn4.getTag();

        gbtn3.setTag(rbtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag(rbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));

        rbtn3.setTag(bbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag(bbtn4.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));

        bbtn3.setTag(obtn3.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag(obtn4.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));

        obtn3.setTag(temp);
        obtn3.setBackgroundColor(Color.parseColor((temp)));
        obtn4.setTag(temp2);
        obtn4.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) wbtn1.getTag();
        wbtn1.setTag(wbtn3.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        wbtn3.setTag(wbtn4.getTag());
        wbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        wbtn4.setTag(wbtn2.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag(tempface);
        wbtn2.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void TurnFrontClockwise() {
        temp = (String) ybtn3.getTag();
        temp2 = (String) ybtn4.getTag();

        ybtn3.setTag((String) rbtn4.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag((String) rbtn2.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));

        rbtn2.setTag((String) wbtn1.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        rbtn4.setTag((String) wbtn2.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));

        wbtn1.setTag((String) obtn3.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        wbtn2.setTag((String) obtn1.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));

        obtn1.setTag(temp);
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn3.setTag(temp2);
        obtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));

        tempface = (String) gbtn1.getTag();
        gbtn1.setTag((String) gbtn3.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn3.setTag((String) gbtn4.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag((String) gbtn2.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        gbtn2.setTag(tempface);
        gbtn2.setBackgroundColor(Color.parseColor((tempface)));


    }

    public void TurnBackClockwise() {
        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn2.getTag();

        ybtn1.setTag((String) obtn2.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));
        ybtn2.setTag((String) obtn4.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) ybtn2.getTag()));

        obtn2.setTag((String) wbtn4.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        obtn4.setTag((String) wbtn3.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));

        wbtn4.setTag((String) rbtn3.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn3.setTag((String) rbtn1.getTag());
        wbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));

        rbtn1.setTag(temp2);
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        rbtn3.setTag(temp);
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));

        tempface = (String) bbtn1.getTag();
        bbtn1.setTag((String) bbtn3.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn3.setTag((String) bbtn4.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag((String) bbtn2.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        bbtn2.setTag(tempface);
        bbtn2.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void TurnRightCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);
        temp = (String) ybtn2.getTag();
        temp2 = (String) ybtn4.getTag();
        tempface = (String) obtn1.getTag();

        ybtn4.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        ybtn4.setTag(bbtn1.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        ybtn2.setTag(bbtn3.getTag());

        bbtn1.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        bbtn1.setTag(wbtn4.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        bbtn3.setTag(wbtn2.getTag());

        wbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        wbtn4.setTag(gbtn4.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        wbtn2.setTag(gbtn2.getTag());

        gbtn4.setBackgroundColor(Color.parseColor(temp2));
        gbtn4.setTag(temp2);
        gbtn2.setBackgroundColor(Color.parseColor(temp));
        gbtn2.setTag(temp);

        //turn the right face counter clockwise
        obtn1.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        obtn1.setTag(obtn2.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        obtn2.setTag(obtn4.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        obtn4.setTag(obtn3.getTag());
        obtn3.setBackgroundColor(Color.parseColor(tempface));
        obtn3.setTag(tempface);
    }

    public void TurnLeftCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);
        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn3.getTag();
        tempface = (String) rbtn1.getTag();

        ybtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        ybtn3.setTag(gbtn3.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        ybtn1.setTag(gbtn1.getTag());

        gbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        gbtn1.setTag(wbtn1.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        gbtn3.setTag(wbtn3.getTag());

        wbtn3.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        wbtn3.setTag(bbtn2.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        wbtn1.setTag(bbtn4.getTag());

        bbtn2.setBackgroundColor(Color.parseColor(temp2));
        bbtn2.setTag(temp2);
        bbtn4.setBackgroundColor(Color.parseColor(temp));
        bbtn4.setTag(temp);

        //turn the left face counter clockwise
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        rbtn1.setTag(rbtn2.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        rbtn2.setTag(rbtn4.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag(rbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor(tempface));
        rbtn3.setTag(tempface);

    }

    public void TurnFrontCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();

        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) ybtn3.getTag();
        temp2 = (String) ybtn4.getTag();
        tempface = (String) gbtn1.getTag();

        ybtn3.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        ybtn3.setTag(obtn1.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        ybtn4.setTag(obtn3.getTag());

        obtn1.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        obtn1.setTag(wbtn2.getTag());
        obtn3.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        obtn3.setTag(wbtn1.getTag());

        wbtn2.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        wbtn2.setTag(rbtn4.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        wbtn1.setTag(rbtn2.getTag());

        rbtn2.setBackgroundColor(Color.parseColor(temp2));
        rbtn2.setTag(temp2);
        rbtn4.setBackgroundColor(Color.parseColor(temp));
        rbtn4.setTag(temp);

        //turn the front face counter clockwise
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        gbtn1.setTag(gbtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        gbtn2.setTag(gbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag(gbtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor(tempface));
        gbtn3.setTag(tempface);

    }

    public void TurnBackCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn2.getTag();
        tempface = (String) bbtn1.getTag();

        ybtn2.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        ybtn2.setTag(rbtn1.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        ybtn1.setTag(rbtn3.getTag());

        rbtn1.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        rbtn1.setTag(wbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        rbtn3.setTag(wbtn4.getTag());

        wbtn3.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        wbtn3.setTag(obtn4.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        wbtn4.setTag(obtn2.getTag());

        obtn2.setBackgroundColor(Color.parseColor(temp));
        obtn2.setTag(temp);
        obtn4.setBackgroundColor(Color.parseColor(temp2));
        obtn4.setTag(temp2);

        //turn the back face counter clockwise
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        bbtn1.setTag(bbtn2.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        bbtn2.setTag(bbtn4.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag(bbtn3.getTag());
        bbtn3.setBackgroundColor(Color.parseColor(tempface));
        bbtn3.setTag(tempface);


    }

    public void TurnUpCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);
        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn2.getTag();
        tempface = (String) ybtn1.getTag();

        gbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        gbtn1.setTag(rbtn1.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        gbtn2.setTag(rbtn2.getTag());


        rbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        rbtn1.setTag(bbtn1.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        rbtn2.setTag(bbtn2.getTag());

        bbtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        bbtn1.setTag(obtn1.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        bbtn2.setTag(obtn2.getTag());

        obtn1.setBackgroundColor(Color.parseColor(temp));
        obtn1.setTag(temp);
        obtn2.setBackgroundColor(Color.parseColor(temp2));
        obtn2.setTag(temp2);

        //turn the up face counter clockwise
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn2.getTag()));
        ybtn1.setTag(ybtn2.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));
        ybtn2.setTag(ybtn4.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag(ybtn3.getTag());
        ybtn3.setBackgroundColor(Color.parseColor(tempface));
        ybtn3.setTag(tempface);

    }

    public void TurnDownCounterClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) gbtn3.getTag();
        temp2 = (String) gbtn4.getTag();
        tempface = (String) wbtn1.getTag();

        gbtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        gbtn3.setTag(obtn3.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        gbtn4.setTag(obtn4.getTag());


        obtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        obtn3.setTag(bbtn3.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        obtn4.setTag(bbtn4.getTag());

        bbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        bbtn3.setTag(rbtn3.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        bbtn4.setTag(rbtn4.getTag());

        rbtn3.setBackgroundColor(Color.parseColor(temp));
        rbtn3.setTag(temp);
        rbtn4.setBackgroundColor(Color.parseColor(temp2));
        rbtn4.setTag(temp2);

        //turn the down face counter clockwise
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));
        wbtn1.setTag(wbtn2.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag(wbtn4.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        wbtn4.setTag(wbtn3.getTag());
        wbtn3.setBackgroundColor(Color.parseColor(tempface));
        wbtn3.setTag(tempface);

    }

    public void TurnRightClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) gbtn2.getTag();
        temp2 = (String) gbtn4.getTag();

        gbtn2.setTag((String) wbtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));
        gbtn4.setTag((String) wbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));

        wbtn4.setTag((String) bbtn1.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag((String) bbtn3.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));

        bbtn1.setTag((String) ybtn4.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn3.setTag((String) ybtn2.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));

        ybtn2.setTag(temp);
        ybtn2.setBackgroundColor(Color.parseColor((temp)));
        ybtn4.setTag(temp2);
        ybtn4.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) obtn1.getTag();
        obtn1.setTag((String) obtn3.getTag());
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn3.setTag((String) obtn4.getTag());
        obtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));
        obtn4.setTag((String) obtn2.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));
        obtn2.setTag(tempface);
        obtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnLeftClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);

        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");

        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");


        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn3.getTag();

        gbtn1.setTag((String) ybtn1.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn3.setTag((String) ybtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));

        ybtn3.setTag((String) bbtn2.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn1.setTag((String) bbtn4.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));

        bbtn2.setTag((String) wbtn3.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));
        bbtn4.setTag((String) wbtn1.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));

        wbtn1.setTag(temp);
        wbtn1.setBackgroundColor(Color.parseColor((temp)));
        wbtn3.setTag(temp2);
        wbtn3.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) rbtn1.getTag();
        rbtn1.setTag((String) rbtn3.getTag());
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        rbtn3.setTag((String) rbtn4.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag((String) rbtn2.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));
        rbtn2.setTag(tempface);
        rbtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnUpClockwise2(View view)
    {gifImageView.setVisibility(View.GONE);
        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) gbtn1.getTag();
        temp2 = (String) gbtn2.getTag();
        gbtn1.setTag((String) obtn1.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn2.setTag((String) obtn2.getTag());
        gbtn2.setBackgroundColor(Color.parseColor((String) gbtn2.getTag()));

        obtn1.setTag((String) bbtn1.getTag());
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn2.setTag((String) bbtn2.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));

        bbtn1.setTag((String) rbtn1.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn2.setTag((String) rbtn2.getTag());
        bbtn2.setBackgroundColor(Color.parseColor((String) bbtn2.getTag()));

        rbtn1.setTag(temp);
        rbtn1.setBackgroundColor(Color.parseColor((temp)));
        rbtn2.setTag(temp2);
        rbtn2.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) ybtn1.getTag();
        ybtn1.setTag((String) ybtn3.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));
        ybtn3.setTag((String) ybtn4.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag((String) ybtn2.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));
        ybtn2.setTag(tempface);
        ybtn2.setBackgroundColor(Color.parseColor((tempface)));
    }

    public void TurnDownClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);
        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);

        temp = (String) gbtn3.getTag();
        temp2 = (String) gbtn4.getTag();

        gbtn3.setTag(rbtn3.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag(rbtn4.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));

        rbtn3.setTag(bbtn3.getTag());
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));
        rbtn4.setTag(bbtn4.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));

        bbtn3.setTag(obtn3.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag(obtn4.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));

        obtn3.setTag(temp);
        obtn3.setBackgroundColor(Color.parseColor((temp)));
        obtn4.setTag(temp2);
        obtn4.setBackgroundColor(Color.parseColor((temp2)));

        tempface = (String) wbtn1.getTag();
        wbtn1.setTag(wbtn3.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        wbtn3.setTag(wbtn4.getTag());
        wbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));
        wbtn4.setTag(wbtn2.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn2.setTag(tempface);
        wbtn2.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void TurnFrontClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);
        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);
        temp = (String) ybtn3.getTag();
        temp2 = (String) ybtn4.getTag();

        ybtn3.setTag((String) rbtn4.getTag());
        ybtn3.setBackgroundColor(Color.parseColor((String) ybtn3.getTag()));
        ybtn4.setTag((String) rbtn2.getTag());
        ybtn4.setBackgroundColor(Color.parseColor((String) ybtn4.getTag()));

        rbtn2.setTag((String) wbtn1.getTag());
        rbtn2.setBackgroundColor(Color.parseColor((String) rbtn2.getTag()));
        rbtn4.setTag((String) wbtn2.getTag());
        rbtn4.setBackgroundColor(Color.parseColor((String) rbtn4.getTag()));

        wbtn1.setTag((String) obtn3.getTag());
        wbtn1.setBackgroundColor(Color.parseColor((String) wbtn1.getTag()));
        wbtn2.setTag((String) obtn1.getTag());
        wbtn2.setBackgroundColor(Color.parseColor((String) wbtn2.getTag()));

        obtn1.setTag(temp);
        obtn1.setBackgroundColor(Color.parseColor((String) obtn1.getTag()));
        obtn3.setTag(temp2);
        obtn3.setBackgroundColor(Color.parseColor((String) obtn3.getTag()));

        tempface = (String) gbtn1.getTag();
        gbtn1.setTag((String) gbtn3.getTag());
        gbtn1.setBackgroundColor(Color.parseColor((String) gbtn1.getTag()));
        gbtn3.setTag((String) gbtn4.getTag());
        gbtn3.setBackgroundColor(Color.parseColor((String) gbtn3.getTag()));
        gbtn4.setTag((String) gbtn2.getTag());
        gbtn4.setBackgroundColor(Color.parseColor((String) gbtn4.getTag()));
        gbtn2.setTag(tempface);
        gbtn2.setBackgroundColor(Color.parseColor((tempface)));


    }

    public void TurnBackClockwise2(View view) {
        gifImageView.setVisibility(View.GONE);
        soundEffect = MediaPlayer.create(this,R.raw.movecube);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");
        displaymoves2.setText("");
        displaymoves.setText("");
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");



        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);
        temp = (String) ybtn1.getTag();
        temp2 = (String) ybtn2.getTag();

        ybtn1.setTag((String) obtn2.getTag());
        ybtn1.setBackgroundColor(Color.parseColor((String) ybtn1.getTag()));
        ybtn2.setTag((String) obtn4.getTag());
        ybtn2.setBackgroundColor(Color.parseColor((String) ybtn2.getTag()));

        obtn2.setTag((String) wbtn4.getTag());
        obtn2.setBackgroundColor(Color.parseColor((String) obtn2.getTag()));
        obtn4.setTag((String) wbtn3.getTag());
        obtn4.setBackgroundColor(Color.parseColor((String) obtn4.getTag()));

        wbtn4.setTag((String) rbtn3.getTag());
        wbtn4.setBackgroundColor(Color.parseColor((String) wbtn4.getTag()));
        wbtn3.setTag((String) rbtn1.getTag());
        wbtn3.setBackgroundColor(Color.parseColor((String) wbtn3.getTag()));

        rbtn1.setTag(temp2);
        rbtn1.setBackgroundColor(Color.parseColor((String) rbtn1.getTag()));
        rbtn3.setTag(temp);
        rbtn3.setBackgroundColor(Color.parseColor((String) rbtn3.getTag()));

        tempface = (String) bbtn1.getTag();
        bbtn1.setTag((String) bbtn3.getTag());
        bbtn1.setBackgroundColor(Color.parseColor((String) bbtn1.getTag()));
        bbtn3.setTag((String) bbtn4.getTag());
        bbtn3.setBackgroundColor(Color.parseColor((String) bbtn3.getTag()));
        bbtn4.setTag((String) bbtn2.getTag());
        bbtn4.setBackgroundColor(Color.parseColor((String) bbtn4.getTag()));
        bbtn2.setTag(tempface);
        bbtn2.setBackgroundColor(Color.parseColor((tempface)));

    }

    public void movesKeeper(String strIn) { //ex   R U R' U'

        ss += strIn;
        int i = 0;

        while (i < strIn.length()) {
            char char1 = strIn.charAt(i);
            if (i + 1 < strIn.length()) {
                char char2 = strIn.charAt(i + 1);

                if (i + 3 < strIn.length())
                    s = "" + char2 + strIn.charAt(i + 2);
                if (char2 == '\'') {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise3();
                            break;
                        case 'U':
                            TurnUpCounterClockwise3();
                            break;
                        case 'D':
                            TurnDownCounterClockwise3();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise3();
                            break;
                        case 'B':
                            TurnBackCounterClockwise3();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise3();
                            break;
                        default:
                            break;
                    }
                    i += 2;
                } else if (s.equals("\'2")) {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise3();
                            TurnRightCounterClockwise3();
                            break;
                        case 'U':
                            TurnUpCounterClockwise3();
                            TurnUpCounterClockwise3();
                            break;
                        case 'D':
                            TurnDownCounterClockwise3();
                            TurnDownCounterClockwise3();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise3();
                            TurnLeftCounterClockwise3();
                            break;
                        case 'B':
                            TurnBackCounterClockwise3();
                            TurnBackCounterClockwise3();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise3();
                            TurnFrontCounterClockwise3();
                            break;
                        default:
                            break;
                    }
                    i += 3;
                } else if (char2 == '2') {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise3();
                            TurnRightClockwise3();
                            break;
                        case 'U':
                            TurnUpClockwise3();
                            TurnUpClockwise3();
                            break;
                        case 'D':
                            TurnDownClockwise3();
                            TurnDownClockwise3();
                            break;
                        case 'L':
                            TurnLeftClockwise3();
                            TurnLeftClockwise3();
                            break;
                        case 'B':
                            TurnBackClockwise3();
                            TurnBackClockwise3();
                            break;
                        case 'F':
                            TurnFrontClockwise3();
                            TurnFrontClockwise3();
                            break;
                        default:
                            break;
                    }
                    i += 2;
                } else {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise3();
                            break;
                        case 'U':
                            TurnUpClockwise3();
                            break;
                        case 'D':
                            TurnDownClockwise3();
                            break;
                        case 'L':
                            TurnLeftClockwise3();
                            break;
                        case 'B':
                            TurnBackClockwise3();
                            break;
                        case 'F':
                            TurnFrontClockwise3();
                            break;
                        default:
                            break;
                    }
                    i += 1;
                }
            } else {
                switch (char1) {
                    case 'R':
                        TurnRightClockwise3();
                        break;
                    case 'U':
                        TurnUpClockwise3();
                        break;
                    case 'D':
                        TurnDownClockwise3();
                        break;
                    case 'L':
                        TurnLeftClockwise3();
                        break;
                    case 'B':
                        TurnBackClockwise3();
                        break;
                    case 'F':
                        TurnFrontClockwise3();
                        break;
                    default:
                        break;
                }
                i += 1;
            }
        }
    }

    public void scramble(String strIn) { //ex   R U R' U'


        int i = 0;

        while (i < strIn.length()) {
            char char1 = strIn.charAt(i);
            if (i + 1 < strIn.length()) {
                char char2 = strIn.charAt(i + 1);

                if (i + 3 < strIn.length())
                    s = "" + char2 + strIn.charAt(i + 2);
                if (char2 == '\'') {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise();
                            break;
                        case 'U':
                            TurnUpCounterClockwise();
                            break;
                        case 'D':
                            TurnDownCounterClockwise();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise();
                            break;
                        case 'B':
                            TurnBackCounterClockwise();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise();
                            break;
                        default:
                            break;
                    }
                    i += 2;
                } else if (s.equals("\'2")) {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise();
                            TurnRightCounterClockwise();
                            break;
                        case 'U':
                            TurnUpCounterClockwise();
                            TurnUpCounterClockwise();
                            break;
                        case 'D':
                            TurnDownCounterClockwise();
                            TurnDownCounterClockwise();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise();
                            TurnLeftCounterClockwise();
                            break;
                        case 'B':
                            TurnBackCounterClockwise();
                            TurnBackCounterClockwise();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise();
                            TurnFrontCounterClockwise();
                            break;
                        default:
                            break;
                    }
                    i += 3;
                } else if (char2 == '2') {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise();
                            TurnRightClockwise();
                            break;
                        case 'U':
                            TurnUpClockwise();
                            TurnUpClockwise();
                            break;
                        case 'D':
                            TurnDownClockwise();
                            TurnDownClockwise();
                            break;
                        case 'L':
                            TurnLeftClockwise();
                            TurnLeftClockwise();
                            break;
                        case 'B':
                            TurnBackClockwise();
                            TurnBackClockwise();
                            break;
                        case 'F':
                            TurnFrontClockwise();
                            TurnFrontClockwise();
                            break;
                        default:
                            break;
                    }
                    i += 2;
                } else {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise();
                            break;
                        case 'U':
                            TurnUpClockwise();
                            break;
                        case 'D':
                            TurnDownClockwise();
                            break;
                        case 'L':
                            TurnLeftClockwise();
                            break;
                        case 'B':
                            TurnBackClockwise();
                            break;
                        case 'F':
                            TurnFrontClockwise();
                            break;
                        default:
                            break;
                    }
                    i += 1;
                }
            } else {
                switch (char1) {
                    case 'R':
                        TurnRightClockwise();
                        break;
                    case 'U':
                        TurnUpClockwise();
                        break;
                    case 'D':
                        TurnDownClockwise();
                        break;
                    case 'L':
                        TurnLeftClockwise();
                        break;
                    case 'B':
                        TurnBackClockwise();
                        break;
                    case 'F':
                        TurnFrontClockwise();
                        break;
                    default:
                        break;
                }
                i += 1;
            }
        }
    }

    public void scrambleButton(View view) throws InterruptedException {
        gifImageView.setVisibility(View.GONE);
        displaymoves2.setText("Scrambled Moves");
        displaymoves.setText("");
        soundEffect = MediaPlayer.create(this,R.raw.scramblesound);
        soundEffect.start();
        moves.clear();
        textView.setText("");
        secondMove.setText("");
        thirdMove.setText("");

        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");

        progressBar.setProgress(0);
        rightArrow.setImageResource(R.drawable.rightarrow);
        leftArrow.setImageResource(R.drawable.leftarrow);



        Scramble = "";
        for(int i=0; i < 9; i++){
            Random notActuallyARandomNumber =  new Random();
            int index = notActuallyARandomNumber.nextInt(13); //random number from {0,12}
            Scramble += movesArray[index];
            Scramble += " ";
        }

        scrambleDelay(Scramble);
        displaymoves.setText(Scramble);
        startTimer = false;

//
//        while (true )
//        {
//            if(startTimer == false)
//            {
//                timer = System.currentTimeMillis();
//                startTimer = true;
//            }
//
//            if(System.currentTimeMillis()>timer+1000)
//            {
//                break;
//            }
//        }


        { //ex   R U R' U'


            final int[] i = {0};
            Thread t = new Thread();




//                        Thread background = new Thread() {
//                                public void run() {
//                                        try {
//                                                // Thread will sleep for 5 seconds
//                                                Thread.sleep(5*1000);
//
//
//                                        } catch (Exception e) {
//                                        }
//                                }
//                        };
//                        // start thread
//                        background.start();

//        }
        }
    }



    public void scrambleDelay(String strIn) throws InterruptedException { //ex   R U R' U'


        final int[] i = {0};

        while (i[0] < strIn.length()) {


            char char1 = strIn.charAt(i[0]);

            if (i[0] + 1 < strIn.length()) {
                char char2 = strIn.charAt(i[0] + 1);

                if (i[0] + 3 < strIn.length())
                    s = "" + char2 + strIn.charAt(i[0] + 2);
                if (char2 == '\'') {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise();
                            break;
                        case 'U':
                            TurnUpCounterClockwise();
                            break;
                        case 'D':
                            TurnDownCounterClockwise();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise();
                            break;
                        case 'B':
                            TurnBackCounterClockwise();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise();
                            break;
                        default:
                            break;
                    }
                    i[0] += 2;
                } else if (s.equals("\'2")) {
                    switch (char1) {
                        case 'R':
                            TurnRightCounterClockwise();
                            TurnRightCounterClockwise();
                            break;
                        case 'U':
                            TurnUpCounterClockwise();
                            TurnUpCounterClockwise();
                            break;
                        case 'D':
                            TurnDownCounterClockwise();
                            TurnDownCounterClockwise();
                            break;
                        case 'L':
                            TurnLeftCounterClockwise();
                            TurnLeftCounterClockwise();
                            break;
                        case 'B':
                            TurnBackCounterClockwise();
                            TurnBackCounterClockwise();
                            break;
                        case 'F':
                            TurnFrontCounterClockwise();
                            TurnFrontCounterClockwise();
                            break;
                        default:
                            break;
                    }
                    i[0] += 3;
                } else if (char2 == '2') {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise();
                            TurnRightClockwise();
                            break;
                        case 'U':
                            TurnUpClockwise();
                            TurnUpClockwise();
                            break;
                        case 'D':
                            TurnDownClockwise();
                            TurnDownClockwise();
                            break;
                        case 'L':
                            TurnLeftClockwise();
                            TurnLeftClockwise();
                            break;
                        case 'B':
                            TurnBackClockwise();
                            TurnBackClockwise();
                            break;
                        case 'F':
                            TurnFrontClockwise();
                            TurnFrontClockwise();
                            break;
                        default:
                            break;
                    }
                    i[0] += 2;
                } else {
                    switch (char1) {
                        case 'R':
                            TurnRightClockwise();
                            break;
                        case 'U':
                            TurnUpClockwise();
                            break;
                        case 'D':
                            TurnDownClockwise();
                            break;
                        case 'L':
                            TurnLeftClockwise();
                            break;
                        case 'B':
                            TurnBackClockwise();
                            break;
                        case 'F':
                            TurnFrontClockwise();
                            break;
                        default:
                            break;
                    }
                    i[0] += 1;
                }
            } else {
                switch (char1) {
                    case 'R':
                        TurnRightClockwise();
                        break;
                    case 'U':
                        TurnUpClockwise();
                        break;
                    case 'D':
                        TurnDownClockwise();
                        break;
                    case 'L':
                        TurnLeftClockwise();
                        break;
                    case 'B':
                        TurnBackClockwise();
                        break;
                    case 'F':
                        TurnFrontClockwise();
                        break;
                    default:
                        break;
                }
                i[0] += 1;
            }



//            Timer timer = new Timer(1000, new WifiP2pManager.ActionListener() {
//                @Override
//                public void onSuccess() {
//
//                }
//
//                @Override
//                public void onFailure(int i) {
//
//                }
//
//            }
        }
//

//                        Thread background = new Thread() {
//                                public void run() {
//                                        try {
//                                                // Thread will sleep for 5 seconds
//                                                Thread.sleep(5*1000);
//
//
//                                        } catch (Exception e) {
//                                        }
//                                }
//                        };
//                        // start thread
//                        background.start();

//        }
    }

        /*public void GenerateScramble(View view){

        }

        public void print(String str){
                textView.setText(str);
        }*/

    public boolean isSolved() {// to check if the cube is solved
        count1 = 4;
        solvedOrNot = true;
        for (int i = 0; i < 24; i++) {

            if (i % 4 == 0) {
                if (count1 != 4) {
                    solvedOrNot = false;
                    return solvedOrNot;
                }

                count1 = 0;
                temp3 = (String) buttons2[i].getTag();
            }

            if (((String) buttons2[i].getTag()).equals(temp3))
                count1++;
        }
        return solvedOrNot;
    }

    public int numberOfStickersFacingUp(String hex) {

        int count = 0;
        if (((String) ybtn12.getTag()).equals(hex))
            count++;
        if (((String) ybtn22.getTag()).equals(hex))
            count++;
        if (((String) ybtn32.getTag()).equals(hex))
            count++;
        if (((String) ybtn42.getTag()).equals(hex))
            count++;

        return count;
    }

    public int numberOfStickersFacingDown(String hex){
        int count =0;
        if(wbtn12.getTag().equals(hex))
            count++;
        if(wbtn22.getTag().equals(hex))
            count++;
        if(wbtn32.getTag().equals(hex))
            count++;
        if(wbtn42.getTag().equals(hex))
            count++;

        return count;

    }

    public boolean oneMoveCheck(){
        // to check if there is only one move to solve the cube
        TurnRightClockwise3();
        if(isSolved())
        {
            TurnRightCounterClockwise3();
            movesKeeper("R");
            return true;
        }
        else TurnRightCounterClockwise3();

        TurnRightCounterClockwise3();
        if(isSolved())
        {
            TurnRightClockwise3();
            movesKeeper("R'");
            return true;
        }
        else TurnRightClockwise3();

        TurnLeftClockwise3();
        if(isSolved())
        {
            TurnLeftCounterClockwise3();
            movesKeeper("L");
            return true;
        }
        else TurnLeftCounterClockwise3();

        TurnLeftCounterClockwise3();
        if(isSolved())
        {
            TurnLeftClockwise3();
            movesKeeper("L'");
            return true;
        }
        else TurnLeftClockwise3();

        TurnUpClockwise3();
        if(isSolved())
        {
            TurnUpCounterClockwise3();
            movesKeeper("U");
            return true;
        }
        else TurnUpCounterClockwise3();

        TurnUpCounterClockwise3();
        if(isSolved())
        {
            TurnUpClockwise3();
            movesKeeper("U'");
            return true;
        }
        else TurnUpClockwise3();

        TurnDownClockwise3();
        if(isSolved())
        {
            TurnDownCounterClockwise3();
            movesKeeper("D");
            return true;
        }
        else TurnDownCounterClockwise3();

        TurnDownCounterClockwise3();
        if(isSolved())
        {
            TurnDownClockwise3();
            movesKeeper("D'");
            return true;
        }
        else TurnDownClockwise3();

        TurnFrontClockwise3();
        if(isSolved())
        {
            TurnFrontCounterClockwise3();
            movesKeeper("F");
            return true;
        }
        else TurnFrontCounterClockwise3();

        TurnFrontCounterClockwise3();
        if(isSolved())
        {
            TurnFrontClockwise3();
            movesKeeper("F'");
            return true;
        }
        else TurnFrontClockwise3();

        TurnBackClockwise3();
        if(isSolved())
        {
            TurnBackCounterClockwise3();
            movesKeeper("B");
            return true;
        }
        else TurnBackCounterClockwise3();

        TurnBackCounterClockwise3();
        if(isSolved())
        {
            TurnBackClockwise3();
            movesKeeper("B'");
            return true;
        }
        else TurnBackClockwise3();
        // if there is two similar moves to solve the cube

        TurnRightClockwise3();TurnRightClockwise3();
        if(isSolved())
        {
            TurnRightCounterClockwise3();
            TurnRightCounterClockwise3();
            movesKeeper("R2");
            return true;
        }
        else
        {
            TurnRightCounterClockwise3();
            TurnRightCounterClockwise3();
        }

        TurnLeftClockwise3();TurnLeftClockwise3();
        if(isSolved())
        {
            TurnLeftCounterClockwise3();
            TurnLeftCounterClockwise3();
            movesKeeper("L2");
            return true;
        }
        else
        {TurnLeftCounterClockwise3();
            TurnLeftCounterClockwise3();}

        TurnUpClockwise3();TurnUpClockwise3();
        if(isSolved())
        {
            TurnUpCounterClockwise3();
            TurnUpCounterClockwise3();
            movesKeeper("U2");
            return true;
        }
        else
        {
            TurnUpCounterClockwise3();
            TurnUpCounterClockwise3();
        }

        TurnDownClockwise3();TurnDownClockwise3();
        if(isSolved())
        {
            TurnDownCounterClockwise3();
            TurnDownCounterClockwise3();
            movesKeeper("D2");
            return true;
        }
        else
        {
            TurnDownCounterClockwise3();
            TurnDownCounterClockwise3();
        }

        TurnFrontClockwise3();TurnFrontClockwise3();
        if(isSolved())
        {
            TurnFrontCounterClockwise3();
            TurnFrontCounterClockwise3();
            movesKeeper("F2");
            return true;
        }
        else
        {
            TurnFrontCounterClockwise3();
            TurnFrontCounterClockwise3();
        }

        TurnBackClockwise3();TurnBackClockwise3();
        if(isSolved())
        {
            TurnBackCounterClockwise3();
            TurnBackCounterClockwise3();
            movesKeeper("B2");
            return true;
        }
        else
        {
            TurnBackCounterClockwise3();
            TurnBackCounterClockwise3();
        }

        return false;

    }

    public void back(View view) {
        displaymoves.setText("");


        try {
            soundEffect = MediaPlayer.create(this,R.raw.clicksound);
            soundEffect.start();

            Intent i=new Intent(getBaseContext(),Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void next(View view) {


        if(movesPointer==moves.size()-1){
            gifImageView.setVisibility(View.VISIBLE);
            rightArrow.setImageResource(R.drawable.rightarrow);
        }
        if(movesPointer==-1)
        {
            movesPointer++;
        }

        if(moves.size()!=0&& movesPointer<moves.size()) {
            back.setText("");
            down.setText("");
            top.setText("");
            left.setText("");
            right.setText("");
            soundEffect = MediaPlayer.create(this, R.raw.movecube);
            soundEffect.start();
            progressBar.incrementProgressBy(1);
            scramble(moves.get(movesPointer));



            leftArrow.setImageResource(R.drawable.whiteleftarrow);

            // the first case if the solution algorithm is only one move
            if (moves.size() == 1) {
                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                secondMove.setTextSize(24);
            }
            // if the solution is only two moves
            else if (moves.size() == 2) {
                if (movesPointer == 0) {
                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);
                    secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                    secondMove.setTextSize(24);
                    movesPointer++;
                } else if (movesPointer == 1) {
                    // if the next is clicked nothing should happen since its already the last move in the array

                }
            }
            //if the solution is only three moves
            else if (moves.size() == 3) {

                if (movesPointer == 0) {
                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);
                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);

                    secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                    secondMove.setTextSize(24);
                    scramble(moves.get(movesPointer));
                    movesPointer++;
                } else if (movesPointer == 1) {
                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);
                    thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
                    thirdMove.setTextSize(24);

                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);
                    scramble(moves.get(movesPointer));
                    movesPointer++;

                } else if (movesPointer == 2) {
                    //nothing should happen since its the last move in the array
                }

            }

            else if(moves.size()%3 == 0 )
            {

                if (movesPointer % 3 == 0) {
                    firstMove.setText(moves.get(movesPointer));
                    secondMove.setText((moves.get(movesPointer + 1)));
                    thirdMove.setText(moves.get(movesPointer + 2));

                    firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                    firstMove.setTextSize(24);

                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);

                }
//                if(moves.size()-(movesPointer+1)==2)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//
//                }
//                else if(moves.size()-(movesPointer+1)==1)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                }
//                else
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//                    thirdMove.setText(moves.get(movesPointer+3));
//                }
//
//
//            }
//
//
//
//            if(movesPointer%3 ==0 )
//            {
//                scramble(moves.get(movesPointer));
//                firstMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface(Typeface.DEFAULT);
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }
//            else
//            if(movesPointer%3==1 && moves.size()>1)
//            {
//                scramble(moves.get(movesPointer));
//
//                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
//                firstMove.setTypeface((Typeface.DEFAULT));
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//            }
//            else
//            {
//                scramble(moves.get(movesPointer));
//
//                thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface((Typeface.DEFAULT));
//                firstMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }
                else if (movesPointer % 3 == 1) {

                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                    secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                    secondMove.setTextSize(24);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);

                }

                else if (movesPointer % 3 == 2) {
                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);

                    thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
                    thirdMove.setTextSize(24);
                }


//            if (moves.size() == 1 && goNext == true) {
//                secondMove.setText(moves.get(0));
//                scramble(moves.get(0));
//                movesCount = 0;
//                goNext = false;
//            } else if (movesCount == -1) {
//                movesCount++;
//                textView.setText(moves.get(movesCount));
//                scramble(moves.get(movesCount));
//
//            } else if (movesCount < moves.size() - 1 && moves.size() != 1) {
//                movesCount++;
//                textView.setText((String) moves.get(movesCount));
//                scramble(moves.get(movesCount));
//            }
//            if (movesCount < moves.size() - 1) {
//                rightArrow.setImageResource(R.drawable.arrow5);
//                progressBar.incrementProgressBy(100 / moves.size());
//
//            } else {
//                rightArrow.setImageResource(R.drawable.arrow6);
//                progressBar.setProgress(100);
//            }
            }

            else if(moves.size()%3 == 1)
            {
                if(movesPointer!=moves.size()-1)
                {
                    if (movesPointer % 3 == 0) {
                        firstMove.setText(moves.get(movesPointer));
                        secondMove.setText((moves.get(movesPointer + 1)));
                        thirdMove.setText(moves.get(movesPointer + 2));

                        firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                        firstMove.setTextSize(24);

                        secondMove.setTypeface(Typeface.DEFAULT);
                        secondMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);

                    }

//                if(moves.size()-(movesPointer+1)==2)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//
//                }
//                else if(moves.size()-(movesPointer+1)==1)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                }
//                else
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//                    thirdMove.setText(moves.get(movesPointer+3));
//                }
//
//
//            }
//
//
//
//            if(movesPointer%3 ==0 )
//            {
//                scramble(moves.get(movesPointer));
//                firstMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface(Typeface.DEFAULT);
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }
//            else
//            if(movesPointer%3==1 && moves.size()>1)
//            {
//                scramble(moves.get(movesPointer));
//
//                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
//                firstMove.setTypeface((Typeface.DEFAULT));
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//            }
//            else
//            {
//                scramble(moves.get(movesPointer));
//
//                thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface((Typeface.DEFAULT));
//                firstMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }

                    else if (movesPointer % 3 == 1) {

                        firstMove.setTypeface(Typeface.DEFAULT);
                        firstMove.setTextSize(20);

                        secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                        secondMove.setTextSize(24);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);

                    }
                    else if (movesPointer % 3 == 2) {
                        firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                        firstMove.setTextSize(20);

                        secondMove.setTypeface(Typeface.DEFAULT);
                        secondMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
                        thirdMove.setTextSize(24);
                    }

                }

                else
                {
                    secondMove.setText(moves.get(movesPointer));
                    firstMove.setText("");
                    thirdMove.setText("");

                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                    secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                    secondMove.setTextSize(24);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);
                }

            }

            else if(moves.size()%3 == 2)
            {

                if(movesPointer!=moves.size()-2)
                {
                    if (movesPointer % 3 == 0) {
                        firstMove.setText(moves.get(movesPointer));
                        secondMove.setText((moves.get(movesPointer + 1)));
                        thirdMove.setText(moves.get(movesPointer + 2));

                        firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                        firstMove.setTextSize(24);

                        secondMove.setTypeface(Typeface.DEFAULT);
                        secondMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);

                    }

//                if(moves.size()-(movesPointer+1)==2)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//
//                }
//                else if(moves.size()-(movesPointer+1)==1)
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                }
//                else
//                {
//                    firstMove.setText(moves.get(movesPointer+1));
//                    secondMove.setText((moves.get(movesPointer+2)));
//                    thirdMove.setText(moves.get(movesPointer+3));
//                }
//
//
//            }
//
//
//
//            if(movesPointer%3 ==0 )
//            {
//                scramble(moves.get(movesPointer));
//                firstMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface(Typeface.DEFAULT);
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }
//            else
//            if(movesPointer%3==1 && moves.size()>1)
//            {
//                scramble(moves.get(movesPointer));
//
//                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
//                firstMove.setTypeface((Typeface.DEFAULT));
//                thirdMove.setTypeface(Typeface.DEFAULT);
//
//
//            }
//            else
//            {
//                scramble(moves.get(movesPointer));
//
//                thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
//                secondMove.setTypeface((Typeface.DEFAULT));
//                firstMove.setTypeface(Typeface.DEFAULT);
//
//
//
//            }

                    else if (movesPointer % 3 == 1) {

                        firstMove.setTypeface(Typeface.DEFAULT);
                        firstMove.setTextSize(20);

                        secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                        secondMove.setTextSize(24);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);

                    }
                    else if (movesPointer % 3 == 2) {
                        firstMove.setTypeface(Typeface.DEFAULT);
                        firstMove.setTextSize(20);

                        secondMove.setTypeface(Typeface.DEFAULT);
                        secondMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
                        thirdMove.setTextSize(24);
                    }
                }

                else
                {
                    if(movesPointer!= moves.size()-1)
                    {
                        firstMove.setText(moves.get(moves.size() - 2));
                        secondMove.setText(moves.get(moves.size() - 1));
                        thirdMove.setText("");

                        firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                        firstMove.setTextSize(24);

                        secondMove.setTypeface(Typeface.DEFAULT);
                        secondMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);
                    }
                    else
                    {
                        secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                        secondMove.setTextSize(24);

                        firstMove.setTypeface(Typeface.DEFAULT);
                        firstMove.setTextSize(20);

                        thirdMove.setTypeface(Typeface.DEFAULT);
                        thirdMove.setTextSize(20);
                    }


                }


            }


            movesPointer++;

        }

        else
        {

        }

//                if(moves.size()==1 && firstMove == false)
//                {
//                        scramble(moves.get(0));
//                        textView.setText((String)moves.get(0));
//                        firstMove = true;
//                }
//                else if(movesCount<(moves.size()))
//                {
//                        scramble(moves.get(movesCount));
//                        textView.setText(moves.get(movesCount));
//                        movesCount++;
//                }
    }
    //        public void next() {
//                if(movesCount<(moves.size()))
//                {
//                        scramble(moves.get(movesCount));
//                        textView.setText(moves.get(movesCount));
//                        movesCount++;
//                        firstMove=true;
//
//                }
//                else if(moves.size()==1)
//                {
//                        scramble(moves.get(0));
//                        textView.setText((String)moves.get(0));
//                        firstMove=true;
//                }
//        }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previous(View view) {

//        soundEffect = MediaPlayer.create(this,R.raw.movecube);
//        soundEffect.start();



        if(moves.size()!=0 && movesPointer>=0) {
            //stop the celebration here
            back.setText("");
            down.setText("");
            top.setText("");
            left.setText("");
            right.setText("");

            gifImageView.setVisibility(View.GONE);
            rightArrow.setImageResource(R.drawable.whiterightarrow);

            soundEffect = MediaPlayer.create(this, R.raw.movecube);
            soundEffect.start();
            progressBar.incrementProgressBy(-1);
            movesPointer--;
            if(movesPointer==-1)
                return;


            switch (moves.get(movesPointer)) {
                case "L":
                    scramble("L'");
                    break;
                case "L'":
                    scramble("L");
                    break;
                case "R":
                    scramble("R'");

                    break;
                case "R'":
                    scramble("R");
                    break;
                case "U":
                    scramble("U'");
                    break;
                case "U'":
                    scramble("U");
                    break;
                case "D":
                    scramble("D'");
                    break;
                case "D'":
                    scramble("D");
                    break;
                case "F":
                    scramble("F'");
                    break;
                case "F'":
                    scramble("F");
                    break;
                case "B":
                    scramble("B'");
                    break;
                case "B'":
                    scramble("B");
                    break;

                case "L2":
                    scramble("L'");
                    scramble("L'");
                    break;
                case "R2":
                    scramble("R'");
                    scramble("R'");
                    break;
                case "U2":
                    scramble("U'");
                    scramble("U'");
                    break;
                case "D2":
                    scramble("D'");
                    scramble("D'");
                    break;
                case "F2":
                    scramble("F'");
                    scramble("F'");
                    break;
                case "B2":
                    scramble("B'");
                    scramble("B'");
                    break;

            }


            if(moves.size()==1)
            {
                secondMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTextSize(20);
                firstMove.setText("");
                firstMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setText("");
                thirdMove.setTypeface(Typeface.DEFAULT);
            }
            else if(moves.size()==2)
            {
                if(movesPointer==0)
                {
                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                }
                else if(movesPointer==1)
                {
                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);

                    firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                    firstMove.setTextSize(24);
                }
                else {
                    //should do nothing
                }
            }

            else if(moves.size()==3)
            {
                if(movesPointer==2)
                {
                    secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                    secondMove.setTextSize(24);

                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);
                }

                else if(movesPointer==1)
                {
                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);

                    firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                    firstMove.setTextSize(24);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);
                }
                else if(movesPointer==0)
                {
                    secondMove.setTypeface(Typeface.DEFAULT);
                    secondMove.setTextSize(20);

                    firstMove.setTypeface(Typeface.DEFAULT);
                    firstMove.setTextSize(20);

                    thirdMove.setTypeface(Typeface.DEFAULT);
                    thirdMove.setTextSize(20);

                }

            }
            else if(movesPointer==0)
            {
                secondMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTextSize(20);

                firstMove.setTypeface(Typeface.DEFAULT);
                firstMove.setTextSize(20);

                thirdMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTextSize(20);
            }

            else if(movesPointer==1)
            {
                secondMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTextSize(20);

                firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                firstMove.setTextSize(24);

                thirdMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTextSize(20);
            }
            else if(movesPointer==2)
            {
                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                secondMove.setTextSize(24);

                firstMove.setTypeface(Typeface.DEFAULT);
                firstMove.setTextSize(20);

                thirdMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTextSize(20);
            }

            else if(movesPointer%3==0)
            {
                thirdMove.setText(moves.get(movesPointer-1));
                secondMove.setText(moves.get(movesPointer-2));
                firstMove.setText(moves.get(movesPointer-3));

                thirdMove.setTypeface(Typeface.DEFAULT_BOLD);
                thirdMove.setTextSize(24);
                secondMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTextSize(20);
                firstMove.setTypeface(Typeface.DEFAULT);
                firstMove.setTextSize(20);
            }
            else if(movesPointer%3==2)
            {
                secondMove.setTypeface(Typeface.DEFAULT_BOLD);
                secondMove.setTextSize(24);
                thirdMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTextSize(20);
                firstMove.setTypeface(Typeface.DEFAULT);
                firstMove.setTextSize(20);
            }
            else if(movesPointer%3==1)
            {
                thirdMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTextSize(20);
                secondMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTextSize(20);
                firstMove.setTypeface(Typeface.DEFAULT_BOLD);
                firstMove.setTextSize(24);
            }


        }

        else {
        }
        if (movesPointer==0)
        {
            leftArrow.setImageResource(R.drawable.leftarrow);
        }

//            if (moves.size() == 1 && movesCount < 0)
//                textView.setText("");
//            if (moves.size() == 1 && goNext == false) {
//                movesCount--;
//                switch (moves.get(0)) {
//                    case "L":
//                        scramble("L'");
//                        textView.setText("L");
//                        break;
//                    case "L'":
//                        scramble("L");
//                        textView.setText("L'");
//                        break;
//                    case "R":
//                        scramble("R'");
//
//                        textView.setText("R");
//                        break;
//                    case "R'":
//                        scramble("R");
//                        textView.setText("R'");
//                        break;
//                    case "U":
//                        scramble("U'");
//                        textView.setText("U");
//                        break;
//                    case "U'":
//                        scramble("U");
//                        textView.setText("U'");
//                        break;
//                    case "D":
//                        scramble("D'");
//                        textView.setText("D");
//                        break;
//                    case "D'":
//                        scramble("D");
//                        textView.setText("D'");
//                        break;
//                    case "F":
//                        scramble("F'");
//                        textView.setText("F");
//                        break;
//                    case "F'":
//                        scramble("F");
//                        textView.setText("F'");
//                        break;
//                    case "B":
//                        scramble("B'");
//                        textView.setText("B");
//                        break;
//                    case "B'":
//                        scramble("B");
//                        textView.setText("B'");
//                        break;
//
//                    case "L2":
//                        scramble("L'");
//                        scramble("L'");
//                        textView.setText("L2");
//                        break;
//                    case "R2":
//                        scramble("R'");
//                        scramble("R'");
//                        textView.setText("R2");
//                        break;
//                    case "U2":
//                        scramble("U'");
//                        scramble("U'");
//                        textView.setText("U2");
//                        break;
//                    case "D2":
//                        scramble("D'");
//                        scramble("D'");
//                        textView.setText("D2");
//                        break;
//                    case "F2":
//                        scramble("F'");
//                        scramble("F'");
//                        textView.setText("F2");
//                        break;
//                    case "B2":
//                        scramble("B'");
//                        scramble("B'");
//                        textView.setText("B2");
//                        break;
//
//                }
//
//
//                goNext = true;
//            } else if (movesCount == 0 && moves.size() > 1) {
//                switch (moves.get(movesCount)) {
//                    case "L":
//                        scramble("L'");
//                        textView.setText("L");
//                        break;
//                    case "L'":
//                        scramble("L");
//                        textView.setText("L'");
//                        break;
//                    case "R":
//                        scramble("R'");
//
//                        textView.setText("R");
//                        break;
//                    case "R'":
//                        scramble("R");
//                        textView.setText("R'");
//                        break;
//                    case "U":
//                        scramble("U'");
//                        textView.setText("U");
//                        break;
//                    case "U'":
//                        scramble("U");
//                        textView.setText("U'");
//                        break;
//                    case "D":
//                        scramble("D'");
//                        textView.setText("D");
//                        break;
//                    case "D'":
//                        scramble("D");
//                        textView.setText("D'");
//                        break;
//                    case "F":
//                        scramble("F'");
//                        textView.setText("F");
//                        break;
//                    case "F'":
//                        scramble("F");
//                        textView.setText("F'");
//                        break;
//                    case "B":
//                        scramble("B'");
//                        textView.setText("B");
//                        break;
//                    case "B'":
//                        scramble("B");
//                        textView.setText("B'");
//                        break;
//
//                    case "L2":
//                        scramble("L'");
//                        scramble("L'");
//                        textView.setText("L2");
//                        break;
//                    case "R2":
//                        scramble("R'");
//                        scramble("R'");
//                        textView.setText("R2");
//                        break;
//                    case "U2":
//                        scramble("U'");
//                        scramble("U'");
//                        textView.setText("U2");
//                        break;
//                    case "D2":
//                        scramble("D'");
//                        scramble("D'");
//                        textView.setText("D2");
//                        break;
//                    case "F2":
//                        scramble("F'");
//                        scramble("F'");
//                        textView.setText("F2");
//                        break;
//                    case "B2":
//                        scramble("B'");
//                        scramble("B'");
//                        textView.setText("B2");
//                        break;
//
//                }
//
//                textView.setText("");
//                movesCount--;
//
//            } else if (movesCount > 0) {
//
//
//                switch (moves.get(movesCount)) {
//                    case "L":
//                        scramble("L'");
//                        textView.setText("L");
//                        break;
//                    case "L'":
//                        scramble("L");
//                        textView.setText("L'");
//                        break;
//                    case "R":
//                        scramble("R'");
//
//                        textView.setText("R");
//                        break;
//                    case "R'":
//                        scramble("R");
//                        textView.setText("R'");
//                        break;
//                    case "U":
//                        scramble("U'");
//                        textView.setText("U");
//                        break;
//                    case "U'":
//                        scramble("U");
//                        textView.setText("U'");
//                        break;
//                    case "D":
//                        scramble("D'");
//                        textView.setText("D");
//                        break;
//                    case "D'":
//                        scramble("D");
//                        textView.setText("D'");
//                        break;
//                    case "F":
//                        scramble("F'");
//                        textView.setText("F");
//                        break;
//                    case "F'":
//                        scramble("F");
//                        textView.setText("F'");
//                        break;
//                    case "B":
//                        scramble("B'");
//                        textView.setText("B");
//                        break;
//                    case "B'":
//                        scramble("B");
//                        textView.setText("B'");
//                        break;
//
//                    case "L2":
//                        scramble("L'");
//                        scramble("L'");
//                        textView.setText("L2");
//                        break;
//                    case "R2":
//                        scramble("R'");
//                        scramble("R'");
//                        textView.setText("R2");
//                        break;
//                    case "U2":
//                        scramble("U'");
//                        scramble("U'");
//                        textView.setText("U2");
//                        break;
//                    case "D2":
//                        scramble("D'");
//                        scramble("D'");
//                        textView.setText("D2");
//                        break;
//                    case "F2":
//                        scramble("F'");
//                        scramble("F'");
//                        textView.setText("F2");
//                        break;
//                    case "B2":
//                        scramble("B'");
//                        scramble("B'");
//                        textView.setText("B2");
//                        break;
//
//                }
//                movesCount--;
//                textView.setText((String) moves.get(movesCount));
//
//            }
//            if (movesCount < 0) {
//                leftArrow.setImageResource(R.drawable.arrow6);
//                progressBar.setProgress(0);
//            } else {
//                leftArrow.setImageResource(R.drawable.arrow5);
//                progressBar.incrementProgressBy(-100 / moves.size());
//            }

    }

//                if(moves.size()==1 && firstMove==true)
//                {
//                        switch(moves.get(0))
//                        {
//                                case "L": scramble("L'");break;
//                                case "L'": scramble("L");break;
//                                case "R": scramble("R'");break;
//                                case "R'": scramble("R");break;
//                                case "U": scramble("U'");break;
//                                case "U'": scramble("U");break;
//                                case "D": scramble("D'");break;
//                                case "D'": scramble("D");break;
//                                case "F": scramble("F'");break;
//                                case "F'": scramble("F");break;
//                                case "B": scramble("B'");break;
//                                case "B'": scramble("B");break;
//                        }
//                        textView.setText(moves.get(0));
//                        firstMove = false;
//                }
//
//                else if(movesCount>moves.size()-1)
//                {
//                        movesCount--;
//                        switch(moves.get(movesCount))
//                        {
//                                case "L": scramble("L'");break;
//                                case "L'": scramble("L");break;
//                                case "R": scramble("R'");break;
//                                case "R'": scramble("R");break;
//                                case "U": scramble("U'");break;
//                                case "U'": scramble("U");break;
//                                case "D": scramble("D'");break;
//                                case "D'": scramble("D");break;
//                                case "F": scramble("F'");break;
//                                case "F'": scramble("F");break;
//                                case "B": scramble("B'");break;
//                                case "B'": scramble("B");break;
//                        }
//                        textView.setText(moves.get(movesCount));
//
//                }
//                else if(movesCount>0)
//                {
//                        switch(moves.get(movesCount))
//                        {
//                                case "L": scramble("L'");break;
//                                case "L'": scramble("L");break;
//                                case "R": scramble("R'");break;
//                                case "R'": scramble("R");break;
//                                case "U": scramble("U'");break;
//                                case "U'": scramble("U");break;
//                                case "D": scramble("D'");break;
//                                case "D'": scramble("D");break;
//                                case "F": scramble("F'");break;
//                                case "F'": scramble("F");break;
//                                case "B": scramble("B'");break;
//                                case "B'": scramble("B");break;
//                        }                        textView.setText(moves.get(movesCount));
//                        movesCount--;
//
//
//                }
//                else if(movesCount==0)
//                {
//                        switch(moves.get(movesCount))
//                        {
//                                case "L": scramble("L'");break;
//                                case "L'": scramble("L");break;
//                                case "R": scramble("R'");break;
//                                case "R'": scramble("R");break;
//                                case "U": scramble("U'");break;
//                                case "U'": scramble("U");break;
//                                case "D": scramble("D'");break;
//                                case "D'": scramble("D");break;
//                                case "F": scramble("F'");break;
//                                case "F'": scramble("F");break;
//                                case "B": scramble("B'");break;
//                                case "B'": scramble("B");break;
//                        }                        textView.setText(moves.get(movesCount));
//                }



    public boolean Solved() {
        // to check if the cube is already solved
        count1 = 4;
        solvedOrNot = true;
        for (int i = 0; i < 24; i++) {

            if (i % 4 == 0) {
                if (count1 != 4) {
                    solvedOrNot = false;
                    return solvedOrNot;
                }

                count1 = 0;
                temp3 = (String) buttons[i].getTag();
            }

            if (((String) buttons[i].getTag()).equals(temp3))
                count1++;
        }
        return solvedOrNot;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void start (View view)
    {
        back.setText("");
        down.setText("");
        top.setText("");
        left.setText("");
        right.setText("");
        displaymoves.setText("");
        displaymoves2.setText("");

        try {

            firstMove.setTypeface(Typeface.DEFAULT);
            firstMove.setTextSize(20);
            firstMove.setText("");

            secondMove.setTypeface(Typeface.DEFAULT);
            secondMove.setTextSize(20);
            secondMove.setText("");

            thirdMove.setTypeface(Typeface.DEFAULT);
            thirdMove.setTextSize(20);
            thirdMove.setText("");
            textView.setText("");
            movesPointer=0;
            progressBar.setProgress(0);
            rightArrow.setImageResource(R.drawable.rightarrow);
            leftArrow.setImageResource(R.drawable.leftarrow);
            movesCount = -1;
            ss = "";
            goNext = true;
            y = r = g = b = w = o = 0;
// To check that all the pieces were entered are 4 of each color
            for (int i = 0; i < buttons.length; i++) {
                switch ((String) buttons[i].getTag()) {
                    case "#FFE8E15A":
                        y++;
                        break;
                    case "#FFE30000":
                        r++;
                        break;
                    case "#FFFFFFFF":
                        w++;
                        break;
                    case "#FFEF8426":
                        o++;
                        break;
                    case "#FF3A9A3E":
                        g++;
                        break;
                    case "#FF1F8FE3":
                        b++;
                        break;

                }
            } // end of for
            piece = new String[8];
            piece[0] = "" + (String) buttons[0].getTag() + (String) buttons[4].getTag() + (String) buttons[17].getTag();
            piece[1] = "" + (String) buttons[1].getTag() + (String) buttons[13].getTag() + (String) buttons[16].getTag();
            piece[2] = "" + (String) buttons[2].getTag() + (String) buttons[5].getTag() + (String) buttons[8].getTag();
            piece[3] = "" + (String) buttons[3].getTag() + (String) buttons[9].getTag() + (String) buttons[12].getTag();
            piece[4] = "" + (String) buttons[20].getTag() + (String) buttons[7].getTag() + (String) buttons[10].getTag();
            piece[5] = "" + (String) buttons[21].getTag() + (String) buttons[11].getTag() + (String) buttons[14].getTag();
            piece[6] = "" + (String) buttons[22].getTag() + (String) buttons[6].getTag() + (String) buttons[19].getTag();
            piece[7] = "" + (String) buttons[23].getTag() + (String) buttons[15].getTag() + (String) buttons[18].getTag();

            if (!(y == 4 && r == 4 && w == 4 && o == 4 && g == 4 && b == 4)) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("Each color must be added exactly 4 times , Please re-enter the colors");
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
//            Toast.makeText(this,
//                    "Each color must be added exactly 4 times , Please re-enter the colors",
//                    Toast.LENGTH_LONG).show();
                return;
            } else {
                // To check that red/orange & green/blue & yellow/white pieces aren't next to each other
                for (int i = 0; i < 8; i++) {
                    if (piece[i].contains("#FFE30000") && piece[i].contains("#FFEF8426")) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                        builder1.setMessage("Red and Orange cant be on the same piece");
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
//                    Toast.makeText(this, "Red and Orange cant be on the same piece", Toast.LENGTH_LONG).show();
                        return;
                    } else if (piece[i].contains("#FF1F8FE3") && piece[i].contains("#FF3A9A3E")) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                        builder1.setMessage("Green and Blue cant be on the same piece");
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
//                    Toast.makeText(this, "Green and Blue cant be on the same piece", Toast.LENGTH_LONG).show();
                        return;
                    } else if (piece[i].contains("#FFE8E15A") && piece[i].contains("#FFFFFFFF")) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                        builder1.setMessage("Yellow and White cant be on the same piece");
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
//                    Toast.makeText(this, "Yellow and White cant be on the same piece", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            }// end of else

            // To check that no color can be placed twice on one piece
            for (int i = 0; i < 8; i++) // needs work
            {
                if (piece[i].contains("#FFE30000#FFE30000")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two red stickers cant be on the same piece");
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

//                Toast.makeText(this, "two red stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                } else if (piece[i].contains("#FFEF8426#FFEF8426")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two orange stickers cant be on the same piece");
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
//                Toast.makeText(this, "two orange stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                } else if (piece[i].contains("#FF1F8FE3#FF1F8FE3")) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two blue stickers cant be on the same piece");
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
//                Toast.makeText(this, "two blue stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                } else if (piece[i].contains("#FF3A9A3E#FF3A9A3E")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two green stickers cant be on the same piece");
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
//                Toast.makeText(this, "two green stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                } else if (piece[i].contains("#FFE8E15A#FFE8E15A")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two yellow stickers cant be on the same piece");
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
//                Toast.makeText(this, "two yellow stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                } else if (piece[i].contains("#FFFFFFFF#FFFFFFFF")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    builder1.setMessage("Two white stickers cant be on the same piece");
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
//                Toast.makeText(this, "two white stickers cant be on the same piece", Toast.LENGTH_LONG).show();
                    return;
                }

            } // end of for

            //check that there is no duplicated pieces


            if (Solved()) {
                Toast.makeText(this,
                        "the cube is already solved",
                        Toast.LENGTH_LONG).show();
                return;
            }


//        Toast.makeText(this,
//                "Colors are correct",
//                Toast.LENGTH_LONG).show();

            soundEffect = MediaPlayer.create(this, R.raw.solvesound);
            soundEffect.start();


            ybtn12.setTag(ybtn1.getTag());
            ybtn22.setTag(ybtn2.getTag());
            ybtn32.setTag(ybtn3.getTag());
            ybtn42.setTag(ybtn4.getTag());

            rbtn12.setTag(rbtn1.getTag());
            rbtn22.setTag(rbtn2.getTag());
            rbtn32.setTag(rbtn3.getTag());
            rbtn42.setTag(rbtn4.getTag());

            gbtn12.setTag(gbtn1.getTag());
            gbtn22.setTag(gbtn2.getTag());
            gbtn32.setTag(gbtn3.getTag());
            gbtn42.setTag(gbtn4.getTag());

            obtn12.setTag(obtn1.getTag());
            obtn22.setTag(obtn2.getTag());
            obtn32.setTag(obtn3.getTag());
            obtn42.setTag(obtn4.getTag());

            bbtn12.setTag(bbtn1.getTag());
            bbtn22.setTag(bbtn2.getTag());
            bbtn32.setTag(bbtn3.getTag());
            bbtn42.setTag(bbtn4.getTag());

            wbtn12.setTag(wbtn1.getTag());
            wbtn22.setTag(wbtn2.getTag());
            wbtn32.setTag(wbtn3.getTag());
            wbtn42.setTag(wbtn4.getTag());


            //check if the first piece is in a possible position (24 case) (yellow orange green)


            if((ybtn42.getTag().equals("#FFE8E15A")&& gbtn22.getTag().equals("#FF3A9A3E")&&obtn12.getTag().equals("#FFEF8426"))
                    || (ybtn42.getTag().equals("#FF3A9A3E")&& gbtn22.getTag().equals("#FFEF8426") &&obtn12.getTag().equals("#FFE8E15A"))
                    || (ybtn42.getTag().equals("#FFEF8426")&& gbtn22.getTag().equals("#FFE8E15A" )&&obtn12.getTag().equals("#FF3A9A3E"))

                    //the second place (ybtn3)
                    || (ybtn32.getTag().equals("#FFE8E15A") && gbtn12.getTag().equals("#FFEF8426")&& rbtn22.getTag().equals("#FF3A9A3E"))
                    || (ybtn32.getTag().equals("#FF3A9A3E") && gbtn12.getTag().equals("#FFE8E15A")&& rbtn22.getTag().equals("#FFEF8426"))
                    || (ybtn32.getTag().equals("#FFEF8426") && gbtn12.getTag().equals("#FF3A9A3E")&& rbtn22.getTag().equals("#FFE8E15A"))

                    //the third place (ybtn2)
                    || (ybtn22.getTag().equals("#FFE8E15A") && obtn22.getTag().equals("#FF3A9A3E" )&& bbtn12.getTag().equals("#FFEF8426"))
                    || (ybtn22.getTag().equals("#FF3A9A3E") && obtn22.getTag().equals("#FFEF8426") && bbtn12.getTag().equals("#FFE8E15A"))
                    || (ybtn22.getTag().equals("#FFEF8426") && obtn22.getTag().equals("#FFE8E15A") && bbtn12.getTag().equals("#FF3A9A3E"))

                    //the forth place (ybtn1)
                    ||(ybtn12.getTag().equals("#FFE8E15A") && rbtn12.getTag().equals("#FFEF8426") &&bbtn22.getTag().equals("#FF3A9A3E"))
                    ||(ybtn12.getTag().equals("#FF3A9A3E") && rbtn12.getTag().equals("#FFE8E15A" )&&bbtn22.getTag().equals("#FFEF8426"))
                    ||(ybtn12.getTag().equals("#FFEF8426") && rbtn12.getTag().equals("#FF3A9A3E") &&bbtn22.getTag().equals("#FFE8E15A"))

                    //the fifth place (wbtn3)
                    ||(wbtn32.getTag().equals("#FFE8E15A") &&rbtn32.getTag().equals("#FF3A9A3E" )&& bbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn32.getTag().equals("#FFEF8426") &&rbtn32.getTag().equals("#FFE8E15A" )&& bbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn32.getTag().equals("#FF3A9A3E") &&rbtn32.getTag().equals("#FFEF8426") && bbtn42.getTag().equals("#FFE8E15A"))

                    //the sixth place (wbtn4)
                    ||(wbtn42.getTag().equals("#FFE8E15A") && obtn42.getTag().equals("#FFEF8426") &&bbtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn42.getTag().equals("#FF3A9A3E") && obtn42.getTag().equals("#FFE8E15A") &&bbtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn42.getTag().equals("#FFEF8426") && obtn42.getTag().equals("#FF3A9A3E") &&bbtn32.getTag().equals("#FFE8E15A"))

                    //the seventh place (wbtn1)
                    ||(wbtn12.getTag().equals("#FFE8E15A") && gbtn32.getTag().equals("#FF3A9A3E") && rbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn12.getTag().equals("#FFEF8426") && gbtn32.getTag().equals("#FFE8E15A") && rbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn12.getTag().equals("#FF3A9A3E") && gbtn32.getTag().equals("#FFEF8426") && rbtn42.getTag().equals("#FFE8E15A"))

                    //the final place (wbtn2)
                    ||(wbtn22.getTag().equals("#FFE8E15A") && gbtn42.getTag().equals("#FFEF8426" )&& obtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn22.getTag().equals("#FF3A9A3E") && gbtn42.getTag().equals("#FFE8E15A") && obtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn22.getTag().equals("#FFEF8426") && gbtn42.getTag().equals("#FF3A9A3E") && obtn32.getTag().equals("#FFE8E15A"))

            )
            {}
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

//                    yellow
//                            FFE8E15A
//                    green
//                            FF3A9A3E
//                    red
//                            FFE30000
            //check if the second piece is in a possible position (24 case) (yellow red green)

            if((ybtn42.getTag().equals("#FFE8E15A")&& gbtn22.getTag().equals("#FFE30000")&&obtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn42.getTag().equals("#FF3A9A3E")&& gbtn22.getTag().equals("#FFE8E15A")&&obtn12.getTag().equals("#FFE30000"))
                    || (ybtn42.getTag().equals("#FFE30000")&& gbtn22.getTag().equals("#FF3A9A3E")&&obtn12.getTag().equals("#FFE8E15A"))

                    //the second place (ybtn3)
                    || (ybtn32.getTag().equals("#FFE8E15A") && gbtn12.getTag().equals("#FF3A9A3E")&& rbtn22.getTag().equals("#FFE30000"))
                    || (ybtn32.getTag().equals("#FFE30000") && gbtn12.getTag().equals("#FFE8E15A")&& rbtn22.getTag().equals("#FF3A9A3E"))
                    || (ybtn32.getTag().equals("#FF3A9A3E") && gbtn12.getTag().equals("#FFE30000")&& rbtn22.getTag().equals("#FFE8E15A"))

                    //the third place (ybtn2)
                    || (ybtn22.getTag().equals("#FFE8E15A") && obtn22.getTag().equals("#FFE30000" )&& bbtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn22.getTag().equals("#FF3A9A3E") && obtn22.getTag().equals("#FFE8E15A") && bbtn12.getTag().equals("#FFE30000"))
                    || (ybtn22.getTag().equals("#FFE30000") && obtn22.getTag().equals("#FF3A9A3E") && bbtn12.getTag().equals("#FFE8E15A"))

                    //the forth place (ybtn1)
                    ||(ybtn12.getTag().equals("#FFE8E15A") && rbtn12.getTag().equals("#FF3A9A3E") &&bbtn22.getTag().equals("#FFE30000"))
                    ||(ybtn12.getTag().equals("#FFE30000") && rbtn12.getTag().equals("#FFE8E15A") &&bbtn22.getTag().equals("#FF3A9A3E"))
                    ||(ybtn12.getTag().equals("#FF3A9A3E" )&& rbtn12.getTag().equals("#FFE30000") &&bbtn22.getTag().equals("#FFE8E15A"))

                    //the fifth place (wbtn3)
                    ||(wbtn32.getTag().equals("#FFE8E15A") &&rbtn32.getTag().equals("#FFE30000") && bbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn32.getTag().equals("#FF3A9A3E") &&rbtn32.getTag().equals("#FFE8E15A") && bbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn32.getTag().equals("#FFE30000") &&rbtn32.getTag().equals("#FF3A9A3E") && bbtn42.getTag().equals("#FFE8E15A"))

                    //the sixth place (wbtn4)
                    ||(wbtn42.getTag().equals("#FFE8E15A") && obtn42.getTag().equals("#FF3A9A3E") &&bbtn32.getTag().equals("#FFE30000"))
                    ||(wbtn42.getTag().equals("#FFE30000") && obtn42.getTag().equals("#FFE8E15A") &&bbtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn42.getTag().equals("#FF3A9A3E") && obtn42.getTag().equals("#FFE30000") &&bbtn32.getTag().equals("#FFE8E15A"))

                    //the seventh place (wbtn1)
                    ||(wbtn12.getTag().equals("#FFE8E15A") && gbtn32.getTag().equals("#FFE30000") && rbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn12.getTag().equals("#FF3A9A3E" )&& gbtn32.getTag().equals("#FFE8E15A") && rbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn12.getTag().equals("#FFE30000" )&& gbtn32.getTag().equals("#FF3A9A3E") && rbtn42.getTag().equals("#FFE8E15A"))

                    //the final place (wbtn2)
                    ||(wbtn22.getTag().equals("#FFE8E15A") && gbtn42.getTag().equals("#FF3A9A3E") && obtn32.getTag().equals("#FFE30000"))
                    ||(wbtn22.getTag().equals("#FFE30000") && gbtn42.getTag().equals("#FFE8E15A") && obtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn22.getTag().equals("#FF3A9A3E") && gbtn42.getTag().equals("#FFE30000") && obtn32.getTag().equals("#FFE8E15A"))

            )
            {}
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

            //check if the third piece is in a possible position (24 case) (yellow red blue)
            // yellow #FFE8E15A
            // red    #FFE30000
            // blue   #FF1F8FE3

            if((ybtn42.getTag().equals("#FFE8E15A")&& gbtn22.getTag().equals("#FF1F8FE3")&&obtn12.getTag().equals("#FFE30000"))
                    || (ybtn42.getTag().equals("#FFE30000")&& gbtn22.getTag().equals("#FFE8E15A")&&obtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn42.getTag().equals("#FF1F8FE3")&& gbtn22.getTag().equals("#FFE30000")&&obtn12.getTag().equals("#FFE8E15A"))

                    //the second place (ybtn3)
                    || (ybtn32.getTag().equals("#FFE8E15A") && gbtn12.getTag().equals("#FFE30000")&& rbtn22.getTag().equals("#FF1F8FE3"))
                    || (ybtn32.getTag().equals("#FF1F8FE3") && gbtn12.getTag().equals("#FFE8E15A")&& rbtn22.getTag().equals("#FFE30000"))
                    || (ybtn32.getTag().equals("#FFE30000") && gbtn12.getTag().equals("#FF1F8FE3")&& rbtn22.getTag().equals("#FFE8E15A"))

                    //the third place (ybtn2)
                    || (ybtn22.getTag().equals("#FFE8E15A") && obtn22.getTag().equals("#FF1F8FE3" )&& bbtn12.getTag().equals("#FFE30000"))
                    || (ybtn22.getTag().equals("#FFE30000") && obtn22.getTag().equals("#FFE8E15A") && bbtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn22.getTag().equals("#FF1F8FE3") && obtn22.getTag().equals("#FFE30000") && bbtn12.getTag().equals("#FFE8E15A"))

                    //the forth place (ybtn1)
                    ||(ybtn12.getTag().equals("#FFE8E15A") && rbtn12.getTag().equals("#FFE30000") &&bbtn22.getTag().equals("#FF1F8FE3"))
                    ||(ybtn12.getTag().equals("#FF1F8FE3") && rbtn12.getTag().equals("#FFE8E15A") &&bbtn22.getTag().equals("#FFE30000"))
                    ||(ybtn12.getTag().equals("#FFE30000" )&& rbtn12.getTag().equals("#FF1F8FE3") &&bbtn22.getTag().equals("#FFE8E15A"))

                    //the fifth place (wbtn3)
                    ||(wbtn32.getTag().equals("#FFE8E15A") &&rbtn32.getTag().equals("#FF1F8FE3") && bbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn32.getTag().equals("#FFE30000") &&rbtn32.getTag().equals("#FFE8E15A") && bbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn32.getTag().equals("#FF1F8FE3") &&rbtn32.getTag().equals("#FFE30000") && bbtn42.getTag().equals("#FFE8E15A"))

                    //the sixth place (wbtn4)
                    ||(wbtn42.getTag().equals("#FFE8E15A") && obtn42.getTag().equals("#FFE30000") &&bbtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn42.getTag().equals("#FF1F8FE3") && obtn42.getTag().equals("#FFE8E15A") &&bbtn32.getTag().equals("#FFE30000"))
                    ||(wbtn42.getTag().equals("#FFE30000") && obtn42.getTag().equals("#FF1F8FE3") &&bbtn32.getTag().equals("#FFE8E15A"))

                    //the seventh place (wbtn1)
                    ||(wbtn12.getTag().equals("#FFE8E15A") && gbtn32.getTag().equals("#FF1F8FE3") && rbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn12.getTag().equals("#FFE30000" )&& gbtn32.getTag().equals("#FFE8E15A") && rbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn12.getTag().equals("#FF1F8FE3" )&& gbtn32.getTag().equals("#FFE30000") && rbtn42.getTag().equals("#FFE8E15A"))

                    //the final place (wbtn2)
                    ||(wbtn22.getTag().equals("#FFE8E15A") && gbtn42.getTag().equals("#FFE30000") && obtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn22.getTag().equals("#FF1F8FE3") && gbtn42.getTag().equals("#FFE8E15A") && obtn32.getTag().equals("#FFE30000"))
                    ||(wbtn22.getTag().equals("#FFE30000") && gbtn42.getTag().equals("#FF1F8FE3") && obtn32.getTag().equals("#FFE8E15A"))

            )
            {}

            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }



            //check if the forth piece is in a possible position (24 case) (yellow orange blue)
            // yellow #FFE8E15A
            // orange #FFEF8426
            // blue   #FF1F8FE3


            if((ybtn42.getTag().equals("#FFE8E15A")&& gbtn22.getTag().equals("#FFEF8426")&&obtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn42.getTag().equals("#FF1F8FE3")&& gbtn22.getTag().equals("#FFE8E15A")&&obtn12.getTag().equals("#FFEF8426"))
                    || (ybtn42.getTag().equals("#FFEF8426")&& gbtn22.getTag().equals("#FF1F8FE3")&&obtn12.getTag().equals("#FFE8E15A"))

                    //the second place (ybtn3)
                    || (ybtn32.getTag().equals("#FFE8E15A") && gbtn12.getTag().equals("#FF1F8FE3")&& rbtn22.getTag().equals("#FFEF8426"))
                    || (ybtn32.getTag().equals("#FFEF8426") && gbtn12.getTag().equals("#FFE8E15A")&& rbtn22.getTag().equals("#FF1F8FE3"))
                    || (ybtn32.getTag().equals("#FF1F8FE3") && gbtn12.getTag().equals("#FFEF8426")&& rbtn22.getTag().equals("#FFE8E15A"))

                    //the third place (ybtn2)
                    || (ybtn22.getTag().equals("#FFE8E15A") && obtn22.getTag().equals("#FFEF8426" )&& bbtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn22.getTag().equals("#FF1F8FE3") && obtn22.getTag().equals("#FFE8E15A") && bbtn12.getTag().equals("#FFEF8426"))
                    || (ybtn22.getTag().equals("#FFEF8426") && obtn22.getTag().equals("#FF1F8FE3") && bbtn12.getTag().equals("#FFE8E15A"))

                    //the forth place (ybtn1)
                    ||(ybtn12.getTag().equals("#FFE8E15A") && rbtn12.getTag().equals("#FF1F8FE3") &&bbtn22.getTag().equals("#FFEF8426"))
                    ||(ybtn12.getTag().equals("#FFEF8426") && rbtn12.getTag().equals("#FFE8E15A") &&bbtn22.getTag().equals("#FF1F8FE3"))
                    ||(ybtn12.getTag().equals("#FF1F8FE3" )&& rbtn12.getTag().equals("#FFEF8426") &&bbtn22.getTag().equals("#FFE8E15A"))

                    //the fifth place (wbtn3)
                    ||(wbtn32.getTag().equals("#FFE8E15A") &&rbtn32.getTag().equals("#FFEF8426") && bbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn32.getTag().equals("#FF1F8FE3") &&rbtn32.getTag().equals("#FFE8E15A") && bbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn32.getTag().equals("#FFEF8426") &&rbtn32.getTag().equals("#FF1F8FE3") && bbtn42.getTag().equals("#FFE8E15A"))

                    //the sixth place (wbtn4)
                    ||(wbtn42.getTag().equals("#FFE8E15A") && obtn42.getTag().equals("#FF1F8FE3") &&bbtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn42.getTag().equals("#FFEF8426") && obtn42.getTag().equals("#FFE8E15A") &&bbtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn42.getTag().equals("#FF1F8FE3") && obtn42.getTag().equals("#FFEF8426") &&bbtn32.getTag().equals("#FFE8E15A"))

                    //the seventh place (wbtn1)
                    ||(wbtn12.getTag().equals("#FFE8E15A") && gbtn32.getTag().equals("#FFEF8426") && rbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn12.getTag().equals("#FF1F8FE3" )&& gbtn32.getTag().equals("#FFE8E15A") && rbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn12.getTag().equals("#FFEF8426" )&& gbtn32.getTag().equals("#FF1F8FE3") && rbtn42.getTag().equals("#FFE8E15A"))

                    //the final place (wbtn2)
                    ||(wbtn22.getTag().equals("#FFE8E15A") && gbtn42.getTag().equals("#FF1F8FE3") && obtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn22.getTag().equals("#FFEF8426") && gbtn42.getTag().equals("#FFE8E15A") && obtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn22.getTag().equals("#FF1F8FE3") && gbtn42.getTag().equals("#FFEF8426") && obtn32.getTag().equals("#FFE8E15A"))

            )
            {}

            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

            //check if the fifth piece is in a possible position (24 case) (white orange blue)

            if((ybtn42.getTag().equals("#FFFFFFFF")&& gbtn22.getTag().equals("#FF1F8FE3")&&obtn12.getTag().equals("#FFEF8426"))
                    || (ybtn42.getTag().equals("#FFEF8426")&& gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn42.getTag().equals("#FF1F8FE3")&& gbtn22.getTag().equals("#FFEF8426")&&obtn12.getTag().equals("#FFFFFFFF"))

                    //the second place (ybtn3) FF1F8FE3
                    || (ybtn32.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals("#FFEF8426")&& rbtn22.getTag().equals("#FF1F8FE3"))
                    || (ybtn32.getTag().equals("#FF1F8FE3") && gbtn12.getTag().equals("#FFFFFFFF")&& rbtn22.getTag().equals("#FFEF8426"))
                    || (ybtn32.getTag().equals("#FFEF8426") && gbtn12.getTag().equals("#FF1F8FE3")&& rbtn22.getTag().equals("#FFFFFFFF"))

                    //the third place (ybtn2) FFEF8426
                    || (ybtn22.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals("#FF1F8FE3" )&& bbtn12.getTag().equals("#FFEF8426"))
                    || (ybtn22.getTag().equals("#FFEF8426") && obtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn22.getTag().equals("#FF1F8FE3") && obtn22.getTag().equals("#FFEF8426") && bbtn12.getTag().equals("#FFFFFFFF"))

                    //the forth place (ybtn1) FF3A9A3E
                    ||(ybtn12.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals("#FFEF8426") &&bbtn22.getTag().equals("#FF1F8FE3"))
                    ||(ybtn12.getTag().equals("#FF1F8FE3") && rbtn12.getTag().equals("#FFFFFFFF") &&bbtn22.getTag().equals("#FFEF8426"))
                    ||(ybtn12.getTag().equals("#FFEF8426" )&& rbtn12.getTag().equals("#FF1F8FE3") &&bbtn22.getTag().equals("#FFFFFFFF"))

                    //the fifth place (wbtn3) FFEF8426
                    ||(wbtn32.getTag().equals("#FFFFFFFF") &&rbtn32.getTag().equals("#FF1F8FE3") && bbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn32.getTag().equals("#FFEF8426") &&rbtn32.getTag().equals("#FFFFFFFF") && bbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn32.getTag().equals("#FF1F8FE3") &&rbtn32.getTag().equals("#FFEF8426") && bbtn42.getTag().equals("#FFFFFFFF"))

                    //the sixth place (wbtn4) FF1F8FE3
                    ||(wbtn42.getTag().equals("#FFFFFFFF") && obtn42.getTag().equals("#FFEF8426") &&bbtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn42.getTag().equals("#FF1F8FE3") && obtn42.getTag().equals("#FFFFFFFF") &&bbtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn42.getTag().equals("#FFEF8426") && obtn42.getTag().equals("#FF1F8FE3") &&bbtn32.getTag().equals("#FFFFFFFF"))

                    //the seventh place (wbtn1) FFEF8426
                    ||(wbtn12.getTag().equals("#FFFFFFFF") && gbtn32.getTag().equals("#FF1F8FE3") && rbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn12.getTag().equals("#FFEF8426" )&& gbtn32.getTag().equals("#FFFFFFFF") && rbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn12.getTag().equals("#FF1F8FE3" )&& gbtn32.getTag().equals("#FFEF8426") && rbtn42.getTag().equals("#FFFFFFFF"))

                    //the final place (wbtn2) FF1F8FE3
                    ||(wbtn22.getTag().equals("#FFFFFFFF") && gbtn42.getTag().equals("#FFEF8426") && obtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn22.getTag().equals("#FF1F8FE3") && gbtn42.getTag().equals("#FFFFFFFF") && obtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn22.getTag().equals("#FFEF8426") && gbtn42.getTag().equals("#FF1F8FE3") && obtn32.getTag().equals("#FFFFFFFF"))

            )
            {}

            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

            //check if the sixth piece is in a possible position (24 case) (white orange green) FF3A9A3E


            if((ybtn42.getTag().equals("#FFFFFFFF")&& gbtn22.getTag().equals("#FFEF8426")&&obtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn42.getTag().equals("#FF3A9A3E")&& gbtn22.getTag().equals("#FFFFFFFF") &&obtn12.getTag().equals("#FFEF8426"))
                    || (ybtn42.getTag().equals("#FFEF8426")&& gbtn22.getTag().equals("#FF3A9A3E" )&&obtn12.getTag().equals("#FFFFFFFF"))

                    //the second place (ybtn3) FFEF8426
                    || (ybtn32.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals("#FF3A9A3E")&& rbtn22.getTag().equals("#FFEF8426"))
                    || (ybtn32.getTag().equals("#FFEF8426") && gbtn12.getTag().equals("#FFFFFFFF")&& rbtn22.getTag().equals("#FF3A9A3E"))
                    || (ybtn32.getTag().equals("#FF3A9A3E") && gbtn12.getTag().equals("#FFEF8426")&& rbtn22.getTag().equals("#FFFFFFFF"))

                    //the third place (ybtn2) FF3A9A3E
                    || (ybtn22.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals("#FFEF8426" )&& bbtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn22.getTag().equals("#FF3A9A3E") && obtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals("#FFEF8426"))
                    || (ybtn22.getTag().equals("#FFEF8426") && obtn22.getTag().equals("#FF3A9A3E") && bbtn12.getTag().equals("#FFFFFFFF"))

                    //the forth place (ybtn1) FFEF8426
                    ||(ybtn12.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals("#FF3A9A3E") &&bbtn22.getTag().equals("#FFEF8426"))
                    ||(ybtn12.getTag().equals("#FFEF8426") && rbtn12.getTag().equals("#FFFFFFFF" )&&bbtn22.getTag().equals("#FF3A9A3E"))
                    ||(ybtn12.getTag().equals("#FF3A9A3E") && rbtn12.getTag().equals("#FFEF8426") &&bbtn22.getTag().equals("#FFFFFFFF"))

                    //the fifth place (wbtn3) FF3A9A3E
                    ||(wbtn32.getTag().equals("#FFFFFFFF") &&rbtn32.getTag().equals("#FFEF8426" )&& bbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn32.getTag().equals("#FF3A9A3E") &&rbtn32.getTag().equals("#FFFFFFFF" )&& bbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn32.getTag().equals("#FFEF8426") &&rbtn32.getTag().equals("#FF3A9A3E") && bbtn42.getTag().equals("#FFFFFFFF"))

                    //the sixth place (wbtn4) FFEF8426
                    ||(wbtn42.getTag().equals("#FFFFFFFF") && obtn42.getTag().equals("#FF3A9A3E") &&bbtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn42.getTag().equals("#FFEF8426") && obtn42.getTag().equals("#FFFFFFFF") &&bbtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn42.getTag().equals("#FF3A9A3E") && obtn42.getTag().equals("#FFEF8426") &&bbtn32.getTag().equals("#FFFFFFFF"))

                    //the seventh place (wbtn1) FF3A9A3E
                    ||(wbtn12.getTag().equals("#FFFFFFFF") && gbtn32.getTag().equals("#FFEF8426") && rbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn12.getTag().equals("#FF3A9A3E") && gbtn32.getTag().equals("#FFFFFFFF") && rbtn42.getTag().equals("#FFEF8426"))
                    ||(wbtn12.getTag().equals("#FFEF8426") && gbtn32.getTag().equals("#FF3A9A3E") && rbtn42.getTag().equals("#FFFFFFFF"))

                    //the final place (wbtn2) FFEF8426
                    ||(wbtn22.getTag().equals("#FFFFFFFF") && gbtn42.getTag().equals("#FF3A9A3E" )&& obtn32.getTag().equals("#FFEF8426"))
                    ||(wbtn22.getTag().equals("#FFEF8426") && gbtn42.getTag().equals("#FFFFFFFF") && obtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn22.getTag().equals("#FF3A9A3E") && gbtn42.getTag().equals("#FFEF8426") && obtn32.getTag().equals("#FFFFFFFF"))

            )

            {}
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

//
            //check if the seventh piece is in a possible position (24 case) (white red green) FFE30000

            if((ybtn42.getTag().equals("#FFFFFFFF")&& gbtn22.getTag().equals("#FF3A9A3E")&&obtn12.getTag().equals("#FFE30000"))
                    || (ybtn42.getTag().equals("#FFE30000")&& gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn42.getTag().equals("#FF3A9A3E")&& gbtn22.getTag().equals("#FFE30000")&&obtn12.getTag().equals("#FFFFFFFF"))

                    //the second place (ybtn3) FF3A9A3E
                    || (ybtn32.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals("#FFE30000")&& rbtn22.getTag().equals("#FF3A9A3E"))
                    || (ybtn32.getTag().equals("#FF3A9A3E") && gbtn12.getTag().equals("#FFFFFFFF")&& rbtn22.getTag().equals("#FFE30000"))
                    || (ybtn32.getTag().equals("#FFE30000") && gbtn12.getTag().equals("#FF3A9A3E")&& rbtn22.getTag().equals("#FFFFFFFF"))

                    //the third place (ybtn2) FFE30000
                    || (ybtn22.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals("#FF3A9A3E" )&& bbtn12.getTag().equals("#FFE30000"))
                    || (ybtn22.getTag().equals("#FFE30000") && obtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals("#FF3A9A3E"))
                    || (ybtn22.getTag().equals("#FF3A9A3E") && obtn22.getTag().equals("#FFE30000") && bbtn12.getTag().equals("#FFFFFFFF"))

                    //the forth place (ybtn1) FF3A9A3E
                    ||(ybtn12.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals("#FFE30000") &&bbtn22.getTag().equals("#FF3A9A3E"))
                    ||(ybtn12.getTag().equals("#FF3A9A3E") && rbtn12.getTag().equals("#FFFFFFFF") &&bbtn22.getTag().equals("#FFE30000"))
                    ||(ybtn12.getTag().equals("#FFE30000" )&& rbtn12.getTag().equals("#FF3A9A3E") &&bbtn22.getTag().equals("#FFFFFFFF"))

                    //the fifth place (wbtn3) FFE30000
                    ||(wbtn32.getTag().equals("#FFFFFFFF") &&rbtn32.getTag().equals("#FF3A9A3E") && bbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn32.getTag().equals("#FFE30000") &&rbtn32.getTag().equals("#FFFFFFFF") && bbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn32.getTag().equals("#FF3A9A3E") &&rbtn32.getTag().equals("#FFE30000") && bbtn42.getTag().equals("#FFFFFFFF"))

                    //the sixth place (wbtn4) FF3A9A3E
                    ||(wbtn42.getTag().equals("#FFFFFFFF") && obtn42.getTag().equals("#FFE30000") &&bbtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn42.getTag().equals("#FF3A9A3E") && obtn42.getTag().equals("#FFFFFFFF") &&bbtn32.getTag().equals("#FFE30000"))
                    ||(wbtn42.getTag().equals("#FFE30000") && obtn42.getTag().equals("#FF3A9A3E") &&bbtn32.getTag().equals("#FFFFFFFF"))

                    //the seventh place (wbtn1) FFE30000
                    ||(wbtn12.getTag().equals("#FFFFFFFF") && gbtn32.getTag().equals("#FF3A9A3E") && rbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn12.getTag().equals("#FFE30000" )&& gbtn32.getTag().equals("#FFFFFFFF") && rbtn42.getTag().equals("#FF3A9A3E"))
                    ||(wbtn12.getTag().equals("#FF3A9A3E" )&& gbtn32.getTag().equals("#FFE30000") && rbtn42.getTag().equals("#FFFFFFFF"))

                    //the final place (wbtn2) FF3A9A3E
                    ||(wbtn22.getTag().equals("#FFFFFFFF") && gbtn42.getTag().equals("#FFE30000") && obtn32.getTag().equals("#FF3A9A3E"))
                    ||(wbtn22.getTag().equals("#FF3A9A3E") && gbtn42.getTag().equals("#FFFFFFFF") && obtn32.getTag().equals("#FFE30000"))
                    ||(wbtn22.getTag().equals("#FFE30000") && gbtn42.getTag().equals("#FF3A9A3E") && obtn32.getTag().equals("#FFFFFFFF"))

            )
            {}
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }

            //check if the finel piece is in a possible position (24 case) (white red blue) FF1F8FE3


            if((ybtn42.getTag().equals("#FFFFFFFF")&& gbtn22.getTag().equals("#FFE30000")&&obtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn42.getTag().equals("#FF1F8FE3")&& gbtn22.getTag().equals("#FFFFFFFF")&&obtn12.getTag().equals("#FFE30000"))
                    || (ybtn42.getTag().equals("#FFE30000")&& gbtn22.getTag().equals("#FF1F8FE3")&&obtn12.getTag().equals("#FFFFFFFF"))

                    //the second place (ybtn3) FFE30000
                    || (ybtn32.getTag().equals("#FFFFFFFF") && gbtn12.getTag().equals("#FF1F8FE3")&& rbtn22.getTag().equals("#FFE30000"))
                    || (ybtn32.getTag().equals("#FFE30000") && gbtn12.getTag().equals("#FFFFFFFF")&& rbtn22.getTag().equals("#FF1F8FE3"))
                    || (ybtn32.getTag().equals("#FF1F8FE3") && gbtn12.getTag().equals("#FFE30000")&& rbtn22.getTag().equals("#FFFFFFFF"))

                    //the third place (ybtn2) FF1F8FE3
                    || (ybtn22.getTag().equals("#FFFFFFFF") && obtn22.getTag().equals("#FFE30000" )&& bbtn12.getTag().equals("#FF1F8FE3"))
                    || (ybtn22.getTag().equals("#FF1F8FE3") && obtn22.getTag().equals("#FFFFFFFF") && bbtn12.getTag().equals("#FFE30000"))
                    || (ybtn22.getTag().equals("#FFE30000") && obtn22.getTag().equals("#FF1F8FE3") && bbtn12.getTag().equals("#FFFFFFFF"))

                    //the forth place (ybtn1) FFE30000
                    ||(ybtn12.getTag().equals("#FFFFFFFF") && rbtn12.getTag().equals("#FF1F8FE3") &&bbtn22.getTag().equals("#FFE30000"))
                    ||(ybtn12.getTag().equals("#FFE30000") && rbtn12.getTag().equals("#FFFFFFFF") &&bbtn22.getTag().equals("#FF1F8FE3"))
                    ||(ybtn12.getTag().equals("#FF1F8FE3" )&& rbtn12.getTag().equals("#FFE30000") &&bbtn22.getTag().equals("#FFFFFFFF"))

                    //the fifth place (wbtn3) FF1F8FE3
                    ||(wbtn32.getTag().equals("#FFFFFFFF") &&rbtn32.getTag().equals("#FFE30000") && bbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn32.getTag().equals("#FF1F8FE3") &&rbtn32.getTag().equals("#FFFFFFFF") && bbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn32.getTag().equals("#FFE30000") &&rbtn32.getTag().equals("#FF1F8FE3") && bbtn42.getTag().equals("#FFFFFFFF"))

                    //the sixth place (wbtn4) FFE30000
                    ||(wbtn42.getTag().equals("#FFFFFFFF") && obtn42.getTag().equals("#FF1F8FE3") &&bbtn32.getTag().equals("#FFE30000"))
                    ||(wbtn42.getTag().equals("#FFE30000") && obtn42.getTag().equals("#FFFFFFFF") &&bbtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn42.getTag().equals("#FF1F8FE3") && obtn42.getTag().equals("#FFE30000") &&bbtn32.getTag().equals("#FFFFFFFF"))

                    //the seventh place (wbtn1) FF1F8FE3
                    ||(wbtn12.getTag().equals("#FFFFFFFF") && gbtn32.getTag().equals("#FFE30000") && rbtn42.getTag().equals("#FF1F8FE3"))
                    ||(wbtn12.getTag().equals("#FF1F8FE3" )&& gbtn32.getTag().equals("#FFFFFFFF") && rbtn42.getTag().equals("#FFE30000"))
                    ||(wbtn12.getTag().equals("#FFE30000" )&& gbtn32.getTag().equals("#FF1F8FE3") && rbtn42.getTag().equals("#FFFFFFFF"))

                    //the final place (wbtn2) FFE30000
                    ||(wbtn22.getTag().equals("#FFFFFFFF") && gbtn42.getTag().equals("#FF1F8FE3") && obtn32.getTag().equals("#FFE30000"))
                    ||(wbtn22.getTag().equals("#FFE30000") && gbtn42.getTag().equals("#FFFFFFFF") && obtn32.getTag().equals("#FF1F8FE3"))
                    ||(wbtn22.getTag().equals("#FF1F8FE3") && gbtn42.getTag().equals("#FFE30000") && obtn32.getTag().equals("#FFFFFFFF"))

            )
            {}

            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

                builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


                return;
            }





            solve();
            moves.clear();
            solution = ss;

            wbtn42.setActivated(false);
            wbtn42.setClickable(false);
            wbtn12.setActivated(false);
            wbtn22.setActivated(false);
            wbtn32.setActivated(false);

            rbtn12.setActivated(false);
            rbtn22.setActivated(false);
            rbtn32.setActivated(false);
            rbtn42.setActivated(false);

            gbtn12.setActivated(false);
            gbtn22.setActivated(false);
            gbtn32.setActivated(false);
            gbtn42.setActivated(false);

            obtn12.setActivated(false);
            obtn22.setActivated(false);
            obtn32.setActivated(false);
            obtn42.setActivated(false);

            bbtn12.setActivated(false);
            bbtn22.setActivated(false);
            bbtn32.setActivated(false);
            bbtn42.setActivated(false);

            ybtn12.setActivated(false);
            ybtn22.setActivated(false);
            ybtn32.setActivated(false);
            ybtn42.setActivated(false);


            //starting to take the string to apply it to  show the solution


            for (int i = 0; i < solution.length(); i++) {
                if (i == 0 && solution.charAt(0) != ' ')
                    moves.add(solution.charAt(i) + "");
                else if (solution.charAt(i) == '\'' || solution.charAt(i) == '2') {

                    String tmp = moves.get(moves.size() - 1) + solution.charAt(i);
                    moves.remove(moves.size() - 1);
                    moves.add(tmp);
                } else if (solution.charAt(i) == ' ') ;
                else if (solution.charAt(i) == 'R' || solution.charAt(i) == 'L'
                        || solution.charAt(i) == 'U' || solution.charAt(i) == 'D'
                        || solution.charAt(i) == 'F' || solution.charAt(i) == 'B')
                    moves.add(solution.charAt(i) + "");

            }
            for (int i = 0; i < moves.size() - 1; i++) {
                if (moves.get(i).equals("F") && moves.get(i + 1).equals("F")) {
                    moves.remove(i + 1);
                    moves.set(i, "F2");
                } else if (moves.get(i).equals("F'") && moves.get(i + 1).equals("F'")) {
                    moves.remove(i + 1);
                    moves.set(i, "F'2");
                } else if (moves.get(i).equals("B") && moves.get(i + 1).equals("B")) {
                    moves.remove(i + 1);
                    moves.set(i, "B2");
                } else if (moves.get(i).equals("B'") && moves.get(i + 1).equals("B'")) {
                    moves.remove(i + 1);
                    moves.set(i, "B'2");
                } else if (moves.get(i).equals("U") && moves.get(i + 1).equals("U")) {
                    moves.remove(i + 1);
                    moves.set(i, "U2");
                } else if (moves.get(i).equals("U'") && moves.get(i + 1).equals("U'")) {
                    moves.remove(i + 1);
                    moves.set(i, "U'2");
                } else if (moves.get(i).equals("D") && moves.get(i + 1).equals("D")) {
                    moves.remove(i + 1);
                    moves.set(i, "D2");
                } else if (moves.get(i).equals("D'") && moves.get(i + 1).equals("D'")) {
                    moves.remove(i + 1);
                    moves.set(i, "D'2");
                } else if (moves.get(i).equals("R") && moves.get(i + 1).equals("R")) {
                    moves.remove(i + 1);
                    moves.set(i, "R2");
                } else if (moves.get(i).equals("R'") && moves.get(i + 1).equals("R'")) {
                    moves.remove(i + 1);
                    moves.set(i, "R'2");
                } else if (moves.get(i).equals("L") && moves.get(i + 1).equals("L")) {
                    moves.remove(i + 1);
                    moves.set(i, "L2");
                } else if (moves.get(i).equals("L") && moves.get(i + 1).equals("L")) {
                    moves.remove(i + 1);
                    moves.set(i, "L2");
                }
            }

            for (int i = 0; i < moves.size() - 1; i++) {
                if (moves.get(i).equals("U'") && moves.get(i + 1).equals("U")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("U") && moves.get(i + 1).equals("U'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;

                }

                if (moves.get(i).equals("D'") && moves.get(i + 1).equals("D")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("D") && moves.get(i + 1).equals("D'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;

                } else if (moves.get(i).equals("R'") && moves.get(i + 1).equals("R")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("R") && moves.get(i + 1).equals("R'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("L'") && moves.get(i + 1).equals("L")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("L") && moves.get(i + 1).equals("L'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("F'") && moves.get(i + 1).equals("F")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("F") && moves.get(i + 1).equals("F'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("B'") && moves.get(i + 1).equals("B")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                } else if (moves.get(i).equals("B") && moves.get(i + 1).equals("B'")) {
                    moves.remove(i);
                    moves.remove(i);
                    i--;
                }


            }

            progressBar.setMax(moves.size());

            arraySizeRemain=moves.size()%3;

            if(moves.size()==1)
                secondMove.setText(moves.get(0));


            // there is six cases for the solution display
            // the first three if the solution algorithm length less than four
            // the rest will be if the solution algorithm length is more than three which will depend on the remaining of the size
            // of the moves array % 3 but it should not effect the way the next and previous buttons work



            if(moves.size()==3)
            {
                firstMove.setText(moves.get(0));
                secondMove.setText((moves.get(1)));
                thirdMove.setText(moves.get(2));

                firstMove.setTypeface(Typeface.DEFAULT_BOLD);

            }
            else if(moves.size()==2)
            {
                firstMove.setText(moves.get(0));
                secondMove.setText((moves.get(1)));
                firstMove.setTypeface(Typeface.DEFAULT_BOLD);

            }
            else if(moves.size()==1)
            {
                secondMove.setText(moves.get(0));
                secondMove.setTypeface(Typeface.DEFAULT_BOLD);

            }
            else{
                firstMove.setText(moves.get(0));
                secondMove.setText((moves.get(1)));
                thirdMove.setText(moves.get(2));

                firstMove.setTypeface(Typeface.DEFAULT);
                secondMove.setTypeface(Typeface.DEFAULT);
                thirdMove.setTypeface(Typeface.DEFAULT);

                firstMove.setTextSize(20);
                secondMove.setTextSize(20);
                thirdMove.setTextSize(20);
            }


            rightArrow.setImageResource(R.drawable.whiterightarrow);
        }
        catch (Exception e){


            AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

            builder1.setMessage("The inserted cube is impossible, re-enter the colors");
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


        }

    }

    public void orange(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");



        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgtsyleclicked1);
        ybtn.setBackgroundResource(R.drawable.rgstyle2);
        gbtn.setBackgroundResource(R.drawable.rgstyle3);
        bbtn.setBackgroundResource(R.drawable.rgstyle4);
        wbtn.setBackgroundResource(R.drawable.rgstyle5);
        rbtn.setBackgroundResource(R.drawable.rgstyle6);
    }

    public void red(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");


        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgstyle);
        ybtn.setBackgroundResource(R.drawable.rgstyle2);
        gbtn.setBackgroundResource(R.drawable.rgstyle3);
        bbtn.setBackgroundResource(R.drawable.rgstyle4);
        wbtn.setBackgroundResource(R.drawable.rgstyle5);
        rbtn.setBackgroundResource(R.drawable.rgstyle6clicked1);

    }

    public void white(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");


        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgstyle);
        ybtn.setBackgroundResource(R.drawable.rgstyle2);
        gbtn.setBackgroundResource(R.drawable.rgstyle3);
        bbtn.setBackgroundResource(R.drawable.rgstyle4);
        wbtn.setBackgroundResource(R.drawable.rgstyle5clicked1);
        rbtn.setBackgroundResource(R.drawable.rgstyle6);
    }

    public void blue(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");


        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgstyle);
        ybtn.setBackgroundResource(R.drawable.rgstyle2);
        gbtn.setBackgroundResource(R.drawable.rgstyle3);
        bbtn.setBackgroundResource(R.drawable.rgstyle4clicked1);
        wbtn.setBackgroundResource(R.drawable.rgstyle5);
        rbtn.setBackgroundResource(R.drawable.rgstyle6);
    }

    public void green(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");


        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgstyle);
        ybtn.setBackgroundResource(R.drawable.rgstyle2);
        gbtn.setBackgroundResource(R.drawable.rgstyle3clicked1);
        bbtn.setBackgroundResource(R.drawable.rgstyle4);
        wbtn.setBackgroundResource(R.drawable.rgstyle5);
        rbtn.setBackgroundResource(R.drawable.rgstyle6);
    }

    public void yellow(View view) {
        gifImageView.setVisibility(View.GONE);
        displaymoves.setText("");
        displaymoves2.setText("");


        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        obtn.setBackgroundResource(R.drawable.rgstyle);
        ybtn.setBackgroundResource(R.drawable.rgstyle2clicked1);
        gbtn.setBackgroundResource(R.drawable.rgstyle3);
        bbtn.setBackgroundResource(R.drawable.rgstyle4);
        wbtn.setBackgroundResource(R.drawable.rgstyle5);
        rbtn.setBackgroundResource(R.drawable.rgstyle6);
    }


//    public void settings(View view) {
//        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
//        soundEffect.start();
//
//        // inflate the layout of the popup window
//        LayoutInflater inflater = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.activity_settingspopup, null);
//
//        // create the popup window
//        int width = 800;
//        int height = 1000;
//        boolean focusable = true; // lets taps outside the popup also dismiss it
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        // show the popup window
//        // which view you pass in doesn't matter, it is only used for the window tolken
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//        // dismiss the popup window when touched
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//                return true;
//            }
//        });
//    }


    public void help(View view) {
        gifImageView.setVisibility(View.GONE);
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {
            Intent i=new Intent(this,Settingspopup.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }

    }
//    public void onDestroy() {
//        super.onDestroy();
//        tooltips.onDestroy();
//        tooltips = null;
//    }


}









