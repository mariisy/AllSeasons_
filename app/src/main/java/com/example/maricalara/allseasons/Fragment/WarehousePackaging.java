package com.example.maricalara.allseasons.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maricalara.allseasons.Activity.WarehouseDetailActivity;
import com.example.maricalara.allseasons.Adapter.PackagingAdapter;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAO;
import com.example.maricalara.allseasons.Controller.IndirectMaterialsDAOImpl;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.Model.Packaging;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

public class WarehousePackaging extends Fragment {

    ArrayList<Packaging> packaging = new ArrayList<Packaging>();
    ArrayList<Object> arrList = new ArrayList<Object>();
    ListView listView;
    private static PackagingAdapter packagingAdapter;
    private IndirectMaterialsDAO imDAO = new IndirectMaterialsDAOImpl();


    public WarehousePackaging() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_warehouse_packaging, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        try {
            packagingAdapter.notifyDataSetChanged();

            DBHelper dbHelper = new DBHelper(getActivity());
            arrList = imDAO.retrieveList(dbHelper, "Packaging");
            packaging = (ArrayList<Packaging>) arrList.get(0);


            packagingAdapter = new PackagingAdapter(packaging, getActivity().getApplicationContext());

            listView.setAdapter(packagingAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final Packaging packagings = packaging.get(position);
                    Snackbar snackbar =
                            Snackbar.make(view, "Type: "+ packagings.getType(), Snackbar.LENGTH_LONG)
                                    .setAction("View", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getActivity(), WarehouseDetailActivity.class);
                                            String strName = packagings.getName().toString();
                                            String strType = packagings.getType().toString();
                                            intent.putExtra("itemType", strType);
                                            intent.putExtra("itemName", strName);
                                            startActivity(intent);

                                        }
                                    });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.RED);

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


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
