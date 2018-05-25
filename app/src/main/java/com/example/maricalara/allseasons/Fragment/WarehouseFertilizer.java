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
import com.example.maricalara.allseasons.Adapter.FertilizerAdapter;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 16/10/2017.
 */

public class WarehouseFertilizer extends Fragment {

    ArrayList<Fertilizers> fertilizers = new ArrayList<Fertilizers>();
    ArrayList<Object> arrList = new ArrayList<Object>();
    ListView listView;
    private static FertilizerAdapter fertilizerAdapter;
    private IndirectMaterialsDAO imDAO = new IndirectMaterialsDAOImpl();


    public WarehouseFertilizer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_warehouse_fertilizer, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        fertilizerAdapter.notifyDataSetChanged();

        DBHelper dbHelper = new DBHelper(getActivity());
        arrList = imDAO.retrieveList(dbHelper, "Fertilizer");
        fertilizers = (ArrayList<Fertilizers>) arrList.get(1);


        fertilizerAdapter = new FertilizerAdapter(fertilizers, getActivity().getApplicationContext());

        listView.setAdapter(fertilizerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Fertilizers fertilizer = fertilizers.get(position);
                Snackbar snackbar =
                        Snackbar.make(view, "Crop ID: " + fertilizer.getType(), Snackbar.LENGTH_LONG)
                                .setAction("View", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), WarehouseDetailActivity.class);
                                        String strName = fertilizer.getName().toString();
                                        String strType = fertilizer.getType().toString();
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
