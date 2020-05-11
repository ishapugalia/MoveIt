package com.example.fm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Listgoods extends AppCompatActivity {

    //Variables
    String ownerid;
    //Firebase
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private RecyclerView mFirestorelist;
    private CollectionReference colref;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listgoods);

        mFirestorelist = findViewById(R.id.firestore_list);

        //INstancing
        mAuth = mAuth.getInstance();
        firestore = firestore.getInstance();

        ownerid = mAuth.getCurrentUser().getUid();
        colref = firestore.collection("owners");

        //Query
        Query query = colref.document(ownerid).collection("Goods");

        //RecyclerOptions
        FirestoreRecyclerOptions<Goodsmodel> options = new FirestoreRecyclerOptions.Builder<Goodsmodel>()
                .setQuery(query,Goodsmodel.class).build();
        adapter = new FirestoreRecyclerAdapter<Goodsmodel, Goodsviewholder>(options) {
            @NonNull
            @Override
            public Goodsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem,parent,false);
                return new Goodsviewholder(view);
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull Goodsviewholder holder, int position, @NonNull Goodsmodel model) {
                holder.name.setText(model.getPname());
                holder.type.setText(model.getType());
               holder.nump.setText(model.getPieces()+" ");
                holder.numb.setText(model.getBoxes()+" ");
                holder.weight.setText((int) model.getWeight()+"");
            }
        };

        mFirestorelist.setHasFixedSize(true);
        mFirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mFirestorelist.setAdapter(adapter);



        //ViewHolder
        }

    private class Goodsviewholder extends  RecyclerView.ViewHolder{

        private TextView name,type,nump,numb,weight;

        public Goodsviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Rename);
            type = itemView.findViewById(R.id.type);
           nump = itemView.findViewById(R.id.pieces);
           numb = itemView.findViewById(R.id.boxes);
            weight = itemView.findViewById(R.id.weight);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}

