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

                    ContentValues val = new ContentValues();
                    val.put("DATE", insecticides.getDate());
                    val.put("TYPE", insecticides.getType());
                    val.put("NAME", insecticides.getName());
                    val.put("QUANTITY", insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    val.put("TOTAL_COST", insecticides.getTotalPrice());
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);
                }
                break;

            case "Fertilizer":
                if (object instanceof Fertilizers) {
                    fertilizers = (Fertilizers) object;

                    ContentValues val = new ContentValues();
                    val.put("DATE", fertilizers.getDate());
                    val.put("TYPE", fertilizers.getType());
                    val.put("NAME", fertilizers.getName());
                    val.put("QUANTITY", fertilizers.getQuantity());
                    val.put("PRICE", fertilizers.getPrice());
                    val.put("TOTAL_COST", fertilizers.getTotalPrice());
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);
                }
                break;

            case "Packaging":
                if (object instanceof Packaging) {
                    packaging = (Packaging) object;

                    ContentValues val = new ContentValues();
                    val.put("DATE", packaging.getDate());
                    val.put("TYPE", packaging.getType());
                    val.put("NAME", packaging.getName());
                    val.put("QUANTITY", packaging.getQuantity());
                    val.put("PRICE", packaging.getPrice());
                    val.put("TOTAL_COST", packaging.getTotalPrice());
                    dbWrite.insert("INDIRECT_MATERIALS", null, val);
                }
                break;
            default: //do nothing
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
                        double totalPrice = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listIns.add(new Insecticides(typ, name, qty, price, totalPrice, date,null));

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
                        double totalPrice = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listFer.add(new Fertilizers(typ, name, qty, price, totalPrice, date,null));

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
                        double totalPrice = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                        String date = cursor.getString(cursor.getColumnIndex("DATE"));
                        listPack.add(new Packaging(typ, name, qty, price, totalPrice, date,null));
                    } while (cursor.moveToNext());
                }
                break;
            default: //do nothing
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
        Insecticides in = new Insecticides(null, null, 0, 0, 0, null,null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, 0, null,null);
        Packaging pa = new Packaging(null, null, 0, 0, 0, null,null);
        Object object = null;

        switch (type) {
            case "Insecticides":
                if (cursor.moveToFirst()) {
                    do {
                        in.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                        in.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                        in.setQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                        in.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                        in.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
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
                        fe.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
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
                        pa.setTotalPrice(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST")));
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
    public Cursor getAllDataIM(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM INDIRECT_MATERIALS", null);
        return result;
    }
    @Override
    public void updateTransactionAdd(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, 0, null,null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, 0, null,null);
        Packaging pa = new Packaging(null, null, 0, 0, 0, null,null);
        ContentValues val = new ContentValues();
        double costTotal = 0;
        ContentValues values = new ContentValues();
        for (Object obj : objArray) {

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


                    String queryUpdate2 = "SELECT * FROM " + "CASH WHERE NAME = '" + insecticides.getName() + "'  AND TYPE = '" + insecticides.getType() + "' ";
                    Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                    double credit=0;
                    if (cursor2.moveToFirst()) {
                        do {
                            credit = cursor2.getDouble(cursor2.getColumnIndex("CREDIT"));
                        } while (cursor2.moveToNext());
                    }
                    double totalCredit = credit + insecticides.getTotalPrice();
                    values.put("CREDIT",totalCredit);
                    dbRead.update("CASH", values, selection, selectionArgs);

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

                    String queryUpdate2 = "SELECT * FROM " + "CASH WHERE NAME = '" + fertilizers.getName() + "'  AND TYPE = '" + fertilizers.getType() + "' ";
                    Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                    double credit=0;
                    if (cursor2.moveToFirst()) {
                        do {
                            credit = cursor2.getDouble(cursor2.getColumnIndex("CREDIT"));
                        } while (cursor2.moveToNext());
                    }
                    double totalCredit = credit + fertilizers.getTotalPrice();
                    values.put("CREDIT",totalCredit);
                    dbRead.update("CASH", values, selection, selectionArgs);

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
                    String queryUpdate2 = "SELECT * FROM " + "CASH WHERE NAME = '" + packaging.getName() + "'  AND TYPE = '" + packaging.getType() + "' ";
                    Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                    double credit=0;
                    if (cursor2.moveToFirst()) {
                        do {
                            credit = cursor2.getDouble(cursor2.getColumnIndex("CREDIT"));
                        } while (cursor2.moveToNext());
                    }
                    double totalCredit = credit + packaging.getTotalPrice();
                    values.put("CREDIT",totalCredit);
                    dbRead.update("CASH", values, selection, selectionArgs);
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
