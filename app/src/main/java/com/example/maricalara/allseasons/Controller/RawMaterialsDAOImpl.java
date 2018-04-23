package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;

import java.util.ArrayList;

public class RawMaterialsDAOImpl implements RawMaterialsDAO {
    SQLiteDatabase dbWrite, dbRead;
    Seedlings seedlings;
    Seeds seeds;


    @Override
    public void addTransaction(DBHelper dbHelper, Object object, String type) {
        dbWrite = dbHelper.getWritableDatabase();

        switch (type) {
            case "Seedlings":
                if (object instanceof Seedlings) {
                    seedlings = (Seedlings) object;
                    double costTotal = Double.valueOf(seedlings.getQuantity()) * Double.valueOf(seedlings.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("QUANTITY", seedlings.getQuantity());
                    val.put("PRICE", seedlings.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("RAW_MATERIALS", null, val);


                    ContentValues value = new ContentValues();
                    value.put("DATE", seedlings.getDate());
                    value.put("TYPE", seedlings.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);

                }

                break;

            case "Seeds":
                if (object instanceof Seeds) {
                    seeds = (Seeds) object;
                    double costTotal = Double.valueOf(seeds.getQuantity()) * Double.valueOf(seeds.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", seeds.getDate());
                    val.put("TYPE", seeds.getType());
                    val.put("NAME", seeds.getName());
                    val.put("QUANTITY", seeds.getQuantity());
                    val.put("PRICE", seeds.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("RAW_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", seeds.getDate());
                    value.put("TYPE", seeds.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);
                }
                break;

            default: //do something
        }
    }

    @Override
    public ArrayList<Object> retrieveList(DBHelper dbHelper, String type) {
        ArrayList<Seedlings> listSeedling = new ArrayList<Seedlings>();
        ArrayList<Seeds> listSeed = new ArrayList<Seeds>();
        ArrayList<Object> resultList = new ArrayList<Object>();

        dbRead = dbHelper.getReadableDatabase();
        String queryRetrieve = "SELECT * FROM " + "RAW_MATERIALS WHERE TYPE = '" + type + "' ";
        Cursor cursor = dbRead.rawQuery(queryRetrieve, null);

        switch (type) {
            case "Seedlings":
                if (cursor.moveToFirst()) {
                    do {

                        String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                        double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listSeedling.add(new Seedlings(typ, name, qty, price, date));

                    } while (cursor.moveToNext());
                }
                break;

            case "Seeds":
                if (cursor.moveToFirst()) {
                    do {

                        String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                        double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listSeed.add(new Seeds(typ, name, qty, price, date));

                    } while (cursor.moveToNext());
                }
                break;
            default: //do something
        }

        resultList.add(listSeedling);
        resultList.add(listSeed);

        return resultList;
    }


    @Override
    public Object retreiveOne(DBHelper dbHelper, String type, String name) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = dbRead.rawQuery(queryForRetrievalOne, null);
        Seeds seed = new Seeds(null, null, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, null);
        Object object = null;
        switch (type) {
            case "Seedlings":
                if (cursor.moveToFirst()) {
                    do {
                        seedling.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        seedling.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        seedling.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seedling.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seedling.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                }
                object = (Object) seedling;
                break;

            case "Seeds":
                if (cursor.moveToFirst()) {
                    do {
                        seed.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        seed.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        seed.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seed.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seed.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());
                }
                object = (Object) seed;
                break;

            default:
                return object;//do something
        }
        return object;
    }

    @Override
    public boolean checkExisting(DBHelper dbHelper, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "RAW_MATERIALS" + " WHERE NAME = '" + name + "' ";

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public void updateTransaction(DBHelper dbHelper, String Date, String type, String name, int quantity) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        String queryUpdate = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = dbRead.rawQuery(queryUpdate, null);
        Seeds seed = new Seeds(null, null, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, null);
        ContentValues val = new ContentValues();
        double costTotal = 0;


        switch (type) {
            case "Seedlings":
                if (cursor.moveToFirst()) {
                    do {
                        seedling.setType(cursor.getString(cursor.getColumnIndex("DATE")));
                        seedling.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        seedling.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        seedling.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seedling.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seedling.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());
                }

                val.put("DATE", seedling.getDate());
                val.put("TYPE", seedling.getType());
                val.put("NAME", seedling.getName());
                val.put("QUANTITY", seedling.getQuantity() + quantity);
                val.put("PRICE", seedling.getPrice());
                costTotal = (seedling.getQuantity() + quantity) * seedling.getPrice();
                val.put("TOTAL_COST", costTotal);
                dbWrite.update("RAW_MATERIALS", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
                break;

            case "Seeds":
                if (cursor.moveToFirst()) {
                    do {
                        seed.setType(cursor.getString(cursor.getColumnIndex("DATE")));
                        seed.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        seed.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        seed.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seed.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seed.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());
                }
                val.put("DATE", seed.getDate());
                val.put("TYPE", seed.getType());
                val.put("NAME", seed.getName());
                val.put("QUANTITY", seed.getQuantity() + quantity);
                val.put("PRICE", seed.getPrice());
                costTotal = (seed.getQuantity() + quantity) * seed.getPrice();
                val.put("TOTAL_COST", costTotal);
                dbWrite.update("RAW_MATERIALS", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
                break;

            default:
                ;//do something
        }
    }

    @Override
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbWrite = dbHelper.getWritableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbWrite.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }


}
