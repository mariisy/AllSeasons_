package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class Crops {

    private String type;
    private String name;
    private double unitPrice;
    private double weight;
    private double totalPrice;
    private String date;

    public Crops(String type, String name, double unitPrice, double weight, double totalPrice, String date) {
        this.type = type;
        this.name = name;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.totalPrice = totalPrice;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    @Override
    public String toString() {
        return
                " " + name + "\n" +
                        "Quantity: " + weight + "\n" +
                        "Total Price: " + totalPrice + "\n \n";
    }
}
