package com.example.maricalara.allseasons.Model;

public class Transaction {

    private int transID;
    private String transactionFullID ;
    private String date ;
    private String deliveryDate ;
    private String transactionType;
    private String itemType;
    private double quantity;
    private double price ;
    private double totalCost ;
    private String employeeID ;
    private int customerID ;

    public Transaction(int transID, String transactionFullID, String date, String deliveryDate,
                       String transactionType, String itemType, double quantity, double price,
                       double totalCost, String employeeID, int customerID) {
        this.transID = transID;
        this.transactionFullID = transactionFullID;
        this.date = date;
        this.deliveryDate = deliveryDate;
        this.transactionType = transactionType;
        this.itemType = itemType;
        this.quantity = quantity;
        this.price = price;
        this.totalCost = totalCost;
        this.employeeID = employeeID;
        this.customerID = customerID;
    }

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public String getTransactionFullID() {
        return transactionFullID;
    }

    public void setTransactionFullID(String transactionFullID) {
        this.transactionFullID = transactionFullID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
