package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface IndirectMaterialsDAO {
     ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);

     void addTransaction(DBHelper dbHelper, Object object, String type);
     ArrayList<Object> retrieveList (DBHelper dbHelper, String type);
     Object retrieveOne (DBHelper dbHelper, String type, String name);
     boolean checkExisting (DBHelper dbHelper,String name);
     public void updateTransaction(DBHelper dbHelper, ArrayList<Object> objArray);
     void deleteEntry (DBHelper dbHelper, String name);

}
