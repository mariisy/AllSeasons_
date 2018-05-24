package com.example.maricalara.allseasons.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.Customer;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TransactionAddSold extends AppCompatActivity {

    //data variables
    String itemName, strName, custName, custNum, custAddress;
    double weight, quantity, price, totalCostSold;
    Crops crops;
    Customer customer;
    Packaging packaging;
    Object object;
    private ArrayList<Object> arrTransact = new ArrayList<>();
    private ArrayList<Transaction> arrTransaction = new ArrayList<>();

    //for UI
    private Button btnAddTransaction, btnView2;
    private EditText txtCustomerName, txtContactNum, txtQty, txtAddress, txtPackagingQty;
    private TextInputLayout inputLayoutCustomerName, inputLayoutContactNum, inputLayoutQuantity, inputLayoutAddress, inputLayoutQtyPackaging;
    private CheckBox chckDelivery;
    private Toolbar toolbar;
    private TextView txtTransactionID, txtDate;
    private ArrayAdapter<String> arrayAdapter3;
    private ArrayList<String> arrListCrop;
    private ArrayAdapter<String> stringArrayAdapter;
    private MaterialBetterSpinner spinnerItem;


    //bundle extra
    String empID, name;
    private AccountingDAO aDAO = new AccountingDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionAddSold.this);


    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    Calendar calendar = Calendar.getInstance();


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

        spinnerItem = (MaterialBetterSpinner) findViewById(R.id.spinnerItem);
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
        btnView2 = (Button) findViewById(R.id.btnView2);
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

                if (validateAddress() && validateContact() && validateCustomerName() && validatePackaging() && validateQuantity()) {
                    setData();
                }

            }
        });
        btnView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = aDAO.getAllDataCGS(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n")
                    buffer.append("CGS: " + result.getString(1) + "\n");
                }

                Cursor result2 = aDAO.getAllDataWPI(dbHelper);
                StringBuffer buffer2 = new StringBuffer();
                while (result2.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n");
                    buffer2.append("WPI Total Cost: " + result2.getString(1) + "\n");
                }

                Cursor result3 = aDAO.getAllDataFGI(dbHelper);
                StringBuffer buffer3 = new StringBuffer();
                while (result3.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n");
                    buffer3.append("FGI Total Cost: " + result3.getString(1) + "\n");
                }

                Cursor result4 = aDAO.getAllDataCash(dbHelper);
                StringBuffer buffer4 = new StringBuffer();
                while (result4.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n");
                    buffer4.append("CASH \n Type: " + result4.getString(1) + "\n");
                    buffer4.append("Name: " + result4.getString(2) + "\n");
                    buffer4.append("Debit: " + result4.getString(3) + "\n");
                    buffer4.append("Credit: " + result4.getString(4) + "\n");
                }

                Cursor result5 = aDAO.getAllDataSalesRevenue(dbHelper);
                StringBuffer buffer5 = new StringBuffer();
                while (result5.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n");
                    buffer5.append("SALES REVENUE \n Type: " + result5.getString(1) + "\n");
                    buffer5.append("Name: " + result5.getString(2) + "\n");
                    buffer5.append("Weight: " + result5.getString(3) + "\n");
                    buffer5.append("Total Earnings: " + result5.getString(5) + "\n");
                }

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TransactionAddSold.this);
                builder.setMessage(buffer.toString() + "\n" + buffer3.toString() + "\n" + buffer2.toString() + "\n" + buffer4.toString() + "\n" + buffer5.toString());
                builder.show();
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

    private void setData() {

        itemName = spinnerItem.getText().toString();
        weight = Integer.parseInt(txtQty.getText().toString());


        if (tDAO.checkExistingWarehouse(dbHelper, "Crops", itemName)) {
            try {
                object = aDAO.retrieveOne2(dbHelper, "Crops", itemName);
                crops = (Crops) object;
                price = crops.getUnitPrice();
                totalCostSold = price * weight;
                arrTransact.add(new Crops("Crops", itemName, price, weight, 0, strDate, 0, 0, totalCostSold));

                object = imDao.retrieveOne(dbHelper, "Packaging", "Plastic Wrapper");
                packaging = (Packaging) object;
                price = packaging.getPrice();
                quantity = Double.valueOf(txtPackagingQty.getText().toString());
                double totalCostSold1 = price * quantity;
                arrTransact.add(new Packaging("Packaging", "Plastic Wrapper", quantity, price, totalCostSold1, strDate, null));


                new AlertDialog.Builder(TransactionAddSold.this)
                        .setTitle("Adding Entry")
                        .setMessage(itemName + " Added! /n Would you like to add another entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewButton();

                            }
                        })
                        .show();

            } catch (Exception e) {
                new AlertDialog.Builder(TransactionAddSold.this)
                        .setTitle("Adding Entry")
                        .setMessage("Adding entry unsuccesful! /n Please try again." + e)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        } else {

            new AlertDialog.Builder(TransactionAddSold.this)
                    .setTitle("Adding Entry")
                    .setMessage("Entry already exists! /n Would you like to add another entry?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }


    }

    private void viewButton() {
        AlertDialog.Builder builderView = new AlertDialog.Builder(TransactionAddSold.this);
        builderView.setTitle("Cart Items");
        ArrayList<String> strings = new ArrayList<>(arrTransact.size());
        for (Object obj : arrTransact) {
            strings.add(Objects.toString(obj, null));
        }

        stringArrayAdapter = new ArrayAdapter<String>(TransactionAddSold.this, android.R.layout.simple_list_item_1, strings);

        builderView.setPositiveButton("Add Transactions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateCGS();
                finish();
            }
        });
        builderView.setNegativeButton("Close View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderView.setAdapter(stringArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                strName = stringArrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(TransactionAddSold.this);
                builderInner.setMessage(strName.toString());
                builderInner.setTitle("Delete item?");
                builderInner.setPositiveButton("Continue ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //action delete
                        stringArrayAdapter.remove(strName.toString());
                        stringArrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                builderInner.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();

            }
        });
        builderView.show();
    }

    public void addTransaction() {
        custName = txtCustomerName.getText().toString();
        custNum = txtContactNum.getText().toString();
        custAddress = txtAddress.getText().toString();

        int custID = 0;


        if (!tDAO.checkExistCustomer(dbHelper, custName, custAddress)) {
            tDAO.addEntry(dbHelper, new Customer(0, custName, custNum, custAddress), "Customer", null, null);
        } else {
            customer = tDAO.retrieveOneCustomer(dbHelper, custName, custAddress);
            custID = customer.getCustomerID();
            tDAO.updateCustomer(dbHelper, String.valueOf(custID), custNum);
        }

        boolean delivery;
        delivery = ((chckDelivery.isChecked()) ? true : false);

        calendar.setTime(date);
        String datePlusOne = null;
        if (strDate.toLowerCase().contains("monday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 2);
            calendar.add(Calendar.MONTH, 2);
            calendar.add(Calendar.DATE, 2);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);


        } else if (strDate.toLowerCase().contains("tuesday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, 1);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);

        } else if (strDate.toLowerCase().contains("wednesday")) {

            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 7);
            calendar.add(Calendar.MONTH, 7);
            calendar.add(Calendar.DATE, 7);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);


        } else if (strDate.toLowerCase().contains("thursday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 6);
            calendar.add(Calendar.MONTH, 6);
            calendar.add(Calendar.DATE, 6);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);


        } else if (strDate.toLowerCase().contains("friday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 5);
            calendar.add(Calendar.MONTH, 5);
            calendar.add(Calendar.DATE, 5);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);


        } else if (strDate.toLowerCase().contains("saturday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 4);
            calendar.add(Calendar.MONTH, 4);
            calendar.add(Calendar.DATE, 4);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);


        } else if (strDate.toLowerCase().contains("sunday")) {
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 3);
            calendar.add(Calendar.MONTH, 3);
            calendar.add(Calendar.DATE, 3);

            Date currentDatePlusOne = calendar.getTime();
            datePlusOne = DateFormat.getDateInstance().format(currentDatePlusOne);
        } else {

        }

        if (delivery) {
            arrTransaction.add(new Transaction(0, null, strDate, datePlusOne, "Revenue", itemName,
                    quantity, totalCostSold, 0, empID, custID));
        } else {

            arrTransaction.add(new Transaction(0, null, strDate, null, "Revenue", itemName,
                    quantity, totalCostSold, 0, empID, custID));
        }


        tDAO.addTransactionList(dbHelper, arrTransaction);
    }

    public void updateCGS() {
        aDAO.updateCGS(dbHelper, arrTransact);
    }
}
