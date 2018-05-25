package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maricalara.allseasons.Activity.WarehouseDetailActivity;
import com.example.maricalara.allseasons.Adapter.InsecticidesAdapter;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Insecticides;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 16/10/2017.
 */

public class WarehouseInsecticides extends Fragment {

    ArrayList<Insecticides> insecticides = new ArrayList<Insecticides>();
    ArrayList<Object> arrList = new ArrayList<Object>();
    ListView listView;
    private static InsecticidesAdapter insecticidesAdapter;
    private IndirectMaterialsDAO imDAO = new IndirectMaterialsDAOImpl();

    public WarehouseInsecticides() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_warehouse_insecticides, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        insecticidesAdapter.notifyDataSetChanged();

        DBHelper dbHelper = new DBHelper(getActivity());
        arrList = imDAO.retrieveList(dbHelper, "Insecticides");
        insecticides = (ArrayList<Insecticides>) arrList.get(0);

        insecticidesAdapter = new InsecticidesAdapter(insecticides, getActivity().getApplicationContext());

        listView.setAdapter(insecticidesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Insecticides insecticide = insecticides.get(position);
                Snackbar snackbar =
                        Snackbar.make(view, "Type: " + insecticide.getType(), Snackbar.LENGTH_LONG)
                                .setAction("View", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getActivity(), WarehouseDetailActivity.class);
                                        String strName = insecticide.getName().toString();
                                        String strType = insecticide.getType().toString();
                                        intent.putExtra("itemType", strType);
                                        intent.putExtra("itemName", strName);
                                        startActivity(intent);
                                    }
                                });

                // Changing message text color
                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                snackbar.show();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
