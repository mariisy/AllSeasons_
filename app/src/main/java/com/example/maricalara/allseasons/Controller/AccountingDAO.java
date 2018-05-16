package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {
    Cursor getAllDataWPI(DBHelper dbHelper);
    void addEntry(DBHelper dbHelper, String type);
    Crops retrieveOne(DBHelper dbHelper, String type, String name);
    boolean checkExisting(DBHelper dbHelper, String type);
    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
}
