package com.project.metacom.activitylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.metacom.R;
import com.project.metacom.Receiver;
import com.project.metacom.data.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataAdapter_users extends ArrayAdapter {

    public DataAdapter_users(Context context, List<User> array) {
        super(context, 0, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final User obj = (User)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.user_profile_list_item,null);
        }
        final TextView val1 = (TextView) convertView.findViewById(R.id.user_name_text_view);
        final TextView val2 = (TextView) convertView.findViewById(R.id.date_text_view);
        final TextView val3 = (TextView) convertView.findViewById(R.id.subscribers_text);
        final CircleImageView val4 = (CircleImageView) convertView.findViewById(R.id.user_image_view);

            val1.setText(obj.username);
            val2.setText(obj.joined);
            val3.setText(obj.subscriptions);
            val4.setImageBitmap(obj.image);

        return convertView;
    }
}
