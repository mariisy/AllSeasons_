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
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, null);
        Seeds seed = new Seeds(null, null, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, null);
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
                        seedling.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        seedling.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("QUANTITY", seedling.getQuantity() - seedlings.getQuantity());
                    val.put("PRICE", seedlings.getPrice());
                    costTotal = (seedling.getQuantity() - seedlings.getQuantity()) * seedlings.getPrice();
                    val.put("TOTAL_COST", costTotal);

                    values.put("DATE", seedlings.getDate());
                    values.put("TYPE", seedlings.getType());
                    values.put("NAME", seedlings.getName());
                    values.put("QUANTITY", seedling.getQuantity() + seedlings.getQuantity());
                    values.put("PRICE", seedlings.getPrice());
                    costTotal = (seedling.getQuantity() + seedlings.getQuantity()) * seedlings.getPrice();
                    values.put("TOTAL_COST", costTotal);

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seedlings.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);
                    dbRead.update("WPI", values, selection, selectionArgs);

                }

            } else if (obj instanceof Seeds) {

                seeds = (Seeds) obj;
                String queryUpdate = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + seeds.getName() + "'  AND TYPE = '" + seeds.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        seed.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        seed.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", seeds.getDate());
                    val.put("TYPE", seeds.getType());
                    val.put("NAME", seeds.getName());
                    val.put("QUANTITY", seed.getQuantity() - seeds.getQuantity());
                    val.put("PRICE", seeds.getPrice());
                    costTotal = (seed.getQuantity() - seeds.getQuantity()) * seeds.getPrice();
                    val.put("TOTAL_COST", costTotal);

                    values.put("DATE", seeds.getDate());
                    values.put("TYPE", seeds.getType());
                    values.put("NAME", seeds.getName());
                    values.put("QUANTITY", seed.getQuantity() + seeds.getQuantity());
                    values.put("PRICE", seeds.getPrice());
                    costTotal = (seed.getQuantity() + seeds.getQuantity()) * seeds.getPrice();
                    values.put("TOTAL_COST", costTotal);

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seedlings.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);
                    dbRead.update("WPI", values, selection, selectionArgs);

                }

            }else if (obj instanceof Insecticides) {

            } else if (obj instanceof Fertilizers) {

            } else if (obj instanceof Packaging) {

            } else {
                break;
            }

        }
    }
}
