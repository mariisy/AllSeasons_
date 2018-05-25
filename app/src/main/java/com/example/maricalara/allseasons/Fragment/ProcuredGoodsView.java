package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.maricalara.allseasons.Adapter.ExpandableListViewAdapter;
import com.example.maricalara.allseasons.Controller.PGAddData;
import com.example.maricalara.allseasons.Controller.TransactionDAO;
import com.example.maricalara.allseasons.Controller.TransactionDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Transaction;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcuredGoodsView extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListViewAdapter expandableListViewAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<HashMap<String, List<String>>> arrayList;


    //DAO
    TransactionDAO tDAO = new TransactionDAOImpl();
    DBHelper dbHelper;

    public ProcuredGoodsView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_procured_goods_view, container, false);


        //List
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListViewPGAdd);

        dbHelper = new DBHelper(getActivity());

        arrayList = tDAO.retrieveTransactionList(dbHelper, "Expense");
        expandableListDetail = (HashMap<String, List<String>>) arrayList.get(1);
        //expandableListDetail = PGAddData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListViewAdapter = new ExpandableListViewAdapter(getActivity(), expandableListTitle, expandableListDetail);


        expandableListView.setAdapter(expandableListViewAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });



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
