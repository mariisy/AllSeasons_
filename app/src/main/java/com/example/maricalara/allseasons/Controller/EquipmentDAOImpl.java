package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;

import java.util.ArrayList;

public class EquipmentDAOImpl implements EquipmentDAO {
    SQLiteDatabase dbWrite, dbRead;
    Equipment equip;


    @Override
    public boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "WAREHOUSE_EQUIPMENT" + " WHERE NAME = '" + name + "' ";

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT NAME FROM " + "EQUIPMENT WHERE TYPE = '" + type + "' ";
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex("NAME")));
            } while (cursor.moveToNext());
        }
        return listHolder;
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
    public Equipment retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
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
    public void updateTransaction(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();

        Equipment equipment = new Equipment(null, null, 0, 0, null);

        double costTotal = 0.0;

        for (Object obj : objArray) {
            if (obj instanceof Equipment) {
                equip = (Equipment) obj;
                String queryUpdate = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + equip.getName() + "'  AND TYPE = '" + equip.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        equipment.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());
                }

                ContentValues val = new ContentValues();

                val.put("DATE", equip.getDate());
                val.put("TYPE", equip.getType());
                val.put("NAME", equip.getName());
                val.put("QUANTITY", equipment.getQuantity() + equip.getQuantity());
                val.put("PRICE", equip.getPrice());
                costTotal = (equipment.getQuantity() + equip.getQuantity()) * equip.getPrice();
                val.put("TOTAL_COST", costTotal);

                String selection = "NAME" + " LIKE ?";
                String[] selectionArgs = {equip.getName()};
                dbRead.update("EQUIPMENT", val, selection, selectionArgs);

                //dbWrite.update("EQUIPMENT", val, "NAME = '" + equip.getName() + "'  AND TYPE = '" + equip.getType() + "' ", null);
            }

        }
    }

       /* dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        String queryUpdate = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = dbRead.rawQuery(queryUpdate, null);

        ContentValues val = new ContentValues();
        double costTotal = 0;

        if (cursor.moveToFirst()) {
            do {
                equipment.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                equipment.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                equipment.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                equipment.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                equipment.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            } while (cursor.moveToNext());


            val.put("DATE", equipment.getDate());
            val.put("TYPE", equipment.getType());
            val.put("NAME", equipment.getName());
            val.put("QUANTITY", equipment.getQuantity());
            val.put("PRICE", equipment.getPrice());
            costTotal = (equipment.getQuantity() + quantity) * equipment.getPrice();
            val.put("TOTAL_COST", costTotal);
            dbWrite.update("EQUIPMENT", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
        }*/


    @Override
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbWrite = dbHelper.getWritableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbWrite.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }


}
