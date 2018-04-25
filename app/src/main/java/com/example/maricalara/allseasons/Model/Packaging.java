package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 14/03/2018.
 */

public class Packaging {

    private String type;
    private String name;
    private int quantity;
    private double price;
    private String date;

    public Packaging(String type, String name, int quantity, double price, String date) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                        "Item Name: " + name + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Total Price: " + price + "\n \n" ;
    }
}
