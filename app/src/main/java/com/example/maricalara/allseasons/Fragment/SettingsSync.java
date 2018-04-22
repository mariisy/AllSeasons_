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

import com.example.maricalara.allseasons.Activity.SyncSettingsActivity;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 28/08/2017.
 */

public class SettingsSync extends Fragment {

    ListView listSync;
    Intent intent;

    public SettingsSync() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_sync, container, false);

        listSync = (ListView) rootView.findViewById(R.id.listExpense);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Data and Sync");
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        // Assign adapterStaff to ListView
        listSync.setAdapter(adapter);
        listSync.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?>  list, View view, int position, long id) {
                //foodName = list.getItemAtPosition(position).toString();
                intent = new Intent(getActivity(), SyncSettingsActivity.class);
                //intent.putExtra("foodName", foodName);
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