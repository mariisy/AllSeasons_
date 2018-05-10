package com.example.maricalara.allseasons.Activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

public class SettingsAddFarmingSeason extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Toolbar toolbar;
    private EditText txtQty, txtQty1, txtQty2, txtDate, txtHectareSize;
    private MaterialBetterSpinner spinnerSeed, spinnerFertilizer, spinnerInsecticide;
    private Button btnSetData, btnDatePicker;

    //data
    //Sample for List for Spinner type 1
    String[] spinnerListType = {"", "", ""};
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_add_farming_season);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Staff");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //UI inflater
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

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListType);
        spinnerSeed.setAdapter(arrayAdapter);
        spinnerFertilizer.setAdapter(arrayAdapter);
        spinnerInsecticide.setAdapter(arrayAdapter);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
