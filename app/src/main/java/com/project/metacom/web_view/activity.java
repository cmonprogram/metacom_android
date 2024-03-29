package com.project.metacom.web_view;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.R;
import com.project.metacom.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import functions.Base32;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.project.metacom.config.me;
import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;


public class activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Choose content page");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        view cv = new view(this);

        setContentView(R.layout.toplist_layout_site_tab);

        final LinearLayout content_page = (LinearLayout)findViewById(R.id.toplist_webView_content);
        final LinearLayout go_to_login = (LinearLayout)findViewById(R.id.toplist_go_to_login);
        final Button go_to_login_button = (Button)findViewById(R.id.go_to_login);
        go_to_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(activity.this, com.project.metacom.login.activity.class);
                startActivity(startIntent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if (!me.checkMe()) {
                    show_login(content_page,go_to_login);
                } else {

                    hide_login(content_page, go_to_login);
                    // web view page url
                    final String[] page_url = {null};

                    // submit dialog
                    Button submit_button = (Button) findViewById(R.id.add_toplist_submit);
                    submit_button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            //EditText text_field = (EditText) findViewById(R.id.add_toplist_text);
                            //final String text = text_field.getText().toString();
                            final String url = page_url[0];
                            final String room = Base32.toBase32Z(url);
                            Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                            startIntent.putExtra("go_to", room);
                            startActivity(startIntent);
 /*
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
                                        activity.this.runOnUiThread(new Runnable() {
                                            public void run() {
                                                new AlertDialog.Builder(activity.this)
                                                        .setTitle("This room already exist")
                                                        .setMessage("Do you want to go there?")
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
                                        });
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
 */

                        }
                    });

                 activity.this.runOnUiThread(new Runnable() {
                    public void run() {
                    // web view settings
                    WebViewClient wc = new WebViewClient() {
                        private ProgressBar progressBar = (ProgressBar) findViewById(R.id.toplist_progressBar);

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            progressBar.setVisibility(View.VISIBLE);
                            page_url[0] = url;
                            Log.d("WebView", "your current url when webpage loading.." + url);
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            Log.d("WebView", "your current url when webpage loading.. finish" + url);
                            super.onPageFinished(view, url);
                            progressBar.setVisibility(View.GONE);
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
                    webView.getSettings().setSupportZoom(true);

                    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                    webView.setScrollbarFadingEnabled(false);

                    webView.loadUrl("https://www.google.com");
                }});
                }
            }
        });
        thread.start();
    }



    class view extends View {
        public view(Context context) {
            super(context);
        }
    }
    private void show_login(final LinearLayout data_target, final LinearLayout go_to_login){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data_target.setVisibility(View.GONE);
                go_to_login.setVisibility(View.VISIBLE);
            }
        });
        //getSupportFragmentManager().beginTransaction().add(R.id.toplist_content_frame, fragment).commit();
    }

    private void hide_login(final LinearLayout data_target, final LinearLayout go_to_login){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data_target.setVisibility(View.VISIBLE);
                go_to_login.setVisibility(View.GONE);
            }
        });
        //getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
