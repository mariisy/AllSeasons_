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
import android.widget.Toast;

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
import com.example.maricalara.allseasons.Model.Transaction;
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

    //bundle extra
    String empID, name;

    //for UI
    private Button btnAddTransaction, btnView;
    private EditText txtQty, txtContactNum;
    private TextInputLayout inputLayoutSupplierName, inputLayoutContactNum, inputLayoutQty;
    private MaterialBetterSpinner spinnerType, spinnerItemName, spinnerSupplierName;
    private TextView txtDate, txtTransaction;

    //DAO
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(TransactionsAddBought.this);


    //Data variables

    private String type, itemName, transactionID, supplierName;
    private int qty;
    private ArrayAdapter<String> stringArrayAdapter, arrayAdapter2, arrayAdapter3, arrayAdapter4;
    private ArrayList<String> arrListSupplierName, arrListType, arrListName;
    private ArrayList<Object> arrTransact = new ArrayList<>();
    private ArrayList<Transaction> arrTransaction = new ArrayList<>();
    Object object = null;
    Seeds seeds;
    Seedlings seedlings;
    Packaging packaging;
    Fertilizers fertilizers;
    Insecticides insecticides;
    Equipment equipment;
    double price = 0, totalPrice = 0;
    Object strName = null;
    String[] spinnerListType = {"Seeds", "Seedlings", "Packaging", "Fertilizer", "Insecticides", "Equipment"};

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
        inputLayoutSupplierName = (TextInputLayout) findViewById(R.id.input_layout_supplierName);
        inputLayoutContactNum = (TextInputLayout) findViewById(R.id.input_layout_contactNum);
        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);

        txtQty = (EditText) findViewById(R.id.txtQty);
        spinnerType = (MaterialBetterSpinner) findViewById(R.id.spinnerType);
        spinnerItemName = (MaterialBetterSpinner) findViewById(R.id.spinnerItemName);
        spinnerSupplierName = (MaterialBetterSpinner) findViewById(R.id.spinnerSupplierName);
        txtContactNum = (EditText) findViewById(R.id.txtContactNum);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTransaction = (TextView) findViewById(R.id.txtTransactionID);

        txtDate.setText(strDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            empID = extras.getString("EmployeeID");
            name = extras.getString("EmployeeName");
        }

        txtTransaction.setText(empID);

        String[] spinnerListType = {"", ""};
        ArrayAdapter<String> arrayAdapters = new ArrayAdapter<String>(TransactionsAddBought.this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerType.setAdapter(arrayAdapters);
        spinnerItemName.setAdapter(arrayAdapters);

        //set array for spinner type 1 and type 2

        arrListSupplierName = tDAO.retrieveListSpinner(dbHelper);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrListSupplierName);
        spinnerSupplierName.setAdapter(arrayAdapter);

        spinnerSupplierName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                populateSpinnerType();
            }
        });

        spinnerType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                populateSpinnerName();
            }
        });

        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSuplierName() && validateContact() && validateType() && validateName()) {
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
        AlertDialog.Builder builderView = new AlertDialog.Builder(TransactionsAddBought.this);
        builderView.setTitle("Cart Items");
        ArrayList<String> strings = new ArrayList<>(arrTransact.size());
        for (Object obj : arrTransact) {
            strings.add(Objects.toString(obj, null));
        }

        stringArrayAdapter = new ArrayAdapter<String>(TransactionsAddBought.this, android.R.layout.simple_list_item_1, strings);

        builderView.setPositiveButton("Add Transactions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCart();
                addTransaction();
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

    private boolean validateSuplierName() {
        if (spinnerSupplierName.getText().toString().trim().isEmpty()) {
            spinnerSupplierName.setError("Pick Supplier!");
            requestFocus(spinnerSupplierName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateContact() {
        if (txtContactNum.getText().toString().trim().isEmpty()) {
            inputLayoutQty.setError("Enter contact number!");
            requestFocus(txtContactNum);
            return false;
        } else {
            inputLayoutContactNum.setErrorEnabled(false);
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

    private boolean validateType() {
        if (spinnerType.getText().toString().trim().isEmpty()) {
            spinnerType.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(spinnerType);
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
                case R.id.spinnerSupplierName:
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

        type = spinnerType.getText().toString();
        itemName = spinnerItemName.getText().toString();
        qty = Integer.parseInt(txtQty.getText().toString());

        Date date = new Date();
        double unitPrice = 0;

        switch (type) {
            case "Equipment":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {
                        object = equipmentDAO.retrieveOne(dbHelper, type, itemName);
                        equipment = (Equipment) object;
                        price = equipment.getPrice();
                        totalPrice = equipment.getPrice() * qty;
                        arrTransact.add(new Equipment(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
                                        addTransaction();
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

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        insecticides = (Insecticides) object;
                        price = insecticides.getPrice();
                        totalPrice = insecticides.getPrice() * qty;
                        arrTransact.add(new Insecticides(type, itemName, qty, price, totalPrice, strDate,null));

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
                                        addCart();
                                        addTransaction();
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

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        fertilizers = (Fertilizers) object;
                        price = fertilizers.getPrice();
                        totalPrice = fertilizers.getPrice() * qty;
                        arrTransact.add(new Fertilizers(type, itemName, qty, price, totalPrice, strDate,null));

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
                                        addCart();
                                        addTransaction();
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

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        packaging = (Packaging) object;
                        price = packaging.getPrice();
                        totalPrice = packaging.getPrice() * qty;
                        arrTransact.add(new Packaging(type, itemName, qty, price, totalPrice, strDate,null));

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
                                        addCart();
                                        addTransaction();
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

                        object = rmDAO.retrieveOne(dbHelper, type, itemName);
                        seeds = (Seeds) object;
                        price = seeds.getPrice();
                        totalPrice = seeds.getPrice() * qty;
                        arrTransact.add(new Seeds(type, itemName, qty, price, totalPrice, strDate));
                        arrTransaction.add(new Transaction(0, null, strDate, null, "Expense", itemName,
                                qty, 0, totalPrice, empID, 0));

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
                                        addCart();
                                        addTransaction();
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

                        object = rmDAO.retrieveOne(dbHelper, type, itemName);
                        seedlings = (Seedlings) object;
                        price = seedlings.getPrice();
                        totalPrice = seedlings.getPrice() * qty;
                        arrTransact.add(new Seedlings(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
                                        addTransaction();
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

    private void addCart() {
        try {
            equipmentDAO.updateTransactionAdd(dbHelper, arrTransact);
            imDao.updateTransactionAdd(dbHelper, arrTransact);
            rmDAO.updateTransactionAdd(dbHelper, arrTransact);
            addTransaction();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
        }

    }

    public void addTransaction() {
        tDAO.addTransactionList(dbHelper, arrTransaction);
    }

    private void populateSpinnerType() {
        type = spinnerSupplierName.getText().toString();
        arrListType = tDAO.retrieveListSpinnerColumn(dbHelper, "TYPE", "SUPPLIER_NAME", type);
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListType);
        spinnerType.setAdapter(arrayAdapter2);
    }

    private void populateSpinnerName() {
        type = spinnerType.getText().toString();
        arrListName = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", type);
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListName);
        spinnerItemName.setAdapter(arrayAdapter3);
    }


}