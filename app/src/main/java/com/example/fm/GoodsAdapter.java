package com.example.fm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class GoodsAdapter extends FirestoreRecyclerAdapter<Goods, GoodsAdapter.GoodHolder> {
    public GoodsAdapter(FirestoreRecyclerOptions<Goods> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GoodHolder holder, int position, @NonNull Goods model) {
        holder.viewname.setText(model.getProductName());
        holder.viewfragile.setText(String.valueOf(model.isFragile()));
        holder.viewweight.setText(String.valueOf(model.getWeight()));
        holder.viewtype.setText(model.getType());
        holder.viewnob.setText(String.valueOf(model.getBoxes()));
        holder.viewnop.setText(String.valueOf(model.getPieces()));


    }

    @NonNull
    @Override
    public GoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new GoodHolder(v);
    }
    class GoodHolder extends RecyclerView.ViewHolder{
        TextView viewname;
        TextView viewnop;
        TextView viewnob;
        TextView viewtype;
        TextView viewweight;
        TextView viewfragile;

        public GoodHolder(View itemView)
        {
            super(itemView);
            viewname =itemView.findViewById(R.id.pname);
            viewnop = itemView.findViewById(R.id.nop);
            viewnob =itemView.findViewById(R.id.nob);
            viewtype=itemView.findViewById(R.id.type);
            viewweight=itemView.findViewById(R.id.weight);
            viewfragile=itemView.findViewById(R.id.fragile);
        }
    }
}
