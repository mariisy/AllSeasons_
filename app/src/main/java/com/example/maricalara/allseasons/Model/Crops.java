package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class Crops {

    private String type;
    private String name;
    private double unitPrice;
    private double weight;
    private double totalCostHarvested;
    private String date;
    private double hectarePercent;
    private double hectareHarvested;
    private double totalCostSold;


    public Crops(String type, String name, double unitPrice, double weight, double totalCostHarvested, String date, double hectarePercent, double hectareHarvested, double totalcostSold) {

        this.type = type;
        this.name = name;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.totalCostHarvested = totalCostHarvested;
        this.date = date;
        this.hectarePercent = hectarePercent;
        this.hectareHarvested = hectareHarvested;
        this.totalCostSold = totalcostSold;
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

    public double getTotalCostHarvested() {
        return totalCostHarvested;
    }

    public void setTotalCostHarvested(double totalCostHarvested) {
        this.totalCostHarvested = totalCostHarvested;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHectarePercent() {
        return hectarePercent;
    }

    public void setHectarePercent(double hectarePercent) {
        this.hectarePercent = hectarePercent;
    }

    public double getHectareHarvested() {
        return hectareHarvested;
    }

    public void setHectareHarvested(double hectareHarvested) {
        this.hectareHarvested = hectareHarvested;
    }

    public double getTotalCostSold() {
        return totalCostSold;
    }

    public void setTotalCostSold(double totalCostSold) {
        this.totalCostSold = totalCostSold;
    }
    @Override
    public String toString() {
        return
        "Type: " + type + "\n" +
                "Item Name: " + name + "\n" +
                "Weight: " + weight + "\n\n";
    }
}
