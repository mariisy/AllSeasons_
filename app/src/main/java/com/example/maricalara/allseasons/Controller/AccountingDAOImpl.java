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
    public Cursor getAllUtilizeWPI(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM UTILIZE_WPI", null);
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
        String queryForRetrievalOne = "SELECT * FROM " + "WAREHOUSE_EQUIPMENT WHERE NAME = '" + name + "'  AND TYPE = '" + type + "' ";
        Cursor cursor = db.rawQuery(queryForRetrievalOne, null);
        Crops crops = new Crops(null, null, 0, 0, 0, null,0,0,0);

        if (cursor.moveToFirst()) {
            do {
                crops.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                crops.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                crops.setWeight(cursor.getDouble(cursor.getColumnIndex("WEIGHT")));
                crops.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
                crops.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                crops.setTotalCostHarvested(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST_HARVESTED")));
                crops.setTotalcostSold(cursor.getDouble(cursor.getColumnIndex("TOTAL_COST_SOLD")));
                crops.setHectarePercent(cursor.getDouble(cursor.getColumnIndex("PERCENTAGE_HECTARE_DONE")));
                crops.setHectareHarvested(cursor.getDouble(cursor.getColumnIndex("HECTARE_SIZE_HARVESTED")));
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
                String[] selectionArgs2 = {seeds.getName()};

                String queryUpdate3 = "SELECT * FROM UTILIZE_WPI WHERE NAME = '" + seeds.getName() + "'";
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

                String queryUpdate4 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" + seeds.getName() + "'";
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


                String queryUpdate3 = "SELECT * FROM UTILIZE_WPI WHERE NAME = '" + seeds.getName() + "'";
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

                String queryUpdate4 = "SELECT * FROM RESOURCE_PLANNING_TABLE WHERE NAME = '" + seeds.getName() + "'";
                Cursor cursor4 = dbRead.rawQuery(queryUpdate4, null);
                double  fertilizers_quantity2 = 0;
                if (cursor4.moveToFirst()) {
                    do {
                        fertilizers_quantity2 = cursor4.getDouble(cursor4.getColumnIndex("FERTILIZER_QUANTITY"));
                    } while (cursor4.moveToNext());
                }
                String selection2 = "NAME" + " LIKE ?";
                String[] selectionArgs2 = {seeds.getName()};
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
}
