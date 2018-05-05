package com.example.maricalara.allseasons.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Employees;
import com.example.maricalara.allseasons.Model.Equipment;
import com.example.maricalara.allseasons.R;

public class LogInActivity extends AppCompatActivity {

    //UI
    private Button btnlogin;
    private Intent intent;
    private EditText txtUsername, txtPass;
    private TextInputLayout inputLayoutUname, inputLayoutPass;


    //DAO
    private TransactionDAO tDAO = new TransactionDAOImpl();
    private DBHelper dbHelper = new DBHelper(LogInActivity.this);

    //data varaiables
    String username, password, empID, name;
    Employees employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputLayoutUname = (TextInputLayout) findViewById(R.id.input_layout_uName);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPass);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (validateUsername() && validatePassword()) {
                 //   getData();
                    Intent myIntent = new Intent(LogInActivity.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }


            }
        });
    }

        private void getData(){
        username = txtUsername.getText().toString();
        password = txtPass.getText().toString();
        if (tDAO.checkExistingEmployee(dbHelper, null, null, username,password)) {
            try {
                employees = tDAO.retrieveOneEmployee(dbHelper, username, password);

                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                name = employees.getName();
                empID = employees.getEmployeeFullID();
                intent.putExtra("EmployeeName", name);
                intent.putExtra("EmployeeID", empID);
                startActivity(intent);


            } catch (Exception e) {
                new AlertDialog.Builder(LogInActivity.this)
                        .setTitle("Log In Error")
                        .setMessage("Log In failed.")
                        .setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        } else {

            new AlertDialog.Builder(LogInActivity.this)
                    .setTitle("Log In Error")
                    .setMessage("Account does not exist")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();

        }
    }


    private boolean validateUsername() {
        if (txtUsername.getText().toString().trim().isEmpty()) {
            inputLayoutUname.setError("Enter Username");
            inputLayoutPass.setError("Enter Password");
            requestFocus(txtUsername);

            return false;
        } else {
            inputLayoutUname.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (txtPass.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError("Enter Password");
            requestFocus(txtPass);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }
        return true;
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
                case R.id.txtUsername:
                    validateUsername();
                    break;
                case R.id.txtPass:
                    validatePassword();
                    break;
            }
        }
    }
}
