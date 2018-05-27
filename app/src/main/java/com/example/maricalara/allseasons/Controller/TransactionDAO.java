package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.Customer;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Employees;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TransactionDAO {

    void addDefault(DBHelper dbHelper);

    Cursor getAllData(DBHelper dbHelper);

    public Cursor getAllEmployee(DBHelper dbHelper);

    public Cursor getAllCash(DBHelper dbHelper);

    ArrayList<WarehouseMaterial> getAllDataWarehouse(DBHelper dbHelper);

    Employees retrieveOneEmployee(DBHelper dbHelper, String username, String password);

    public void addEntry(DBHelper dbHelper, Object object, String type, String name, String contact);

    boolean checkExistingEmployee(DBHelper dbHelper, String type, String employeeName);

    boolean checkExistCustomer(DBHelper dbHelper, String name, String address);

    Customer retrieveOneCustomer(DBHelper dbHelper, String name, String address);

    void updateCustomer(DBHelper dbHelper, String custID, String custContact);

    boolean checkExist(DBHelper dbHelper, String username, String password);

     void addTransactionList(DBHelper dbHelper, ArrayList<Transaction> arrayList);

    ArrayList<HashMap<String, List<String>>> retrieveTransactionList(DBHelper dbHelper,  String type);

    boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name);

    void updateEntry(DBHelper dbHelper, String type, String name, Double price);

    void deleteEntry(DBHelper dbHelper, String name);

    ArrayList<String> retrieveListSpinner(DBHelper dbHelper);

    ArrayList<String> retrieveListSpinnerColumn(DBHelper dbHelper, String spinnerCategory, String columnName, String value);

    ArrayList<Crops> retrieveSum(DBHelper dbHelper);

    ArrayList<Transaction> retrieveExpense(DBHelper dbHelper);


}
