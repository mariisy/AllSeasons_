package com.example.maricalara.allseasons.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionAddSold extends AppCompatActivity {

    //Sample for List for Spinner


    //for UI
    private Button btnAddTransaction;
    private EditText txtCustomerName, txtContactNum, txtQty, txtAddress, txtPackagingQty;
    private TextInputLayout inputLayoutCustomerName, inputLayoutContactNum, inputLayoutQuantity, inputLayoutAddress, inputLayoutQtyPackaging;
    private CheckBox chckDelivery;
    private Toolbar toolbar;
    private TextView txtTransactionID, txtDate;
    private ArrayAdapter<String> arrayAdapter3;
    private ArrayList<String> arrListCrop;
    private MaterialBetterSpinner spinnerItem;
    //bundle extra
    String empID, name;
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionAddSold.this);
    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add_sold);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Sold Items");
        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerItem =(MaterialBetterSpinner)findViewById(R.id.spinnerItem);
        inputLayoutCustomerName = (TextInputLayout) findViewById(R.id.input_layout_customerName);
        inputLayoutContactNum = (TextInputLayout) findViewById(R.id.input_layout_contactNum);
        inputLayoutQuantity = (TextInputLayout) findViewById(R.id.input_layout_qty);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutQtyPackaging = (TextInputLayout) findViewById(R.id.input_layout_qty_package);
        txtPackagingQty = (EditText) findViewById(R.id.txtPackagingQty);
        txtCustomerName = (EditText) findViewById(R.id.txtCustomerName);
        txtContactNum = (EditText) findViewById(R.id.txtContactNum);
        txtQty = (EditText) findViewById(R.id.txtQty);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        chckDelivery = (CheckBox) findViewById(R.id.delivery);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            empID = extras.getString("EmployeeID");
            name = extras.getString("EmployeeName");
        }

        txtTransactionID = (TextView) findViewById(R.id.txtTransactionID);
        txtTransactionID.setText(empID);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(strDate);


        //layout for spinnerCrop
        arrListCrop = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", "Crops");
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListCrop);
        spinnerItem.setAdapter(arrayAdapter3);

        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               if(validateAddress() && validateContact() && validateCustomerName() && validatePackaging() && validateQuantity()){
                   //do something
               }

            }
        });


    }

    private boolean validatePackaging() {
            if (txtPackagingQty.getText().toString().trim().isEmpty()) {
                inputLayoutQtyPackaging.setError("Enter Customer Address!");
                requestFocus(chckDelivery);
                return false;
            } else {
                inputLayoutQtyPackaging.setErrorEnabled(false);
            }

        return true;
    }

    private boolean validateAddress() {

        if (chckDelivery.isChecked()) {
            if (txtAddress.getText().toString().trim().isEmpty()) {
                inputLayoutAddress.setError("Enter Customer Address!");
                requestFocus(txtAddress);
                return false;
            } else {
                inputLayoutAddress.setErrorEnabled(false);
            }
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }


        return true;
    }

    private boolean validateQuantity() {
        if (txtQty.getText().toString().trim().isEmpty()) {
            inputLayoutQuantity.setError("Enter Item Quantity!");
            requestFocus(txtQty);
            return false;
        } else {
            inputLayoutQuantity.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerName() {
        if (txtCustomerName.getText().toString().trim().isEmpty()) {
            inputLayoutCustomerName.setError("Enter Customer Name");
            requestFocus(txtCustomerName);
            return false;
        } else {
            inputLayoutCustomerName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateContact() {
        if (txtContactNum.getText().toString().trim().isEmpty()) {
            inputLayoutContactNum.setError("Enter Contact Number");
            requestFocus(txtContactNum);
            return false;
        } else {
            inputLayoutContactNum.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.txtCustomerName:
                    validateCustomerName();
                    break;
                case R.id.txtContactNum:
                    validateContact();
                    break;
                case R.id.txtQty:
                    validateQuantity();
                    break;
                case R.id.txtAddress:
                    validateAddress();
                    break;
                case R.id.txtPackagingQty:
                    validatePackaging();
                    break;
            }
        }
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
