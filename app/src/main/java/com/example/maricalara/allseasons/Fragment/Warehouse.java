package com.example.maricalara.allseasons.Fragment;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maricalara.allseasons.Activity.TransactionsAddBought;
import com.example.maricalara.allseasons.Adapter.ViewPagerAdapter;
import com.example.maricalara.allseasons.R;
import com.github.clans.fab.FloatingActionMenu;

public class Warehouse extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //buttons
    FloatingActionMenu materialDesignFAb;
    FloatingActionButton fabAdd, fabUse, fabHarvest, fabMsg;

    public Warehouse() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_warehouse, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        materialDesignFAb = (FloatingActionMenu) rootView.findViewById(R.id.material_design_android_floating_action_menu);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab1);
        fabMsg = (FloatingActionButton) rootView.findViewById(R.id.fabMessage);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click action
                Intent intent = new Intent(getActivity(), TransactionsAddBought.class);
                getActivity().startActivity(intent);

            }
        });



        fabMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click action
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        //ViewPagerAdapter adapterStaff = new ViewPagerAdapter(getSupportFragmentManager());
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new WarehouseCrops(), "Crops");
        adapter.addFragment(new WarehouseSeeds(), "Seeds");
        adapter.addFragment(new WarehouseSeedlings(), "Seedlings");
        adapter.addFragment(new WarehouseFertilizer(), "Fertilizer");
        adapter.addFragment(new WarehouseInsecticides(), "Insecticides");
        adapter.addFragment(new WarehouseEquipment(), "Equipment");

        viewPager.setAdapter(adapter);
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
