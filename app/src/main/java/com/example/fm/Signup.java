package com.example.fm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.lang.Throwable;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    //Variables
    TextInputLayout editName,editUsername,editEmail,editPhnumber,editPassword;
    Button register,login;
     String ownerid;
     public static final String TAG ="TAG";
    //Firebase
    private FirebaseAuth mAuth;
    //FirebaseDatabase rootNode;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Hooks
        editName = findViewById(R.id.name);
        editUsername = findViewById(R.id.username);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPhnumber = findViewById(R.id.phone);
        register = findViewById(R.id.register);
        login = findViewById(R.id.haveacc);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fstore= FirebaseFirestore.getInstance();

       // register.setOnClickListener((View.OnClickListener) this);
        //UserHelperClass helperClass = new UserHelperClass(name,username,email,phone,password);
            //reference.child(username).setValue(helperClass));

           /* final String name = editName.getEditText().getText().toString().trim();
            final String username = editUsername.getEditText().getText().toString().trim();
            final String email = editEmail.getEditText().getText().toString().trim();
            final String phone = editPhnumber.getEditText().getText().toString().trim();
            String password = editPassword.getEditText().getText().toString().trim();*/
    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String name, username, email, phone, password;
            name = editName.getEditText().getText().toString().trim();
            username = editUsername.getEditText().getText().toString().trim();
            email = editEmail.getEditText().getText().toString().trim();
            phone = editPhnumber.getEditText().getText().toString().trim();
            password = editPassword.getEditText().getText().toString().trim();
            if (email.isEmpty()) {
                editEmail.setError("Email is required!");
                editEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.setError("Email is invalid!");
                editEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editPassword.setError("Password is required!");
                editPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                editPassword.setError("Password is too short!");
                editPassword.requestFocus();
                return;
            }


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        ownerid = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("owners").document(ownerid);
                        Map<String, Object> owner = new HashMap<>();
                        owner.put("fname", name);
                        owner.put("uname", username);
                        owner.put("phone", phone);
                        owner.put("email", email);
                        documentReference.set(owner).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "owner profile is created!" + ownerid);
                            }
                        });
                        startActivity(new Intent(Signup.this, Options.class));
                        finish();
                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "Account already exists!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });
        }
    });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, LoginActivity.class));
            }



            });


    }
}


