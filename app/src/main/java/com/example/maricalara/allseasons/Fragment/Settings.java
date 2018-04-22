package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maricalara.allseasons.R;

public class Settings extends Fragment {

    SettingsStaff settingsStaff;
    SettingsWarehousing settingsLiab;
    SettingsSync settingsSync;

    FragmentManager manager;
    FragmentTransaction transaction;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsStaff = new SettingsStaff();
        settingsLiab = new SettingsWarehousing();
        settingsSync = new SettingsSync();


        //create an instance of fragment manager
        manager = getFragmentManager();
        //create an instance of Fragment-transaction
        transaction = manager.beginTransaction();

        transaction.add(R.id.settings_staff, settingsStaff, "staffFragment");
        transaction.add(R.id.settings_accounting, settingsLiab, "staffFragment");
        transaction.add(R.id.settings_expense, settingsSync, "staffFragment");

        transaction.commit();



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
