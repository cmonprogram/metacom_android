package com.project.metacom.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.metacom.R;
import com.project.metacom.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.project.metacom.config.token;
import static functions.CheckMe.checkMe;

public class activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*

        final String[] title = {getIntent().getStringExtra("go_to")};
        Request request = new Request.Builder()
                .url(config.server + "/metacom/room_info/" + getIntent().getStringExtra("go_to") )
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
                                 JSONObject json = null;
                                 JSONArray jArray = null;
                                 try {
                                     json = new JSONObject(result);
                                     title[0] = json.optString("title");

                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         });
*/

        setTitle(getIntent().getStringExtra("page_title"));

        setContentView(R.layout.comment_layout);
        RecyclerView data_target = (RecyclerView) findViewById(R.id.comments);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        data_target.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        data_target.setLayoutManager(layoutManager);

        final DataAdapter data_adapter = new DataAdapter(this);
        data_target.setAdapter(data_adapter);

        final DataReceiver_comment data_receiver_comment = new DataReceiver_comment(data_adapter);
        data_receiver_comment.execute(getIntent().getStringExtra("go_to"));

        //final DataReceiver_user data_receiver_user = new DataReceiver_user(data_adapter);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // enable toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // input event
        TextInputEditText editText = (TextInputEditText) findViewById(R.id.send);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                final boolean[] handled = {false};
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            if(checkMe()) {
                                data_receiver_comment.ws.sendText("{\"action\": \"post\", \"text\": \"" + v.getText().toString() + "\", \"token\": \"" + token + "\"}");
                                handled[0] = true;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        v.setText("");
                                    }
                                });

                            }else{
                                Intent startIntent = new Intent(activity.this, com.project.metacom.login.activity.class);
                                startIntent.putExtra("go_to", getIntent().getStringExtra("go_to"));
                                startIntent.putExtra("page_title", getIntent().getStringExtra("page_title"));
                                startActivity(startIntent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                }

                return handled[0];
            }
        });
        /*
        data_target.setOnClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment item = (Comment)data_adapter.getItem(position);
                Toast.makeText(getBaseContext(),item.url,Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

    // toolbar actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent startIntent = new Intent(com.project.metacom.comments.activity.this, com.project.metacom.toplist.activity.class);
            startActivity(startIntent);
            //finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
