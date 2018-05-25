package com.example.maricalara.allseasons.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

public class SfpActivity extends AppCompatActivity {

    Button btnView;
    TextView textviewAccountType, textviewDebit, textviewCredit;
    ArrayList<String> list = null;
    AccountingDAO aDao = new AccountingDAOImpl();
    DBHelper dbHelper = new DBHelper(SfpActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfp);

        btnView = (Button)findViewById(R.id.btnView);
        textviewAccountType = (TextView)findViewById(R.id.viewAccountType);
        textviewDebit = (TextView)findViewById(R.id.viewDebit);
        textviewCredit = (TextView)findViewById(R.id.viewCredit);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               aDao.addSFP(dbHelper);
               aDao.updateSFP(dbHelper);
               viewType();
               viewDebit();
               viewCredit();
            }
        });

    }

    public  void  viewDebit(){
        textviewDebit.setText("");
        list = aDao.viewSFP(dbHelper,"DEBIT");
        StringBuilder builder = new StringBuilder();
        for (String details : list) {
            builder.append(details + "\n");
        }
        textviewDebit.setText(builder.toString());
    }
    public  void  viewType(){
        textviewAccountType.setText("");
        list = aDao.viewSFP(dbHelper,"ACCOUNT_TYPE");
        StringBuilder builder = new StringBuilder();
        for (String details : list) {
            builder.append(details + "\n");
        }
        textviewAccountType.setText(builder.toString());
    }
    public  void  viewCredit(){
        textviewCredit.setText("");
        list = aDao.viewSFP(dbHelper,"CREDIT");
        StringBuilder builder = new StringBuilder();
        for (String details : list) {
            builder.append(details + "\n");
        }
        textviewCredit.setText(builder.toString());
    }
}
