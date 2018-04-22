package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class Customer {

    private String customerName;
    private String contactNumber;
    private String address;

    public Customer(String customerName,  String contactNumber, String address) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.address = address;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
