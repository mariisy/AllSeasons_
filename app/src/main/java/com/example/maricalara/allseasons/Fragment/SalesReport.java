package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maricalara.allseasons.Activity.SfpActivity;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.R;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


public class SalesReport extends Fragment {

    //DAO
    TransactionDAO tDAO = new TransactionDAOImpl();
    DBHelper dbHelper;

    //data variables
    ArrayList<Crops> arrCrops = new ArrayList<>();
    ArrayList<Transaction> arrExp = new ArrayList<>();
    ArrayList<Transaction> arrSum = new ArrayList<>();
    Crops crop;

    //variables for spinner
    ArrayAdapter<String> adapterCrops;
    List<String> listCrops;
    Spinner spinnerCrops;
    Button button;
    //variables for bar chart
    BarChart barChart, barChart2;
    ArrayList<BarEntry> BARENTRY, BARENTRY2;
    ArrayList<String> BarEntryLabels, BarEntryLabels2;
    BarDataSet Bardataset, Bardataset2;
    BarData BARDATA, BARDATA2;


    //variables for pie chart
    PieChart pieChart;
    ArrayList<Entry> PIEENTRY;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
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

        dbHelper = new DBHelper(getActivity());



        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SfpActivity.class);
                startActivity(intent);

            }
        });


        //for bar graph
        barChart = (BarChart) rootView.findViewById(R.id.barChartRevenue);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();
        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Cells");
        BARDATA = new BarData(BarEntryLabels, Bardataset);
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(BARDATA);
        barChart.animateY(3000);


        barChart2 = (BarChart) rootView.findViewById(R.id.barChartExpense);
        BARENTRY2 = new ArrayList<>();
        BarEntryLabels2 = new ArrayList<>();
        AddValuesToBARENTRY2();
        AddValuesToBarEntryLabels2();
        Bardataset2 = new BarDataSet(BARENTRY2, "Cells");

        BARDATA2 = new BarData(BarEntryLabels2, Bardataset2);
        Bardataset2.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart2.setData(BARDATA2);
        barChart2.animateY(3000);



        //for line chart
        lineChart = (LineChart) rootView.findViewById(R.id.chartLineRevenue);
        LINEENTRY = new ArrayList<>();
        LineEntryLabels = new ArrayList<>();
        AddValuesToLINEENTRY();
        ADdValuesToLineENtryLabels();
        lineDataSet = new LineDataSet(LINEENTRY, "");
        LINEDATA = new LineData(LineEntryLabels, lineDataSet);
        lineChart.setData(LINEDATA);
        lineChart.animateY(3000);


        // Inflate the layout for this fragment
        return rootView;
    }

    public void AddValuesToBARENTRY() {
        arrCrops = tDAO.retrieveSum(dbHelper);
        int index = 0;
        for (Crops crops : arrCrops) {
            BARENTRY.add(new BarEntry((int) crops.getTotalCostSold(),index));
            index++;
        }



    }

    public void AddValuesToBarEntryLabels() {
        arrCrops = tDAO.retrieveSum(dbHelper);
        int index = 0;
        for (Crops cro : arrCrops) {
            BarEntryLabels.add(index, cro.getName());
            index++;
        }

    }

    public void AddValuesToBARENTRY2() {
        int index = 0;
        arrExp = tDAO.retrieveExpense(dbHelper);
        for (Transaction trans : arrExp) {
            BARENTRY2.add(new BarEntry((int) trans.getTotalCost(),index));
            index++;
        }

    }

    public void AddValuesToBarEntryLabels2() {

        arrExp = tDAO.retrieveExpense(dbHelper);

        int index = 0;
        for (Transaction trans : arrExp) {

            BarEntryLabels2.add(index, trans.getItemType());
            index++;
        }

    }



    public void AddValuesToLINEENTRY() {
        int i = 0;
        arrSum = tDAO.retrieveYearlySum(dbHelper);
        for(Transaction trans : arrSum) {
            LINEENTRY.add(new Entry((float) trans.getPrice(), i));
            i++;
        }
    }

    public void ADdValuesToLineENtryLabels() {
        LineEntryLabels.add("January");
        LineEntryLabels.add("February");
        LineEntryLabels.add("March");
        LineEntryLabels.add("April");
        LineEntryLabels.add("May");
        LineEntryLabels.add("June");
        LineEntryLabels.add("July");
        LineEntryLabels.add("August");
        LineEntryLabels.add("September");
        LineEntryLabels.add("October");
        LineEntryLabels.add("November");
        LineEntryLabels.add("December");
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
