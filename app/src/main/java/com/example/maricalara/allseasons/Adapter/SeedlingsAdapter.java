package com.example.maricalara.allseasons.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maricalara.allseasons.Model.Seedlings;
import com.example.maricalara.allseasons.Model.Seeds;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

/**
 * Created by Mari Calara on 19/10/2017.
 */

public class SeedlingsAdapter extends ArrayAdapter<Seedlings> implements View.OnClickListener {

    private ArrayList<Seedlings> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItem;
        TextView txtQty;
    }

    public SeedlingsAdapter(ArrayList<Seedlings> data, Context context) {
        super(context, R.layout.list_item_row, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Seedlings dataModel = (Seedlings) object;

        switch (v.getId()) {
            case R.id.item:
                Snackbar.make(v, "ID: " + dataModel.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Seedlings dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        SeedlingsAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new SeedlingsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_row, parent, false);
            viewHolder.txtItem = (TextView) convertView.findViewById(R.id.item);
            viewHolder.txtQty = (TextView) convertView.findViewById(R.id.qty);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SeedlingsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        viewHolder.txtItem.setText(dataModel.getName());
        viewHolder.txtQty.setText(String.valueOf(dataModel.getQuantity()) + " Pieces");

        // Return the completed view to render on screen
        return convertView;
    }
}