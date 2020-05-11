package com.example.fm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Newgoods extends AppCompatActivity {

    //Variables
    TextInputLayout prodname,numofP,numofB,typeof,weight1;
    String ownerid;
    String goodsid;
    Switch fragile;
    ProgressBar progressBar;

    Button register;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private CollectionReference colref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgoods);

        //Hooks
        prodname = findViewById(R.id.pname);
        numofP = findViewById(R.id.pieces);
        numofB = findViewById(R.id.numb);
        typeof = findViewById(R.id.typeof);
        weight1 = findViewById(R.id.weight);
         fragile=findViewById(R.id.fragility);
         register=findViewById(R.id.register);
         progressBar = findViewById(R.id.progressbar3);

        //Instance linking
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
         colref = firestore.collection("owners");
    }

    public void addGoods(View view){
        final String productName,type;
        final int pieces,boxes;
        final int weight;
        final boolean fragile1;

        productName = prodname.getEditText().getText().toString().trim();
        type = typeof.getEditText().getText().toString().trim();
        pieces = Integer.parseInt(numofP.getEditText().getText().toString().trim());
        boxes = Integer.parseInt(numofB.getEditText().getText().toString().trim());
        weight = Integer.parseInt(weight1.getEditText().getText().toString().trim());
        fragile1 = fragile.isChecked();

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
        if(pieces==0){
            numofP.setError("No. of Pieces must be more than 0");
            numofP.requestFocus();
            return;
        }
        if(boxes==0){
            numofB.setError("No. of Boxes must be more than 0");
            numofB.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        ownerid = mAuth.getCurrentUser().getUid();
        final Goodsmodel newg= new Goodsmodel(productName,type,pieces,boxes,weight,fragile1);
        colref.document(ownerid).collection("Goods").add(newg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                prodname.getEditText().setText("");
                typeof.getEditText().setText("");
                numofB.getEditText().setText("");
                numofP.getEditText().setText("");
                weight1.getEditText().setText("");
                fragile.setChecked(false);


            }
        });

        startActivity(new Intent(Newgoods.this,Options.class));

    }
}
