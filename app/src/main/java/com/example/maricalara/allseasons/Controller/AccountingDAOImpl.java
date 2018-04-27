package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

import java.util.ArrayList;

public class AccountingDAOImpl implements AccountingDAO {
    SQLiteDatabase dbWrite, dbRead;
    Crops crop;


    @Override
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {

    }
}
