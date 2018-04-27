package com.example.maricalara.allseasons.Controller;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

public interface AccountingDAO {

    void addTransaction(DBHelper dbHelper, Crops crops);
}
