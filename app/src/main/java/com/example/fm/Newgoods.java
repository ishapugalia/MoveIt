package com.example.fm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    boolean fragile;

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
         fragile= ((Switch)findViewById(R.id.fragile)).isChecked();
         register=findViewById(R.id.register);
        //Instance linking
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
         colref = firestore.collection("owners");
    }

    public void addGoods(View view){
        final String productName,type;
        final int pieces,boxes;
        final int weight;

        productName = prodname.getEditText().getText().toString().trim();
        type = typeof.getEditText().getText().toString().trim();
        pieces = Integer.parseInt(numofP.getEditText().getText().toString().trim());
        boxes = Integer.parseInt(numofB.getEditText().getText().toString().trim());
        weight = Integer.parseInt(weight1.getEditText().getText().toString().trim());


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

        ownerid = mAuth.getCurrentUser().getUid();
        final Goodsmodel newg= new Goodsmodel(productName,type,pieces,boxes,weight,fragile);
        colref.document(ownerid).collection("Goods").add(newg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

         startActivity(new Intent(Newgoods.this,Options.class));

    }
}
