package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;

import java.util.ArrayList;

public interface EquipmentDAO {


     void addTransaction(DBHelper dbHelper, Equipment equipment);

     ArrayList<Equipment> retrieveEquipmentList (DBHelper dbHelper);
     Equipment retrieveOne (DBHelper dbHelper, String type, String name);
     boolean checkExisting (DBHelper dbHelper,String name);
     ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);
     boolean checkExistingWarehouse (DBHelper dbHelper,String type,String name);
     void updateTransaction(DBHelper dbHelper,String Date, String type, String name, int quantity);
     void deleteEntry (DBHelper dbHelper, String name);

}
