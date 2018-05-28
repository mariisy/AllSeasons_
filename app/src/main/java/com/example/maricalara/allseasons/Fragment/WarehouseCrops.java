package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maricalara.allseasons.Activity.WarehouseDetailActivity;
import com.example.maricalara.allseasons.Adapter.CropsAdapter;
import com.example.maricalara.allseasons.Controller.AccountingDAO;
import com.example.maricalara.allseasons.Controller.AccountingDAOImpl;
import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 16/10/2017.
 */

public class    WarehouseCrops extends Fragment {


    ArrayList<Crops> crops = new ArrayList<>();
    ListView listView;
    private static CropsAdapter cropsAdapter;
    AccountingDAO aDAO = new AccountingDAOImpl();

    FragmentManager manager;
    FragmentTransaction transaction;

    public WarehouseCrops() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_warehouse_crops, container, false);


        try {


            DBHelper dbHelper = new DBHelper(getActivity());
            crops = aDAO.retrieveCropsList(dbHelper);


            cropsAdapter = new CropsAdapter(crops, getActivity().getApplicationContext());

            listView.setAdapter(cropsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Crops crop= crops.get(position);
                    Snackbar snackbar =
                            Snackbar.make(view, "Type: "+ crop.getType(), Snackbar.LENGTH_LONG)
                                    .setAction("View", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Intent intent = new Intent(getActivity(), WarehouseDetailActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.GREEN);

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                }
            });

            cropsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }







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
