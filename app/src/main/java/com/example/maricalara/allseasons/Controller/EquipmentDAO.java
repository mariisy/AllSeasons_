package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;

import java.util.ArrayList;

public interface EquipmentDAO {
    public Cursor getAllData(DBHelper dbHelper);
    public void addEntry(DBHelper dbHelper, Equipment equipment);
    public void addTransaction(DBHelper dbHelper, Equipment equipment);

    public ArrayList<Equipment> retrieveEquipmentList (DBHelper dbHelper);
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);
    public Equipment retrieveOne (DBHelper dbHelper, String type, String name);
    public boolean checkExisting (DBHelper dbHelper,String name);

    public boolean checkExistingWarehouse (DBHelper dbHelper,String name);
    public void updateEntry (DBHelper dbHelper, String name);
    public void deleteEntry (DBHelper dbHelper, String name);

}
