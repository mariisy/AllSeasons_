package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {

    Cursor getAllDataWPI(DBHelper dbHelper);
    Cursor getAllDataFGI(DBHelper dbHelper);
    Cursor getAllPlan(DBHelper dbHelper);
    Cursor getAllUtilizeWPI(DBHelper dbHelper);
    Cursor getAllUtilizeFGI(DBHelper dbHelper);
    void addEntry(DBHelper dbHelper, String type);
    public void addEntryPlanning(DBHelper dbHelper, ArrayList<Object> objArray, double hectareSize);
    Crops retrieveOne(DBHelper dbHelper, String type, String name);
    boolean checkExisting(DBHelper dbHelper, String type);
    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
    void updateFGI(DBHelper dbHelper, ArrayList<Object> objArray);
}
