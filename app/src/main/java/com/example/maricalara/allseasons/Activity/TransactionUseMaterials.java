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
import android.widget.EditText;

import com.example.maricalara.allseasons.Controller.EquipmentDAO;
import com.example.maricalara.allseasons.Controller.EquipmentDAOImpl;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionUseMaterials extends AppCompatActivity {

    //for UI
    private String type, itemName;
    private int qty;
    private Button btnAddTransaction, btnView;
    private MaterialBetterSpinner spinnerItem, spinnerItemName;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutQty;
    private EditText txtQty;

    //DAO
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionUseMaterials.this);

    //data variable
    Object object = null;
    Seeds seeds;
    Seedlings seedlings;
    Packaging packaging;
    Fertilizers fertilizers;
    Insecticides insecticides;
    double totalPrice = 0;
    private ArrayList<String> arrList;
    private ArrayList<Object> arrTransact = new ArrayList<>();

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = "Date: " + dayOfTheWeek + ", " + dateForTheDay;

    //Sample for List for Spinner type 1
    String[] spinnerListType = {"Seeds", "Seedlings", "Packaging", "Fertilizer", "Insecticides", "Equipment"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_use_materials);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        txtQty = (EditText) findViewById(R.id.txtQty);

        btnView = (Button) findViewById(R.id.btnView);
        spinnerItem = (MaterialBetterSpinner) findViewById(R.id.spinnerItem);
        spinnerItemName = (MaterialBetterSpinner) findViewById(R.id.spinnerItemName);

        //set array for spinner type 1 and type 2
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerItem.setAdapter(arrayAdapter);


        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                submitForm();
            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor result = tDAO.getAllData(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("Date: " + result.getString(0) + "\n");
                    buffer.append("Type: " + result.getString(1) + "\n");
                    buffer.append("Name: " + result.getString(2) + "\n");
                    buffer.append("Quantity: " + result.getString(3) + "\n");
                    buffer.append("Price: " + result.getString(4) + "\n");
                    buffer.append("TotalCost: " + result.getString(5) + "\n");
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TransactionUseMaterials.this);
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }


    private void submitForm() {
        if (validateQty()) {
            return;
        }

        if (validateType()) {
            return;
        }

        if (validateName()) {
            return;
        }

    }

    private void setData() {

        type = spinnerItem.getText().toString();
        itemName = spinnerItemName.getText().toString();
        qty = Integer.parseInt(txtQty.getText().toString());

        Date date = new Date();
        double unitPrice = 0;

        switch (type) {
            case "Insecticides":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        insecticides = (Insecticides) object;
                        totalPrice = insecticides.getPrice() * qty;
                        arrTransact.add(new Insecticides(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        setData();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Fertilizer":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        fertilizers = (Fertilizers) object;
                        totalPrice = fertilizers.getPrice() * qty;
                        arrTransact.add(new Fertilizers(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        setData();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Packaging":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        packaging = (Packaging) object;
                        totalPrice = packaging.getPrice() * qty;
                        arrTransact.add(new Packaging(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        setData();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Seeds":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = rmDAO.retreiveOne(dbHelper, type, itemName);
                        seeds = (Seeds) object;
                        totalPrice = seeds.getPrice() * qty;
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        setData();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage(e.toString())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Seedlings":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = rmDAO.retreiveOne(dbHelper, type, itemName);
                        seedlings = (Seedlings) object;
                        totalPrice = seedlings.getPrice() * qty;
                        arrTransact.add(new Seedlings(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        setData();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            default: //do something
        }

    }

    private void addData() {
        switch (type) {
            case "Equipment":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        equipmentDAO.updateTransaction(dbHelper, arrTransact);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();

                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage(e.toString())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Insecticides":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //imDao.updateTransaction(dbHelper, strDate, type, itemName, qty);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Fertilizer":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //imDao.updateTransaction(dbHelper, strDate, type, itemName, qty);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Packaging":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //imDao.updateTransaction(dbHelper, strDate, type, itemName, qty);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Seeds":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //rmDAO.updateTransaction(dbHelper, strDate, type, itemName, qty);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage(e.toString())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            case "Seedlings":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //rmDAO.updateTransaction(dbHelper, strDate, type, itemName, qty);


                        new AlertDialog.Builder(TransactionUseMaterials.this)
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
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(TransactionUseMaterials.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionUseMaterials.this)
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
                break;

            default: //do something
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

    private boolean validateType() {
        if (spinnerItem.getText().toString().trim().isEmpty()) {
            spinnerItem.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(spinnerItem);
            return false;
        } else {

            return true;
        }

    }

    private boolean validateName() {
        if (spinnerItemName.getText().toString().trim().isEmpty()) {
            spinnerItemName.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(spinnerItemName);
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
                case R.id.spinnerItem:
                    validateType();
                    break;
                case R.id.spinnerItemName:
                    validateName();
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
