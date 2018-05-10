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

import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
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

public class TransactionUseMaterials extends AppCompatActivity {

    //for UI
    private String type, itemName;
    private int qty;
    private Button btnAddTransaction, btnView,btnView2;
    private MaterialBetterSpinner spinnerItem, spinnerItemName, spinnerCropType;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutQty;
    private EditText txtQty;
    private TextView txtDate, txtTransaction;

    //DAO
    private AccountingDAO aDAO = new AccountingDAOImpl();
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
    double price = 0, totalPrice = 0;
    private ArrayList<String> arrList;
    private ArrayList<Object> arrTransact = new ArrayList<>();
    private ArrayAdapter<String> stringArrayAdapter;
    private ArrayAdapter<String> arrayAdapter3;
    private ArrayList<String> arrListName;
    Object strName = null;

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
        getSupportActionBar().setTitle("Use Warehouse Materials");

        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        txtQty = (EditText) findViewById(R.id.txtQty);

        spinnerItem = (MaterialBetterSpinner) findViewById(R.id.spinnerItem);
        spinnerItemName = (MaterialBetterSpinner) findViewById(R.id.spinnerItemName);
        spinnerCropType = (MaterialBetterSpinner) findViewById(R.id.spinnerCropType);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTransaction = (TextView) findViewById(R.id.txtTransactionID);
        btnView2= (Button)findViewById(R.id.btnView2);
        txtDate.setText(strDate);

        //set array for spinner type 1 and type 2
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerItem.setAdapter(arrayAdapter);


        btnAddTransaction = (Button) findViewById(R.id.btnAdd);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateQty() && validateType() && validateCrop() && validateName()){
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


        btnView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = aDAO.getAllDataWPI(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("ID: " + result.getString(0) + "\n");
                    buffer.append("Total Cost: " + result.getString(1) + "\n\n");
                }
                Cursor result2 = rmDAO.getAllDataRM(dbHelper);
                StringBuffer buffer2 = new StringBuffer();
                while (result2.moveToNext()) {
                    buffer2.append("Type: " + result2.getString(1) + "\n");
                    buffer2.append("Name: " + result2.getString(2) + "\n");
                    buffer2.append("Quantity: " + result2.getString(3) + "\n");
                    buffer2.append("Price:" + result2.getString(4) + "\n");
                    buffer2.append("Total Cost: " + result2.getString(5) + "\n\n");
                }
                Cursor result3 = imDao.getAllDataIM(dbHelper);
                StringBuffer buffer3 = new StringBuffer();
                while (result3.moveToNext()) {
                    buffer3.append("Type: " + result3.getString(1) + "\n");
                    buffer3.append("Name: " + result3.getString(2) + "\n");
                    buffer3.append("Quantity: " + result3.getString(3) + "\n");
                    buffer3.append("Price:" + result3.getString(4) + "\n");
                    buffer3.append("Total Cost: " + result3.getString(5) + "\n\n");
                }

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TransactionUseMaterials.this);
                builder.setMessage(buffer2.toString()+"\n"+buffer3.toString()+"\n"+buffer.toString());
                builder.show();
            }
        });
    }


    private void viewButton() {
        AlertDialog.Builder builderView = new AlertDialog.Builder(TransactionUseMaterials.this);
        builderView.setTitle("Cart Items");

        ArrayList<String> strings = new ArrayList<>(arrTransact.size());
        for (Object obj : arrTransact) {
            strings.add(Objects.toString(obj, null));
        }

        stringArrayAdapter = new ArrayAdapter<String>(TransactionUseMaterials.this, android.R.layout.simple_list_item_1, strings);

        builderView.setPositiveButton("Add Transactions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCart();
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
                AlertDialog.Builder builderInner = new AlertDialog.Builder(TransactionUseMaterials.this);
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

    private boolean validateCrop() {
        if (spinnerCropType.getText().toString().trim().isEmpty()) {
            spinnerCropType.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(spinnerCropType);
            return false;
        } else {

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
                case R.id.spinnerCropType:
                    validateCrop();
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

        double unitPrice = 0;

        switch (type) {
            case "Insecticides":
                if (tDAO.checkExistingWarehouse(dbHelper, type, itemName)) {
                    try {

                        object = imDao.retrieveOne(dbHelper, type, itemName);
                        insecticides = (Insecticides) object;
                        price = insecticides.getPrice();
                        totalPrice = insecticides.getPrice() * qty;
                        arrTransact.add(new Insecticides(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
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
                        price = fertilizers.getPrice();
                        totalPrice = fertilizers.getPrice() * qty;
                        arrTransact.add(new Fertilizers(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
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
                        price = packaging.getPrice();
                        totalPrice = packaging.getPrice() * qty;
                        arrTransact.add(new Packaging(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
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
                        price = seeds.getPrice();
                        totalPrice = seeds.getPrice() * qty;
                        arrTransact.add(new Seeds(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
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
                        price = seedlings.getPrice();
                        totalPrice = seedlings.getPrice() * qty;
                        arrTransact.add(new Seedlings(type, itemName, qty, price, totalPrice, strDate));

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
                                        addCart();
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

    private void addCart() {
        aDAO.updateWPI(dbHelper, arrTransact);
    }

    private void populateSpinnerName() {
        type = spinnerItem.getText().toString();
        arrListName = tDAO.retrieveListSpinnerColumn(dbHelper, "NAME", "TYPE", type);
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrListName);
        spinnerItemName.setAdapter(arrayAdapter3);
    }
}
