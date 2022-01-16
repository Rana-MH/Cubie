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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
private TextView signup, forgotpass;
private EditText email1, password1;
private Button login;
private FirebaseAuth mAuth;
private ProgressBar progressBar;
MediaPlayer soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
       signup=(TextView)findViewById(R.id.login_textview3);
        forgotpass=(TextView)findViewById(R.id.login_textview2);
        login=(Button) findViewById(R.id.Loginbtn);
        login.setOnClickListener(this);
        email1=(EditText) findViewById(R.id.emailedit);
        password1=(EditText) findViewById(R.id.passedit);
        progressBar=findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(this);
        forgotpass.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_textview3:  try {soundEffect = MediaPlayer.create(this,R.raw.colorscound);
                soundEffect.start();
                Intent i=new Intent(getBaseContext(),Signup.class);
                startActivity(i);
                finish();
            } catch (Exception e) { } break;

            case R.id.Loginbtn:
                soundEffect = MediaPlayer.create(this,R.raw.colorscound);
                soundEffect.start();
                userLogin();
              break;
            case R.id.login_textview2:
                soundEffect = MediaPlayer.create(this,R.raw.colorscound);
                soundEffect.start();
                try {
                    Intent i=new Intent(getBaseContext(),Resetpass.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) { }
                break;


        }

    }

    private void userLogin() {
        String email=email1.getText().toString().trim();
        String pass=password1.getText().toString().trim();
        if(email.isEmpty()){
            email1.setError("Email is required");
            email1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email1.setError("Please enter a valid email");
            email1.requestFocus();
            return;
        }
        if(pass.isEmpty()) {
            password1.setError("Password is required");
            password1.requestFocus();
            return;
        }
        if(pass.length()<6) {
            password1.setError("Password must be at least 6 characters");
            password1.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        try {
                            Intent i = new Intent(getBaseContext(), Home.class);
                            startActivity(i);
                            finish();
                        } catch (Exception e) {
                        }
                    }
                    else {

                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }


                }
                else
                {
                    Toast.makeText(Login.this,"Failed to login, Try again!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);}



            }
        });



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
}