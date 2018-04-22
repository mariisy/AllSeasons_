package com.example.maricalara.allseasons.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maricalara.allseasons.Adapter.WarehouseMaterialAdapter;
import com.example.maricalara.allseasons.Controller.EquipmentDAO;
import com.example.maricalara.allseasons.Controller.EquipmentDAOImpl;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAO;
import com.example.maricalara.allseasons.Controller.RawMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

public class SettingsEditWarehouse extends AppCompatActivity {

    private Toolbar toolbar;

    ArrayList<WarehouseMaterial> arrList = new ArrayList<>();
    ListView listView;

    private static WarehouseMaterialAdapter warehouseMaterialAdapter;

    //DAO variables
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IndirectMaterialsDAO imDao = new IndirectMaterialsDAOImpl();
    private RawMaterialsDAO rmDAO = new RawMaterialsDAOImpl();
    private DBHelper dbHelper = new DBHelper(SettingsEditWarehouse.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_warehouse);


        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        arrList = rmDAO.getAllDataWarehouse(dbHelper);

        warehouseMaterialAdapter = new WarehouseMaterialAdapter(arrList, this.getApplicationContext());

        listView.setAdapter(warehouseMaterialAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final WarehouseMaterial warehouseMaterial = arrList.get(position);
                // Click action
                Intent intent = new Intent(SettingsEditWarehouse.this, EditWarehouse.class);
                String strName = warehouseMaterial.getName().toString();
                String strType = warehouseMaterial.getType().toString();
                double strPrice = warehouseMaterial.getPrice();
                intent.putExtra("itemType", strType);
                intent.putExtra("itemName", strName);
                intent.putExtra("itemName", strPrice);
                startActivity(intent);



            }
        });



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
