package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 14/03/2018.
 */

public class Employees {

    private String employeeID;
    private String name;
    private String accountype;
    private String date;
    private int salary;


    public Employees(String employeeID, String name, String accountype, String date, int salary) {
        this.employeeID = employeeID;
        this.name = name;
        this.accountype = accountype;
        this.date = date;
        this.salary = salary;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountype() {
        return accountype;
    }

    public void setAccountype(String accountype) {
        this.accountype = accountype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
