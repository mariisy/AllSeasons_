package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;

import java.util.ArrayList;

public class IndirectMaterialsDAOImpl implements IndirectMaterialsDAO {
    SQLiteDatabase dbWrite, dbRead;
    Insecticides ins;
    Fertilizers fer;
    Packaging pack;


    @Override
    public void addTransaction(DBHelper dbHelper, Object object, String type) {
        dbWrite = dbHelper.getWritableDatabase();

        switch (type) {
            case "Insecticides":
                if (object instanceof Insecticides) {
                    ins = (Insecticides) object;
                    double costTotal = Double.valueOf(ins.getQuantity()) * Double.valueOf(ins.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", ins.getDate());
                    val.put("TYPE", ins.getType());
                    val.put("NAME", ins.getName());
                    val.put("QUANTITY", ins.getQuantity());
                    val.put("PRICE", ins.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", ins.getDate());
                    value.put("TYPE", ins.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);
                }

                break;

            case "Fertilizer":
                if (object instanceof Fertilizers) {
                    fer = (Fertilizers) object;
                    double costTotal = Double.valueOf(fer.getQuantity()) * Double.valueOf(fer.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", fer.getDate());
                    val.put("TYPE", fer.getType());
                    val.put("NAME", fer.getName());
                    val.put("QUANTITY", fer.getQuantity());
                    val.put("PRICE", fer.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", fer.getDate());
                    value.put("TYPE", fer.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);
                }
                break;

            case "Packaging":
                if (object instanceof Packaging) {
                    pack = (Packaging) object;
                    double costTotal = Double.valueOf(pack.getQuantity()) * Double.valueOf(pack.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", pack.getDate());
                    val.put("TYPE", pack.getType());
                    val.put("NAME", pack.getName());
                    val.put("QUANTITY", pack.getQuantity());
                    val.put("PRICE", pack.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", pack.getDate());
                    value.put("TYPE", pack.getType());
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
        dbRead = dbHelper.getReadableDatabase();
        String queryRetrieve = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE TYPE = '" + type + "' ";
        ArrayList<Insecticides> listIns = new ArrayList<Insecticides>();
        ArrayList<Fertilizers> listFer = new ArrayList<Fertilizers>();
        ArrayList<Packaging> listPack = new ArrayList<Packaging>();
        ArrayList<Object> resultList = new ArrayList<Object>();
        Cursor cursor = dbRead.rawQuery(queryRetrieve, null);

        switch (type) {
            case "Insecticides":
                if (cursor.moveToFirst()) {
                    do {
                        String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                        double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listIns.add(new Insecticides(typ, name, qty, price, date));

                    } while (cursor.moveToNext());
                }

                break;

            case "Fertilizer":
                if (cursor.moveToFirst()) {
                    do {
                        String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                        double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listFer.add(new Fertilizers(typ, name, qty, price, date));

                    } while (cursor.moveToNext());
                }

                break;

            case "Packaging":
                if (cursor.moveToFirst()) {
                    do {
                        String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        int qty = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                        double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listPack.add(new Packaging(typ, name, qty, price, date));

                    } while (cursor.moveToNext());
                }

                break;
            default: //do something
        }


        resultList.add(listIns);
        resultList.add(listFer);
        resultList.add(listPack);

        return resultList;

    }

    @Override
    public Object retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "RAW_MATERIALS WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Insecticides in = new Insecticides(null, null, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, null);
        Object object = null;

        switch (type) {
            case "Insecticides":
                if (cursor.moveToFirst()) {
                    do {
                        in.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        in.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        in.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        in.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        in.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                }
                object = (Object) in;

                break;

            case "Fertilizer":
                if (cursor.moveToFirst()) {
                    do {
                        fe.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        fe.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        fe.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        fe.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        fe.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                }
                object = (Object) fe;
                break;

            case "Packaging":
                if (cursor.moveToFirst()) {
                    do {
                        pa.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        pa.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        pa.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        pa.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        pa.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                }
                object = (Object) pa;
                break;


            default: //do something
        }
        return object;
    }

    @Override
    public boolean checkExisting(DBHelper dbHelper, String name) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "INDIRECT_MATERIALS" + " WHERE NAME = '" + name + "' ";

        Cursor result = dbRead.rawQuery(queryForCheck, null);
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
        Insecticides in = new Insecticides(null, null, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, null);
        ContentValues val = new ContentValues();
        double costTotal = 0;

        switch (type) {
            case "Insecticides":
                if (cursor.moveToFirst()) {
                    do {
                        in.setType(cursor.getString(cursor.getColumnIndex("DATE")));
                        in.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        in.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        in.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        in.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        in.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                    val.put("DATE", ins.getDate());
                    val.put("TYPE", ins.getType());
                    val.put("NAME", ins.getName());
                    val.put("QUANTITY", ins.getQuantity());
                    val.put("PRICE", ins.getPrice());
                    costTotal = (ins.getQuantity() + quantity) * ins.getPrice();
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.update("RAW_MATERIALS", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
                }
                break;

            case "Fertilizer":
                if (cursor.moveToFirst()) {
                    do {
                        fe.setType(cursor.getString(cursor.getColumnIndex("DATE")));
                        fe.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        fe.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        fe.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        fe.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        fe.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                    val.put("DATE", fer.getDate());
                    val.put("TYPE", fer.getType());
                    val.put("NAME", fer.getName());
                    val.put("QUANTITY", fer.getQuantity());
                    val.put("PRICE", fer.getPrice());
                    costTotal = (fer.getQuantity() + quantity) * fer.getPrice();
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.update("RAW_MATERIALS", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
                }
                break;

            case "Packaging":
                if (cursor.moveToFirst()) {
                    do {
                        pa.setType(cursor.getString(cursor.getColumnIndex("DATE")));
                        pa.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        pa.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        pa.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        pa.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        pa.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                    } while (cursor.moveToNext());

                    val.put("DATE", pack.getDate());
                    val.put("TYPE", pack.getType());
                    val.put("NAME", pack.getName());
                    val.put("QUANTITY", pack.getQuantity());
                    val.put("PRICE", pack.getPrice());
                    costTotal = (pack.getQuantity() + quantity) * pack.getPrice();
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.update("RAW_MATERIALS", val, "NAME = '" + name + "'  AND TYPE = '" + type + "' ", null);
                }
                break;

            default: //do something
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
