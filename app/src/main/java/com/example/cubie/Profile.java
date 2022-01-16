package com.example.cubie;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Profile extends AppCompatActivity {
    private Spinner spinner;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String userId;
    public Uri imageUri;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    public StorageReference ref;
    private ProgressDialog pd2;
    String name, namee, email, gender, age;
    public TextView welcomeuser;
    public EditText name1, email1, age1, gender1;
    private static final String[] paths = {"Choose", "Female", "Male"};
    ImageView picture, addpic;
    MediaPlayer soundEffect;
    EditText timeRecord;
    SharedPreferences prefs3 ;
    String bestData ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFFFF"));

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        ref = storage.getReference();
        StorageReference ref2 = ref.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(picture);
            }
        });
        timeRecord=findViewById(R.id.timerrecord);
        prefs3 = getSharedPreferences("prefs3",MODE_PRIVATE);
        bestData = prefs3.getString("bestData", "00:00:00");
        timeRecord.setText(prefs3.getString("bestData", "00:00:00"));

        picture = findViewById(R.id.imageView16);
        addpic = findViewById(R.id.imageView29);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        welcomeuser = findViewById(R.id.textView12);
        name1 = findViewById(R.id.textView14);
        email1 = findViewById(R.id.textView21);
        age1 = findViewById(R.id.textView49);
        gender1 = findViewById(R.id.textView47);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
//                if(userprofile !=null){
                if (user.isEmailVerified()) {
                    name = userprofile.name;
                    namee = userprofile.name;
                    email = userprofile.email;
                    gender = userprofile.gender;
                    age = userprofile.date;
                    welcomeuser.setText(name + " " + "!");
                    name1.setText(namee);
                    email1.setText(email);
                    gender1.setText(gender);
                    age1.setText(age);


                } else {
                    // open profile leave dialog box telling the user he must verfiy
                    Intent intent2 = new Intent(Profile.this, Login.class);
                    startActivity(intent2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Profile.this, "Something wrong happened", Toast.LENGTH_LONG).show();

            }
        });
        BottomNavigationView bottomnav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
                        soundEffect.start();
                        Intent intent1 = new Intent(Profile.this, Home.class);
                        startActivity(intent1);
                        break;
                    case R.id.page_2:
                        soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
                        soundEffect.start();
                        Intent intent2 = new Intent(Profile.this, Intro.class);
                        startActivity(intent2);
                        break;

                    case R.id.page_3:
                        soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
                        soundEffect.start();
                        Intent intent3 = new Intent(Profile.this, Profile.class);
                        startActivity(intent3);
                        break;

                    case R.id.page_4: {
                        soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
                        soundEffect.start();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(Profile.this, Login.class);
                        startActivity(intent4);
                        break;
                    }
                }


                return false;
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
                soundEffect.start();
                choosepic();
            }
        });


    }

    private void choosepic() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            //   picture.setImageURI(imageUri);
            uploadPic();
        }
    }

    private void uploadPic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
//        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = ref.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(picture);
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Faild to upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + " %");
                    }
                });

    }

    //    public void onItemSelected(Profile parent, View v, int position, long id) {
//
//        switch (position) {
//            case 0:
//                // Whatever you want to happen when the first item gets selected
//                break;
//            case 1:
//                // Whatever you want to happen when the second item gets selected
//                break;
//        }
//    }
    public void back(View view) {
        try {
            soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
            soundEffect.start();

            Intent i = new Intent(getBaseContext(), Home.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
        }
    }

    // save button on click to save changes made by the user
    public void save(View view) {
        soundEffect = MediaPlayer.create(Profile.this, R.raw.colorscound);
        soundEffect.start();

        if (isNameChanged() || isAgeChanged() || isGenderChanged()) {
            Toast.makeText(this, "Changes are saved", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isGenderChanged() {

        if (!(gender.equals(gender1.getText().toString()))) {
            reference.child(userId).child("gender").setValue(gender1.getText().toString());
            gender = gender1.getText().toString();
            return true;
        }

        else {
            return false;
        }

    }

    private boolean isAgeChanged() {

        if (!(age.equals(age1.getText().toString()))) {
            reference.child(userId).child("date").setValue(age1.getText().toString());
            age = age1.getText().toString();
            return true;
        }

        else {
            return false;
        }

    }

    private boolean isNameChanged() {
        if (!(name.equals(name1.getText().toString()))) {
            reference.child(userId).child("name").setValue(name1.getText().toString());
            namee = name1.getText().toString();
            return true;
        } else {
            return false;
        }

    }

}