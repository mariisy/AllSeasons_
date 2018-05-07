package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
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
    public void addEntry(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TOTAL_COST", 0);
        dbWrite.insert("WPI", null, values);
    }

    @Override
    public boolean checkExistingWPI(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT * FROM WPI" ;

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, 0,null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, 0,null);
        Packaging pa = new Packaging(null, null, 0, 0, 0,null);
        Seeds seed = new Seeds(null, null, 0, 0, 0,null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, 0,null);
        ContentValues val = new ContentValues();
        ContentValues values = new ContentValues();
        double costTotal = 0;
        for (Object obj : objArray) {
            if (obj instanceof Seedlings) {
                seedlings = (Seedlings) obj;
                String queryUpdate = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + seedlings.getName() + "'  AND TYPE = '" + seedlings.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        seedling.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seedling.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seedling.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                    } while (cursor.moveToNext());

                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("QUANTITY", seedling.getQuantity() + seedlings.getQuantity());
                    val.put("PRICE", seedlings.getPrice());
                    val.put("TOTAL_COST", seedling.getTotalPrice() + seedlings.getTotalPrice());

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seedlings.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);



                }
            }

            if (obj instanceof Seeds) {
                seeds = (Seeds) obj;
                String queryUpdate = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + seeds.getName() + "'  AND TYPE = '" + seeds.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        seed.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seed.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                        seed.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", seeds.getDate());
                    val.put("TYPE", seeds.getType());
                    val.put("NAME", seeds.getName());
                    val.put("QUANTITY", seed.getQuantity() + seeds.getQuantity());
                    val.put("PRICE", seeds.getPrice());
                    val.put("TOTAL_COST", seed.getTotalPrice() + seeds.getTotalPrice());

                }
            }
            if (obj instanceof Insecticides) {
                insecticides = (Insecticides) obj;
                String queryUpdate = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE NAME = '" + insecticides.getName() + "'  AND TYPE = '" + insecticides.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        in.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        in.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                        in.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", insecticides.getDate());
                    val.put("TYPE", insecticides.getType());
                    val.put("NAME", insecticides.getName());
                    val.put("QUANTITY", in.getQuantity() + insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    val.put("TOTAL_COST", in.getTotalPrice() + insecticides.getTotalPrice());

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {insecticides.getName()};
                    dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);


                }
            }

            if (obj instanceof Fertilizers) {
                fertilizers = (Fertilizers) obj;
                String queryUpdate = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE NAME = '" + fertilizers.getName() + "'  AND TYPE = '" + fertilizers.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        fe.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        fe.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                        fe.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", fertilizers.getDate());
                    val.put("TYPE", fertilizers.getType());
                    val.put("NAME", fertilizers.getName());
                    val.put("QUANTITY", fe.getQuantity() + fertilizers.getQuantity());
                    val.put("PRICE", fertilizers.getPrice());
                    val.put("TOTAL_COST", fe.getTotalPrice() + fertilizers.getTotalPrice());

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {fertilizers.getName()};
                    dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);

                }
            }

            if (obj instanceof Packaging) {
                packaging = (Packaging) obj;
                String queryUpdate = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE NAME = '" + packaging.getName() + "'  AND TYPE = '" + packaging.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        pa.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        pa.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                        pa.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", packaging.getDate());
                    val.put("TYPE", packaging.getType());
                    val.put("NAME", packaging.getName());
                    val.put("QUANTITY", pa.getQuantity() + packaging.getQuantity());
                    val.put("PRICE", packaging.getPrice());
                    val.put("TOTAL_COST", pa.getTotalPrice() + packaging.getTotalPrice());

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {packaging.getName()};
                    dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);

                }
            }
        }

    }
}
