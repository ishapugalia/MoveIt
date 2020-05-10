package com.example.fm;

public class Goods {
    String productName;
    String type;
    int pieces;
    int boxes;
    Double weight;
    boolean fragile;


    Goods(){
        this.productName = "";
        this.boxes = 0;
        this.pieces = 0;
        this.weight = 0.00;
        this.type = "";
        this.fragile = false;
    }

    Goods(String productName,String type,int pieces,int boxes,Double weight,boolean fragile){
        this.productName = productName;
        this.boxes = boxes;
        this.type = type;
        this.pieces = pieces;
        this.weight = weight;
        this.fragile = fragile;
    }
}
