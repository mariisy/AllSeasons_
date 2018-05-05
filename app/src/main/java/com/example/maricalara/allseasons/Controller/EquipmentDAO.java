package com.example.maricalara.allseasons.Controller;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;

import java.util.ArrayList;

public interface EquipmentDAO {


    void addTransaction(DBHelper dbHelper, Equipment equipment);

    ArrayList<Equipment> retrieveEquipmentList(DBHelper dbHelper);

    Equipment retrieveOne(DBHelper dbHelper, String type, String name);

    boolean checkExisting(DBHelper dbHelper, String name);

    ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type);

    boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name);

    void updateTransactionAdd(DBHelper dbHelper, ArrayList<Object> objArray);

    void deleteEntry(DBHelper dbHelper, String name);

}
