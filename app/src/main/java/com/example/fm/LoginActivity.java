package com.example.fm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity {

    //Variables
    Button callSignup,logIn;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout username,passwd;

    //Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        callSignup = findViewById(R.id.signup);
        logIn = findViewById(R.id.login);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        passwd = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();



        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,Signup.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(username,"username_trans");
                pairs[4] = new Pair<View,String>(passwd,"password_trans");
                pairs[5] = new Pair<View,String>(logIn,"button_trans");
                pairs[6] = new Pair<View,String>(callSignup,"signup_trans");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(i,options.toBundle());


            }
        });
    }

    public void userLogin(View view){

        String email,password;
        email = username.getEditText().getText().toString().trim();
        password = passwd.getEditText().getText().toString().trim();

        if(email.isEmpty()) {
            username.setError("Email is required!");
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Email is invalid!");
            username.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            passwd.setError("Password is required!");
            passwd.requestFocus();
            return;
        }
        if(password.length()<6) {
            passwd.setError("Password is too short!");
            passwd.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   startActivity(new Intent(LoginActivity.this,Options.class));
                    finish();
               }
               else{
                   Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

               }
            }
        });




    }
}
