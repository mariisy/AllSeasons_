package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface IndirectMaterialsDAO {
    public Cursor getAllData(DBHelper dbHelper);
    public void addEntry(DBHelper dbHelper, Object object, String type);
    public void addTransaction(DBHelper dbHelper, Object object, String type);
    public ArrayList<Object> retrieveList (DBHelper dbHelper, String type);
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);
    public Object retrieveOne (DBHelper dbHelper, String type, String name);
    public boolean checkExisting (DBHelper dbHelper,String name);

    public boolean checkExistingWarehouse (DBHelper dbHelper,String name);
    public void updateEntry (DBHelper dbHelper, String name);
    public void deleteEntry (DBHelper dbHelper, String name);

}
