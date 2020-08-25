package com.project.metacom.comments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.R;
import com.project.metacom.config;
import com.project.metacom.data.Comment;
import com.project.metacom.data.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
    public DataReceiver_comment data_receiver_comment;
    public DataReceiver_user data_receiver_user;

    public Room room;
    public void set_room(final Room room){
        this.room = room;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitle(room.title);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        // enable toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.comment_layout);

        try {
        RecyclerView data_target = (RecyclerView) findViewById(R.id.comments);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        data_target.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        data_target.setLayoutManager(layoutManager);

        final DataAdapter data_adapter = new DataAdapter(this);

        data_receiver_comment = new DataReceiver_comment(data_adapter);
        data_receiver_comment.execute(getIntent().getStringExtra("go_to"));
        data_receiver_user = data_receiver_comment.data_receiver_user;
        data_target.setAdapter(data_adapter);

        //this.room = data_receiver_comment.room;


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
                                checkMe_dialog( activity.this);
                                /*
                                Intent startIntent = new Intent(activity.this, com.project.metacom.login.activity.class);
                                startIntent.putExtra("go_to", getIntent().getStringExtra("go_to"));
                                startIntent.putExtra("page_title", getIntent().getStringExtra("page_title"));
                                startActivity(startIntent);
                                 */
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
        } catch (Exception e) {
            setTitle("Room cannot be created");
            e.printStackTrace();
        }
    }

    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

    // toolbar actions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent startIntent = new Intent(activity.this, com.project.metacom.toplist.activity.class);
            startActivity(startIntent);
            //finish(); // close this activity and return to preview activity (if there is any)
        }

        if (id == R.id.action_goout_comment) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(room.url));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



}
