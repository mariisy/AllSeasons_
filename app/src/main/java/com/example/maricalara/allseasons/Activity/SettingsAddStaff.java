package com.example.maricalara.allseasons.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Fragment.Settings;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class SettingsAddStaff extends AppCompatActivity {

    //UI
    private Toolbar toolbar;

    private EditText txtFname, txtLname, txtSalary;
    private TextInputLayout inputLayoutFname, inputLayoutLname, inputLayoutSalary;
    private Button btnAddEmployee,btnViewEmployee;
    private CheckBox chckSalary;
    private DBHelper dbHelper = new DBHelper(SettingsAddStaff.this);

    //for getting values
    private String employeeName;
    private int salary;
    private final int defaultSalary = 13000;
    private TransactionDAO transactionDAO = new TransactionDAOImpl();
    //Sample for List for Spinner
    String[] SPINNERLIST = {"Farmer", "Staff", "Supervisor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_staff);

        inputLayoutFname = (TextInputLayout) findViewById(R.id.input_layout_fName);
        inputLayoutLname = (TextInputLayout) findViewById(R.id.input_layout_lfName);
        inputLayoutSalary = (TextInputLayout) findViewById(R.id.input_layout_salary);
        txtFname = (EditText) findViewById(R.id.txtFname);
        txtLname  = (EditText) findViewById(R.id.txtLname);
        txtSalary  = (EditText) findViewById(R.id.txtSalary);
        chckSalary = (CheckBox) findViewById(R.id.defaultSalary);
        btnViewEmployee = (Button)findViewById(R.id.btnViewEmployee);

        employeeName = txtFname.getText().toString() + " " + txtLname.getText().toString();
        if (chckSalary.isChecked()){
            salary = defaultSalary;
        }else{
            salary = Integer.parseInt(txtSalary.getText().toString());
        }

        btnViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = transactionDAO.getAllEmployee(dbHelper);
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("ID: " + result.getString(0) + "\n");
                    buffer.append("Full ID: " + result.getString(1) + "\n");
                    buffer.append("NAME: " + result.getString(2) + "\n");
                    buffer.append("ACCOUNT TYPE: " + result.getString(3) + "\n");
                    buffer.append("SALARY: " + result.getString(4) + "\n");
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsAddStaff.this);
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        btnAddEmployee = (Button) findViewById(R.id.btnAddEmployee);
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEditText();
             //   transactionDAO.addEmployee(dbHelper);
            }
        });

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //layout for spinnerCrop
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.spinnerPosition);
        materialDesignSpinner.setAdapter(arrayAdapter);


    }

    private void submitEditText(){
        if (!validateFname()) {
            return;
        }

        if (!validateLname()) {
            return;
        }

        if (!validateSalary()) {
            return;
        }

        Toast.makeText(this.getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFname() {
        if (txtFname.getText().toString().trim().isEmpty()){
            inputLayoutFname.setError("Enter First Name!");
            requestFocus(txtFname);
            return false;
        } else {
            inputLayoutFname.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLname() {
        if (txtLname.getText().toString().trim().isEmpty()){
            inputLayoutLname.setError("Enter Last Name!");
            requestFocus(txtLname);
            return false;
        } else {
            inputLayoutLname.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateSalary() {
        if (txtSalary.getText().toString().trim().isEmpty()){
            inputLayoutSalary.setError("Enter Salary!");
            requestFocus(txtSalary);
            return false;
        } else {
            inputLayoutSalary.setErrorEnabled(false);
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
                case R.id.txtFname:
                    validateFname();
                    break;
                case R.id.txtLname:
                    validateLname();
                    break;
                case R.id.txtSalary:
                    validateSalary();
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

                /*
                Intent intent = new Intent(SettingsAddStaff.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
