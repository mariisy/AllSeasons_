package com.example.maricalara.allseasons.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.example.maricalara.allseasons.WifiP2P.Wifi.GlobalActivity;
import com.example.maricalara.allseasons.WifiP2P.Wifi.WiFiDirectActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SyncSettingsActivity extends AppCompatActivity  {

    private Toolbar toolbar;

    //UI
    Button btnExport, btnSync;

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = dayOfTheWeek + "_" + dateForTheDay;

    //data variable
    String directory_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_settings);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sync and Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //inflate UI
        btnExport = (Button) findViewById(R.id.btnExport);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportDB();
            }
        });

        btnSync = (Button) findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SyncSettingsActivity.this, WiFiDirectActivity.class);
                SyncSettingsActivity.this.startActivity(intent);
            }
        });



    }




    private void exportDB() {
        ArrayList<String> dbList = new ArrayList<>();
        dbList.add("RAW_MATERIALS");
        dbList.add("INDIRECT_MATERIALS");
        dbList.add("UTILIZE_WPI");
        dbList.add("WPI");
        dbList.add("UTILIZE_FGI");
        dbList.add("FGI");
        dbList.add("UTILIZE_CGS");
        dbList.add("CGS");
        dbList.add("SALES_REVENUE");
        dbList.add("CASH");
        dbList.add("WAREHOUSE_EQUIPMENT");
        dbList.add("CUSTOMER");
        dbList.add("EMPLOYEE");
        dbList.add("TRANSACTIONS");
        dbList.add("RESOURCE_PLANNING_TABLE");




        directory_path = Environment.DIRECTORY_DOCUMENTS;
        //  Environment.getRootDirectory().getPath();
        //   .getExternalStorageDirectory().getPath() + "/DBBackup/";
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DATABASE_NAME);
        sqliteToExcel.exportSpecificTables(dbList,"FarmDB.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                Toast.makeText(SyncSettingsActivity.this, "Database Exporting! \n" + directory_path, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(SyncSettingsActivity.this, "Database Exported!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(SyncSettingsActivity.this, "Database Export Fail \n!" + e, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
