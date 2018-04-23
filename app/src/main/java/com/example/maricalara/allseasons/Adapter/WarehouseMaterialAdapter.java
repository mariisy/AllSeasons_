package com.example.maricalara.allseasons.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maricalara.allseasons.Model.Crops;
import com.example.maricalara.allseasons.Model.WarehouseMaterial;
import com.example.maricalara.allseasons.R;

import java.util.ArrayList;

import java.util.ArrayList;

public class WarehouseMaterialAdapter  extends ArrayAdapter<WarehouseMaterial> implements View.OnClickListener{
    private ArrayList<WarehouseMaterial> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItem;
        TextView txtQty;
    }

    public WarehouseMaterialAdapter(ArrayList<WarehouseMaterial> data, Context context) {
        super(context, R.layout.list_item_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

        int position =(Integer) v.getTag();
        Object object = getItem(position);
        WarehouseMaterial dataModel =(WarehouseMaterial)object;

        switch (v.getId())
        {
            case R.id.item:
                Snackbar.make(v, "Type: " +dataModel.getType(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WarehouseMaterial dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        WarehouseMaterialAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new WarehouseMaterialAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_row, parent, false);
            viewHolder.txtItem = (TextView) convertView.findViewById(R.id.item);
            viewHolder.txtQty = (TextView) convertView.findViewById(R.id.qty);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (WarehouseMaterialAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtItem.setText(dataModel.getType() + ": " + dataModel.getName());
        viewHolder.txtQty.setText(String.valueOf("Price: ₱ " + dataModel.getPrice()));

        // Return the completed view to render on screen
        return convertView;
    }
}
