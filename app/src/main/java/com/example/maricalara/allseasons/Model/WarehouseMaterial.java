package com.example.maricalara.allseasons.Model;

public class WarehouseMaterial {
    private String type;
    private String name;
    private double price;

    public WarehouseMaterial(String type, String name, double price) {
        this.type = type;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
