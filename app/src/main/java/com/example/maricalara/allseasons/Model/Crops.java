package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class Crops {

    private String type;
    private String name;
    private double weight;
    private double price;
    private String date;

    public Crops(String type, String name, double weight, double price, String date) {
        this.type = type;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
                " " + name + "\n" +
                        "Quantity: " + weight + "\n" +
                        "Total Price: " + price + "\n \n";
    }
}
