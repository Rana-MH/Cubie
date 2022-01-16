package com.example.cubie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resetpass extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    MediaPlayer soundEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
        email=findViewById(R.id.emailedit);
        reset=(Button)findViewById(R.id.resetpassbtn);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        auth=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            soundEffect = MediaPlayer.create(Resetpass.this,R.raw.colorscound);
            soundEffect.start();
            resetPass();

        }
       });


    }

    private void resetPass() {
        String email1=email.getText().toString().trim();
        if(email1.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Resetpass.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
                else{
                    Toast.makeText(Resetpass.this, "Try again, Something went wrong!", Toast.LENGTH_LONG).show();


                }
            }
        });
    }


    public void back(View view) {
        try {

            Intent i=new Intent(getBaseContext(),Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }
}