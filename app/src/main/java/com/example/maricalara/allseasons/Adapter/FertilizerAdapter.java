package com.example.maricalara.allseasons.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.Fertilizers;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 19/10/2017.
 */

public class FertilizerAdapter extends ArrayAdapter<Fertilizers> implements View.OnClickListener{

    private ArrayList<Fertilizers> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItem;
        TextView txtQty;
    }

    public FertilizerAdapter(ArrayList<Fertilizers> data, Context context) {
        super(context, R.layout.list_item_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Fertilizers dataModel=(Fertilizers)object;

        switch (v.getId())
        {
            case R.id.item:
                Snackbar.make(v, "ID: " +dataModel.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Fertilizers dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        FertilizerAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new FertilizerAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_row, parent, false);
            viewHolder.txtItem = (TextView) convertView.findViewById(R.id.item);
            viewHolder.txtQty = (TextView) convertView.findViewById(R.id.qty);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FertilizerAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtItem.setText(dataModel.getName());
        viewHolder.txtQty.setText("Stocks Available: " + String.valueOf(dataModel.getQuantity()) + " Sacks");

        // Return the completed view to render on screen
        return convertView;
    }
}
