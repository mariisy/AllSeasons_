package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        String queryForCheck = "SELECT NAME FROM " + "EMPLOYEE" + " WHERE NAME = '" + name + "' AND ACCOUNT_TYPE = '"+type+"'";

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
                listHolder.add(new WarehouseMaterial(typ, name, price));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }

    @Override
    public void addEntry(DBHelper dbHelper, Object object, String type) {
        dbWrite = dbHelper.getWritableDatabase();

        switch (type) {
            case "Seedlings":
                if (object instanceof Seedlings) {
                    seedlings = (Seedlings) object;
                    double costTotal = Double.valueOf(seedlings.getQuantity()) * Double.valueOf(seedlings.getPrice());

                    ContentValues values = new ContentValues();
                    values.put("TYPE", seedlings.getType());
                    values.put("NAME", seedlings.getName());
                    values.put("PRICE", seedlings.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);
                }

                break;

            case "Seeds":
                if (object instanceof Seeds) {
                    seeds = (Seeds) object;
                    double costTotal = Double.valueOf(seeds.getQuantity()) * Double.valueOf(seeds.getPrice());

                    ContentValues values = new ContentValues();
                    values.put("TYPE", seeds.getType());
                    values.put("NAME", seeds.getName());
                    values.put("PRICE", seeds.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);
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
                if (object instanceof Employees) {
                    employees = (Employees) object;
                    ContentValues values = new ContentValues();
                    values.put("EMPLOYEE_ID", employees.getEmployeeID());
                    if(employees.getAccountype().equals("Farmer")){
                        values.put("EMPLOYEE_FULL_ID", "EMPFRM" + employees.getEmployeeID());
                    }
                    if(employees.getAccountype().equals("Staff")){
                        values.put("EMPLOYEE_FULL_ID", "EMPSTF" + employees.getEmployeeID());
                    }
                    if(employees.getAccountype().equals("Supervisor")){
                        values.put("EMPLOYEE_FULL_ID", "EMPSRV" + employees.getEmployeeID());
                    }
                    values.put("NAME", employees.getName());
                    values.put("ACCOUNT_TYPE", employees.getAccountype());
                    values.put("SALARY", employees.getSalary());
                    dbWrite.insert("EMPLOYEE", null, values);
                }

                break;

           default: //do something
        }
    }


    @Override
    public HashMap<String, List<String>> retrieveBoughtList(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryGetDate = "SELECT DATE FROM TRANSACTIONS";
        Cursor cursor = dbWrite.rawQuery(queryGetDate, null);

        HashMap<String, List<String>> listDate = new HashMap<String, List<String>>();
        List<String> listTransacion = new ArrayList<String>();

        listDate.put("",listTransacion);


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
