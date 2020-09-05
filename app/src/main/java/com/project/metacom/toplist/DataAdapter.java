package com.project.metacom.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.metacom.R;
import com.project.metacom.data.TopListItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataAdapter extends ArrayAdapter {

    public DataAdapter(Context context, ArrayList<TopListItem> array) {
        super(context, 0, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        TopListItem obj = (TopListItem)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.toplist_layout_item,null);
        }
        TextView val1 = (TextView) convertView.findViewById(R.id.title);
        TextView val2 = (TextView) convertView.findViewById(R.id.count);
        CircleImageView val3 = (CircleImageView) convertView.findViewById(R.id.toplist_item_image_view);

        val1.setText(obj.page_title + "\n" + obj.url);
        val2.setText(obj.count);
        val3.setImageBitmap(obj.icon);
        return convertView;
    }
}