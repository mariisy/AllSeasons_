package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;

public interface RawMaterialsDAO {


     ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);
     void addTransaction(DBHelper dbHelper, Object object, String type);
     ArrayList<Object> retrieveList (DBHelper dbHelper, String type);
     Object retreiveOne(DBHelper dbHelper, String type, String name);
     boolean checkExisting (DBHelper dbHelper,String name);
     void updateTransaction(DBHelper dbHelper, ArrayList<Object> objArray);
     void deleteEntry (DBHelper dbHelper, String name);

}
