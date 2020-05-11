package com.example.fm;

public class Goodsmodel {
    private  String pname;
    private String type;
    private int pieces;
    private  int boxes;
    private double weight;
    //private boolean fragile;

    public Goodsmodel(){

    }

    public Goodsmodel(String pname,String type,int pieces,int boxes,int weight){
        this.pname = pname;
        this.type = type;
        this.pieces = pieces;
        this.boxes = boxes;
        this.weight = weight;
      //  this.fragile = fragile;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getBoxes() {
        return boxes;
    }

    public void setBoxes(int boxes) {
        this.boxes = boxes;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /*public boolean isFragile() {
        if(this.fragile==true)
        return true;
        else
            return false;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }*/
}
