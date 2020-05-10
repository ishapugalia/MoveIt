package com.example.fm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

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
    Switch frag;
    public static final String TAG ="TAG";
     //boolean fragile;
     Button register;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    //private CollectionReference colref;
    private DocumentReference db;
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
        frag = findViewById(R.id.fragile);
        register=findViewById(R.id.register);


        //Instance linking
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        ownerid = mAuth.getCurrentUser().getUid();
        db = firestore.collection("owners").document(ownerid);
         //colref = firestore.collection("owners");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productName,type;
                final int pieces,boxes;
                final double weight;
                final boolean res;

                productName = prodname.getEditText().getText().toString().trim();
                type = typeof.getEditText().getText().toString().trim();
                pieces = Integer.parseInt(numofP.getEditText().getText().toString().trim());
                boxes = Integer.parseInt(numofB.getEditText().getText().toString().trim());
                weight = Double.parseDouble(weight1.getEditText().getText().toString().trim());
                res = frag.isChecked();

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

                Goods newg= new Goods(productName,type,pieces,boxes,weight,res);
                db.collection("Goods").add(newg);
                Map<String, Object> good = new HashMap<>();
                good.put("pname", productName);
                good.put("type", type);
                good.put("numb", boxes);
                good.put("nump", pieces);
                good.put("weight", weight);
                good.put("fragile", res);
                db.set(good).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Object is added!");
                    }
                });


                //colref.document(ownerid).collection("Goods").add();


            }
        });
    }


}
