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
    public Cursor getAllDataWPI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM WPI", null);
        return result;
    }

    @Override
    public Cursor getAllPlan(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM RESOURCE_PLANNING_TABLE", null);
        return result;
    }

    @Override
    public void addEntry(DBHelper dbHelper, String type) {
        dbWrite = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TOTAL_COST", 0);
        dbWrite.insert("WPI", null, values);
    }

    @Override
    public void addEntryPlanning(DBHelper dbHelper, ArrayList<Object> objArray, double hectareSize) {
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        double totalCost = 0;
        for (Object obj : objArray) {
            if (obj instanceof Seeds) {
                seeds = (Seeds) obj;
                values.put("NAME", seeds.getName());
                values.put("SEEDS_NAME", seeds.getName());
                values.put("SEEDS_PRICE", seeds.getPrice());
                values.put("SEEDS_QUANTITY", seeds.getQuantity());
                values.put("SEEDS_COST", seeds.getTotalPrice());
                values.put("SEEDS_PERCENTAGE", 1);
                totalCost += seeds.getTotalPrice();
            }

            if (obj instanceof Fertilizers) {
                fertilizers = (Fertilizers) obj;
                values.put("FERTILIZER_NAME", fertilizers.getName());
                values.put("FERTILIZER_PRICE", fertilizers.getPrice());
                values.put("FERTILIZER_QUANTITY", fertilizers.getQuantity());
                values.put("FERTILIZER_COST", fertilizers.getTotalPrice());
                values.put("FERTILIZER_PERCENTAGE", 1);
                totalCost += fertilizers.getTotalPrice();
            }

            if (obj instanceof Insecticides) {
                insecticides = (Insecticides) obj;
                values.put("INSECTICIDES_NAME", insecticides.getName());
                values.put("INSECTICIDES_PRICE", insecticides.getPrice());
                values.put("INSECTICIDES_QUANTITY", insecticides.getQuantity());
                values.put("INSECTICIDES_COST", insecticides.getTotalPrice());
                values.put("INSECTICIDES_PERCENTAGE", 1);
                totalCost += insecticides.getTotalPrice();
            }




        }
        values.put("TOTAL_PERCENTAGE_PRODUCTS", 1);
        values.put("HECTARE_SIZE", hectareSize);
        values.put("PERCENTAGE_HECTARE_DONE", 1);
        values.put("TOTAL_COST", totalCost);
        dbWrite.insert("RESOURCE_PLANNING_TABLE", null, values);
    }

    @Override
    public boolean checkExistingWPI(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT * FROM WPI";

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    public Crops retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "FGI WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Crops crops = new Crops(null, null, 0, 0, 0, null);

        if (cursor.moveToFirst()) {
            do {

                crops.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                crops.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                crops.setWeight(cursor.getDouble(cursor.getColumnIndex("WEIGHT")));
                crops.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                crops.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
                crops.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            } while (cursor.moveToNext());
        }

        return crops;
    }

    @Override
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, 0, null);
        Seeds seed = new Seeds(null, null, 0, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, 0, null);

        for (Object obj : objArray) {
            if (obj instanceof Seedlings) {
                seedlings = (Seedlings) obj;
                ContentValues val = new ContentValues();
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
                    val.put("QUANTITY", seedling.getQuantity() - seedlings.getQuantity());
                    val.put("PRICE", seedlings.getPrice());
                    val.put("TOTAL_COST", seedling.getTotalPrice() - seedlings.getTotalPrice());
                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seedlings.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);
                }
                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    values.put("TOTAL_COST", costTotal + seedlings.getTotalPrice());
                    dbRead.update("WPI", values, "WPIID=" + 1, null);
                }
            }


            if (obj instanceof Seeds) {
                ContentValues val = new ContentValues();
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
                    val.put("QUANTITY", seed.getQuantity() - seeds.getQuantity());
                    val.put("PRICE", seeds.getPrice());
                    val.put("TOTAL_COST", seed.getTotalPrice() - seeds.getTotalPrice());
                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seeds.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);
                    //costTotal += seeds.getTotalPrice();

                }
                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    values.put("TOTAL_COST", costTotal + seeds.getTotalPrice());
                    dbRead.update("WPI", values, "WPIID=" + 1, null);
                }
            }

            if (obj instanceof Insecticides) {
                ContentValues val = new ContentValues();
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
                    // costTotal += insecticides.getTotalPrice();
                }
                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    values.put("TOTAL_COST", costTotal + insecticides.getTotalPrice());
                    dbRead.update("WPI", values, "WPIID=" + 1, null);
                }
            }

            if (obj instanceof Fertilizers) {
                ContentValues val = new ContentValues();
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
                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    values.put("TOTAL_COST", costTotal + fertilizers.getTotalPrice());
                    dbRead.update("WPI", values, "WPIID=" + 1, null);
                }
            }

            if (obj instanceof Packaging) {
                ContentValues val = new ContentValues();
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
                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    values.put("TOTAL_COST", costTotal + packaging.getTotalPrice());
                    dbRead.update("WPI", values, "WPIID=" + 1, null);
                }
            }
        }

    }
}
