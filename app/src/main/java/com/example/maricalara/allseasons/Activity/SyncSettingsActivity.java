package com.example.maricalara.allseasons.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;

import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncSettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //UI
    Button btnExport;

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
                directory_path = Environment.getRootDirectory().getPath() + "/sdcard/Android";
                    //    .getExternalStorageDirectory().getPath() + "/DBBackup/";
                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DATABASE_NAME, directory_path);
                sqliteToExcel.exportAllTables(strDate+"_Farm.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(SyncSettingsActivity.this, "Database Exporting!", Toast.LENGTH_LONG).show();
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
        });
    }

    private void exportDB(){
        String Fnamexls=strDate+"_Farm.xls";

        File sdCard = Environment.getExternalStorageDirectory();

        File directory = new File (sdCard.getAbsolutePath() + "/newfolder");
        directory.mkdirs();

        File file = new File(directory, Fnamexls);

        //WritableWorkbook workbook;
       // workbook = Workbook.createWorkbook(file, wbSettings);
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
