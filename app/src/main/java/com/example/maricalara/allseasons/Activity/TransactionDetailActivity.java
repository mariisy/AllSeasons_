package com.example.maricalara.allseasons.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.R;

public class TransactionDetailActivity extends AppCompatActivity {

    TextView txtType, txtName, txtPrice, txtQty,  txtDate, txtDateDel, txtCustomer, txtCostTotal;
    private TransactionDAO tDAO = new TransactionDAOImpl();
    Transaction transction;
    DBHelper dbHelper = new DBHelper(TransactionDetailActivity.this);

    String ID;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        txtType = (TextView) findViewById(R.id.txtType);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtQty = (TextView) findViewById(R.id.txtQty);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDateDel = (TextView) findViewById(R.id.txtDateDel);
        txtCostTotal = (TextView) findViewById(R.id.txtCostTotal);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("transID");
            flag = extras.getBoolean("Flag");

        }

        setTexts();


    }

    private void setTexts(){
        transction = tDAO.retrieveOneTrans(dbHelper, ID);
        txtType.setText(transction.getTransactionType());
        txtName.setText(transction.getItemType());

        txtCostTotal.setText(String.valueOf(transction.getTotalCost()));
//        txtPrice.setText(String.valueOf( transction.getPrice()));

/*
        try {
            if(flag){
                txtPrice.setText(String.valueOf( transction.getPrice()));
            }else if (ID.contains("TRANSACT-XPNS") || ID.contains("TRANSACT-USG") ||
                    ID.contains("TRANSACT-STRG") || ID.contains("TRANSACT-DLVR")){
                txtPrice.setText(String.valueOf(transction.getTotalCost()));
            }else{

            }
        } catch (Exception e) {
            Toast.makeText(TransactionDetailActivity.this, e.toString(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }*/

        txtQty.setText(Double.valueOf(transction.getQuantity()).toString());
        txtDate.setText(transction.getDate());
        txtDateDel.setText(transction.getDeliveryDate());
//        txtCustomer.setText(transction.getCustomerID());

    }
}
