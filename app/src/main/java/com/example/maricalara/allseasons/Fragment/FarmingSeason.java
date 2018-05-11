package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maricalara.allseasons.Activity.TransactionHarvest;
import com.example.maricalara.allseasons.Activity.TransactionUseMaterials;
import com.example.maricalara.allseasons.Activity.TransactionsAddBought;
import com.example.maricalara.allseasons.Adapter.ViewPagerAdapter;
import com.example.maricalara.allseasons.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class FarmingSeason extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //buttons
    FloatingActionMenu materialDesignFAb;
    FloatingActionButton fabAdd, fabUse, fabHarvest, fabMsg;

    public FarmingSeason() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farming_season, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        materialDesignFAb = (FloatingActionMenu) rootView.findViewById(R.id.material_design_android_floating_action_menu);
        fabUse = (FloatingActionButton) rootView.findViewById(R.id.fabUse);
        fabHarvest = (FloatingActionButton) rootView.findViewById(R.id.fabHarvest);
        fabMsg = (FloatingActionButton) rootView.findViewById(R.id.fabMessage);




        fabUse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click action
                Intent intent = new Intent(getActivity(), TransactionUseMaterials.class);
                getActivity().startActivity(intent);

            }
        });

        fabHarvest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click action
                Intent intent = new Intent(getActivity(), TransactionHarvest.class);
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
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FarmingProgress(), "Farming Season Progress");
        adapter.addFragment(new FarmingUtilizedRecord(), "Used Materials Record");
        adapter.addFragment(new FarmingHarvestRecords(), "Harvest Records");


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
