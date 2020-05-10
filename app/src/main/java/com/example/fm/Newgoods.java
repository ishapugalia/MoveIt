package com.example.fm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


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
        numofP = findViewById(R.id.nump);
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
        final double weight;

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
        final Goods newg= new Goods(productName,type,pieces,boxes,weight,fragile);
        colref.document(ownerid).collection("Goods").add(newg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
