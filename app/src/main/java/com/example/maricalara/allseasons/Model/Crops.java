package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class Crops {

    private String type;
    private int weight;
    private int price;
    private String date;

    public Crops(String type, int weight, int price, String date) {
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "Type: " + type + "\n" +
                        "Quantity: " + weight + "\n" +
                        "Total Price: " + price + "\n \n";
    }
}
