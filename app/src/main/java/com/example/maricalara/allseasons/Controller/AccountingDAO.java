package com.example.maricalara.allseasons.Controller;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {
    void addEntry(DBHelper dbHelper);
    boolean checkExistingWPI(DBHelper dbHelper);
    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
}
