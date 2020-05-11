package com.example.fm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Options extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //Variables
    String ownerid;
    TextView welcome;
    Button addfreight,goList,logout;

     private CollectionReference colref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Hooks
        welcome = findViewById(R.id.welcome);
        addfreight= findViewById(R.id.addfrieght);
        goList = findViewById(R.id.listofitems);
        logout = findViewById(R.id.logout);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fstore= FirebaseFirestore.getInstance();

        ownerid= mAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fstore.collection("owners").document(ownerid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome.setText("Welcome\n"+documentSnapshot.getString("uname")+"!");
            }
        });


        addfreight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Options.this,Newgoods.class));
            }
        });

        goList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Options.this,Listgoods.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                finish();
            }
        });

    }
}
