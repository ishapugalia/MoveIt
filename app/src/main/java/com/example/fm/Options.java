package com.example.fm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    String ownerid;
    TextView welcome;
    Button addfreight,goList;
     private CollectionReference colref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        welcome = findViewById(R.id.welcome);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fstore= FirebaseFirestore.getInstance();
        addfreight= findViewById(R.id.addfrieght);
        goList = findViewById(R.id.listofitems);

        ownerid= mAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fstore.collection("owners").document(ownerid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome.setText("Welcome ! "+documentSnapshot.getString("uname"));
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

    }
}
