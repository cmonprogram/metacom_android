package com.project.metacom.toplist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.R;
import com.project.metacom.config;
import com.project.metacom.data.TopListItem;
import com.project.metacom.fragment.go_to_login_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import functions.Base32;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;
import static com.project.metacom.config.token;
import static functions.CheckMe.checkMe;
import static functions.CheckMe.checkMe_dialog;

public class activity extends AppCompatActivity {

    private DataAdapter da;
    private DataReceiver_toplistitem dr;

    private TabLayout tabLayout;
    private final int ALL_TAB = 0;
    private final int MY_TAB = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Top chart");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        view cv = new view(this);
        setContentView(R.layout.toplist_layout);
        // RelativeLayout topList_layout= (RelativeLayout) findViewById(R.id.rl);
        // topList_layout.addView(cv);


        da = new DataAdapter(this,new ArrayList<TopListItem>());
        final ListView data_target = (ListView) findViewById(R.id.toplist_view);
        final LinearLayout go_to_login = (LinearLayout)findViewById(R.id.toplist_go_to_login);
        final Button go_to_login_button = (Button)findViewById(R.id.go_to_login);

        go_to_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(activity.this, com.project.metacom.login.activity.class);
                startActivity(startIntent);
            }
        });

        data_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopListItem item = (TopListItem)da.getItem(position);
                //Toast.makeText(getBaseContext(),item.chat_room,Toast.LENGTH_SHORT).show();

                Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                startIntent.putExtra("go_to", item.chat_room);
                startIntent.putExtra("page_title", item.page_title);
                startActivity(startIntent);

            }
        });

        dr = new DataReceiver_toplistitem(da);
        try { dr.new all().get();  } catch (IOException e) {   e.printStackTrace();   }
        data_target.setAdapter(da);

        tabLayout = (TabLayout) findViewById(R.id.toplist_tabs);
        //tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

        //Implementing tab selected listener over tablayout
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
                                if (!checkMe()) {
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
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toplist_menu, menu);

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

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_toplistitem) {
            Intent startIntent = new Intent(activity.this, com.project.metacom.web_view.activity.class);
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void show_login(final ListView data_target, final LinearLayout go_to_login){
        dr.reset();
        runOnUiThread(new Runnable() {
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data_target.setVisibility(View.VISIBLE);
                go_to_login.setVisibility(View.GONE);
            }
        });
        //getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
