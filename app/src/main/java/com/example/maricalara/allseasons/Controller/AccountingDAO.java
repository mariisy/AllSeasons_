package com.example.maricalara.allseasons.Controller;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {

    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
}
