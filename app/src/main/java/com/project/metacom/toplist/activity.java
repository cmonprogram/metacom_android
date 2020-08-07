package com.project.metacom.toplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.metacom.R;
import com.project.metacom.data.TopListItem;

import java.io.IOException;
import java.util.ArrayList;

public class activity extends AppCompatActivity {
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


        final DataAdapter data_adapter = new DataAdapter(this,new ArrayList<TopListItem>());
        ListView data_target = (ListView) findViewById(R.id.toplist_view);

        data_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopListItem item = (TopListItem)data_adapter.getItem(position);
                //Toast.makeText(getBaseContext(),item.chat_room,Toast.LENGTH_SHORT).show();

                Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                startIntent.putExtra("go_to", item.chat_room);
                startIntent.putExtra("page_title", item.page_title);
                startActivity(startIntent);

            }
        });

        DataReceiver_toplistitem data_receiver = new DataReceiver_toplistitem(data_adapter);
        try { data_receiver.execute();  } catch (IOException e) {   e.printStackTrace();   }

        data_target.setAdapter(data_adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toplist_menu, menu);

        // change menu font size
        //for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(0);
            SpannableString spanString = new SpannableString(item.getTitle().toString());
            spanString.setSpan(new RelativeSizeSpan(1.5f), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        //}

        
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_toplistitem) {

            setContentView(R.layout.toplist_layout_site_tab);
            WebView webView = (WebView) findViewById(R.id.toplist_webView);
            webView.setWebViewClient(new WebViewClient());

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);

            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(false);

            webView.loadUrl("https://www.google.com");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
