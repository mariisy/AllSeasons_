package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.maricalara.allseasons.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


public class SalesReport extends Fragment {

    //variables for spinner
    ArrayAdapter<String> adapterCrops;
    List<String> listCrops;
    Spinner spinnerCrops;

    //variables for bar chart
    BarChart barChart;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;



    //variables for pie chart
    PieChart pieChart ;
    ArrayList<Entry> PIEENTRY ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData PIEDATA;


    //variables for line chart
    LineChart lineChart;
    LineDataSet lineDataSet;
    ArrayList<Entry> LINEENTRY;
    ArrayList<String> LineEntryLabels;
    LineData LINEDATA;

    //Sample for List for Spinner
    String[] SPINNERLIST = {"Monthly ", "Quarterly", "Yearly"};

    public SalesReport() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sales_report, container, false);





        //layout for spinnerCrop
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                rootView.findViewById(R.id.spinnerView);
        materialDesignSpinner.setAdapter(arrayAdapter);


        //for bar graph
        barChart = (BarChart) rootView.findViewById(R.id.chartBar);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();
        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();
        Bardataset = new BarDataSet(BARENTRY, "Projects");
        BARDATA = new BarData(BarEntryLabels, Bardataset);
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(BARDATA);
        barChart.animateY(3000);

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


        //for line chart
        lineChart = (LineChart) rootView.findViewById(R.id.chartLine);
        LINEENTRY = new ArrayList<>();
        LineEntryLabels = new ArrayList<>();
        AddValuesToLINEENTRY();
        ADdValuesToLineENtryLabels();
        lineDataSet = new LineDataSet(LINEENTRY, "");
        LINEDATA = new LineData(LineEntryLabels, lineDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setData(LINEDATA);
        lineChart.animateY(3000);



        // Inflate the layout for this fragment
        return rootView;
    }

    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2f, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(6f, 2));
        BARENTRY.add(new BarEntry(8f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(3f, 5));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("January");
        BarEntryLabels.add("February");
        BarEntryLabels.add("March");
        BarEntryLabels.add("April");
        BarEntryLabels.add("May");
        BarEntryLabels.add("June");

    }

    public void AddValuesToPIEENTRY(){

        PIEENTRY.add(new BarEntry(2f, 0));
        PIEENTRY.add(new BarEntry(4f, 1));
        PIEENTRY.add(new BarEntry(6f, 2));
        PIEENTRY.add(new BarEntry(8f, 3));
        PIEENTRY.add(new BarEntry(7f, 4));
        PIEENTRY.add(new BarEntry(3f, 5));

    }

    public void AddValuesToPieEntryLabels(){

        PieEntryLabels.add("January");
        PieEntryLabels.add("February");
        PieEntryLabels.add("March");
        PieEntryLabels.add("April");
        PieEntryLabels.add("May");
        PieEntryLabels.add("June");

    }

    public void AddValuesToLINEENTRY(){
        LINEENTRY.add(new Entry(4f, 0));
        LINEENTRY.add(new Entry(8f, 1));
        LINEENTRY.add(new Entry(6f, 2));
        LINEENTRY.add(new Entry(2f, 3));
        LINEENTRY.add(new Entry(18f, 4));
        LINEENTRY.add(new Entry(9f, 5));
    }

    public void ADdValuesToLineENtryLabels(){
        LineEntryLabels.add("January");
        LineEntryLabels.add("February");
        LineEntryLabels.add("March");
        LineEntryLabels.add("April");
        LineEntryLabels.add("May");
        LineEntryLabels.add("June");
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
