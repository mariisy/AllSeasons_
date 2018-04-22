package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maricalara.allseasons.Activity.TransactionAddSold;
import com.example.maricalara.allseasons.Adapter.ViewPagerAdapter;
import com.example.maricalara.allseasons.R;


public class GoodsSold extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public GoodsSold() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goods_sold, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionAddSold.class);
                getActivity().startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }





    private void setupViewPager(ViewPager viewPager) {
        //ViewPagerAdapter adapterStaff = new ViewPagerAdapter(getSupportFragmentManager());
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new GoodsSoldView(), "Sold");
        adapter.addFragment(new GoodsSoldDelivery(), "Deliveries");

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
