package com.example.maricalara.allseasons.Activity;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TransactionHarvest extends AppCompatActivity {

    //for UI
    private String itemName;
    private double weight,hectare;
    private Button btnAddTransaction, btnView;
    private MaterialBetterSpinner spinnerName;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutQty, inputLayoutHectare;
    private EditText txtQty, txtHectare;
    private TextView txtDate, txtTransaction;

    //DAO
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private AccountingDAO aDAO = new AccountingDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionHarvest.this);

    //data variable
    Crops crops = null;
    Object object = null;
    double totalPrice = 0;
    private ArrayList<String> arrList;
    private ArrayList<Object> arrTransact = new ArrayList<>();
    private ArrayAdapter<String> stringArrayAdapter;
    Object strName = null;

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    //Sample for List for Spinner type 1
    String[] spinnerListType = {"Pechay"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_harvest);


        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Harvest");

        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        txtQty = (EditText) findViewById(R.id.txtQty);
        inputLayoutHectare = (TextInputLayout) findViewById(R.id.input_layout_percent);
        txtHectare = (EditText) findViewById(R.id.txtHectare);

        spinnerName = (MaterialBetterSpinner) findViewById(R.id.spinnerName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTransaction = (TextView) findViewById(R.id.txtTransactionID);

        txtDate.setText(strDate);

        //set array for spinner type 1 and type 2
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerName.setAdapter(arrayAdapter);


        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateQty() && validateName() && validateHectare()) {
                    setData();
                }

            }
        });

        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButton();
            }
        });

    }

    private void viewButton() {
        AlertDialog.Builder builderView = new AlertDialog.Builder(TransactionHarvest.this);
        builderView.setTitle("Cart Items");
        ArrayList<String> strings = new ArrayList<>(arrTransact.size());
        for (Object obj : arrTransact) {
            strings.add(Objects.toString(obj, null));
        }

        stringArrayAdapter = new ArrayAdapter<String>(TransactionHarvest.this, android.R.layout.simple_list_item_1, strings);

        builderView.setPositiveButton("Add Transactions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCart();
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
                AlertDialog.Builder builderInner = new AlertDialog.Builder(TransactionHarvest.this);
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

    private void setData() {

        hectare = Double.valueOf(txtHectare.getText().toString());
        itemName = spinnerName.getText().toString();
        weight = Double.valueOf(txtQty.getText().toString());

        double unitPrice = 0;

        if (tDAO.checkExistingWarehouse(dbHelper, "Crops", itemName)) {
            try {
                object = aDAO.retrieveOne(dbHelper, "Crops", itemName);
                crops = (Crops) object;
                arrTransact.add(new Crops("Crops", itemName, crops.getUnitPrice(), weight, 0, strDate,0,hectare,0));

                new AlertDialog.Builder(TransactionHarvest.this)
                        .setTitle("Adding Entry")
                        .setMessage(itemName + " Added! /n Would you like to add another entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addCart();
                                finish();
                            }
                        })
                        .show();
            } catch (Exception e) {
                new AlertDialog.Builder(TransactionHarvest.this)
                        .setTitle("Adding Entry")
                        .setMessage("Adding entry unsuccesful! /n Please try again. " + e)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        } else {
            new AlertDialog.Builder(TransactionHarvest.this)
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

    private void addCart() {

    }

    private boolean validateHectare() {
        if (txtHectare.getText().toString().trim().isEmpty()) {
            inputLayoutHectare.setError("Enter Quantity!");
            requestFocus(txtHectare);
            return false;
        } else {
            inputLayoutHectare.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateQty() {
        if (txtQty.getText().toString().trim().isEmpty()) {
            inputLayoutQty.setError("Enter Quantity!");
            requestFocus(txtQty);
            return false;
        } else {
            inputLayoutQty.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateName() {
        if (spinnerName.getText().toString().trim().isEmpty()) {
            spinnerName.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(spinnerName);
            return false;
        } else {

            return true;
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.txtQty:
                    validateQty();
                    break;
                case R.id.spinnerItemName:
                    validateName();
                    break;
                case R.id.txtHectare:
                    validateHectare();
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
