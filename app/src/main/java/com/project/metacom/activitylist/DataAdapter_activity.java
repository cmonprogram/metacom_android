package com.project.metacom.activitylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.metacom.R;
import com.project.metacom.data.User_last;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataAdapter_activity extends ArrayAdapter {

        public DataAdapter_activity(Context context, List<User_last> array) {
            super(context, 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            User_last obj = (User_last)getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.user_profile_item,null);
            }
            TextView val1 = (TextView) convertView.findViewById(R.id.profile_item_text_view);
            TextView val2 = (TextView) convertView.findViewById(R.id.profile_item_description_text_view);
            CircleImageView val3 = (CircleImageView) convertView.findViewById(R.id.profile_item_image_view);

            val1.setText(obj.title);
            val2.setText(obj.url);
            val3.setImageBitmap(obj.icon);
            //val3.setText(obj.count);

            return convertView;
        }

}
