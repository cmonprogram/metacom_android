package com.project.metacom.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.metacom.R;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter {

    public DataAdapter(Context context, ArrayList<DataStructure> array) {
        super(context, 0, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        DataStructure obj = (DataStructure)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.toplist_layout_item,null);
        }
        TextView val1 = (TextView) convertView.findViewById(R.id.title);
        TextView val2 = (TextView) convertView.findViewById(R.id.count);
        val1.setText(obj.page_title + "\n" + obj.chat_room);
        val2.setText(obj.count);
        return convertView;
    }
}