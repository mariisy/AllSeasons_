package com.example.maricalara.allseasons.Activity;

import android.content.Context;
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
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.util.Date;

public class SettingsAddToWarehouse extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText txtItemName, txtUprice, txtQty;
    private TextInputLayout inputLayoutItemname, inputLayoutUnitPrice, inputLayoutQty;
    private Button btnAddItem,btnView;
    private MaterialBetterSpinner spinnerType1;

    //DAO variables
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(SettingsAddToWarehouse.this);


    //Sample for List for Spinner type 1
    String[] spinnerListType = {"Seeds", "Seedlings", "Packaging", "Fertilizer", "Insecticides", "Equipment"};


    //data holder
    private String itemName, type, stringDate;
    private Double unitPrice;
    private int qty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_to_warehous);

        //layout for buttons and edit text
        inputLayoutItemname = (TextInputLayout) findViewById(R.id.input_layout_itemName);
        inputLayoutUnitPrice = (TextInputLayout) findViewById(R.id.input_layout_unitPrice);
        inputLayoutQty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        txtItemName = (EditText) findViewById(R.id.txtItemName);
        txtUprice = (EditText) findViewById(R.id.txtUnitPrice);
        txtQty = (EditText) findViewById(R.id.txtQty);
        spinnerType1 = (MaterialBetterSpinner) findViewById(R.id.spinnerType);
        btnView = (Button)findViewById(R.id.btnView);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //set array for spinner type
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerType1.setAdapter(arrayAdapter);



        btnAddItem = (Button) findViewById(R.id.btnAdd);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (validateType() && validateQty() && validateItemName() && validateUnitPrice()) {
                        getData();
                    }
             }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = tDAO.getAllData(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("Type: " + result.getString(0) + "\n");
                    buffer.append("Name: " + result.getString(1) + "\n");
                    buffer.append("Price: " + result.getString(2) + "\n \n");
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsAddToWarehouse.this);
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }

    private void clearField(){
        txtItemName.setText("");
        txtUprice.setText("");
        txtQty.setText("");
        spinnerType1.setAdapter(null);
    }

    private void getData() {
        itemName = txtItemName.getText().toString();
        unitPrice = Double.valueOf(txtUprice.getText().toString());
        qty = Integer.valueOf(txtQty.getText().toString());
        type = spinnerType1.getText().toString();


        Date date = new Date();
        stringDate = DateFormat.getDateTimeInstance().format(date);

        switch (type) {
            case "Equipment":
                if (!equipmentDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Equipment equip = new Equipment(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, equip, type);
                        equipmentDAO.addTransaction(dbHelper, equip);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
                else{
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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
                if (!tDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Insecticides ins = new Insecticides(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, ins, type);
                        imDao.addTransaction(dbHelper, ins, type);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
                else{
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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
                if (!tDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Fertilizers fer = new Fertilizers(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, fer, type);
                        imDao.addTransaction(dbHelper, fer, type);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
                else{
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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
                if (!tDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Packaging packaging = new Packaging(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, packaging, type);
                        imDao.addTransaction(dbHelper, packaging, type);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
                else{
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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
                if (!tDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Seeds seeds = new Seeds(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, seeds, type);
                        rmDAO.addTransaction(dbHelper, seeds, type);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }

                }
                else {
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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
                if (!tDAO.checkExistingWarehouse(dbHelper, itemName)) {
                    Seedlings seedlings = new Seedlings(type, itemName, qty, unitPrice, stringDate);
                    try {
                        tDAO.addEntry(dbHelper, seedlings, type);
                        rmDAO.addTransaction(dbHelper, seedlings, type);

                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage(itemName + " Added! \n Would you like to add another entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearField();
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
                        new AlertDialog.Builder(SettingsAddToWarehouse.this)
                                .setTitle("Adding Entry")
                                .setMessage("Adding entry unsuccesful! \n Please try again.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
                else{
                    new AlertDialog.Builder(SettingsAddToWarehouse.this)
                            .setTitle("Adding Entry")
                            .setMessage("Entry already exists! \n Would you like to add another entry?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearField();
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



    private boolean validateItemName() {
        if (txtItemName.getText().toString().trim().isEmpty()) {
            inputLayoutItemname.setError("Enter Item Name!");
            requestFocus(txtItemName);
            return false;
        } else {
            inputLayoutItemname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUnitPrice() {
        if (txtUprice.getText().toString().trim().isEmpty()) {
            inputLayoutUnitPrice.setError("Enter Item Price!");
            requestFocus(txtUprice);
            return false;
        } else {
            inputLayoutUnitPrice.setErrorEnabled(false);
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
        if (spinnerType1.getText().toString().trim().isEmpty()) {
            spinnerType1.setError("Pick Item Type!");
            //inputLayoutUnitPrice.setError("Enter Last Name!");
            requestFocus(txtUprice);
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
                case R.id.spinnerType:
                    validateType();
                    break;
                case R.id.txtQty:
                    validateQty();
                    break;
                case R.id.txtItemName:
                    validateItemName();
                    break;
                case R.id.txtUnitPrice:
                    validateUnitPrice();
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
