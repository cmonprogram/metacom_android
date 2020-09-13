package com.project.metacom.activitylist;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.project.metacom.R;
import com.project.metacom.data.User;


import java.util.ArrayList;
import java.util.List;

import static com.project.metacom.config.me;


public class fragment extends Fragment {
    private AppCompatActivity context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activitylist_layout, container, false);
        context = (AppCompatActivity)getActivity();
        context.setTitle("Activity");
        setHasOptionsMenu(true);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // Data init place
                    List<User.User_last> activity_last = me.get_last_activity();
                    final DataAdapter_activity activity_last_da = new DataAdapter_activity(context, activity_last);

                    //DataReceiver_activity dr = new DataReceiver_activity(da);

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // View change place
                            final ListView data_target = (ListView) view.findViewById(R.id.activitylist_view);
                            final LinearLayout go_to_login = (LinearLayout)view.findViewById(R.id.activitylist_go_to_login);
                            final Button go_to_login_button = (Button)view.findViewById(R.id.go_to_login);
                            final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.activitylist_tabs);

                            final int ACTIVITY_TAB = 0;
                            final int SUBSCRIPTIONS_TAB = 1;
                            final int USERS_TAB = 2;

                            go_to_login_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent startIntent = new Intent(context, com.project.metacom.login.activity.class);
                                    startActivity(startIntent);
                                }
                            });

                            data_target.setAdapter(activity_last_da);
                            /*
                            data_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TopListItem item = (TopListItem)da.getItem(position);
                                    //Toast.makeText(getBaseContext(),item.chat_room,Toast.LENGTH_SHORT).show();

                                    Intent startIntent = new Intent(context, com.project.metacom.comments.activity.class);
                                    startIntent.putExtra("go_to", item.chat_room);
                                    startIntent.putExtra("page_title", item.page_title);
                                    startActivity(startIntent);

                                }
                            });

                            */

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!me.checkMe()) {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                data_target.setVisibility(View.GONE);
                                                go_to_login.setVisibility(View.VISIBLE);
                                            }});
                                    } else {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                data_target.setVisibility(View.VISIBLE);
                                                go_to_login.setVisibility(View.GONE);
                                                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                                    @Override
                                                    public void onTabSelected(TabLayout.Tab tab) {
                                                        //viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                                                        switch (tab.getPosition()) {
                                                            case ACTIVITY_TAB:
                                                                List<User.User_last> activity_last = me.get_last_activity();
                                                                final DataAdapter_activity activity_last_da = new DataAdapter_activity(context, activity_last);
                                                                data_target.setAdapter(activity_last_da);
                                                                break;
                                                            case SUBSCRIPTIONS_TAB:
                                                                final List<User> subscriptions_last = me.get_last_subscriptions();;
                                                                final DataAdapter_users subscriptions_last_da = new DataAdapter_users(context, subscriptions_last);
                                                                data_target.setAdapter(subscriptions_last_da);
                                                                break;
                                                            case USERS_TAB:
                                                                final List<User> users_last = me.get_last_users();
                                                                final DataAdapter_users users_last_da = new DataAdapter_users(context, users_last);
                                                                data_target.setAdapter(users_last_da);
                                                                break;
                                                        }
                                                    }

                                                    @Override
                                                    public void onTabUnselected(TabLayout.Tab tab) {
                                                    }

                                                    @Override
                                                    public void onTabReselected(TabLayout.Tab tab) {
                                                    }

                                                });

                                            }});
                                    }
                                }});
                            thread.start();

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        return view;
    }

}
