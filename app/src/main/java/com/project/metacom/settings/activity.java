package com.project.metacom.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.project.metacom.R;
import com.project.metacom.data.TopListItem;
import com.project.metacom.toplist.DataAdapter;
import com.project.metacom.toplist.DataReceiver_toplistitem;

import java.io.IOException;
import java.util.ArrayList;


public class activity extends AppCompatActivity {

    private DataAdapter da;
    private DataReceiver_toplistitem dr;

    private TabLayout tabLayout;
    private final int ACTIVITY_TAB = 0;
    private final int SUBSCRIPTIONS_TAB = 1;
    private final int USERS_TAB = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Top chart");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.settings_layout);
        // RelativeLayout topList_layout= (RelativeLayout) findViewById(R.id.rl);
        // topList_layout.addView(cv);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        startActivity(new Intent(com.project.metacom.settings.activity.this, com.project.metacom.toplist.activity.class));
                        break;
                    case R.id.action_activity:
                        startActivity(new Intent(com.project.metacom.settings.activity.this, com.project.metacom.activitylist.activity.class));
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(com.project.metacom.settings.activity.this, com.project.metacom.settings.activity.class));
                        break;
                }
                return true;
            }
        });

    }






}
