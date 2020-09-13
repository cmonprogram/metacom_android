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

public class DataAdapter_activity extends ArrayAdapter {

        public DataAdapter_activity(Context context, List<User.User_last> array) {
            super(context, 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            final User.User_last obj = (User.User_last)getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.activitylist_activity_item,null);
            }
            final TextView val1 = (TextView) convertView.findViewById(R.id.user_name_text_view);
            final TextView val2 = (TextView) convertView.findViewById(R.id.activity_title_text_view);
            final TextView val3 = (TextView) convertView.findViewById(R.id.activity_url_text_view);
            final CircleImageView val4 = (CircleImageView) convertView.findViewById(R.id.activity_image_view);


            ExecutorService pool = Executors.newFixedThreadPool(1); // creates a pool of threads for the Future to draw from
            Future<User> future = pool.submit(new Callable<User>() {
                @Override
                public User call() {return Receiver.data_receiver_user.check(obj.user_id);}
            });

            try {

                User user = future.get();
                val1.setText(String.format("%s posted %s posts, last: %s", user.username, obj.count, obj.time));
                val2.setText(obj.title);
                val3.setText(obj.url);
                val4.setImageBitmap(obj.icon);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }


            return convertView;
        }

}
