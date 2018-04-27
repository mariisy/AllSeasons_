package com.example.maricalara.allseasons.Model;

public class WarehouseMaterial {
    private String supplierName;
    private String supplierContact;
    private String type;
    private String name;
    private double price;

    public WarehouseMaterial(String supplierName, String supplierContact, String type, String name, double price) {
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
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
