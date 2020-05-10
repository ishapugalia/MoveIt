package com.example.fm;

import com.google.firebase.database.Exclude;

public class Goods {
    String productName;
    String type;
    int pieces;
    int boxes;
    Double weight;
    boolean fragile;


    Goods( ){

    }

    Goods(String productName,String type,int pieces,int boxes,Double weight,boolean fragile){
        this.productName = productName;
        this.boxes = boxes;
        this.type = type;
        this.pieces = pieces;
        this.weight = weight;
        this.fragile = fragile;
         this.type=type;

    }
     @Exclude
    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public int getPieces() {
        return pieces;
    }

    public int getBoxes() {
        return boxes;
    }

    public Double getWeight() {
        return weight;
    }

    public boolean isFragile() {
        return fragile;
    }
}
