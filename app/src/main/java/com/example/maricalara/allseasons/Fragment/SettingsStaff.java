package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.maricalara.allseasons.Activity.SettingsAddStaff;
import com.example.maricalara.allseasons.Activity.SettingsEditStaff;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 28/08/2017.
 */

public class SettingsStaff extends Fragment {

    ListView listStaff;
    Intent intent;


    public SettingsStaff() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_staff, container, false);

        listStaff = (ListView) rootView.findViewById(R.id.listStaffing);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Add Staff");
        list.add("View and Edit Staff");
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        // Assign adapterStaff to ListView
        listStaff.setAdapter(adapter);
        listStaff.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> list, View view, int position, long id) {

                if (position == 0) {
                    intent = new Intent(getActivity(), SettingsAddStaff.class);
                } else if (position == 1) {
                    intent = new Intent(getActivity(), SettingsEditStaff.class);
                }


                startActivity(intent);
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
