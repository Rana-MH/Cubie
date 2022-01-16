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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText emailedit, passwordedit ,nameedit , passconfirm, genderedit,dateedit;
     private Button signupbtn;
     MediaPlayer soundEffect;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));
        mAuth = FirebaseAuth.getInstance();
        signupbtn= findViewById(R.id.signup_button);
        signupbtn.setOnClickListener(this);
        emailedit= findViewById(R.id.passedit);
        genderedit= findViewById(R.id.passedit3);
        dateedit= findViewById(R.id.passedit2);
        passwordedit= findViewById(R.id.login_edit3);
        nameedit= findViewById(R.id.emailedit);
        passconfirm= findViewById(R.id.login_edit4);
        progressBar=findViewById(R.id.progressBar2);

            }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_button:
                soundEffect = MediaPlayer.create(this,R.raw.colorscound);
                soundEffect.start();
                signupbtn(); break;
        }
    }

    private void signupbtn() {
        String email=emailedit.getText().toString().trim();
        String pass=passwordedit.getText().toString().trim();
        String name=nameedit.getText().toString().trim();
        String passcon=passconfirm.getText().toString().trim();
        String gender=genderedit.getText().toString().trim();
        String date=dateedit.getText().toString().trim();

        if(gender.isEmpty()) {
            genderedit.setError("Gender is required");
            genderedit.requestFocus();
            return;
        }

            if(!(gender.matches("Female")  || gender.matches("female")|| gender.matches("Male" )||
                    gender.matches("male"))){
                genderedit.setError("Gender is either Female or Male");
                genderedit.requestFocus();
                return;
            }
        if(date.isEmpty()) {
            dateedit.setError("Age is required");
            dateedit.requestFocus();
            return;
        }

        if(name.isEmpty()){
            nameedit.setError("Name is required");
            nameedit.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailedit.setError("Email is required");
            emailedit.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedit.setError("Please enter a valid email");
            emailedit.requestFocus();
            return;
        }
        if(pass.isEmpty()) {
            passwordedit.setError("Password is required");
            passwordedit.requestFocus();
            return;
        }
        if(pass.length()<6){
            passwordedit.setError("Password must be at least 6 characters");
            passwordedit.requestFocus();
            return;
        }
        if(passcon.isEmpty()) {
            passconfirm.setError("Password confirm is required");
            passconfirm.requestFocus();
            return;
        }
        if(!(passcon.equals(pass))){
            passconfirm.setError("Password doesn't match");
            passconfirm.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(email,name, date,gender);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                            Toast.makeText(Signup.this,"Registered successfully!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                                        try {
                                            Intent i=new Intent(getBaseContext(),Login.class);
                                            startActivity(i);
                                            finish();
                                        } catch (Exception e) { }
                                    }
                                    else
                       Toast.makeText(Signup.this,"Failed to register, Try again!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                        else
                            Toast.makeText(Signup.this,"Failed to register, Try again!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

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

    public void login(View view) {
        soundEffect = MediaPlayer.create(this,R.raw.colorscound);
        soundEffect.start();
        try {

            Intent i=new Intent(getBaseContext(),Login.class);
            startActivity(i);
            finish();
        } catch (Exception e) { }
    }

}