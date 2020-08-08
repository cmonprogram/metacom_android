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
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.R;
import com.project.metacom.config;
import com.project.metacom.data.TopListItem;

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
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_toplistitem) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!checkMe()) {
                        checkMe_dialog( activity.this);
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setContentView(R.layout.toplist_layout_site_tab);

            // web view page url
            final String[] page_url = {null};

            // submit dialog
            Button submit_button = (Button) findViewById(R.id.add_toplist_submit);
            submit_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText text_field = (EditText) findViewById(R.id.add_toplist_text);
                    final String text = text_field.getText().toString();
                    final String url = page_url[0];
                    final String room = Base32.toBase32Z(url);

                    Request request = new Request.Builder()
                            .url(config.server + "/metacom/room_info/" + room )
                            .build();

                    new OkHttpClient().newCall(request)
                            .enqueue(new Callback() {
                                         @Override
                                         public void onFailure(final Call call, IOException e) {
                                             // Error
                                         }
                                         @Override
                                         public void onResponse(Call call, final Response response) throws IOException {
                                             String result = response.body().string();

                                             try {
                                                 final JSONObject json = new JSONObject(result);
                                                 if(json.optString("count").equals("0") || json.optString("count").equals("")){

                                                     try {
                                                         WebSocket ws = new WebSocketFactory()
                                                                 .setConnectionTimeout(timeout)
                                                                 .createSocket(server+"/chat/"+room)
                                                                 .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                                                                 .connect();
                                                         ws.sendText("{\"action\": \"post\", \"text\": \"" + text + "\", \"token\": \"" + token + "\"}");
                                                         Intent startIntent = new Intent(activity.this, com.project.metacom.toplist.activity.class);
                                                         startActivity(startIntent);

                                                     } catch (WebSocketException e) {
                                                         e.printStackTrace();
                                                     } ;
                                                 }else{
                                                     new AlertDialog.Builder(activity.this)
                                                             .setTitle("This room already exist")
                                                             .setMessage("Do you want to go there?")

                                                             // Specifying a listener allows you to take an action before dismissing the dialog.
                                                             // The dialog is automatically dismissed when a dialog button is clicked.
                                                             .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                                                                     startIntent.putExtra("go_to", room);
                                                                     startIntent.putExtra("page_title", json.optString("title"));
                                                                     startActivity(startIntent);
                                                                 }
                                                             })
                                                             // A null listener allows the button to dismiss the dialog and take no further action.
                                                             .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     Intent startIntent = new Intent(activity.this, com.project.metacom.toplist.activity.class);
                                                                     startActivity(startIntent);
                                                                 }
                                                             })
                                                             .setIcon(android.R.drawable.ic_dialog_alert)
                                                             .show();
                                                 }

                                             } catch (JSONException e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     });


                }
            });




            // web view settings
            WebViewClient wc = new WebViewClient()
            {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    page_url[0] = url;
                    Log.d("WebView", "your current url when webpage loading.." + url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d("WebView", "your current url when webpage loading.. finish" + url);
                    super.onPageFinished(view, url);
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    // TODO Auto-generated method stub
                    super.onLoadResource(view, url);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            };

            WebView webView = (WebView) findViewById(R.id.toplist_webView);
            webView.setWebViewClient(wc);

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
