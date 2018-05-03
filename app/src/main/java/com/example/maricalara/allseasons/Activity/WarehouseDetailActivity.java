package com.example.maricalara.allseasons.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.maricalara.allseasons.Controller.EquipmentDAO;
import com.example.maricalara.allseasons.Controller.EquipmentDAOImpl;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Equipment;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.R;

public class WarehouseDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String strName, strType;
    TextView txtType, txtName, txtPrice, txtQty, txtPriceTotal, txtDate;


    //DAO variables
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private DBHelper dbHelper = new DBHelper(WarehouseDetailActivity.this);
    Object object = null;
    Seeds seeds;
    Seedlings seedlings;
    Packaging packaging;
    Fertilizers fertilizers;
    Insecticides insecticides;
    Equipment equipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_detail);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Item Details");
        //inflate back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtType = (TextView) findViewById(R.id.txtType);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtQty = (TextView) findViewById(R.id.txtQty);
        txtPriceTotal = (TextView) findViewById(R.id.txtPriceTotal);
        txtDate = (TextView) findViewById(R.id.txtDate);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strName = extras.getString("itemName");
            strType = extras.getString("itemType");
        }

        setData();


    }

    private void setData() {
        switch (strType) {
            case "Equipment":
                object = imDao.retrieveOne(dbHelper, strType, strName);
                equipment = (Equipment) object;
                txtType.setText(equipment.getType().toString());
                txtName.setText(equipment.getName().toString());
                txtPrice.setText(String.valueOf(equipment.getPrice()));
                txtQty.setText(String.valueOf(equipment.getQuantity()));
                txtPriceTotal.setText(String.valueOf(equipment.getPrice() * equipment.getQuantity()));
                txtDate.setText(equipment.getDate().toString());

                break;

            case "Insecticides":
                object = imDao.retrieveOne(dbHelper, strType, strName);
                insecticides = (Insecticides) object;
                txtType.setText(insecticides.getType().toString());
                txtName.setText(insecticides.getName().toString());
                txtPrice.setText(String.valueOf(insecticides.getPrice()));
                txtQty.setText(String.valueOf(insecticides.getQuantity()));
                txtPriceTotal.setText(String.valueOf(insecticides.getPrice() * insecticides.getQuantity()));
                txtDate.setText(insecticides.getDate().toString());

                break;

            case "Fertilizer":
                object = imDao.retrieveOne(dbHelper, strType, strName);
                fertilizers = (Fertilizers) object;
                txtType.setText(fertilizers.getType().toString());
                txtName.setText(fertilizers.getName().toString());
                txtPrice.setText(String.valueOf(fertilizers.getPrice()));
                txtQty.setText(String.valueOf(fertilizers.getQuantity()));
                txtPriceTotal.setText(String.valueOf(fertilizers.getPrice() * fertilizers.getQuantity()));
                txtDate.setText(fertilizers.getDate().toString());

                break;

            case "Packaging":
                object = imDao.retrieveOne(dbHelper, strType, strName);
                packaging = (Packaging) object;
                txtType.setText(packaging.getType().toString());
                txtName.setText(packaging.getName().toString());
                txtPrice.setText(String.valueOf(packaging.getPrice()));
                txtQty.setText(String.valueOf(packaging.getQuantity()));
                txtPriceTotal.setText(String.valueOf(packaging.getPrice() * packaging.getQuantity()));
                txtDate.setText(packaging.getDate().toString());

                break;

            case "Seeds":
                object = rmDAO.retreiveOne(dbHelper, strType, strName);
                seeds = (Seeds) object;
                txtType.setText(seeds.getType().toString());
                txtName.setText(seeds.getName().toString());
                txtPrice.setText(String.valueOf(seeds.getPrice()));
                txtQty.setText(String.valueOf(seeds.getQuantity()));
                txtPriceTotal.setText(String.valueOf(seeds.getPrice() * seeds.getQuantity()));
                txtDate.setText(seeds.getDate().toString());

                break;

            case "Seedlings":
                object = rmDAO.retreiveOne(dbHelper, strType, strName);
                seedlings = (Seedlings) object;
                txtType.setText(seedlings.getType().toString());
                txtName.setText(seedlings.getName().toString());
                txtPrice.setText(String.valueOf(seedlings.getPrice()));
                txtQty.setText(String.valueOf(seedlings.getQuantity()));
                txtPriceTotal.setText(String.valueOf(seedlings.getPrice() * seedlings.getQuantity()));
                txtDate.setText(seedlings.getDate().toString());
                break;

            default: //do something
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
