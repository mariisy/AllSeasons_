package com.example.maricalara.allseasons.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
public class SettingsEditStaff extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnPayroll;
    AccountingDAO aDAO = new AccountingDAOImpl();
    DBHelper dbHelper = new DBHelper(SettingsEditStaff.this);

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    java.util.Calendar calendar = Calendar.getInstance();


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_staff);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnPayroll = (Button)findViewById(R.id.btnSalary);
        setSupportActionBar(toolbar);
         getSupportActionBar().setTitle("Edit Staffing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



         btnPayroll.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 boolean isLastDay = calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);

                 try {
                    if(!aDAO.checkExistingSalary( dbHelper,  strDate)) {
                        if (isLastDay) {
                            aDAO.updateSalary(dbHelper, strDate);
                        }
                    }

                 } catch (Exception e) {
                     e.printStackTrace();
                 }


             }
         });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                this.finish();
                /*
                Intent intent = new Intent(SettingsEditStaff.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
