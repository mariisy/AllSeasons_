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
    Insecticides insecticides;
    Fertilizers fertilizers;
    Packaging packaging;


    @Override
    public void addTransaction(DBHelper dbHelper, Object object, String type) {
        dbWrite = dbHelper.getWritableDatabase();

        switch (type) {
            case "Insecticides":
                if (object instanceof Insecticides) {
                    insecticides = (Insecticides) object;
                    double costTotal = Double.valueOf(insecticides.getQuantity()) * Double.valueOf(insecticides.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", insecticides.getDate());
                    val.put("TYPE", insecticides.getType());
                    val.put("NAME", insecticides.getName());
                    val.put("QUANTITY", insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", insecticides.getDate());
                    value.put("TYPE", insecticides.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);
                }

                break;

            case "Fertilizer":
                if (object instanceof Fertilizers) {
                    fertilizers = (Fertilizers) object;
                    double costTotal = Double.valueOf(fertilizers.getQuantity()) * Double.valueOf(fertilizers.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", fertilizers.getDate());
                    val.put("TYPE", fertilizers.getType());
                    val.put("NAME", fertilizers.getName());
                    val.put("QUANTITY", fertilizers.getQuantity());
                    val.put("PRICE", fertilizers.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", fertilizers.getDate());
                    value.put("TYPE", fertilizers.getType());
                    value.put("DEBIT", 0);
                    value.put("CREDIT", costTotal);
                    dbWrite.insert("CASH", null, value);
                }
                break;

            case "Packaging":
                if (object instanceof Packaging) {
                    packaging = (Packaging) object;
                    double costTotal = Double.valueOf(packaging.getQuantity()) * Double.valueOf(packaging.getPrice());

                    ContentValues val = new ContentValues();
                    val.put("DATE", packaging.getDate());
                    val.put("TYPE", packaging.getType());
                    val.put("NAME", packaging.getName());
                    val.put("QUANTITY", packaging.getQuantity());
                    val.put("PRICE", packaging.getPrice());
                    val.put("TOTAL_COST", costTotal);
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);

                    ContentValues value = new ContentValues();
                    value.put("DATE", packaging.getDate());
                    value.put("TYPE", packaging.getType());
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
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper, String type) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT NAME FROM " + "INDIRECT_MATERIALS WHERE TYPE = '" + type + "' ";
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
    public Object retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
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
                object = in;

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
                object = pa;
                break;

            default:
                return object; //do something
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
    public void updateTransaction(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, null);
        Packaging pa = new Packaging(null, null, 0, 0, null);
        ContentValues val = new ContentValues();
        double costTotal = 0;

        for (Object obj : objArray) {
            insecticides = (Insecticides) obj;
            if (obj instanceof Insecticides) {
                String queryUpdate = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + insecticides.getName() + "'  AND TYPE = '" + insecticides.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        insecticides.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        insecticides.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", insecticides.getDate());
                    val.put("TYPE", insecticides.getType());
                    val.put("NAME", insecticides.getName());
                    val.put("QUANTITY", in.getQuantity() + insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    costTotal = (in.getQuantity() + insecticides.getQuantity()) * insecticides.getPrice();
                    val.put("TOTAL_COST", costTotal);

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {insecticides.getName()};
                    dbRead.update("RAW_MATERIAL", val, selection, selectionArgs);
                }
            }

            if (obj instanceof Fertilizers) {
                String queryUpdate = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + insecticides.getName() + "'  AND TYPE = '" + insecticides.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        insecticides.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        insecticides.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", insecticides.getDate());
                    val.put("TYPE", insecticides.getType());
                    val.put("NAME", insecticides.getName());
                    val.put("QUANTITY", in.getQuantity() + insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    costTotal = (in.getQuantity() + insecticides.getQuantity()) * insecticides.getPrice();
                    val.put("TOTAL_COST", costTotal);

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {insecticides.getName()};
                    dbRead.update("RAW_MATERIAL", val, selection, selectionArgs);
                }
            }

            if (obj instanceof Packaging) {
                String queryUpdate = "SELECT * FROM " + "EQUIPMENT WHERE NAME = '" + packaging.getName() + "'  AND TYPE = '" + packaging.getType() + "' ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);

                if (cursor.moveToFirst()) {
                    do {
                        packaging.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                        packaging.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    } while (cursor.moveToNext());

                    val.put("DATE", packaging.getDate());
                    val.put("TYPE", packaging.getType());
                    val.put("NAME", packaging.getName());
                    val.put("QUANTITY", pa.getQuantity() + packaging.getQuantity());
                    val.put("PRICE", packaging.getPrice());
                    costTotal = (pa.getQuantity() + packaging.getQuantity()) * packaging.getPrice();
                    val.put("TOTAL_COST", costTotal);

                    String selection = "NAME" + " LIKE ?";
                    String[] selectionArgs = {packaging.getName()};
                    dbRead.update("RAW_MATERIAL", val, selection, selectionArgs);
                }
            }


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
