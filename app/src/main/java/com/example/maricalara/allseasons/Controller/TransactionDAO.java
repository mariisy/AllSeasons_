package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TransactionDAO {
    Cursor getAllData(DBHelper dbHelper);

    public void addEmployee(DBHelper dbHelper, Object object);

    public Cursor getAllEmployee(DBHelper dbHelper);

    ArrayList<WarehouseMaterial> getAllDataWarehouse(DBHelper dbHelper);

    void addEntry(DBHelper dbHelper, Object object, String type);

    void addBoughtList(DBHelper dbHelper, Object object, String type);

    void addSoldList(DBHelper dbHelper, Object object);


    HashMap<String, List<String>> retrieveBoughtList(DBHelper dbHelper);

    HashMap<String, List<String>> retrieveSoldList(DBHelper dbHelper);

    boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name);

    void updateEntry(DBHelper dbHelper, String name, Double price);

    void deleteEntry(DBHelper dbHelper, String name);
}
