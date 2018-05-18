package com.example.maricalara.allseasons.Activity;

import android.app.DatePickerDialog;
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
import android.widget.EditText;

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SettingsAddFarmingSeason extends AppCompatActivity {

    Toolbar toolbar;
    private EditText txtQty, txtQty1, txtQty2, txtDate, txtHectareSize;
    private MaterialBetterSpinner spinnerSeed, spinnerFertilizer, spinnerInsecticide;
    private Button btnSetData, btnDatePicker, btnViewData, btnView;
    private TextInputLayout inputLayoutqty, inputLayoutqty1, inputLayoutqty2, inputLayoutDate, inputLayoutLand;
    private ArrayAdapter<String> stringArrayAdapter, arrayAdapter3;
    private ArrayList<String> arrListSeed, arrListInsecticides, arrListFertilizers;


    //DAO
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private AccountingDAO aDAO = new AccountingDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(SettingsAddFarmingSeason.this);

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    //data variables
    private DatePickerDialog dpd;
    Calendar cal = Calendar.getInstance();
    private String dateSet, strName;
    private String seed, fertilizer, insecticide;
    private int seedQty, fertilizerQty, insecticideQty;
    private double price, totalPrice, hectareSize;
    private ArrayList<Object> arrObject = new ArrayList<>();
    private ArrayList<ArrayList<Object>> arrTransact = new ArrayList<>();
    Object object = null, object1 = null, object2 = null;
    Seeds seeds;
    Seedlings seedlings;
    Fertilizers fertilizers;
    Insecticides insecticides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_farming_season);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Resource Planning");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //UI inflater
        inputLayoutqty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        inputLayoutqty1 = (TextInputLayout) findViewById(R.id.input_layout_qty1);
        inputLayoutqty2 = (TextInputLayout) findViewById(R.id.input_layout_qty2);
        inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutLand = (TextInputLayout) findViewById(R.id.input_layout_hectare);
        txtQty = (EditText) findViewById(R.id.txtQty);
        txtQty1 = (EditText) findViewById(R.id.txtQty1);
        txtQty2 = (EditText) findViewById(R.id.txtQty2);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtHectareSize = (EditText) findViewById(R.id.txtHectareSize);
        spinnerSeed = (MaterialBetterSpinner) findViewById(R.id.spinnerSeed);
        spinnerFertilizer = (MaterialBetterSpinner) findViewById(R.id.spinnerFertilizer);
        spinnerInsecticide = (MaterialBetterSpinner) findViewById(R.id.spinnerInsecticide);
        btnSetData = (Button) findViewById(R.id.btnSetData);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);
        btnView = (Button) findViewById(R.id.btnView);


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = aDAO.getAllPlan(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    //buffer.append("ID: " + result.getString(0) + "\n");
                    buffer.append(result.getString(0) + "\n");
                    buffer.append(result.getString(1) + "\n");
                    buffer.append(result.getString(2) + "\n");
                    buffer.append(result.getString(3) + "\n");
                    buffer.append(result.getString(4) + "\n");
                    buffer.append(result.getString(5) + "\n");
                    buffer.append(result.getString(6) + "\n");
                    buffer.append(result.getString(7) + "\n");
                    buffer.append(result.getString(8) + "\n");
                    buffer.append(result.getString(9) + "\n");
                    buffer.append(result.getString(10) + "\n");
                    buffer.append(result.getString(11) + "\n");
                    buffer.append(result.getString(12) + "\n");
                    buffer.append(result.getString(13) + "\n");
                    buffer.append(result.getString(14) + "\n");
                    buffer.append(result.getString(15) + "\n");
                    buffer.append(result.getString(16) + "\n");
                    buffer.append(result.getString(17) + "\n");
                    buffer.append(result.getString(18) + "\n");
                    buffer.append(result.getString(19) + "\n\n\n");
                }

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsAddFarmingSeason.this);
                builder.setMessage(buffer.toString()+"\n");
                builder.show();


            }
        });

        btnSetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateQty() && validateType()) {
                    //do something
                    setData();
                }
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButton();
            }
        });

        arrListFertilizers = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", "Fertilizer");
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListFertilizers);
        spinnerFertilizer.setAdapter(arrayAdapter3);
        arrListInsecticides = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", "Insecticides");
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListInsecticides);
        spinnerInsecticide.setAdapter(arrayAdapter3);
        arrListSeed = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", "Seeds");
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListSeed);
        spinnerSeed.setAdapter(arrayAdapter3);


    }

    private void viewButton() {
        AlertDialog.Builder builderView = new AlertDialog.Builder(SettingsAddFarmingSeason.this);
        builderView.setTitle("Cart Items");
        ArrayList<String> strings = new ArrayList<>(arrObject.size());
        for (Object obj : arrObject) {
            strings.add(Objects.toString(obj, null));
        }

        stringArrayAdapter = new ArrayAdapter<String>(SettingsAddFarmingSeason.this, android.R.layout.simple_list_item_1, strings);

        builderView.setPositiveButton("Add Transactions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hectareSize = Double.valueOf(txtHectareSize.getText().toString());
                aDAO.addEntryPlanning(dbHelper, arrObject, hectareSize);
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
                AlertDialog.Builder builderInner = new AlertDialog.Builder(SettingsAddFarmingSeason.this);
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

        seedQty = Integer.valueOf(txtQty.getText().toString());
        fertilizerQty = Integer.valueOf(txtQty1.getText().toString());
        insecticideQty = Integer.valueOf(txtQty2.getText().toString());
        seed = spinnerSeed.getText().toString();
        fertilizer = spinnerFertilizer.getText().toString();
        insecticide = spinnerInsecticide.getText().toString();


        try {
            object = rmDAO.retrieveOne(dbHelper, "Seeds", seed);
            seeds = (Seeds) object;
            price = seeds.getPrice();
            totalPrice = price * seedQty;
            arrObject.add(new Seeds("Seeds", seed, seedQty, price, totalPrice, strDate));

            object = imDao.retrieveOne(dbHelper, "Fertilizer", fertilizer);
            fertilizers = (Fertilizers) object;
            price = fertilizers.getPrice();
            totalPrice = price * fertilizerQty;
            arrObject.add(new Fertilizers("Fertilizers", fertilizer, fertilizerQty, price, totalPrice, strDate,null));

            object = imDao.retrieveOne(dbHelper, "Insecticides", insecticide);
            insecticides = (Insecticides) object;
            price = insecticides.getPrice();
            totalPrice = price * insecticideQty;
            arrObject.add(new Insecticides("Insecticides", insecticide, insecticideQty, price, totalPrice, strDate,null));


            new AlertDialog.Builder(SettingsAddFarmingSeason.this)
                    .setTitle("Adding Entry")
                    .setMessage(arrObject.toString() + " Added! /n Would you like to add another entry?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hectareSize = Double.valueOf(txtHectareSize.getText().toString());
                            aDAO.addEntryPlanning(dbHelper, arrObject, hectareSize);
                            finish();
                        }
                    })
                    .show();

        } catch (Exception e) {
            new AlertDialog.Builder(SettingsAddFarmingSeason.this)
                    .setTitle("Adding Entry")
                    .setMessage("Adding entry unsuccesful! /n Please try again." + e)
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }


    private boolean validateQty() {
        if (txtQty.getText().toString().trim().isEmpty() || txtQty1.getText().toString().trim().isEmpty()
                || txtQty2.getText().toString().trim().isEmpty() || txtDate.getText().toString().trim().isEmpty()
                || txtHectareSize.getText().toString().trim().isEmpty()) {
            inputLayoutqty.setError("Enter Quantity!");
            inputLayoutqty1.setError("Enter Quantity!");
            inputLayoutqty2.setError("Enter Quantity!");
            inputLayoutDate.setError("Enter Date!");
            inputLayoutLand.setError("Enter Size");
            requestFocus(txtQty);
            requestFocus(txtQty1);
            requestFocus(txtQty2);
            requestFocus(txtDate);
            requestFocus(txtHectareSize);
            return false;
        } else {
            inputLayoutqty.setErrorEnabled(false);
            inputLayoutqty1.setErrorEnabled(false);
            inputLayoutqty2.setErrorEnabled(false);
            inputLayoutDate.setErrorEnabled(false);
            inputLayoutLand.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateType() {
        if (spinnerSeed.getText().toString().trim().isEmpty() || spinnerInsecticide.getText().toString().trim().isEmpty()
                || spinnerFertilizer.getText().toString().trim().isEmpty()) {
            spinnerSeed.setError("Pick Seed Type!");
            spinnerInsecticide.setError("Pick Insecticide Type!");
            spinnerFertilizer.setError("Pick Ferilizer Type!");

            return false;
        } else {

            return true;
        }

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
                case R.id.txtDate:
                    validateQty();
                    break;
                case R.id.txtHectareSize:
                    validateQty();
                    break;
                case R.id.spinnerSeed:
                    validateType();
                    break;
                case R.id.txtQty:
                    validateQty();
                    break;
                case R.id.spinnerFertilizer:
                    validateType();
                    break;
                case R.id.txtQty1:
                    validateQty();
                    break;
                case R.id.spinnerInsecticide:
                    validateType();
                    break;
                case R.id.txtQty2:
                    validateQty();
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
