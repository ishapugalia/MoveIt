package com.example.fm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Listgoods extends AppCompatActivity {

    //Variables
    String ownerid;
    //Firebase
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private CollectionReference colref=firestore.collection("owners");
    private GoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listgoods);
        setUpRecyclerView();

    }

       private void setUpRecyclerView() {


            Query query = colref.document(ownerid).collection("Goods").orderBy("pieces",Query.Direction.DESCENDING);
            //RecyclerOptions
            FirestoreRecyclerOptions<Goods> options = new FirestoreRecyclerOptions.Builder<Goods>()
                    .setQuery(query, Goods.class).build();
            adapter=new GoodsAdapter(options);
            RecyclerView recyclerView = findViewById(R.id.firestore_list);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}


