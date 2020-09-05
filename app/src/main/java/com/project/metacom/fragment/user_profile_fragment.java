package com.project.metacom.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.project.metacom.R;
import com.project.metacom.Receiver;
import com.project.metacom.data.User;
import com.project.metacom.data.User_last;
import com.project.metacom.data.User_stats;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//import com.project.metacom.comments.activity;

public class user_profile_fragment extends Fragment {
    private String title;
    private Menu menu;
    private String user_id;
    private AppCompatActivity context;
    public DataAdapter data_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = (AppCompatActivity)getActivity();
        title = String.valueOf(context.getTitle());
        user_id = getArguments().getString("user_id");
        final View view = inflater.inflate(R.layout.user_profile_fragment, container, false);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    final User user = Receiver.data_receiver_user.check(user_id);
                    final User_stats user_stats = user.get_stats();
                    final ListView data_target = (ListView) view.findViewById(R.id.user_explist_view);
                    final List<User_last> user_last = user.get_last();
                    data_adapter = new DataAdapter(context, user_last);
                    // Inflate the layout for this fragment
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            data_target.setAdapter(data_adapter);

                            TextView username_text = (TextView) view.findViewById(R.id.user_name_text_view);
                            username_text.setText(user.username);

                            CircleImageView usr_image = (CircleImageView) view.findViewById(R.id.user_image_view);
                            usr_image.setImageBitmap(user.image);

                            TextView date_text = (TextView) view.findViewById(R.id.date_text_view);
                            date_text.setText(user.joined);

                            TextView likes_text = (TextView) view.findViewById(R.id.user_likes_text_view);
                            likes_text.setText(user_stats.like_sum);

                            TextView dislikes_text = (TextView) view.findViewById(R.id.user_dislikes_text_view);
                            dislikes_text.setText(user_stats.dislike_sum);

                            ProgressBar progress_bar = (ProgressBar)view.findViewById(R.id.progressBar);

                            try {
                                Integer int_likes = Integer.parseInt(user_stats.like_sum);
                                Integer int_dislikes = Integer.parseInt(user_stats.dislike_sum);
                                double int_carma = ((double) int_likes / (double) (int_likes + int_dislikes)) * 100;
                                progress_bar.setProgress((int) int_carma);
                            }catch (Exception E){
                                progress_bar.setProgress(50);
                            }

                            Button subscribe_button = (Button)view.findViewById(R.id.button6);
                            subscribe_button.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Thread thread = new Thread(new Runnable(){
                                                @Override
                                                public void run() {
                                                    user.SubscribeMe(user.id, context, data_adapter);
                                                }
                                            });
                                            thread.start();
                                        }
                                    }
                            );


                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        context.setTitle("User profile");
        return view;
    }

    public class DataAdapter extends ArrayAdapter {

        public DataAdapter(Context context,List<User_last> array) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        this.menu = menu;
        set_menu(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            context.findViewById(R.id.user_profile_conteiner).setVisibility(View.GONE);
            context.setTitle(title);
            set_menu(true);
            context.getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    private void set_menu(Boolean value){
        for(int i=0; i<menu.size(); i++){
            menu.getItem(i).setVisible(value);
        }
    }
}
