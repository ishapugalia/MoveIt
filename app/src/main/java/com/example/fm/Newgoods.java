package com.example.fm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Newgoods extends AppCompatActivity {

    //Variables
    TextInputLayout prodname,numofP,numofB,typeof,weight1;
    String ownerid;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgoods);

        //Hooks
        prodname = findViewById(R.id.pname);
        numofP = findViewById(R.id.nump);
        numofB = findViewById(R.id.numb);
        typeof = findViewById(R.id.typeof);
        weight1 = findViewById(R.id.weight);

        //Instance linking
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    }

    public void addGoods(){
        String productName,type;
        int pieces,boxes;
        double weight;

        productName = prodname.getEditText().getText().toString().trim();
        type = typeof.getEditText().getText().toString().trim();
        pieces = Integer.parseInt(numofP.getEditText().getText().toString().trim());
        boxes = Integer.parseInt(numofB.getEditText().getText().toString().trim());
        weight = Double.parseDouble(weight1.getEditText().getText().toString().trim());

        if (productName.isEmpty()) {
            prodname.setError("Name is required!");
            prodname.requestFocus();
            return;
        }
        if (type.isEmpty()) {
            typeof.setError("Type is required!");
            typeof.requestFocus();
            return;
        }

        ownerid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("owners").document(ownerid).collection("Goods");




    }
}
