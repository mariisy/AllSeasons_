package com.example.maricalara.allseasons.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.Customer;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Employees;
import com.example.maricalara.allseasons.Model.Equipment;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    SQLiteDatabase dbWrite, dbRead;
    private Insecticides ins;
    private Fertilizers fer;
    private Packaging pack;
    Transaction transaction;
    private Seedlings seedlings;
    private Seeds seeds;
    Crops crop;
    private Equipment equipment;
    private Employees employees;

    @Override
    public void addDefault(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USERNAME", "admin");
        values.put("EMPLOYEE_FULL_ID", "ADMIN_01");
        values.put("PASSWORD", "admin");
        values.put("ACCOUNT_TYPE", "admin");
        values.put("NAME", "admin_Hennry");
        values.put("SALARY", 0);
        dbWrite.insert("EMPLOYEE", null, values);

    }

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
    public Cursor getAllCash(DBHelper dbHelper) {
        dbWrite = dbHelper.getWritableDatabase();
        Cursor result = dbWrite.rawQuery("SELECT * FROM CASH", null);
        return result;
    }


    @Override
    public Employees retrieveOneEmployee(DBHelper dbHelper, String username, String password) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT * FROM " + "EMPLOYEE" + " WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
        Cursor cursor = dbRead.rawQuery(queryForRetrievalOne, null);
        Employees employees = new Employees(0, null, null, null, null, null, 0);


        if (cursor.moveToFirst()) {
            do {
                employees.setEmployeeID(cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID")));
                employees.setEmployeeFullID(cursor.getString(cursor.getColumnIndex("EMPLOYEE_FULL_ID")));
                employees.setUserName(cursor.getString(cursor.getColumnIndex("USERNAME")));
                employees.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
                employees.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                employees.setAccountype(cursor.getString(cursor.getColumnIndex("ACCOUNT_TYPE")));
                employees.setSalary(cursor.getInt(cursor.getColumnIndex("SALARY")));

            } while (cursor.moveToNext());
        }

        return employees;
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
                listHolder.add(new WarehouseMaterial(null, null, typ, name, price));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }


    @Override
    public boolean checkExistingEmployee(DBHelper dbHelper, String type, String employeeName) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "EMPLOYEE" + " WHERE ACCOUNT_TYPE = '" + type + "' AND NAME = '" + employeeName + "'";

        Cursor result = dbWrite.rawQuery(queryForCheck, null);

        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL
    }

    @Override
    public boolean checkExistCustomer(DBHelper dbHelper, String name, String address) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForCheck = "SELECT NAME FROM " + "CUSTOMER" + " WHERE NAME = '" + name + "' AND ADRESS = '" + address + "'";

        Cursor result = dbWrite.rawQuery(queryForCheck, null);

        if (result.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL
    }

    @Override
    public Customer retrieveOneCustomer(DBHelper dbHelper, String name, String address) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalOne = "SELECT NAME FROM " + "CUSTOMER" + " WHERE NAME = '" + name + "' AND ADRESS = '" + address + "'";
        Cursor cursor = dbRead.rawQuery(queryForRetrievalOne, null);
        Customer customer = new Customer(0, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                customer.setCustomerID(cursor.getInt(cursor.getColumnIndex("CUSTOMER_ID")));
                customer.setCustomerName(cursor.getString(cursor.getColumnIndex("NAME")));
                customer.setContactNumber(cursor.getString(cursor.getColumnIndex("CONTACT_NUMBER")));
                customer.setAddress(cursor.getString(cursor.getColumnIndex("ADDRESS")));


            } while (cursor.moveToNext());
        }

        return customer;
    }

    @Override
    public void updateCustomer(DBHelper dbHelper, String custID, String custContact) {
        dbRead = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("PRICE", custContact);
        String selections = "CUSTOMER_ID" + " LIKE ?";
        String[] selectionArgs = {custID};
        dbRead.update("WAREHOUSE_EQUIPMENT", values, selections, selectionArgs);
    }

    @Override
    public boolean checkExist(DBHelper dbHelper, String username, String password) {
        dbWrite = dbHelper.getWritableDatabase();
        String queryForLogin = "SELECT * FROM " + "EMPLOYEE" + " WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";

        Cursor res = dbWrite.rawQuery(queryForLogin, null);
        if (res.getCount() == 0) {
            return false;//not existing. NULL
        }
        return true;//existing. NOT NULL
    }

    @Override
    public void addTransactionList(DBHelper dbHelper, ArrayList<Transaction> arrayList) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Transaction transactions = new Transaction(0, null, null, null, "", null,
                0, 0, 0, null, 0);

        ContentValues values = new ContentValues();
        for (Object obj : arrayList) {
            if (obj instanceof Transaction) {
                transaction = (Transaction) obj;

                values.put("TRANSACTION_FULL_ID", transaction.getTransactionFullID());
                values.put("DATE", transaction.getDate());
                values.put("DELIVERY_DATE", transaction.getDeliveryDate());
                values.put("TRANSACTION_TYPE", transaction.getTransactionType());
                values.put("TYPE", transaction.getItemType());
                values.put("QUANTITY", transaction.getQuantity());
                values.put("PRICE", transaction.getPrice());
                values.put("TOTAL_COST", transaction.getTotalCost());
                values.put("EMPLOYEE_ID", transaction.getEmployeeID());
                values.put("CUSTOMER_ID", transaction.getCustomerID());
                dbWrite.insert("TRANSACTIONS", null, values);

                //String queryUpdate = "SELECT * FROM " + "TRANSACTIONS WHERE DATE = '" + transaction.getDate()+"' AND DELIVERY_DATE = '" + transaction.getDeliveryDate() + "'   AND DELIVERY_DATE = '" + transaction.getDeliveryDate() + "' AND EMPLOYEE_ID = '" + transaction.getEmployeeID() +"'  AND CUSTOMER_ID = '" + transaction.getCustomerID() + "' AND TYPE = '" + transaction.getItemType() + "' ";

                String queryGet = "SELECT * FROM TRANSACTIONS";
                Cursor res = dbWrite.rawQuery(queryGet, null);
                int count = res.getCount();

                String queryUpdate = "SELECT * FROM " + "TRANSACTIONS WHERE TRANS_ID = '" + count + "' ";

                Cursor cursor = dbRead.rawQuery(queryUpdate, null);
                ContentValues values2 = new ContentValues();

                if (cursor.moveToFirst()) {
                    do {
                        transactions.setTransID(cursor.getInt(cursor.getColumnIndex("TRANS_ID")));
                        transactions.setTransactionType(cursor.getString(cursor.getColumnIndex("TRANSACTION_TYPE")));
                        transactions.setEmployeeID(cursor.getString(cursor.getColumnIndex("EMPLOYEE_ID")));
                    } while (cursor.moveToNext());
                    String selection = "TRANS_ID" + " LIKE ?";
                    String[] selectionArgs = {String.valueOf(transactions.getTransID())};

                    switch (transaction.getTransactionType()) {
                        case "Revenue":
                            values2.put("TRANSACTION_FULL_ID", "TRANSACT-RVN_" + transactions.getEmployeeID() + String.format("%03d", transactions.getTransID()));

                            dbRead.update("TRANSACTIONS", values2, selection, selectionArgs);
                            break;

                        case "Expense":
                            values2.put("TRANSACTION_FULL_ID", "TRANSACT-XPNS" + transactions.getEmployeeID() + String.format("%03d", transactions.getTransID()));

                            dbRead.update("TRANSACTIONS", values2, selection, selectionArgs);
                            break;

                        case "Usage":
                            values2.put("TRANSACTION_FULL_ID", "TRANSACT-USG" + transactions.getEmployeeID() + String.format("%03d", transactions.getTransID()));

                            dbRead.update("TRANSACTIONS", values2, selection, selectionArgs);
                            break;

                        case "Storage":
                            values2.put("TRANSACTION_FULL_ID", "TRANSACT-STRG" + transactions.getEmployeeID() + String.format("%03d", transactions.getTransID()));

                            dbRead.update("TRANSACTIONS", values2, selection, selectionArgs);
                            break;

                        case "Delivery":
                            values2.put("TRANSACTION_FULL_ID", "TRANSACT-DLVR" + transactions.getEmployeeID() + String.format("%03d", transactions.getTransID()));

                            dbRead.update("TRANSACTIONS", values2, selection, selectionArgs);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<HashMap<String, List<String>>> retrieveTransactionList(DBHelper dbHelper, String type) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();

        ArrayList<HashMap<String, List<String>>> allTransactionsList = new ArrayList<>();
        HashMap<String, List<String>> revenueList = new HashMap<>();
        HashMap<String, List<String>> expenseList = new HashMap<>();
        HashMap<String, List<String>> usageList = new HashMap<>();
        HashMap<String, List<String>> storageList = new HashMap<>();
        HashMap<String, List<String>> deliveryList = new HashMap<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> deliveryDateList = new ArrayList<>();
        ArrayList<String> transactionIDList = new ArrayList<>();
        ArrayList<String> transactionIDList2 = new ArrayList<>();
        ArrayList<String> transactionIDList3 = new ArrayList<>();
        ArrayList<String> transactionIDList4 = new ArrayList<>();
        ArrayList<String> transactionIDList5 = new ArrayList<>();

        String queryForDeliveryDate = "SELECT DELIVERY_DATE FROM TRANSACTIONS GROUP BY DELIVERY_DATE";
        String queryForDate = "SELECT DATE FROM TRANSACTIONS GROUP BY DATE ";


        Cursor cursor = dbRead.rawQuery(queryForDate, null);
        Cursor cursor2 = dbRead.rawQuery(queryForDeliveryDate, null);


        if (cursor2.moveToFirst()) {
            do {
                deliveryDateList.add(cursor2.getString(cursor2.getColumnIndex("DELIVERY_DATE")));
            } while (cursor2.moveToNext());
        }

        if (cursor.moveToFirst()) {
            do {
                dateList.add(cursor.getString(cursor.getColumnIndex("DATE")));
            } while (cursor.moveToNext());
        }

        String dates = null;
        for (String date : dateList) {
            dates = date;
            String queryID = "SELECT TRANSACTION_FULL_ID FROM " + "TRANSACTIONS" + " WHERE DATE = '" + date + "' AND TRANSACTION_TYPE = '" + type + "'";
            Cursor cursor3 = dbWrite.rawQuery(queryID, null);
            switch (type) {
                case "Revenue":
                    if (cursor3.moveToFirst()) {
                        do {
                            String id = cursor3.getString(cursor3.getColumnIndex("TRANSACTION_FULL_ID"));
                            transactionIDList.add(id);
                        } while (cursor3.moveToNext());
                        revenueList.put(date, transactionIDList);
                    }
                    break;

                case "Expense":
                    if (cursor3.moveToFirst()) {
                        do {
                            transactionIDList2.add(cursor3.getString(cursor3.getColumnIndex("TRANSACTION_FULL_ID")));
                        } while (cursor3.moveToNext());
                        expenseList.put(dates, transactionIDList2);
                    }

                    break;

                case "Usage":
                    if (cursor3.moveToFirst()) {
                        do {
                            transactionIDList3.add(cursor3.getString(cursor3.getColumnIndex("TRANSACTION_FULL_ID")));
                        } while (cursor3.moveToNext());
                        usageList.put(date, transactionIDList3);
                    }
                    break;

                case "Storage":
                    if (cursor3.moveToFirst()) {
                        do {
                            transactionIDList4.add(cursor3.getString(cursor3.getColumnIndex("TRANSACTION_FULL_ID")));
                        } while (cursor3.moveToNext());
                        storageList.put(date, transactionIDList4);
                    }
                    break;
                default:
                    break;

            }
        }


        for (String date : deliveryDateList) {
            String queryID2 = "SELECT TRANSACTION_FULL_ID FROM " + "TRANSACTIONS" + " WHERE DELIVERY_DATE = '" + date + "'";
            Cursor cursor4 = dbWrite.rawQuery(queryID2, null);

            if (cursor4.moveToFirst()) {
                do {
                    transactionIDList5.add(cursor4.getString(cursor4.getColumnIndex("TRANSACTION_FULL_ID")));
                } while (cursor4.moveToNext());
                deliveryList.put(date, transactionIDList5);
            }
        }



        //expenseList.put("date", transactionIDList2);

        allTransactionsList.add(revenueList);
        allTransactionsList.add(expenseList);
        allTransactionsList.add(usageList);
        allTransactionsList.add(storageList);
        allTransactionsList.add(deliveryList);


        return allTransactionsList;
    }

    @Override
    public void addEntry(DBHelper dbHelper, Object object, String type, String name, String contact) {
        dbWrite = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();

        switch (type) {
            case "Seedlings":
                if (object instanceof Seedlings) {
                    seedlings = (Seedlings) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", seedlings.getType());
                    values.put("NAME", seedlings.getName());
                    values.put("PRICE", seedlings.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", seedlings.getDate());
                    val.put("TYPE", seedlings.getType());
                    val.put("NAME", seedlings.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", seedlings.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }
                break;

            case "Seeds":
                if (object instanceof Seeds) {
                    seeds = (Seeds) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", seeds.getType());
                    values.put("NAME", seeds.getName());
                    values.put("PRICE", seeds.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);


                    ContentValues val = new ContentValues();
                    val.put("DATE", seeds.getDate());
                    val.put("TYPE", seeds.getType());
                    val.put("NAME", seeds.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", seeds.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }
                break;

            case "Crops":
                if (object instanceof Crops) {
                    crop = (Crops) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", crop.getType());
                    values.put("NAME", crop.getName());
                    values.put("PRICE", 0);
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("TYPE", crop.getType());
                    val.put("NAME", crop.getName());
                    val.put("WEIGHT", 0);
                    val.put("DATE", crop.getDate());
                    val.put("TOTAL_COST_HARVESTED", 0);
                    val.put("PERCENTAGE_HECTARE_DONE", 0);
                    val.put("HECTARE_HARVESTED", 0);
                    dbWrite.insert("UTILIZE_FGI", null, val);

                    ContentValues val1 = new ContentValues();
                    val1.put("TYPE", crop.getType());
                    val1.put("NAME", crop.getName());
                    val1.put("WEIGHT", 0);
                    val1.put("DATE", crop.getDate());
                    val1.put("TOTAL_COST_HARVESTED", 0);
                    dbWrite.insert("UTILIZE_CGS", null, val1);

                    ContentValues val2 = new ContentValues();
                    val2.put("DATE", crop.getDate());
                    val2.put("TYPE", crop.getType());
                    val2.put("NAME", crop.getName());
                    val2.put("DEBIT", 0);
                    val2.put("CREDIT", 0);
                    dbWrite.insert("CASH", null, val2);

                    ContentValues val3 = new ContentValues();
                    val3.put("DATE", crop.getDate());
                    val3.put("TYPE", crop.getType());
                    val3.put("NAME", crop.getName());
                    val3.put("WEIGHT", 0);
                    val3.put("PRICE", 0);
                    val3.put("TOTAL_EARNINGS", 0);
                    dbWrite.insert("SALES_REVENUE", null, val3);

                }
                break;

            case "Insecticides":
                if (object instanceof Insecticides) {
                    ins = (Insecticides) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", ins.getType());
                    values.put("NAME", ins.getName());
                    values.put("PRICE", ins.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", ins.getDate());
                    val.put("TYPE", ins.getType());
                    val.put("NAME", ins.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", ins.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }
                break;

            case "Fertilizer":
                if (object instanceof Fertilizers) {
                    fer = (Fertilizers) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", fer.getType());
                    values.put("NAME", fer.getName());
                    values.put("PRICE", fer.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", fer.getDate());
                    val.put("TYPE", fer.getType());
                    val.put("NAME", fer.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", fer.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }

                break;

            case "Packaging":
                if (object instanceof Packaging) {
                    pack = (Packaging) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", pack.getType());
                    values.put("NAME", pack.getName());
                    values.put("PRICE", pack.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", pack.getDate());
                    val.put("TYPE", pack.getType());
                    val.put("NAME", pack.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", pack.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }
                break;

            case "Equipment":
                if (object instanceof Equipment) {
                    equipment = (Equipment) object;

                    ContentValues values = new ContentValues();
                    values.put("SUPPLIER_NAME", name);
                    values.put("CONTACT_NUMBER", contact);
                    values.put("TYPE", equipment.getType());
                    values.put("NAME", equipment.getName());
                    values.put("PRICE", equipment.getPrice());
                    dbWrite.insert("WAREHOUSE_EQUIPMENT", null, values);

                    ContentValues val = new ContentValues();
                    val.put("DATE", equipment.getDate());
                    val.put("TYPE", equipment.getType());
                    val.put("NAME", equipment.getName());
                    val.put("DEBIT", 0);
                    val.put("CREDIT", equipment.getTotalPrice());
                    dbWrite.insert("CASH", null, val);
                }
                break;

            case "Employee":
                Employees employee = new Employees(0, null, null, null, null, null, 0);
                String fLetter = "";
                String lName = "";
                String userName = "";
                if (object instanceof Employees) {
                    employees = (Employees) object;
                    ContentValues values = new ContentValues();
                    values.put("EMPLOYEE_FULL_ID", employees.getEmployeeFullID());
                    fLetter = String.valueOf(employees.getName().charAt(0));
                    lName = employees.getName().substring(employees.getName().lastIndexOf(" ") + 1);
                    userName = fLetter + lName;
                    values.put("USERNAME", userName);
                    values.put("PASSWORD", employees.getPassword());
                    values.put("ACCOUNT_TYPE", employees.getAccountype());
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
                                values2.put("PASSWORD", "FRM" + String.format("%03d", employee.getEmployeeID()));
                                dbRead.update("EMPLOYEE", values2, selection, selectionArgs);
                                break;

                            case "Staff":
                                values2.put("EMPLOYEE_FULL_ID", "EMPSTF" + String.format("%03d", employee.getEmployeeID()));
                                values2.put("PASSWORD", "STF" + String.format("%03d", employee.getEmployeeID()));
                                dbRead.update("EMPLOYEE", values2, selection, selectionArgs);
                                break;
                            case "Supervisor":
                                values2.put("EMPLOYEE_FULL_ID", "EMPSPV" + String.format("%03d", employee.getEmployeeID()));
                                values2.put("PASSWORD", "SPV" + String.format("%03d", employee.getEmployeeID()));
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
    public void updateEntry(DBHelper dbHelper, String type, String name, Double price) {
        dbRead = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("PRICE", price);
        String selections = "TYPE" + " LIKE ?" + " AND " + "NAME" + " LIKE ?";
        String[] selectionArgs = {type, name};
        dbRead.update("WAREHOUSE_EQUIPMENT", values, selections, selectionArgs);


    }

    @Override
    public void deleteEntry(DBHelper dbHelper, String name) {
        dbRead = dbHelper.getReadableDatabase();

        String selection = "NAME" + " LIKE ?";
        String[] selectionArgs = {name};
        dbRead.delete("WAREHOUSE_EQUIPMENT", selection, selectionArgs);
    }

    @Override
    public ArrayList<String> retrieveListSpinner(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT SUPPLIER_NAME FROM " + "WAREHOUSE_EQUIPMENT GROUP BY SUPPLIER_NAME  ";
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex("SUPPLIER_NAME")));
            } while (cursor.moveToNext());
        }
        return listHolder;
    }

    @Override
    public ArrayList<String> retrieveListSpinnerColumn(DBHelper dbHelper, String spinnerCategory, String columnName, String value) {
        dbRead = dbHelper.getReadableDatabase();
        String queryForRetrievalAll = "SELECT " + spinnerCategory + " FROM " + "WAREHOUSE_EQUIPMENT WHERE " + columnName + " = '" + value + "'  GROUP BY " + spinnerCategory;
        ArrayList<String> listHolder = new ArrayList<String>();
        Cursor cursor = dbRead.rawQuery(queryForRetrievalAll, null);
        if (cursor.moveToFirst()) {
            do {
                listHolder.add(cursor.getString(cursor.getColumnIndex(spinnerCategory)));
            } while (cursor.moveToNext());

        }
        return listHolder;
    }

    @Override
    public ArrayList<Crops> retrieveSum(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();

        String queryForName = "SELECT NAME FROM UTILIZE_CGS";
        Cursor cursor2 = dbRead.rawQuery(queryForName, null);
        Crops cro = new Crops(null,null,0,0,0,null,0,0,0);

        float sum = 0;
        ArrayList<String> arrName = new ArrayList<>();
        ArrayList<Crops> arrCrops = new ArrayList<>();

        if (cursor2.moveToFirst()) {
            do {
                arrName.add(cursor2.getString(cursor2.getColumnIndex("NAME")));
            } while (cursor2.moveToNext());
        }

        //arrName.indexOf(name);

        for (String cropName : arrName){
            String queryForSum = "SELECT SUM(TOTAL_COST_SOLD)" + "as Total FROM UTILIZE_CGS WHERE TYPE = '" + cropName + "' ";
            Cursor cursor = dbWrite.rawQuery(queryForSum, null);
            if (cursor.moveToFirst()) {
                do {
                    cro.setName(cropName);
                    double total = cursor.getInt(cursor.getColumnIndex("Total"));
                    cro.setTotalCostSold(total);
                } while (cursor.moveToNext());
                arrCrops.add(cro);
            }
        }

        return arrCrops;
    }

    @Override
    public ArrayList<Transaction> retrieveExpense(DBHelper dbHelper) {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        Transaction transactions = new Transaction(0, null, null, null, "", null,
                0, 0, 0, null, 0);
        ArrayList<Transaction> arrExp = new ArrayList<>();
        String queryForName = "SELECT TYPE FROM TRANSACTIONS WHERE TYPE = 'Expense'";
        Cursor cursor2 = dbRead.rawQuery(queryForName, null);
        ArrayList<String> arrName = new ArrayList<>();

        if (cursor2.moveToFirst()) {
            do {
                arrName.add(cursor2.getString(cursor2.getColumnIndex("NAME")));
            } while (cursor2.moveToNext());
        }

        for (String expName : arrName){
            String queryForSum = "SELECT SUM(TOTAL_COST)" + "as Total FROM TRANSACTIONS WHERE NAME = '" + expName + "' ";
            Cursor cursor = dbWrite.rawQuery(queryForSum, null);
            if (cursor.moveToFirst()) {
                do {
                    transactions.setItemType(expName);
                    double total = cursor.getInt(cursor.getColumnIndex("Total"));
                    transactions.setTotalCost(total);
                } while (cursor.moveToNext());
                arrExp.add(transactions);
            }
        }

        return null;
    }


}
