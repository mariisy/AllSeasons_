package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {
    Cursor getAllDataWPI(DBHelper dbHelper);
    void addEntry(DBHelper dbHelper);
    boolean checkExistingWPI(DBHelper dbHelper);
    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
}
