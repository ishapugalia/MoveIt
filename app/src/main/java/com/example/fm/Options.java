package com.example.fm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Options extends AppCompatActivity {
    private FirebaseAuth mAuth;
     String ownerid;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        welcome = findViewById(R.id.welcome);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fstore= FirebaseFirestore.getInstance();
        ownerid= mAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fstore.collection("owners").document(ownerid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome.setText("Welcome ! "+documentSnapshot.getString("uname"));
            }
        });
    }
}
