package com.example.maricalara.allseasons.Activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

public class SettingsAddFarmingSeason extends AppCompatActivity {

    Toolbar toolbar;
    private EditText txtQty, txtQty1, txtQty2, txtDate, txtHectareSize;
    private MaterialBetterSpinner spinnerSeed, spinnerFertilizer, spinnerInsecticide;
    private Button btnSetData, btnDatePicker;
    private TextInputLayout inputLayoutqty, inputLayoutqty1, inputLayoutqty2, inputLayoutDate, inputLayoutLand;

    //data
    //Sample for List for Spinner type 1

    private DatePickerDialog dpd;




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
   //     inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutLand  = (TextInputLayout) findViewById(R.id.input_layout_hectare);
        txtQty = (EditText) findViewById(R.id.txtQty);
        txtQty1 = (EditText) findViewById(R.id.txtQty1);
        txtQty2 = (EditText) findViewById(R.id.txtQty2);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtHectareSize = (EditText) findViewById(R.id.txtHectareSize);
        spinnerSeed = (MaterialBetterSpinner) findViewById(R.id.spinnerSeed);
        spinnerFertilizer = (MaterialBetterSpinner) findViewById(R.id.spinnerFertilizer);
        spinnerInsecticide = (MaterialBetterSpinner) findViewById(R.id.spinnerInsecticide);
        btnSetData = (Button) findViewById(R.id.btnSetData);
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);

        btnSetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateQty() && validateType()){
                    //do something
                }
            }
        });
        String[] spinnerListType = {"", "", ""};
         ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);



        spinnerSeed.setAdapter(arrayAdapter);
        spinnerFertilizer.setAdapter(arrayAdapter1);
        spinnerInsecticide.setAdapter(arrayAdapter2);
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
