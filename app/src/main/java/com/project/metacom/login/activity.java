package com.project.metacom.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.project.metacom.R;
import com.project.metacom.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.project.metacom.config.server;
import static functions.CheckMe.checkMe;
import static functions.CheckMe.reset;

public class activity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view cv = new view(this);
        setContentView(R.layout.login_layout);

        //setTitle("Login");

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        //final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loadingProgressBar.setVisibility(View.VISIBLE);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", usernameEditText.getText().toString())
                        .addFormDataPart("password", passwordEditText.getText().toString())
                        .build();

                final Request request = new Request.Builder()
                        .url(server + "/token")
                        .post(requestBody)
                        .build();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = new OkHttpClient().newCall(request).execute();
                            String result = response.body().string();
                            JSONObject json  = new JSONObject(result);
                            config.token = json.optString("access_token");
                            if(checkMe()){
                                String room = getIntent().getStringExtra("go_to");
                                if(room != null){
                                    Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                                    startIntent.putExtra("go_to", room);
                                    //startIntent.putExtra("page_title", getIntent().getStringExtra("page_title"));
                                    startActivity(startIntent);
                                }else {
                                    Intent startIntent = new Intent(activity.this, com.project.metacom.toplist.activity.class);
                                    startActivity(startIntent);
                                }
                            }else {
                                reset();
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                        new AlertDialog.Builder(activity.this)
                                                .setTitle("Wrong data")
                                                .setMessage("Please try again..")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Continue with operation
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    }
                                };
                                activity.this.runOnUiThread(runnable);
                            }

                                } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                //loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });



    }



    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
