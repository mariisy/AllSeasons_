package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 16/10/2017.
 */

public class Insecticides {

    private String type;
    private String name;
    private int quantity;
    private double price,totalPrice;
    private String date;
    private String cropType;

    public Insecticides(String type, String name, int quantity, double price, double totalPrice, String date, String cropType) {

        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.date = date;
        this.cropType = cropType;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    @Override
    public String toString() {
        return
                "Type: " + type + "\n" +
                        "Item Name: " + name + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Price: " + price + "\n" +
                        "Total Price: " + totalPrice + "\n \n";
    }
}
