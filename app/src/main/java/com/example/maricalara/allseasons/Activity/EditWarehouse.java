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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

public class EditWarehouse extends AppCompatActivity {

    //UI
    private EditText txtUprice;
    private TextView txtType, txtName;
    private TextInputLayout inputLayoutUnitPrice;
    private Toolbar toolbar;
    private Button btnUpdate, btnDelete;

    //DAO variables
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(EditWarehouse.this);

    //data variables
    private String strName, strType;
    double strPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warehouse);

        //layout for buttons and edit text
        inputLayoutUnitPrice = (TextInputLayout) findViewById(R.id.input_layout_unitPrice);
        txtUprice = (EditText) findViewById(R.id.txtUnitPrice);
        txtType = (TextView) findViewById(R.id.txtType);
        txtName = (TextView) findViewById(R.id.txtName);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strName = extras.getString("itemName");
            strType = extras.getString("itemType");
            strPrice = extras.getDouble("itemPrice");
        }

        txtType.setText(strType);
        txtName.setText(strName);
        txtUprice.setText(Double.toString(strPrice), TextView.BufferType.EDITABLE);


        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateUnitPrice()) {

                }
            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    private void updateData() {
        try {
            tDAO.updateEntry(dbHelper, strName, strPrice);
            new AlertDialog.Builder(EditWarehouse.this)
                    .setTitle("Updating Entry")
                    .setMessage("Entry Update Successful!.")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }catch (Exception e){
            new AlertDialog.Builder(EditWarehouse.this)
                    .setTitle("Updating Entry")
                    .setMessage("Updating entry unsuccesful! \n Please try again.")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    private void deleteData() {
        try {
            tDAO.deleteEntry(dbHelper, strName);
            new AlertDialog.Builder(EditWarehouse.this)
                    .setTitle("Deleting Entry")
                    .setMessage("Deleting entry succesful!")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }catch (Exception e){
            new AlertDialog.Builder(EditWarehouse.this)
                    .setTitle("Deleting Entry")
                    .setMessage("Deleting entry unsuccesful! \n Please try again.")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
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


                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
