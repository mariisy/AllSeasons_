package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;

public class AccountingDAOImpl implements AccountingDAO {
    SQLiteDatabase dbWrite, dbRead;
    Crops crop;
    @Override
    public void addTransaction(DBHelper dbHelper, Crops crops) {
        dbWrite = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TYPE", crops.getType());
        values.put("NAME", crops.getName());
        values.put("PRICE", crops.getPrice());
        dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

        }
}
