package com.example.maricalara.allseasons.Controller;

import android.database.Cursor;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public interface AccountingDAO {

    Cursor getAllDataWPI(DBHelper dbHelper);
    Cursor getAllDataFGI(DBHelper dbHelper);
    Cursor getAllDataCGS(DBHelper dbHelper);
    Cursor getAllPlan(DBHelper dbHelper);
    Cursor getAllUtilizeWPI(DBHelper dbHelper);
    Cursor getAllUtilizeFGI(DBHelper dbHelper);
    Cursor getAllUtilizeCGS(DBHelper dbHelper);
    Cursor getAllDataCash(DBHelper dbHelper);
    Cursor getAllDataSalesRevenue(DBHelper dbHelper);
    void addSFP(DBHelper dbHelper);
    void updateSFP(DBHelper dbHelper);
    void addSCI(DBHelper dbHelper);
    void updateSCI(DBHelper dbHelper);
    ArrayList<String> viewSFP(DBHelper dbHelper,String columnName) ;
    ArrayList<String> viewSCI(DBHelper dbHelper,String columnName) ;
    void addEntry(DBHelper dbHelper, String type);
    Crops retrieveOne2 (DBHelper dbHelper, String type, String name);
    ArrayList<Crops> retrieveCropsList(DBHelper dbHelper);
    public void addEntryPlanning(DBHelper dbHelper, ArrayList<Object> objArray, double hectareSize);
    Crops retrieveOne(DBHelper dbHelper, String type, String name);
    boolean checkExisting(DBHelper dbHelper, String type);
    void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray);
    void updateFGI(DBHelper dbHelper, ArrayList<Object> objArray);
    void updateCGS(DBHelper dbHelper, ArrayList<Object> objArray);
}
