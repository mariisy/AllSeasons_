package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;

import java.util.ArrayList;

public class AccountingDAOImpl implements AccountingDAO {
    SQLiteDatabase dbWrite, dbRead;
    Crops crop;
    Insecticides insecticides;
    Fertilizers fertilizers;
    Packaging packaging;
    Seedlings seedlings;
    Seeds seeds;


    @Override
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, null);
        Seeds seed = new Seeds(null, null, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, null);
        ContentValues val = new ContentValues();
        double costTotal = 0;
    }
}
