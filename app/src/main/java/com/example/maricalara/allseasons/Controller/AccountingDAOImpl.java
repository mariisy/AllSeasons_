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
import java.util.Arrays;
import java.util.List;

public class AccountingDAOImpl implements AccountingDAO {
    SQLiteDatabase dbWrite, dbRead;
    Crops crops;
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
    public Cursor getAllDataFGI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM FGI", null);
        return result;
    }

    @Override
    public Cursor getAllDataCGS(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM CGS", null);
        return result;
    }

    @Override
    public Cursor getAllDataSalesRevenue(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM SALES_REVENUE", null);
        return result;
    }

    @Override
    public void addSFP(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT * FROM SFP";
        Cursor result = dbWrite.rawQuery(queryForCheck, null);
        ContentValues val = new ContentValues();
        ArrayList<String> list = new ArrayList<>();
        list.add("Raw Materials");
        list.add("Indirect Materials");
        list.add("Equipment");
        list.add("WPI");
        list.add("Direct Labor");
        list.add("Indirect Labor");
        list.add("Salaries Expense");
        list.add("FGI");
        list.add("CGS");
        list.add("Sales Revenue");
        list.add("Cash");
        if (result.getCount() == 0) {
            for(String accType : list){
                val.put("ACCOUNT_TYPE", accType);
                val.put("DEBIT", 0);
                val.put("CREDIT", 0);
                dbWrite.insert("SFP", null, val);
            }
        }

    }

    @Override
    public void updateSFP(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        ContentValues val = new ContentValues();
        String addRM = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "RAW_MATERIALS ";
        Cursor cursor = dbRead.rawQuery(addRM, null);
        double debit =0;
        if (cursor.moveToFirst()) {
            do {
                debit = cursor.getDouble(cursor.getColumnIndex("DEBIT"));
            } while (cursor.moveToNext());
        }
        String selection = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs = {"Raw Materials"};
        val.put("ACCOUNT_TYPE","Raw Materials");
        val.put("DEBIT", debit);
        val.put("CREDIT",0);
        dbRead.update("SFP", val, selection, selectionArgs);

        ContentValues val1 = new ContentValues();
        String addIM = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "INDIRECT_MATERIALS ";
        Cursor cursor1 = dbRead.rawQuery(addIM, null);
        double debit1 =0;
        if (cursor1.moveToFirst()) {
            do {
                debit1 = cursor1.getDouble(cursor1.getColumnIndex("DEBIT"));
            } while (cursor1.moveToNext());
        }
        String selection1 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs1 = {"Indirect Materials"};
        val1.put("ACCOUNT_TYPE","Indirect Materials");
        val1.put("DEBIT", debit1);
        val1.put("CREDIT",0);
        dbRead.update("SFP", val1, selection1, selectionArgs1);


        ContentValues val2 = new ContentValues();
        String addEquip = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "EQUIPMENT ";
        Cursor cursor2 = dbRead.rawQuery(addEquip, null);
        double debit2 =0;
        if (cursor2.moveToFirst()) {
            do {
                debit2 = cursor2.getDouble(cursor2.getColumnIndex("DEBIT"));
            } while (cursor2.moveToNext());
        }
        String selection2 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs2 = {"Equipment"};
        val2.put("ACCOUNT_TYPE","Equipment");
        val2.put("DEBIT", debit2);
        val2.put("CREDIT",0);
        dbRead.update("SFP", val2, selection2, selectionArgs2);

        ContentValues val3 = new ContentValues();
        String addWPI = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "WPI ";
        Cursor cursor3 = dbRead.rawQuery(addWPI, null);
        double debit3 =0;
        if (cursor3.moveToFirst()) {
            do {
                debit3 = cursor3.getDouble(cursor3.getColumnIndex("DEBIT"));
            } while (cursor3.moveToNext());
        }
        String selection3 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs3 = {"WPI"};
        val3.put("ACCOUNT_TYPE","WPI");
        val3.put("DEBIT", debit3);
        val3.put("CREDIT",0);
        dbRead.update("SFP", val3, selection3, selectionArgs3);

        ContentValues val4 = new ContentValues();
        String addFGI = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "FGI ";
        Cursor cursor4 = dbRead.rawQuery(addFGI, null);
        double debit4 =0;
        if (cursor4.moveToFirst()) {
            do {
                debit4 = cursor4.getDouble(cursor4.getColumnIndex("DEBIT"));
            } while (cursor4.moveToNext());
        }
        String selection4 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs4 = {"FGI"};
        val4.put("ACCOUNT_TYPE","FGI");
        val4.put("DEBIT", debit4);
        val4.put("CREDIT",0);
        dbRead.update("SFP", val4, selection4, selectionArgs4);


        ContentValues val5 = new ContentValues();
        String addCash = "SELECT SUM(DEBIT) AS DEBIT, SUM(CREDIT) AS CREDIT FROM " + "CASH";
        Cursor cursor5 = dbRead.rawQuery(addCash, null);
        double debit5 =0, credit5=0;
        if (cursor5.moveToFirst()) {
            do {
                debit5 = cursor5.getDouble(cursor5.getColumnIndex("DEBIT"));
                credit5 = cursor5.getDouble(cursor5.getColumnIndex("CREDIT"));
            } while (cursor4.moveToNext());
        }
        String selection5 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs5 = {"Cash"};
        val5.put("ACCOUNT_TYPE","Cash");
        if(debit5>credit5){
            val5.put("DEBIT", debit5-credit5);
        }
        else {
            val5.put("CREDIT", credit5-debit5);
        }
        dbRead.update("SFP", val5, selection5, selectionArgs5);


    }

    @Override
    public ArrayList<String> viewSFP(DBHelper dbHelper,String columnName) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT "+ columnName + " FROM  SFP";
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex(columnName)));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }

    @Override
    public void addSCI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT * FROM SCI";
        Cursor result = dbWrite.rawQuery(queryForCheck, null);
        ContentValues val = new ContentValues();
        ArrayList<String> list = new ArrayList<>();
        list.add("Raw Materials");
        list.add("Indirect Materials");
        list.add("Equipment");
        list.add("WPI");
        list.add("Direct Labor");
        list.add("Indirect Labor");
        list.add("Salaries Expense");
        list.add("FGI");
        list.add("CGS");
        list.add("Sales Revenue");
        list.add("Cash");
        if (result.getCount() == 0) {
            for(String accType : list){
                val.put("ACCOUNT_TYPE", accType);
                val.put("DEBIT", 0);
                val.put("CREDIT", 0);
                dbWrite.insert("SCI", null, val);
            }
        }

    }

    @Override
    public void updateSCI(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        ContentValues val = new ContentValues();
        String addIL = "SELECT SUM(SALARY) AS CREDIT FROM EMPLOYEE WHERE ACCOUNT_TYPE= 'Staff'  OR ACCOUNT_TYPE= 'Supervisor' ";
        Cursor cursor = dbRead.rawQuery(addIL, null);
        double credit =0;
        if (cursor.moveToFirst()) {
            do {
                credit = cursor.getDouble(cursor.getColumnIndex("CREDIT"));
            } while (cursor.moveToNext());
        }
        String selection = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs = {"Indirect Labor"};
        val.put("ACCOUNT_TYPE","Indirect Labor");
        val.put("DEBIT", 0);
        val.put("CREDIT",credit);
        dbRead.update("SCI", val, selection, selectionArgs);

        ContentValues val1 = new ContentValues();
        String addDL = "SELECT SUM(SALARY) AS CREDIT FROM EMPLOYEE WHERE ACCOUNT_TYPE= 'Farmer'  ";
        Cursor cursor1 = dbRead.rawQuery(addDL, null);
        double credit1 =0;
        if (cursor1.moveToFirst()) {
            do {
                credit1 = cursor1.getDouble(cursor1.getColumnIndex("CREDIT"));
            } while (cursor1.moveToNext());
        }
        String selection1 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs1 = {"Direct Labor"};
        val1.put("ACCOUNT_TYPE","Direct Labor");
        val1.put("DEBIT", 0);
        val1.put("CREDIT",credit1);
        dbRead.update("SCI", val1, selection1, selectionArgs1);


        ContentValues val2 = new ContentValues();
        String addSalary = "SELECT SUM(SALARY) AS DEBIT FROM " + "EMPLOYEE ";
        Cursor cursor2 = dbRead.rawQuery(addSalary, null);
        double debit2 =0;
        if (cursor2.moveToFirst()) {
            do {
                debit2 = cursor2.getDouble(cursor2.getColumnIndex("DEBIT"));
            } while (cursor2.moveToNext());
        }
        String selection2 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs2 = {"Salaries Expense"};
        val2.put("ACCOUNT_TYPE","Salaries Expense");
        val2.put("DEBIT", debit2);
        val2.put("CREDIT",0);
        dbRead.update("SCI", val2, selection2, selectionArgs2);

        ContentValues val3 = new ContentValues();
        String addCGS = "SELECT SUM(TOTAL_COST) AS DEBIT FROM " + "CGS ";
        Cursor cursor3 = dbRead.rawQuery(addCGS, null);
        double debit3 =0;
        if (cursor3.moveToFirst()) {
            do {
                debit3 = cursor3.getDouble(cursor3.getColumnIndex("DEBIT"));
            } while (cursor3.moveToNext());
        }
        String selection3 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs3 = {"CGS"};
        val3.put("ACCOUNT_TYPE","CGS");
        val3.put("DEBIT", debit3);
        val3.put("CREDIT",0);
        dbRead.update("SCI", val3, selection3, selectionArgs3);

        ContentValues val4 = new ContentValues();
        String addFGI = "SELECT SUM(TOTAL_EARNINGS) AS CREDIT FROM " + "SALES_REVENUE ";
        Cursor cursor4 = dbRead.rawQuery(addFGI, null);
        double credit4 =0;
        if (cursor4.moveToFirst()) {
            do {
                credit4 = cursor4.getDouble(cursor4.getColumnIndex("CREDIT"));
            } while (cursor4.moveToNext());
        }
        String selection4 = "ACCOUNT_TYPE" + " LIKE ?";
        String[] selectionArgs4 = {"Sales Revenue"};
        val4.put("ACCOUNT_TYPE","Sales Revenue");
        val4.put("DEBIT",0 );
        val4.put("CREDIT",credit4);
        dbRead.update("SCI", val4, selection4, selectionArgs4);
    }

    @Override
    public ArrayList<String> viewSCI(DBHelper dbHelper,String columnName) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT "+ columnName + " FROM  SCI";
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex(columnName)));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }
    @Override
    public Cursor getAllDataCash(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM CASH where TYPE = 'Crops' ", null);
        return result;
    }

    @Override
    public Cursor getAllPlan(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM RESOURCE_PLANNING_TABLE", null);
        return result;
    }

    @Override
    public Cursor getAllUtilizeCGS(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM UTILIZE_CGS", null);
        return result;
    }

    @Override
    public Cursor getAllUtilizeWPI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM UTILIZE_WPI", null);
        return result;
    }

    @Override
    public Cursor getAllUtilizeFGI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM UTILIZE_FGI", null);
        return result;
    }

    @Override
    public void addEntry(DBHelper dbHelper, String type) {
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TOTAL_COST", 0);
        dbWrite.insert(type, null, values);
    }

    @Override
    public Crops retrieveOne2(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "WAREHOUSE_EQUIPMENT WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Crops crops = new Crops(null, null, 0, 0, 0, null,0,0,0);

        if (cursor.moveToFirst()) {
            do {
                crops.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                crops.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                crops.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
            } while (cursor.moveToNext());
        }

        return crops;
    }

    @Override
    public boolean checkExisting(DBHelper dbHelper, String type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT * FROM " + type;

        Cursor result = db.rawQuery(queryForCheck, null);
        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL

    }

    @Override
    public void addEntryPlanning(DBHelper dbHelper, ArrayList<Object> objArray, double hectareSize) {
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues val = new ContentValues();
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


                val.put("NAME", seeds.getName());
                val.put("SEEDS_NAME", seeds.getName());
                val.put("SEEDS_PRICE", seeds.getPrice());
            }

            if (obj instanceof Fertilizers) {
                fertilizers = (Fertilizers) obj;
                values.put("FERTILIZER_NAME", fertilizers.getName());
                values.put("FERTILIZER_PRICE", fertilizers.getPrice());
                values.put("FERTILIZER_QUANTITY", fertilizers.getQuantity());
                values.put("FERTILIZER_COST", fertilizers.getTotalPrice());
                values.put("FERTILIZER_PERCENTAGE", 1);
                totalCost += fertilizers.getTotalPrice();

                val.put("FERTILIZER_NAME", fertilizers.getName());
                val.put("FERTILIZER_PRICE", fertilizers.getPrice());
            }

            if (obj instanceof Insecticides) {
                insecticides = (Insecticides) obj;
                values.put("INSECTICIDES_NAME", insecticides.getName());
                values.put("INSECTICIDES_PRICE", insecticides.getPrice());
                values.put("INSECTICIDES_QUANTITY", insecticides.getQuantity());
                values.put("INSECTICIDES_COST", insecticides.getTotalPrice());
                values.put("INSECTICIDES_PERCENTAGE", 1);
                totalCost += insecticides.getTotalPrice();

                val.put("INSECTICIDES_NAME", insecticides.getName());
                val.put("INSECTICIDES_PRICE", insecticides.getPrice());
            }




        }
        values.put("TOTAL_PERCENTAGE_PRODUCTS", 1);
        values.put("HECTARE_SIZE", hectareSize);
        values.put("PERCENTAGE_HECTARE_DONE", 1);
        values.put("TOTAL_COST", totalCost);
        dbWrite.insert("RESOURCE_PLANNING_TABLE", null, values);



        val.put("SEEDS_QUANTITY", 0);
        val.put("SEEDS_COST",0 );
        val.put("SEEDS_PERCENTAGE", 0);

        val.put("FERTILIZER_QUANTITY", 0);
        val.put("FERTILIZER_COST", 0);
        val.put("FERTILIZER_PERCENTAGE", 0);

        val.put("INSECTICIDES_QUANTITY", 0);
        val.put("INSECTICIDES_COST", 0);
        val.put("INSECTICIDES_PERCENTAGE", 0);

        val.put("TOTAL_PERCENTAGE_PRODUCTS", 0);

        val.put("HECTARE_SIZE", hectareSize);

        val.put("PERCENTAGE_HECTARE_DONE", 0);

        val.put("TOTAL_COST", 0);

        dbWrite.insert("UTILIZE_WPI", null, val);
    }


    public Crops retrieveOne(DBHelper dbHelper, String type, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "UTILIZE_FGI WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Crops crops = new Crops(null, null, 0, 0, 0, null,0,0,0);

        if (cursor.moveToFirst()) {
            do {
                crops.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                crops.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                crops.setWeight(cursor.getDouble(cursor.getColumnIndex("WEIGHT")));
                crops.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                crops.setTotalCostHarvested(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST_HARVESTED")));
                crops.setHectarePercent(cursor.getDouble(cursor.getColumnIndex("PERCENTAGE_HECTARE_DONE")));
                crops.setHectareHarvested(cursor.getDouble(cursor.getColumnIndex("HECTARE_HARVESTED")));
            } while (cursor.moveToNext());
        }

        return crops;
    }

    @Override
    public void updateWPI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Insecticides in = new Insecticides(null, null, 0, 0, 0, null,null);
        Fertilizers fe = new Fertilizers(null, null, 0, 0, 0, null,null);
        Packaging pa = new Packaging(null, null, 0, 0, 0, null,null);
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


                String queryUpdate3 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double costTotal3 = 0;
                if (cursor3.moveToFirst()) {
                    do {
                        costTotal3 = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST"));
                    } while (cursor3.moveToNext());
                    val3.put("TOTAL_COST", costTotal3 + seedlings.getTotalPrice());
                    dbRead.update("WPI", val3, "WPIID=" + 1, null);
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
                }
                val.put("DATE", seeds.getDate());
                val.put("TYPE", seeds.getType());
                val.put("NAME", seeds.getName());
                val.put("QUANTITY", seed.getQuantity() - seeds.getQuantity());
                val.put("PRICE", seeds.getPrice());
                val.put("TOTAL_COST", seed.getTotalPrice() - seeds.getTotalPrice());
                String selection = "NAME" + " LIKE ?";
                String[] selectionArgs = {seeds.getName()};
                dbRead.update("RAW_MATERIALS", val, selection, selectionArgs);


                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues val2 = new ContentValues();
                double costTotal2 = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal2 = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                    val2.put("TOTAL_COST", costTotal2 + seeds.getTotalPrice());
                }
                dbRead.update("WPI", val2, "WPIID=" + 1, null);


                String queryUpdate3 = "SELECT * FROM UTILIZE_WPI WHERE NAME = '" + seeds.getName() + "'";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double  seeds_quantity = 0,seeds_cost = 0, fertilzer_percent=0,insecticides_percent=0,total_cost=0 ;
                if (cursor3.moveToFirst()) {
                    do {
                        seeds_quantity = cursor3.getDouble(cursor3.getColumnIndex("SEEDS_QUANTITY"));
                        seeds_cost = cursor3.getDouble(cursor3.getColumnIndex("SEEDS_COST"));
                        total_cost = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST"));
                        fertilzer_percent = cursor3.getDouble(cursor3.getColumnIndex("FERTILIZER_PERCENTAGE"));
                        insecticides_percent = cursor3.getDouble(cursor3.getColumnIndex("INSECTICIDES_PERCENTAGE"));
                    } while (cursor3.moveToNext());
                }

                String queryUpdate4 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" + seeds.getName() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double  seeds_quantity2 = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        seeds_quantity2 = cursor4.getDouble(cursor4.getColumnIndex("SEEDS_QUANTITY"));
                    } while (cursor4.moveToNext());
                }

                val3.put("SEEDS_PRICE", seeds.getPrice());
                val3.put("SEEDS_QUANTITY",seeds_quantity + seeds.getQuantity());
                val3.put("SEEDS_COST", seeds_cost + seeds.getTotalPrice());
                val3.put("SEEDS_PERCENTAGE",(seeds_quantity + seeds.getQuantity())/seeds_quantity2);
                val3.put("TOTAL_PERCENTAGE_PRODUCTS",(fertilzer_percent+insecticides_percent +((seeds_quantity + seeds.getQuantity())/seeds_quantity2))/3);
                val3.put("PERCENTAGE_HECTARE_DONE",(fertilzer_percent+insecticides_percent +((seeds_quantity + seeds.getQuantity())/seeds_quantity2))/3);
                val3.put("TOTAL_COST",total_cost+ seeds.getTotalPrice());
                dbRead.update("UTILIZE_WPI", val3, selection, selectionArgs);



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
                    val.put("QUANTITY", in.getQuantity() - insecticides.getQuantity());
                    val.put("PRICE", insecticides.getPrice());
                    val.put("TOTAL_COST", in.getTotalPrice() - insecticides.getTotalPrice());
                }
                String selection = "NAME" + " LIKE ?";
                String[] selectionArgs = {insecticides.getName()};
                dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);


                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                }
                values.put("TOTAL_COST", costTotal + insecticides.getTotalPrice());
                dbRead.update("WPI", values, "WPIID=" + 1, null);

                String selection2 = "NAME" + " LIKE ?";
                String[] selectionArgs2 = {insecticides.getCropType()};

                String queryUpdate3 = "SELECT * FROM UTILIZE_WPI WHERE NAME = '" +insecticides.getCropType() + "'";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double  insecticides_quantity = 0,insecticides_cost = 0,  fertilzer_percent=0,seeds_percent=0,total_cost=0;

                if (cursor3.moveToFirst()) {
                    do {
                        insecticides_quantity = cursor3.getDouble(cursor3.getColumnIndex("INSECTICIDES_QUANTITY"));
                        insecticides_cost = cursor3.getDouble(cursor3.getColumnIndex("INSECTICIDES_COST"));
                        total_cost = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST"));
                        fertilzer_percent = cursor3.getDouble(cursor3.getColumnIndex("FERTILIZER_PERCENTAGE"));
                        seeds_percent = cursor3.getDouble(cursor3.getColumnIndex("SEEDS_PERCENTAGE"));
                    } while (cursor3.moveToNext());
                }

                String queryUpdate4 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" + insecticides.getCropType() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double  insecticides_quantity2 = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        insecticides_quantity2 = cursor4.getDouble(cursor4.getColumnIndex("INSECTICIDES_QUANTITY"));
                    } while (cursor4.moveToNext());
                }

                val3.put("INSECTICIDES_PRICE", insecticides.getPrice());
                val3.put("INSECTICIDES_QUANTITY",insecticides_quantity + insecticides.getQuantity());
                val3.put("INSECTICIDES_COST", insecticides_cost + insecticides.getTotalPrice());
                val3.put("INSECTICIDES_PERCENTAGE",(insecticides_quantity + insecticides.getQuantity())/insecticides_quantity2);
                val3.put("TOTAL_PERCENTAGE_PRODUCTS",(fertilzer_percent+seeds_percent +((insecticides_quantity + insecticides.getQuantity())/insecticides_quantity2))/3);
                val3.put("PERCENTAGE_HECTARE_DONE",(fertilzer_percent+seeds_percent +((insecticides_quantity + insecticides.getQuantity())/insecticides_quantity2))/3);
                val3.put("TOTAL_COST",total_cost+ insecticides.getTotalPrice());
                dbRead.update("UTILIZE_WPI", val3, selection2, selectionArgs2);

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
                    val.put("QUANTITY", fe.getQuantity() - fertilizers.getQuantity());
                    val.put("PRICE", fertilizers.getPrice());
                    val.put("TOTAL_COST", fe.getTotalPrice() - fertilizers.getTotalPrice());
                }
                String selection = "NAME" + " LIKE ?";
                String[] selectionArgs = {fertilizers.getName()};
                dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);


                String queryUpdate2 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                }
                values.put("TOTAL_COST", costTotal + fertilizers.getTotalPrice());
                dbRead.update("WPI", values, "WPIID=" + 1, null);


                String queryUpdate3 = "SELECT * FROM UTILIZE_WPI WHERE NAME = '" + fertilizers.getCropType() + "'";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double  fertilizers_quantity = 0,fertilizers_cost = 0, total_cost=0,seeds_percent=0, insecticides_percent=0 ;
                if (cursor3.moveToFirst()) {
                    do {
                        fertilizers_quantity = cursor3.getDouble(cursor3.getColumnIndex("FERTILIZER_QUANTITY"));
                        fertilizers_cost = cursor3.getDouble(cursor3.getColumnIndex("FERTILIZER_COST"));
                        total_cost = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST"));
                        seeds_percent = cursor3.getDouble(cursor3.getColumnIndex("SEEDS_PERCENTAGE"));
                        insecticides_percent = cursor3.getDouble(cursor3.getColumnIndex("INSECTICIDES_PERCENTAGE"));
                    } while (cursor3.moveToNext());
                }

                String queryUpdate4 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" +  fertilizers.getCropType() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double  fertilizers_quantity2 = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        fertilizers_quantity2 = cursor4.getDouble(cursor4.getColumnIndex("FERTILIZER_QUANTITY"));
                    } while (cursor4.moveToNext());
                }
                String selection2 = "NAME" + " LIKE ?";
                String[] selectionArgs2 = { fertilizers.getCropType()};
                val3.put("FERTILIZER_PRICE", fertilizers.getPrice());
                val3.put("FERTILIZER_QUANTITY",fertilizers_quantity + fertilizers.getQuantity());
                val3.put("FERTILIZER_COST", fertilizers_cost + fertilizers.getTotalPrice());
                val3.put("FERTILIZER_PERCENTAGE",(fertilizers_quantity + fertilizers.getQuantity())/fertilizers_quantity2);
                val3.put("TOTAL_PERCENTAGE_PRODUCTS",(seeds_percent+ insecticides_percent+((fertilizers_quantity + fertilizers.getQuantity())/fertilizers_quantity2))/3);
                val3.put("PERCENTAGE_HECTARE_DONE",(seeds_percent+ insecticides_percent+((fertilizers_quantity + fertilizers.getQuantity())/fertilizers_quantity2))/3);
                val3.put("TOTAL_COST",total_cost+ fertilizers.getTotalPrice());
                dbRead.update("UTILIZE_WPI", val3, selection2, selectionArgs2);


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
                    val.put("QUANTITY", pa.getQuantity() - packaging.getQuantity());
                    val.put("PRICE", packaging.getPrice());
                    val.put("TOTAL_COST", pa.getTotalPrice() - packaging.getTotalPrice());

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


    @Override
    public void updateFGI(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Crops crop = new Crops(null, null, 0, 0, 0, null,0,0,0);

        for (Object obj : objArray) {
            if (obj instanceof Crops) {
                crops = (Crops) obj;
                String queryUpdate3 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" + crops.getName() + "'";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double  seeds_cost = 0,fertilizers_cost = 0,insecticides_cost=0,hectareSize=0 ;
                if (cursor3.moveToFirst()) {
                    do {
                        seeds_cost = cursor3.getDouble(cursor3.getColumnIndex("SEEDS_COST"));
                        fertilizers_cost = cursor3.getDouble(cursor3.getColumnIndex("FERTILIZER_COST"));
                        insecticides_cost = cursor3.getDouble(cursor3.getColumnIndex("INSECTICIDES_COST"));
                        hectareSize = cursor3.getDouble(cursor3.getColumnIndex("HECTARE_SIZE"));
                    } while (cursor3.moveToNext());
                }

                String queryUpdate4 = "SELECT * FROM UTILIZE_FGI WHERE NAME = '" +  crops.getName() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double  weight = 0,hectareHarvested=0,percentageDoneHectare=0;
                if (cursor4.moveToFirst()) {
                    do {
                        weight = cursor4.getDouble(cursor4.getColumnIndex("WEIGHT"));
                        hectareHarvested = cursor4.getDouble(cursor4.getColumnIndex("HECTARE_HARVESTED"));
                        percentageDoneHectare  = cursor4.getDouble(cursor4.getColumnIndex("PERCENTAGE_HECTARE_DONE"));
                    } while (cursor4.moveToNext());
                }
                String selection2 = "NAME" + " LIKE ?";
                String[] selectionArgs2 = { crops.getName()};
                val3.put("WEIGHT",weight + crops.getWeight());
                val3.put("HECTARE_HARVESTED",hectareHarvested + crops.getHectareHarvested());
                val3.put("PERCENTAGE_HECTARE_DONE",(hectareHarvested + crops.getHectareHarvested())/hectareSize);
                val3.put("TOTAL_COST_HARVESTED",(((hectareHarvested + crops.getHectareHarvested())/hectareSize)*seeds_cost)+(((hectareHarvested + crops.getHectareHarvested())/hectareSize)*fertilizers_cost)+(((hectareHarvested + crops.getHectareHarvested())/hectareSize)*insecticides_cost) );
                dbRead.update("UTILIZE_FGI", val3, selection2, selectionArgs2);


                String queryUpdate2 = "SELECT TOTAL_COST FROM FGI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                }
                values.put("TOTAL_COST", costTotal + (((crops.getHectareHarvested())/hectareSize)*seeds_cost)+(((crops.getHectareHarvested())/hectareSize)*fertilizers_cost)+(((crops.getHectareHarvested())/hectareSize)*insecticides_cost));
                dbRead.update("FGI", values, "FGIID=" + 1, null);

                String queryUpdate1 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor1 = dbRead.rawQuery(queryUpdate1, null);
                ContentValues val1 = new ContentValues();
                double costTotal1 = 0;
                if (cursor1.moveToFirst()) {
                    do {
                        costTotal1 = cursor1.getDouble(cursor1.getColumnIndex("TOTAL_COST"));
                    } while (cursor1.moveToNext());
                }
                val1.put("TOTAL_COST", costTotal1 - ((((crops.getHectareHarvested())/hectareSize)*seeds_cost)+(((crops.getHectareHarvested())/hectareSize)*fertilizers_cost)+(((crops.getHectareHarvested())/hectareSize)*insecticides_cost)));
                dbRead.update("WPI", val1, "WPIID=" + 1, null);
            }
        }
    }


    @Override
    public void updateCGS(DBHelper dbHelper, ArrayList<Object> objArray) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Crops crop = new Crops(null, null, 0, 0, 0, null, 0, 0, 0);
        Packaging pa = new Packaging(null, null, 0, 0, 0, null, null);

        for (Object obj : objArray) {
            if (obj instanceof Crops) {
                crops = (Crops) obj;
                //Updating FGI and UTILIZE CGS
                String queryUpdate3 = "SELECT * FROM UTILIZE_FGI WHERE NAME = '" + crops.getName() + "'";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double weight = 0, totalCostHarvested = 0;
                if (cursor3.moveToFirst()) {
                    do {
                        weight = cursor3.getDouble(cursor3.getColumnIndex("WEIGHT"));
                        totalCostHarvested = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST_HARVESTED"));
                    } while (cursor3.moveToNext());
                }

                String queryUpdate4 = "SELECT * FROM UTILIZE_CGS WHERE NAME = '" + crops.getName() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double weight1 = 0, totalCostSold = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        weight1 = cursor4.getDouble(cursor4.getColumnIndex("WEIGHT"));
                        totalCostSold = cursor4.getDouble(cursor4.getColumnIndex("TOTAL_COST_SOLD"));

                    } while (cursor4.moveToNext());
                }
                String selection2 = "NAME" + " LIKE ?";
                String[] selectionArgs2 = {crops.getName()};
                val3.put("WEIGHT", weight1 + crops.getWeight());
                val3.put("TOTAL_COST_HARVESTED", totalCostHarvested * ((weight1 + crops.getWeight()) / weight));
                val3.put("TOTAL_COST_SOLD", totalCostSold + crops.getTotalCostSold());
                dbRead.update("UTILIZE_CGS", val3, selection2, selectionArgs2);

                //Updating FGI
                String queryUpdate2 = "SELECT TOTAL_COST FROM FGI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                }
                values.put("TOTAL_COST", costTotal - totalCostHarvested * ((crops.getWeight()) / weight));
                dbRead.update("FGI", values, "FGIID=" + 1, null);

                //Updating CGS
                String queryUpdate1 = "SELECT TOTAL_COST FROM CGS ";
                Cursor cursor1 = dbRead.rawQuery(queryUpdate1, null);
                ContentValues val1 = new ContentValues();
                double costTotal1 = 0;
                if (cursor1.moveToFirst()) {
                    do {
                        costTotal1 = cursor1.getDouble(cursor1.getColumnIndex("TOTAL_COST"));
                    } while (cursor1.moveToNext());
                }
                val1.put("TOTAL_COST", costTotal1 + (totalCostHarvested * ((crops.getWeight()) / weight)));
                dbRead.update("CGS", val1, "CGSID=" + 1, null);


                //UPDATING CASH AND SALESREVENUE
                String queryUpdate5 = "SELECT * FROM " + "CASH WHERE NAME = '" + crops.getName() + "'  AND TYPE = '" + crops.getType() + "' ";
                Cursor cursor5 = dbRead.rawQuery(queryUpdate5, null);
                double debit = 0;
                ContentValues val4 = new ContentValues();
                if (cursor5.moveToFirst()) {
                    do {
                        debit = cursor5.getDouble(cursor5.getColumnIndex("DEBIT"));
                    } while (cursor5.moveToNext());
                }
                String selection3 = "TYPE" + " LIKE ?" + " AND " + "NAME" + " LIKE ?";
                String[] selectionArgs3 = {crops.getType(), crops.getName()};
                val4.put("DEBIT", debit + crops.getTotalCostSold());
                dbRead.update("CASH", val4, selection3, selectionArgs3);


                String queryUpdate6 = "SELECT * FROM " + "SALES_REVENUE WHERE NAME = '" + crops.getName() + "'  AND TYPE = '" + crops.getType() + "' ";
                Cursor cursor6 = dbRead.rawQuery(queryUpdate6, null);
                double weight2 = 0, price = 0, totalEarnings = 0;
                ContentValues val6 = new ContentValues();
                if (cursor6.moveToFirst()) {
                    do {
                        weight2 = cursor6.getDouble(cursor6.getColumnIndex("WEIGHT"));
                        price = cursor6.getDouble(cursor6.getColumnIndex("PRICE"));
                        totalEarnings = cursor6.getDouble(cursor6.getColumnIndex("TOTAL_EARNINGS"));
                    } while (cursor6.moveToNext());
                }

                val6.put("WEIGHT", weight2 + crops.getWeight());
                val6.put("PRICE", price + crops.getUnitPrice());
                val6.put("TOTAL_EARNINGS", totalEarnings + crops.getTotalCostSold());
                dbRead.update("SALES_REVENUE", val6, selection2, selectionArgs2);
            }

            if (obj instanceof Packaging) {
                //WPI
                packaging = (Packaging) obj;
                String queryUpdate = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor = dbRead.rawQuery(queryUpdate, null);
                ContentValues values = new ContentValues();
                double costTotal = 0;
                if (cursor.moveToFirst()) {
                    do {
                        costTotal = cursor.getDouble(cursor.getColumnIndex("TOTAL_COST"));
                    } while (cursor.moveToNext());
                }
                values.put("TOTAL_COST", costTotal + packaging.getTotalPrice());
                dbRead.update("WPI", values, "WPIID=" + 1, null);

                //Indirect Materials
                String queryUpdate1 = "SELECT * FROM " + "INDIRECT_MATERIALS WHERE NAME = '" + packaging.getName() + "'  AND TYPE = '" + packaging.getType() + "' ";
                Cursor cursor1 = dbRead.rawQuery(queryUpdate1, null);
                ContentValues val = new ContentValues();
                if (cursor1.moveToFirst()) {
                    do {
                        pa.setTotalPrice(cursor1.getDouble(cursor1.getColumnIndex("TOTAL_COST")));
                        pa.setQuantity(cursor1.getInt(cursor1.getColumnIndex("QUANTITY")));
                    } while (cursor1.moveToNext());
                }
                val.put("QUANTITY", pa.getQuantity() - packaging.getQuantity());
                val.put("TOTAL_COST", pa.getTotalPrice() - packaging.getTotalPrice());
                String selection = "NAME" + " LIKE ?";
                String[] selectionArgs = {packaging.getName()};
                dbRead.update("INDIRECT_MATERIALS", val, selection, selectionArgs);

                //Update FGI
                String queryUpdate2 = "SELECT TOTAL_COST FROM FGI ";
                Cursor cursor2 = dbRead.rawQuery(queryUpdate2, null);
                ContentValues val2 = new ContentValues();
                double costTotal2 = 0;
                if (cursor2.moveToFirst()) {
                    do {
                        costTotal2 = cursor2.getDouble(cursor2.getColumnIndex("TOTAL_COST"));
                    } while (cursor2.moveToNext());
                }
                val2.put("TOTAL_COST", costTotal2 + packaging.getTotalPrice());
                dbRead.update("FGI", val2, "FGIID=" + 1, null);

                //Update WPI
                String queryUpdate3 = "SELECT TOTAL_COST FROM WPI ";
                Cursor cursor3 = dbRead.rawQuery(queryUpdate3, null);
                ContentValues val3 = new ContentValues();
                double costTotal3 = 0;
                if (cursor3.moveToFirst()) {
                    do {
                        costTotal3 = cursor3.getDouble(cursor3.getColumnIndex("TOTAL_COST"));
                    } while (cursor3.moveToNext());
                }
                val3.put("TOTAL_COST", costTotal3 - packaging.getTotalPrice());
                dbRead.update("WPI", val3, "WPIID=" + 1, null);

                //Update CGS
                String queryUpdate4 = "SELECT TOTAL_COST FROM CGS ";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                ContentValues val4 = new ContentValues();
                double costTotal4 = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        costTotal4 = cursor4.getDouble(cursor4.getColumnIndex("TOTAL_COST"));
                    } while (cursor4.moveToNext());
                }
                val4.put("TOTAL_COST", costTotal4 + packaging.getTotalPrice());
                dbRead.update("CGS", val4, "CGSID=" + 1, null);


                //Update FGI
                String queryUpdate5 = "SELECT TOTAL_COST FROM FGI ";
                Cursor cursor5 = dbRead.rawQuery(queryUpdate5, null);
                ContentValues val5 = new ContentValues();
                double costTotal5 = 0;
                if (cursor5.moveToFirst()) {
                    do {
                        costTotal5 = cursor5.getDouble(cursor5.getColumnIndex("TOTAL_COST"));
                    } while (cursor5.moveToNext());
                }
                val5.put("TOTAL_COST", costTotal5 - packaging.getTotalPrice());
                dbRead.update("FGI", val5, "FGIID=" + 1, null);

            }
        }
    }

}
