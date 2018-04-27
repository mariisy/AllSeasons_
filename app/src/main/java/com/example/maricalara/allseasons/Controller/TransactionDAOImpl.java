package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Employees;
import com.example.maricalara.allseasons.Model.Equipment;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    SQLiteDatabase dbWrite, dbRead;
    private Insecticides ins;
    private Fertilizers fer;
    private Packaging pack;
    private Seedlings seedlings;
    private Seeds seeds;
    Crops crop;
    private Equipment equipment;
    private Employees employees;

    @Override
    public Cursor getAllData(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM WAREHOUSE_EQUIPMENT", null);
        return result;
    }

    @Override
    public Cursor getAllEmployee(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM EMPLOYEE", null);
        return result;
    }

    @Override
    public boolean checkExistingEmployee(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "EMPLOYEE" + " WHERE NAME = '" + name + "' AND ACCOUNT_TYPE = '" + type + "'";

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public ArrayList<WarehouseMaterial> getAllDataWarehouse(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryGetAll = "SELECT * FROM WAREHOUSE_EQUIPMENT";
        ArrayList<WarehouseMaterial> listHolder = new ArrayList<>();
        Cursor cursor = dbWrite.rawQuery(queryGetAll, null);
        // WarehouseMaterial warehouseMaterial = new WarehouseMaterial(null,null,0);

        if (cursor.moveToFirst()) {
            do {
                String typ = cursor.getString(cursor.getColumnIndex("TYPE"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                listHolder.add(new WarehouseMaterial("", "", typ, name, price));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }

    @Override
    public void addEntry(DBHelper dbHelper, Object object, String type) {
        dbWrite = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();
        switch (type) {
            case "Seedlings":
                if (object instanceof Seedlings) {
                    seedlings = (Seedlings) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", seedlings.getType());
                    values.put("NAME", seedlings.getName());
                    values.put("PRICE", seedlings.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);


                    ContentValues val = new ContentValues();
                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("QUANTITY", 0);
                    val.put("PRICE", seedlings.getPrice());
                    val.put("TOTAL_COST", 0);
                    dbWrite.insert("FGI", null, val);
                }

                break;

            case "Seeds":
                if (object instanceof Seeds) {
                    seeds = (Seeds) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", seeds.getType());
                    values.put("NAME", seeds.getName());
                    values.put("PRICE", seeds.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                }
                break;

            case "Crops":
                if (object instanceof Seeds) {
                    crop = (Crops) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", crop.getType());
                    values.put("NAME", crop.getName());
                    values.put("PRICE", crop.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", crop.getDate());
                    val.put("TYPE", crop.getType());
                    val.put("NAME", crop.getName());
                    val.put("QUANTITY", 0);
                    val.put("PRICE", 0);
                    val.put("TOTAL_COST", 0);
                    dbWrite.insert("FGI", null, val);
                }
                break;

            case "Insecticides":
                if (object instanceof Insecticides) {
                    ins = (Insecticides) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", ins.getType());
                    values.put("NAME", ins.getName());
                    values.put("PRICE", ins.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", ins.getDate());
                    val.put("TYPE", ins.getType());
                    val.put("NAME", ins.getName());
                    val.put("QUANTITY", 0);
                    val.put("PRICE", ins.getPrice());
                    val.put("TOTAL_COST", 0);
                    dbWrite.insert("WPI", null, val);
                }

                break;

            case "Fertilizer":
                if (object instanceof Fertilizers) {
                    fer = (Fertilizers) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", fer.getType());
                    values.put("NAME", fer.getName());
                    values.put("PRICE", fer.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", fer.getDate());
                    val.put("TYPE", fer.getType());
                    val.put("NAME", fer.getName());
                    val.put("QUANTITY", 0);
                    val.put("PRICE", fer.getPrice());
                    val.put("TOTAL_COST", 0);
                    dbWrite.insert("FGI", null, val);
                }
                break;

            case "Packaging":
                if (object instanceof Packaging) {
                    pack = (Packaging) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", pack.getType());
                    values.put("NAME", pack.getName());
                    values.put("PRICE", pack.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", pack.getDate());
                    val.put("TYPE", pack.getType());
                    val.put("NAME", pack.getName());
                    val.put("QUANTITY", 0);
                    val.put("PRICE", pack.getPrice());
                    val.put("TOTAL_COST", 0);
                    dbWrite.insert("FGI", null, val);
                }
                break;

            case "Equipment":
                if (object instanceof Equipment) {
                    equipment = (Equipment) object;

                    ContentValues values = new ContentValues();
                    values.put("TYPE", equipment.getType());
                    values.put("NAME", equipment.getName());
                    values.put("PRICE", equipment.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);
                }
                break;

            case "Employee":
                Employees employee = new Employees(0, null, null, null, null, 0);
                if (object instanceof Employees) {
                    employees = (Employees) object;
                    ContentValues values = new ContentValues();
                    values.put("EMPLOYEE_FULL_ID", employees.getEmployeeFullID());
                    values.put("NAME", employees.getName());
                    values.put("ACCOUNT_TYPE", employees.getAccountype());
                    values.put("SALARY", employees.getSalary());
                    dbWrite.insert("EMPLOYEE", null, values);

                    String queryUpdate = "SELECT * FROM " + "EMPLOYEE WHERE NAME = '" + employees.getName() + "'  ";
                    Cursor cursor = dbRead.rawQuery(queryUpdate, null);
                    ContentValues values2 = new ContentValues();

                    if (cursor.moveToFirst()) {
                        do {
                            employee.setEmployeeID(cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID")));
                            employee.setAccountype(cursor.getString(cursor.getColumnIndex("ACCOUNT_TYPE")));
                        } while (cursor.moveToNext());
                        String selection = "NAME" + " LIKE ?";
                        String[] selectionArgs = {employees.getName()};

                        switch (employee.getAccountype()) {
                            case "Farmer":
                                values2.put("EMPLOYEE_FULL_ID", "EMPFRM" + String.format("%03d", employee.getEmployeeID()));
                                dbRead.update("EMPLOYEE", values2, selection, selectionArgs);
                                break;

                            case "Staff":
                                values2.put("EMPLOYEE_FULL_ID", "EMPSTF" + String.format("%03d", employee.getEmployeeID()));
                                dbRead.update("EMPLOYEE", values2, selection, selectionArgs);
                                break;
                            case "Supervisor":
                                values2.put("EMPLOYEE_FULL_ID", "EMPSPV" + String.format("%03d", employee.getEmployeeID()));
                                dbRead.update("EMPLOYEE", values2, selection, selectionArgs);
                                break;

                            default:
                                break;
                        }
                    }
                }

                break;

            default: //do something
        }
    }

    @Override
    public void addBoughtList(DBHelper dbHelper, Object object, String type) {

    }

    @Override
    public void addSoldList(DBHelper dbHelper, Object object, String type) {

    }


    @Override
    public HashMap<String, List<String>> retrieveBoughtList(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryGetDate = "SELECT DATE FROM TRANSACTIONS";
        Cursor cursor = dbWrite.rawQuery(queryGetDate, null);

        HashMap<String, List<String>> listDate = new HashMap<String, List<String>>();
        List<String> listTransacion = new ArrayList<String>();

        listDate.put("", listTransacion);


        return listDate;
    }

    @Override
    public HashMap<String, List<String>> retrieveSoldList(DBHelper dbHelper) {
        HashMap<String, List<String>> listDate = new HashMap<String, List<String>>();
        List<String> listTransaction = new ArrayList<String>();


        return listDate;
    }


    @Override
    public boolean checkExistingWarehouse(DBHelper dbHelper, String type, String name) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "WAREHOUSE_EQUIPMENT" + " WHERE NAME = '" + name + "' AND TYPE = '" + type + "' ";

        Cursor result = dbWrite.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL
    }

    @Override
    public void updateEntry(DBHelper dbHelper, String name, Double price) {
        dbRead = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("PRICE", price);

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};

        dbRead.update("WAREHOUSE_EQUIPMENT", values, selection, selectionArgs);

    }

    @Override
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbRead = dbHelper.getReadableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbRead.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }
}
