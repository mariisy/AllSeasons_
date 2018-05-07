package com.example.maricalara.allseasons.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mari Calara on 01/09/2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VER = 4;
    public static final String DATABASE_NAME = "FarmAccounting.db";

    int dbExecQuery = 1;

    //tables
    public String dbTableRawMaterials = "RAW_MATERIALS";
    public String dbTableIndirectMaterials = "INDIRECT_MATERIALS";
    public String dbTableEquipment = "EQUIPMENT";

    public String dbTableWarehouseEquip = "WAREHOUSE_EQUIPMENT";
    public String dbTableWPI = "WPI";
    public String dbTableFGI = "FGI";
    public String dbTableCGS = "CGS";
    public String dbTableSalesRevenue = "SALES_REVENUE";
    public String dbTableCash = "CASH";
    public String dbTableCustomer = "CUSTOMER";
    public String dbTableEmployee = "EMPLOYEE";
    public String dbTableTransation = "TRANSACTIONS";
    public String dbTableDL = "DIRECT_LABOR";
    public String dbTableIL = "INDIRECT_LABOR";
    public String dbTableSE = "SALARIES_EXPENSE";
    public String dbTableSupplier ="SUPPLIER";






    //column
    public String dBColumnDate = "DATE";
    public String dBColumnName = "NAME";
    public String dBColumnType = "TYPE";
    public String dBColumnQuantity = "QUANTITY";
    public String dBColumnPrice = "PRICE";
    public String dBColumnTotalCost = "TOTAL_COST";
    public String dBColumnTotalEarnings = "TOTAL_EARNINGS";
    public String dbColumnDebit = "DEBIT";
    public String dbColumnCredit = "CREDIT";
    public String dbColumnContactNum = "CONTACT_NUMBER";
    public String dbColumnAddress = "ADDRESS";
    public String dbColumnCustomerID = "CUSTOMER_ID";

    public String dbColumnEmployeeID = "EMPLOYEE_ID";
    public String dbColumnEmployeeFullID = "EMPLOYEE_FULL_ID";
    public String dbColumnAccountType = "ACCOUNT_TYPE";
    public String dbColumnSalary = "SALARY";
    public String dbColumnTransactionID = "TRANSACTIONID";
    public String dbColumnDelivery = "DELIVERY";
    public String dBColumnSupplierName = "SUPPLIER_NAME";
    public String dBColumnTransID = "TRANSID";
    public String dbColumnWPIID = "WPIID";
    public String dbColumnEmployeeUsername = "USERNAME";
    public String dbColumnEmployeePassword = "PASSWORD";
    //db Query



    private String dbBuildQuery1 = "CREATE TABLE " + dbTableRawMaterials + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL )";


    private String dbBuildQuery2 = "CREATE TABLE " + dbTableIndirectMaterials + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL )";

    private String dbBuildQuery3 = "CREATE TABLE " + dbTableEquipment + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL )";

    private String dbBuildQuery4 = "CREATE TABLE " + dbTableWPI + " ( " +
            dbColumnWPIID+ " int PRIMARY KEY, " +
            dBColumnTotalCost + " REAL )";

    private String dbBuildQuery5 = "CREATE TABLE " + dbTableFGI + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL )";

    private String dbBuildQuery6 = "CREATE TABLE " + dbTableCGS + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL )";

    private String dbBuildQuery7 = "CREATE TABLE " + dbTableSalesRevenue + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalEarnings + " REAL )";

    private String dbBuildQuery8 = "CREATE TABLE " + dbTableCash + " ( " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dbColumnDebit + " REAL, " +
            dbColumnCredit + " REAL )";

    private String dbBuildQuery9 = "CREATE TABLE " + dbTableCustomer + " ( " +
            dbColumnCustomerID + " TEXT PRIMARY KEY, " +
            dBColumnName + " TEXT, " +
            dbColumnContactNum + " TEXT, " +
            dbColumnAddress + " TEXT )";

    private String dbBuildQuery10 = "CREATE TABLE " + dbTableEmployee + " ( " +
            dbColumnEmployeeID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            dbColumnEmployeeFullID + " TEXT , " +
            dbColumnEmployeeUsername + " TEXT , " +
            dbColumnEmployeePassword + " TEXT , " +
            dBColumnName + " TEXT, " +
            dbColumnAccountType + " TEXT, " +
            dbColumnSalary + " REAL )";

    private String dbBuildQuery11 = "CREATE TABLE " + dbTableTransation + " ( " +
            dBColumnTransID+ " int PRIMARY KEY, " +
            dbColumnTransactionID + " TEXT, " +
            dBColumnDate + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnQuantity + " INTEGER, " +
            dBColumnPrice + " REAL, " +
            dBColumnTotalCost + " REAL, " +
            dbColumnDelivery + " TEXT , " +
            dbColumnEmployeeID + " TEXT, " +
            dbColumnCustomerID + " TEXT )";

    private String dbBuildQuery12 = "CREATE TABLE " + dbTableDL + " ( " +
            dbColumnSalary + " REAL, " +
            dBColumnDate + " TEXT, " +
            "FOREIGN KEY (dbColumnEmployeeID) REFERENCES " + dbTableEmployee + "(" + dbColumnEmployeeID + "));";

    private String dbBuildQuery13 = "CREATE TABLE " + dbTableIL + " ( " +
            dbColumnSalary + " REAL, " +
            dBColumnDate + " TEXT, " +
            "FOREIGN KEY (dbColumnEmployeeID) REFERENCES " + dbTableEmployee + "(" + dbColumnEmployeeID + "));";

    private String dbBuildQuery14 = "CREATE TABLE " + dbTableSE + " ( " +
            dbColumnSalary + " REAL, " +
            dBColumnDate + " TEXT, " +
            "FOREIGN KEY (dbColumnEmployeeID) REFERENCES " + dbTableEmployee + "(" + dbColumnEmployeeID + "));";

    private String dbBuildQuery15 = "CREATE TABLE " + dbTableWarehouseEquip + " ( " +
            dBColumnSupplierName + " TEXT, " +
            dbColumnContactNum + " TEXT, " +
            dBColumnType + " TEXT, " +
            dBColumnName + " TEXT, " +
            dBColumnPrice + " REAL )";


    private String dbDestroyQuery1 = "DROP TABLE IF EXISTS " + dbTableRawMaterials;
    private String dbDestroyQuery2 = "DROP TABLE IF EXISTS " + dbTableIndirectMaterials;
    private String dbDestroyQuery3 = "DROP TABLE IF EXISTS " + dbTableEquipment;
    private String dbDestroyQuery4 = "DROP TABLE IF EXISTS " + dbTableWarehouseEquip;
    private String dbDestroyQuery5 = "DROP TABLE IF EXISTS " + dbTableWPI;
    private String dbDestroyQuery6 = "DROP TABLE IF EXISTS " + dbTableCGS;
    private String dbDestroyQuery7 = "DROP TABLE IF EXISTS " + dbTableSalesRevenue;
    private String dbDestroyQuery8 = "DROP TABLE IF EXISTS " + dbTableCash;
    private String dbDestroyQuery9 = "DROP TABLE IF EXISTS " + dbTableCustomer;
    private String dbDestroyQuery10 = "DROP TABLE IF EXISTS " + dbTableEmployee;
    private String dbDestroyQuery11 = "DROP TABLE IF EXISTS " + dbTableTransation;
    private String dbDestroyQuery12 = "DROP TABLE IF EXISTS " + dbTableDL;
    private String dbDestroyQuery13 = "DROP TABLE IF EXISTS " + dbTableIL;
    private String dbDestroyQuery14 = "DROP TABLE IF EXISTS " + dbTableSE;
    private String dbDestroyQuery15 = "DROP TABLE IF EXISTS " + dbTableFGI;



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        db.execSQL(dbBuildQuery5);
        db.execSQL(dbBuildQuery6);
        db.execSQL(dbBuildQuery7);

        db.execSQL(dbBuildQuery9);
        db.execSQL(dbBuildQuery10);



        db.execSQL(dbBuildQuery3);
        db.execSQL(dbBuildQuery4);
        db.execSQL(dbBuildQuery5);
        db.execSQL(dbBuildQuery6);
        db.execSQL(dbBuildQuery7);
        db.execSQL(dbBuildQuery8);
        db.execSQL(dbBuildQuery9);
        db.execSQL(dbBuildQuery10);

        db.execSQL(dbBuildQuery12);
        db.execSQL(dbBuildQuery13);
        db.execSQL(dbBuildQuery14);



        */

        db.execSQL(dbBuildQuery4);
        db.execSQL(dbBuildQuery1);
        db.execSQL(dbBuildQuery2);
        db.execSQL(dbBuildQuery3);
        db.execSQL(dbBuildQuery15);
        db.execSQL(dbBuildQuery8);
        db.execSQL(dbBuildQuery10);
    //    db.execSQL(dbBuildQuery11);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {




        /*


        db.execSQL(dbDestroyQuery5);
        db.execSQL(dbDestroyQuery6);
        db.execSQL(dbDestroyQuery7);
        db.execSQL(dbDestroyQuery8);
        db.execSQL(dbDestroyQuery9);
        db.execSQL(dbDestroyQuery10);
        db.execSQL(dbDestroyQuery11);
        db.execSQL(dbDestroyQuery12);
        db.execSQL(dbDestroyQuery13);
        db.execSQL(dbDestroyQuery14);
        db.execSQL(dbDestroyQuery15);
        */
    }
}
