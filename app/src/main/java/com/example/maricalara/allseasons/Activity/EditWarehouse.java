package com.example.maricalara.allseasons.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

public class EditWarehouse extends AppCompatActivity {

    //UI
    private EditText  txtUprice;
    private TextInputLayout  inputLayoutUnitPrice;
    private Toolbar toolbar;
    private Button btnUpdate;

    //DAO variables
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private DBHelper dbHelper = new DBHelper(EditWarehouse.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warehouse);

        //layout for buttons and edit text
        inputLayoutUnitPrice = (TextInputLayout) findViewById(R.id.input_layout_unitPrice);
        txtUprice = (EditText) findViewById(R.id.txtUnitPrice);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //submitEditText();
                if (validateUnitPrice()) {

                }
            }
        });
    }

    private void setData() {



    }

    private void deleteData() {



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
