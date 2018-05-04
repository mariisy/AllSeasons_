package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;
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

                    ContentValues val = new ContentValues();
                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("QUANTITY", seedlings.getQuantity());
                    val.put("PRICE", seedlings.getPrice());
                    val.put("TOTAL_COST", seedlings.getTotalPrice());
                    dbWrite.insert("RAW_MATERIALS", null, val);
                }
                break;

            case "Seeds":
                if (object instanceof Seeds) {
                    seeds = (Seeds) object;

                    ContentValues val = new ContentValues();
                    val.put("DATE", seeds.getDate());
                    val.put("TYPE", seeds.getType());
                    val.put("NAME", seeds.getName());
                    val.put("QUANTITY", seeds.getQuantity());
                    val.put("PRICE", seeds.getPrice());
                    val.put("TOTAL_COST", seeds.getTotalPrice());
                    dbWrite.insert("RAW_MATERIALS", null, val);
                }
                break;
            default: //do nothing
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
                        double totalPrice = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listSeedling.add(new Seedlings(typ, name, qty, price, totalPrice, date));
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
                        double totalPrice = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listSeed.add(new Seeds(typ, name, qty, price, totalPrice, date));
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
        Seeds seed = new Seeds(null, null, 0, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, 0, null);
        Object object = null;
        switch (type) {
            case "Seedlings":
                if (cursor.moveToFirst()) {
                    do {
                        seedling.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        seedling.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        seedling.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        seedling.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        seedling.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
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
                        seed.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
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
    public void updateTransaction(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Seeds seed = new Seeds(null, null, 0, 0, 0, null);
        Seedlings seedling = new Seedlings(null, null, 0, 0, 0, null);
        ContentValues val = new ContentValues();
        double costTotal = 0.0;
        ContentValues values = new ContentValues();
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


                    String queryUpdate2 = "SELECT * FROM " + "CASH WHERE NAME = '" + seedlings.getName() + "'  AND TYPE = '" + seedlings.getType() + "' ";
                    Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                    double credit=0;
                    if (cursor2.moveToFirst()) {
                        do {
                            credit = cursor2.getDouble(cursor2.getColumnIndex("CREDIT"));
                        } while (cursor2.moveToNext());
                    }
                    double totalCredit = credit + seedlings.getTotalPrice();
                    values.put("CREDIT",totalCredit);
                    dbRead.update("CASH", values, selection, selectionArgs);
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

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {seeds.getName()};
                    dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);

                    String queryUpdate2 = "SELECT * FROM " + "CASH WHERE NAME = '" + seeds.getName() + "'  AND TYPE = '" + seeds.getType() + "' ";
                    Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                    double credit=0;
                    if (cursor2.moveToFirst()) {
                        do {
                            credit = cursor2.getDouble(cursor2.getColumnIndex("CREDIT"));
                        } while (cursor2.moveToNext());
                    }
                    double totalCredit = credit + seeds.getTotalPrice();
                    values.put("CREDIT",totalCredit);
                    dbRead.update("CASH", values, selection, selectionArgs);
                }
            }
        }
    }

    @Override
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT NAME FROM " + "RAW_MATERIALS WHERE TYPE = '" + type + "' ";
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
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbWrite = dbHelper.getWritableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbWrite.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }
}
