package com.project.metacom.toplist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.project.metacom.R;
import com.project.metacom.data.TopListItem;
import com.project.metacom.toplist.DataAdapter;
import com.project.metacom.toplist.DataReceiver_toplistitem;
import com.project.metacom.toplist.activity;

import java.io.IOException;
import java.util.ArrayList;

import static com.project.metacom.config.me;

public class fragment extends Fragment {
    private AppCompatActivity context;

    private DataAdapter da;
    private DataReceiver_toplistitem dr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.toplist_layout, container, false);
        context = (AppCompatActivity)getActivity();
        context.setTitle("Top chart");
        setHasOptionsMenu(true);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    da = new DataAdapter(context,new ArrayList<TopListItem>());
                    dr = new DataReceiver_toplistitem(da);
                    try { dr.new all().get();  } catch (IOException e) {   e.printStackTrace();   }


                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final ListView data_target = (ListView) view.findViewById(R.id.toplist_view);
                            final LinearLayout go_to_login = (LinearLayout)view.findViewById(R.id.toplist_go_to_login);
                            final Button go_to_login_button = (Button)view.findViewById(R.id.go_to_login);
                            final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.toplist_tabs);

                            final int ALL_TAB = 0;
                            final int MY_TAB = 1;

                            go_to_login_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent startIntent = new Intent(context, com.project.metacom.login.activity.class);
                                    startActivity(startIntent);
                                }
                            });

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
                            data_target.setAdapter(da);




                            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    //viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                                    switch (tab.getPosition()) {
                                        case ALL_TAB:
                                            hide_login(data_target,go_to_login);
                                            try {
                                                dr.new all().get();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case MY_TAB:
                                            Thread thread = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!me.checkMe()) {
                                                        show_login(data_target,go_to_login);
                                                        //checkMe_dialog(activity.this);
                                                    } else {
                                                        hide_login(data_target,go_to_login);
                                                        try {
                                                            dr.new my().get();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }});
                                            thread.start();
                                            break;
                                    }
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {
                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {
                                }

                                private void show_login(final ListView data_target, final LinearLayout go_to_login){
                                    dr.reset();
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            data_target.setVisibility(View.GONE);
                                            go_to_login.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    //getSupportFragmentManager().beginTransaction().add(R.id.toplist_content_frame, fragment).commit();
                                }

                                private void hide_login(final ListView data_target, final LinearLayout go_to_login){
                                    dr.reset();
                                            data_target.setVisibility(View.VISIBLE);
                                            go_to_login.setVisibility(View.GONE);
                                }
                            });


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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toplist_menu, menu);
        final TabLayout tabLayout = (TabLayout) context.findViewById(R.id.toplist_tabs);

        final int ALL_TAB = 0;
        final int MY_TAB = 1;
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    dr.new all().search(query);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    dr.new all().search(newText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item){

                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item){
                try {
                    switch(tabLayout.getSelectedTabPosition()){
                        case ALL_TAB:dr.new all().get();
                        case MY_TAB:dr.new my().get();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_toplistitem) {
            Intent startIntent = new Intent(context, com.project.metacom.web_view.activity.class);
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}