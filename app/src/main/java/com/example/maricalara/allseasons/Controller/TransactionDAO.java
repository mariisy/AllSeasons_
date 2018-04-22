package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;

public interface TransactionDAO {
     Cursor getAllData(DBHelper dbHelper);
     ArrayList<WarehouseMaterial> getAllDataWarehouse(DBHelper dbHelper);
     void addEntry(DBHelper dbHelper, Object object, String type);
     ArrayList<String> retrieveListSpinner (DBHelper dbHelper, String type);
     boolean checkExistingWarehouse (DBHelper dbHelper,String name);
     void updateEntry (DBHelper dbHelper, String name);
     void deleteEntry (DBHelper dbHelper, String name);
}
