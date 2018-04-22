package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface RawMaterialsDAO {
    public Cursor getAllData(DBHelper dbHelper);
    public ArrayList<String> getAllDataWarehouse(DBHelper dbHelper);
    public void addEntry(DBHelper dbHelper, Object object, String type);
    public void addTransaction(DBHelper dbHelper, Object object, String type);
    public ArrayList<Object> retrieveList (DBHelper dbHelper, String type);
    public Object retreiveOne(DBHelper dbHelper, String type, String name);
    public boolean checkExisting (DBHelper dbHelper,String name);
    public boolean checkExistingWarehouse (DBHelper dbHelper,String name);
    public void updateEntry (DBHelper dbHelper, String name);
    public void deleteEntry (DBHelper dbHelper, String name);
    public ArrayList<String> retrieveListSpinner (DBHelper dbHelper, String type);
}
