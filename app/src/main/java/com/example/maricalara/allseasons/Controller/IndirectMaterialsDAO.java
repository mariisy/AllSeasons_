package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface IndirectMaterialsDAO {


     void addTransaction(DBHelper dbHelper, Object object, String type);
     ArrayList<Object> retrieveList (DBHelper dbHelper, String type);
     Object retrieveOne (DBHelper dbHelper, String type, String name);
     boolean checkExisting (DBHelper dbHelper,String name);
     void updateEntry (DBHelper dbHelper, String name);
     void deleteEntry (DBHelper dbHelper, String name);

}
