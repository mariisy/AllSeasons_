package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TransactionDAO {
    Cursor getAllData(DBHelper dbHelper);

    ArrayList<WarehouseMaterial> getAllDataWarehouse(DBHelper dbHelper);

    void addEntry(DBHelper dbHelper, Object object, String type);

    void addTransactionBought(DBHelper dbHelper, Object object, String type);

    void addTranasctionSold(DBHelper dbHelper, Object object, String type);

    HashMap<String, List<String>> retrieveBoughtList();

    HashMap<String, List<String>> retrieveSoldList();

    ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);

    boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name);

    void updateEntry(DBHelper dbHelper, String name, Double price);

    void deleteEntry(DBHelper dbHelper, String name);
}
