package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maricalara.allseasons.Adapter.ViewPagerAdapter;
import com.example.maricalara.allseasons.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class FarmingProgress extends Fragment {

    //UI
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //variables for pie chart
    PieChart pieChart ;
    ArrayList<Entry> PIEENTRY ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData PIEDATA;

    public FarmingProgress() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farming_progress, container, false);


        //for pie chart
        pieChart = (PieChart) rootView.findViewById(R.id.chartPie);
        PIEENTRY = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();
        AddValuesToPIEENTRY();
        AddValuesToPieEntryLabels();
        pieDataSet = new PieDataSet(PIEENTRY, "");
        PIEDATA = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(PIEDATA);
        pieChart.animateY(2000);


        // Inflate the layout for this fragment
        return rootView;
    }

    public void AddValuesToPIEENTRY(){

        PIEENTRY.add(new BarEntry(50, 0));
        PIEENTRY.add(new BarEntry(30, 1));

    }

    public void AddValuesToPieEntryLabels(){
        PieEntryLabels.add("Planted");
        PieEntryLabels.add("Not Yet Planted");
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