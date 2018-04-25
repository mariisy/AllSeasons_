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
import android.widget.TextView;

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
import java.util.Objects;


/**
 * Created by Mari Calara on 25/08/2017.
 */

public class TransactionsAddBought extends AppCompatActivity {


    private Toolbar toolbar;


    //for UI
    private Button btnAddTransaction, btnView;
    private EditText txtSupplierName, txtContactNum, txtQty;
    private TextInputLayout inputLayoutSupplierName, inputLayoutContactNum, inputLayoutQty;
    private MaterialBetterSpinner spinnerItem, spinnerItemName;
    private TextView txtDate, txtTransaction;

    //DAO
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionsAddBought.this);


    //Data variables
    String[] spinnerListType = {"Seeds", "Seedlings", "Packaging", "Fertilizer", "Insecticides", "Equipment"};
    private String type, itemName, transactionID;
    private int qty;
    ArrayAdapter<String> stringArrayAdapter;
    private ArrayList<String> arrList;
    private ArrayList<Object> arrTransact  = new ArrayList<>();
    Object object = null;
    Seeds seeds;
    Seedlings seedlings;
    Packaging packaging;
    Fertilizers fertilizers;
    Insecticides insecticides;
    Equipment equipment;
    double totalPrice = 0;
    Object strName = null;

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
        setContentView(R.layout.activity_transaction_add_bought);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //UI inflater
        inputLayoutSupplierName = (TextInputLayout) findViewById(R.id.input_layout_supplier);
        inputLayoutContactNum = (TextInputLayout) findViewById(R.id.input_layout_contactNum);
        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        txtSupplierName = (EditText) findViewById(R.id.txtSupplier);
        txtContactNum = (EditText) findViewById(R.id.txtContactNum);
        txtQty = (EditText) findViewById(R.id.txtQty);
        btnView = (Button) findViewById(R.id.btnView);
        spinnerItem = (MaterialBetterSpinner) findViewById(R.id.spinnerItem);
        spinnerItemName = (MaterialBetterSpinner) findViewById(R.id.spinnerItemName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTransaction = (TextView) findViewById(R.id.txtTransactionID);

        txtDate.setText(strDate);

        //set array for spinner type 1 and type 2
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerItem.setAdapter(arrayAdapter);



        spinnerItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                populateSpinnerName();
            }
        });

        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                submitForm();
                setData();
                //getData();

            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderView = new AlertDialog.Builder(TransactionsAddBought.this);
                builderView.setTitle("Cart Items");

                ArrayList<String> strings = new ArrayList<>(arrTransact.size());
                for (Object obj : arrTransact){
                    strings.add(Objects.toString(obj, null));
                }

                stringArrayAdapter = new ArrayAdapter<String>(TransactionsAddBought.this, android.R.layout.simple_list_item_1, strings);

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
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(TransactionsAddBought.this);
                        builderInner.setMessage(strName.toString());
                        builderInner.setTitle("Delete item?");
                        builderInner.setPositiveButton("Continue ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               //action delete
                                stringArrayAdapter.remove(strName.toString());
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




                /*
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TransactionsAddBought.this);
                builder.setMessage(buffer.toString());
                builder.show();*/
            }
        });
    }


    private void submitForm() {
        if (validateSuplierName()) {
            return;
        }

        if (validateContact()) {
            return;
        }

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

    private boolean validateSuplierName() {
        if (txtSupplierName.getText().toString().trim().isEmpty()) {
            inputLayoutSupplierName.setError("Enter Supplier Name");
            requestFocus(txtSupplierName);
            return false;
        } else {
            inputLayoutSupplierName.setErrorEnabled(false);
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
                case R.id.txtSupplier:
                    validateSuplierName();
                    break;
                case R.id.txtContactNum:
                    validateContact();
                    break;
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

    private void setData() {

        type = spinnerItem.getText().toString();
        itemName = spinnerItemName.getText().toString();
        qty = Integer.parseInt(txtQty.getText().toString());

        Date date = new Date();
        double unitPrice = 0;

        switch (type) {
            case "Equipment":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        //equipmentDAO.updateTransaction(dbHelper, strDate, type, itemName, qty);
                        object = equipmentDAO.retrieveOne(dbHelper, type, itemName);
                        equipment = (Equipment) object;
                        totalPrice = equipment.getPrice() * equipment.getQuantity();
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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
                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        insecticides = (Insecticides) object;
                        totalPrice = insecticides.getPrice() * insecticides.getQuantity();
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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
                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        fertilizers = (Fertilizers) object;
                        totalPrice = fertilizers.getPrice() * fertilizers.getQuantity();
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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
                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        packaging = (Packaging) object;
                        totalPrice = packaging.getPrice() * packaging.getQuantity();
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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
                        object = rmDAO.retreiveOne(dbHelper, type, itemName);
                        seeds = (Seeds) object;
                        totalPrice = seeds.getPrice() * seeds.getQuantity();
                        arrTransact.add(new Seeds(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage(e.toString())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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
                        object = rmDAO.retreiveOne(dbHelper, type, itemName);
                        seedlings = (Seedlings) object;
                        totalPrice = seedlings.getPrice() * seedlings.getQuantity();
                        arrTransact.add(new Seedlings(type, itemName, qty, totalPrice, strDate));

                        new AlertDialog.Builder(TransactionsAddBought.this)
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
                        new AlertDialog.Builder(TransactionsAddBought.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! /n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(TransactionsAddBought.this)
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

    private void addCart(){

    }

    private void populateSpinnerName() {
        type = spinnerItem.getText().toString();
        ArrayAdapter<String> arrayAdapter2;
        switch (type) {
            case "Equipment":
                arrList = equipmentDAO.retrieveListSpinner(dbHelper, type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);
                break;

            case "Insecticides":
                arrList = imDao.retrieveListSpinner(dbHelper, type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);
                break;

            case "Fertilizer":
                arrList = imDao.retrieveListSpinner(dbHelper, type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);

                break;

            case "Packaging":
                arrList = imDao.retrieveListSpinner(dbHelper, type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);
                break;

            case "Seeds":
                arrList = rmDAO.retrieveListSpinner(dbHelper,type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);
                break;

            case "Seedlings":
                arrList = rmDAO.retrieveListSpinner(dbHelper,type);
                arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrList);
                spinnerItemName.setAdapter(arrayAdapter2);
                break;

            default: //do something
        }
    }


}