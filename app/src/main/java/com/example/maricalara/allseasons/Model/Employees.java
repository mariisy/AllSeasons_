package com.example.maricalara.allseasons.Model;

/**
 * Created by Mari Calara on 14/03/2018.
 */

public class Employees {

    private int employeeID;
    private String employeeFullID;
    private String userName;
    private String password;
    private String name;
    private String accountype;
    private int salary;


    public Employees(int employeeID, String employeeFullID, String userName, String password, String name, String accountype, int salary) {

        this.employeeID = employeeID;
        this.employeeFullID = employeeFullID;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.accountype = accountype;
        this.salary = salary;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeFullID() {
        return employeeFullID;
    }

    public void setEmployeeFullID(String employeeFullID) {
        this.employeeFullID = employeeFullID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


}
