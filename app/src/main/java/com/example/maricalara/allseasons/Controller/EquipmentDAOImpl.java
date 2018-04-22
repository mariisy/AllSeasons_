package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;

import java.util.ArrayList;

public class EquipmentDAOImpl implements EquipmentDAO {
    SQLiteDatabase dbWrite, dbRead;

    @Override
    public Cursor getAllData(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM EQUIPMENT", null);
        return result;
    }

    @Override
    public void addEntry(DBHelper dbHelper, Equipment equipment) {
        dbWrite = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TYPE", equipment.getType());
        values.put("NAME", equipment.getName());
        values.put("PRICE", equipment.getPrice());
        dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);


    }

    @Override
    public boolean checkExistingWarehouse(DBHelper dbHelper, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "WAREHOUSE_EQUIPMENT" + " WHERE NAME = '" + name + "' ";

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public void addTransaction(DBHelper dbHelper, Equipment equipment) {
        dbWrite = dbHelper.getWritableDatabase();
        double costTotal = Double.valueOf(equipment.getQuantity()) * Double.valueOf(equipment.getPrice());

        ContentValues val = new ContentValues();
        val.put("DATE", equipment.getDate());
        val.put("TYPE", equipment.getType());
        val.put("NAME", equipment.getName());
        val.put("QUANTITY", equipment.getQuantity());
        val.put("PRICE", equipment.getPrice());
        val.put("TOTAL_COST", costTotal);

        dbWrite.insert("EQUIPMENT", null, val);


        ContentValues value = new ContentValues();
        value.put("DATE", equipment.getDate());
        value.put("TYPE", equipment.getType());
        value.put("DEBIT", 0);
        value.put("CREDIT", costTotal);
        dbWrite.insert("CASH", null, value);
    }

    @Override
    public ArrayList<Equipment> retrieveEquipmentList(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT * FROM " + "EQUIPMENT WHERE TYPE = '" + "Equipment" + "' ";
        ArrayList<Equipment> listEquip = new ArrayList<Equipment>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                String date = cursor.getString(cursor.getColumnIndex("DATE"));
                listEquip.add(new Equipment(typ, name, qty, price, date));

            } while (cursor.moveToNext());
        }
        return listEquip;
    }

    @Override
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT NAME FROM " + "EQUIPMENT WHERE TYPE = '" + type + "' ";
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = db.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex("NAME")));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }

    @Override
    public Equipment retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Equipment equipment = new Equipment(null, null, 0, 0, null);
        Object object = null;

        if (cursor.moveToFirst()) {
            do {
                equipment.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                equipment.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                equipment.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                equipment.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                equipment.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            } while (cursor.moveToNext());

        }
        object = (Object) equipment;

        return equipment;
    }

    @Override
    public boolean checkExisting(DBHelper dbHelper, String name) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "EQUIPMENT" + " WHERE NAME = '" + name + "' ";

        Cursor result = dbRead.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL
    }

    @Override
    public void updateEntry(DBHelper dbHelper, String name) {

    }

    @Override
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbWrite = dbHelper.getWritableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbWrite.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }


}
