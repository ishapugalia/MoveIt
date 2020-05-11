package com.example.fm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private GoodsAdapter adapter;

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
                .setQuery(query, Goodsmodel.class).build();

          adapter=new GoodsAdapter(options);

        mFirestorelist.setHasFixedSize(true);
        mFirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mFirestorelist.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.deleteItem(position);

            }
        }).attachToRecyclerView(mFirestorelist);
        //ViewHolder
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
class GoodsAdapter extends FirestoreRecyclerAdapter<Goodsmodel, GoodsAdapter.Goodsviewholder> {

    public GoodsAdapter(@NonNull FirestoreRecyclerOptions<Goodsmodel> options) {
        super(options);
    }

    public Goodsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem, parent, false);
        return new Goodsviewholder(view);
    }

    class Goodsviewholder extends RecyclerView.ViewHolder {

        private TextView name, type, nump, numb, weight, fragile;

        public Goodsviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Rename);
            type = itemView.findViewById(R.id.type);
            nump = itemView.findViewById(R.id.pieces);
            numb = itemView.findViewById(R.id.boxes);
            weight = itemView.findViewById(R.id.weight);
            fragile = itemView.findViewById((R.id.fragility));

        }
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull Goodsviewholder holder, int position, @NonNull Goodsmodel model) {
        holder.name.setText(model.getPname());
        holder.type.setText(model.getType());
        holder.nump.setText(model.getPieces() + " ");
        holder.numb.setText(model.getBoxes() + " ");
        holder.weight.setText((int) model.getWeight() + "");
        holder.fragile.setText(String.valueOf(model.isFragile()));
    }

          /*  public void deleteItem(int position) {
                getSnapshots().getSnapshot(position).getReference().delete();
            }*/
}

;



